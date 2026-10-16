# E00 software qualification, v0.1

Date: 2026-10-16
Task: #8 / ET-E00-TEST
Evidence class: **software/synthetic only**
Source baseline: `main@03a58558427ffb20e360fc4adb92a682f2f2a0c6`
Implementation branch: `research/e00-software-qualification-issue-8`
Status: **NOT QUALIFIED; implementation and runtime verification pending**

## Scope implemented in this change

Six new JVM/JUnit4 tests cover deterministic synthetic timestamp-to-estimate replay via a stub `GazeEstimator`, a stub identity `CalibrationModel`, negative status representation, numeric/boundary input rejection, calibration metadata invariants, a fixed-seed 10,000-iteration metric property check, and the distinction between invalid samples and genuine screen origin. The fixtures are entirely synthetic. They do **not** exercise Android CameraX/camera frames, an actual estimator, fitted calibration or research export, none of which is present in the inspected baseline.

## Reproducibility command and exact execution status

```bash
git checkout research/e00-software-qualification-issue-8
./gradlew --no-daemon testDebugUnitTest --tests 'com.edu.eyetracking.measurement.E00SoftwareQualificationTest'
./gradlew --no-daemon testDebugUnitTest
```

These commands are **specified, not executed** as part of this connector-only source qualification. Neither a passing Gradle result nor a clean-build result has been observed. No CI or self-hosted runner has been triggered.

Environment: source inspected through authenticated GitHub repository connector; no checked-out Android SDK/JDK/Gradle runtime. Expected JUnit 4.13.2 per `gradle/libs.versions.toml`; baseline Gradle wrapper 9.3.1. Execution environment and observed pass/fail counts: **not available**.

## Software gaps and gates

1. No real image/frame acquisition, timestamp delivery or orientation/mirroring transform.
2. No actual gaze estimator. The deterministic replay stub only tests the contract.
3. No fitted calibration, mapping diagnostics or disjoint target-set enforcement.
4. No versioned derived-only export schema, encoder/decoder, round-trip or schema rejection tests.
5. No cross-module real pipeline/integration test or end-to-end camera-to-export replay.
6. Existing `GazeSample` permits an invalid status with a non-null point, so consumers must not assume invalid samples have no coordinate; tightening this requires an explicit compatibility decision and additional tests.
7. Coordinate-frame identity and provenance are described in `docs/measurement_contract.md` but not fully represented by the current `GazeSample` type. Monotonic sequencing across samples is likewise not enforced by the type.
8. Need locally executed clean-build proof, failure logs, Java/Android SDK/Gradle versions, head SHA and QA results before E00 can be closed.

## Decision and evidence ledger

- Decision: retain software-only evidence classification; do not report synthetic geometry as gaze accuracy or empirical precision.
- Decision: no changes to E01 ethics and physical-measurement gates; issues #9 and #10 remain blocked.
- Negative finding: no implemented camera, estimator, calibration, or export path in `MainActivity.kt` and the inspected measurement module.
- Risk: synthetic tests may pass although no usable gaze measurement is produced.
- Progression: **NO-GO** for participant-facing E01 or research performance claims until E00 implementation qualification, approved E01 protocol and applicable ethics/privacy route.

Related: `docs/research_plan.md`, `docs/measurement_contract.md`, issues #3, #8, #9, #10.
