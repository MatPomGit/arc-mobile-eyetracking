package com.edu.eyetracking.measurement

import kotlin.math.sqrt

enum class GazeStatus {
    VALID,
    FACE_NOT_FOUND,
    EYES_NOT_FOUND,
    LOW_CONFIDENCE,
    CALIBRATION_INVALID,
    OUT_OF_FRAME,
    INTERNAL_ERROR,
}

data class NormalizedPoint(
    val x: Double,
    val y: Double,
) {
    init {
        require(x.isFinite() && y.isFinite()) { "Coordinates must be finite." }
        require(x in 0.0..1.0 && y in 0.0..1.0) {
            "Normalized coordinates must lie in [0, 1]."
        }
    }
}

data class GazeSample(
    val monotonicNanos: Long,
    val point: NormalizedPoint?,
    val confidence: Double?,
    val status: GazeStatus,
    val calibrationId: String?,
) {
    init {
        require(monotonicNanos >= 0) { "Monotonic time cannot be negative." }
        require(confidence == null || (confidence.isFinite() && confidence in 0.0..1.0)) {
            "Confidence must be null or lie in [0, 1]."
        }
        require(status != GazeStatus.VALID || point != null) {
            "A VALID sample must contain a gaze point."
        }
        require(status != GazeStatus.VALID || !calibrationId.isNullOrBlank()) {
            "A VALID sample must identify the calibration used."
        }
    }
}

data class CalibrationTarget(
    val id: String,
    val point: NormalizedPoint,
)

data class CalibrationRecord(
    val calibrationId: String,
    val algorithmVersion: String,
    val targetSetVersion: String,
    val acceptedSampleCount: Int,
    val rejectedSampleCount: Int,
) {
    init {
        require(calibrationId.isNotBlank())
        require(algorithmVersion.isNotBlank())
        require(targetSetVersion.isNotBlank())
        require(acceptedSampleCount >= 0 && rejectedSampleCount >= 0)
    }
}

interface GazeEstimator {
    val estimatorVersion: String

    fun estimate(frameTimestampNanos: Long): GazeSample
}

interface CalibrationModel {
    val calibrationId: String

    fun map(rawPoint: NormalizedPoint): NormalizedPoint
}

fun normalizedEuclideanError(
    observed: NormalizedPoint,
    target: NormalizedPoint,
): Double {
    val dx = observed.x - target.x
    val dy = observed.y - target.y
    return sqrt(dx * dx + dy * dy)
}
