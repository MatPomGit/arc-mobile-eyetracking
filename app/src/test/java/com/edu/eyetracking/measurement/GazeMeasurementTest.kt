package com.edu.eyetracking.measurement

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class GazeMeasurementTest {
    @Test
    fun normalizedErrorIsZeroForIdenticalPoints() {
        val point = NormalizedPoint(0.25, 0.75)
        assertEquals(0.0, normalizedEuclideanError(point, point), 1e-12)
    }

    @Test(expected = IllegalArgumentException::class)
    fun normalizedPointRejectsOutOfRangeCoordinates() {
        NormalizedPoint(-0.01, 0.5)
    }

    @Test(expected = IllegalArgumentException::class)
    fun validSampleRequiresPoint() {
        GazeSample(
            monotonicNanos = 1,
            point = null,
            confidence = 0.9,
            status = GazeStatus.VALID,
            calibrationId = "cal-1",
        )
    }

    @Test
    fun invalidSampleCanRepresentTrackingLossWithoutFakeCoordinates() {
        val sample = GazeSample(
            monotonicNanos = 1,
            point = null,
            confidence = null,
            status = GazeStatus.FACE_NOT_FOUND,
            calibrationId = "cal-1",
        )
        assertNull(sample.point)
    }
}
