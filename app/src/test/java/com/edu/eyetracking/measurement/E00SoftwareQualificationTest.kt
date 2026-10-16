package com.edu.eyetracking.measurement

import org.junit.Assert.*
import org.junit.Test
import java.util.Random
import kotlin.math.sqrt

/**
 * Synthetic, device-free E00 qualification of the currently implemented measurement contract.
 * No real camera/estimator/calibration/export pipeline is exercised here.
 */
class E00SoftwareQualificationTest {
    private val identityCalibration = object : CalibrationModel {
        override val calibrationId = "synthetic-cal-v1"
        override fun map(rawPoint: NormalizedPoint) = rawPoint
    }

    private val replayEstimator = object : GazeEstimator {
        override val estimatorVersion = "fixture-v1"
        override fun estimate(frameTimestampNanos: Long) = GazeSample(
            monotonicNanos = frameTimestampNanos,
            point = NormalizedPoint(0.25, 0.75),
            confidence = 1.0,
            status = GazeStatus.VALID,
            calibrationId = identityCalibration.calibrationId,
        )
    }

    @Test fun deterministicSyntheticFrameToEstimateAndCalibrationReplay() {
        val timestamps = listOf(0L, 1L, 1_000_000L, 9_999_999_999L)
        fun replay() = timestamps.map { timestamp ->
            val estimated = replayEstimator.estimate(timestamp)
            estimated.copy(point = identityCalibration.map(estimated.point!!))
        }
        assertEquals(replay(), replay())
        assertEquals(timestamps, replay().map { it.monotonicNanos })
        assertTrue(replay().all { it.status == GazeStatus.VALID && it.calibrationId == "synthetic-cal-v1" })
        assertTrue(replay().all { it.point == NormalizedPoint(0.25, 0.75) })
    }

    @Test fun invalidStatusIsRepresentableWithoutInventedCoordinates() {
        for (status in GazeStatus.entries.filter { it != GazeStatus.VALID }) {
            val sample = GazeSample(7L, null, null, status, null)
            assertNull(sample.point)
            assertNull(sample.confidence)
            assertEquals(status, sample.status)
        }
    }

    @Test fun invalidNumericAndBoundaryCoordinatesAreRejected() {
        val bad = listOf(Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, -0.001, 1.001)
        for (coordinate in bad) {
            assertThrows(IllegalArgumentException::class.java) { NormalizedPoint(coordinate, 0.5) }
            assertThrows(IllegalArgumentException::class.java) { NormalizedPoint(0.5, coordinate) }
        }
        for (coordinate in listOf(0.0, 1.0)) {
            assertEquals(coordinate, NormalizedPoint(coordinate, coordinate).x, 0.0)
        }
    }

    @Test fun timestampConfidenceAndCalibrationIdentifiersAreConstrained() {
        assertThrows(IllegalArgumentException::class.java) {
            GazeSample(-1, null, null, GazeStatus.FACE_NOT_FOUND, null)
        }
        for (confidence in listOf(Double.NaN, Double.POSITIVE_INFINITY, -0.01, 1.01)) {
            assertThrows(IllegalArgumentException::class.java) {
                GazeSample(1L, null, confidence, GazeStatus.LOW_CONFIDENCE, null)
            }
        }
        for (identifier in listOf(null, "", " ")) {
            assertThrows(IllegalArgumentException::class.java) {
                GazeSample(1, NormalizedPoint(0.5, 0.5), 0.5, GazeStatus.VALID, identifier)
            }
        }
        assertThrows(IllegalArgumentException::class.java) {
            CalibrationRecord("", "algorithm-v1", "targets-v1", 0, 0)
        }
        assertThrows(IllegalArgumentException::class.java) {
            CalibrationRecord("cal-v1", "algorithm-v1", "targets-v1", -1, 0)
        }
        assertThrows(IllegalArgumentException::class.java) {
            CalibrationRecord("cal-v1", "algorithm-v1", "targets-v1", 0, -1)
        }
    }

    @Test fun seededPropertyCheckNormalizedDistanceSymmetryIdentityAndRange() {
        val random = Random(0xE00L)
        repeat(10_000) {
            val a = NormalizedPoint(random.nextDouble(), random.nextDouble())
            val b = NormalizedPoint(random.nextDouble(), random.nextDouble())
            val distance = normalizedEuclideanError(a, b)
            assertEquals(0.0, normalizedEuclideanError(a, a), 0.0)
            assertEquals(distance, normalizedEuclideanError(b, a), 1e-12)
            assertTrue("Distance outside [0, sqrt(2)]", distance >= 0.0 && distance <= sqrt(2.0))
            assertTrue(distance.isFinite())
        }
        assertEquals(sqrt(2.0), normalizedEuclideanError(NormalizedPoint(0.0, 0.0),
            NormalizedPoint(1.0, 1.0)), 1e-12)
    }

    @Test fun invalidSamplesAreNotSilentlyConvertedToOriginBySyntheticReplay() {
        val lost = GazeSample(100, null, null, GazeStatus.EYES_NOT_FOUND, null)
        assertNull(lost.point)
        val realOrigin = GazeSample(101, NormalizedPoint(0.0, 0.0), 1.0, GazeStatus.VALID, "cal-v1")
        assertNotEquals(lost.point, realOrigin.point)
    }
}
