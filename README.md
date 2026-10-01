# ARC Mobile Eye Tracking

Research and engineering repository for evaluating whether a commodity Android device can provide a reproducible screen-space gaze measurement after explicit calibration.

## Current implementation status

The current Android application is a **UI scaffold only**. It contains navigation for modules, calibration and measurements, but it does not yet implement a camera acquisition pipeline, face/eye landmark estimation, gaze estimation, calibration fitting, quality control or research-grade export. The UI must not be treated as evidence that eye tracking works.

## Scientific scope

The first publication line is a measurement/engineering validation study, not a clinical or diagnostic study.

Primary question:

> Under controlled use, how accurately and repeatably can an on-device Android gaze-estimation pipeline recover instructed screen fixation targets after participant-specific calibration, and how do device, head-pose and illumination conditions affect error and failure rate?

Planned evaluation separates software correctness, calibration repeatability, within-device accuracy/precision, robustness, cross-device heterogeneity and optional agreement with an external reference eye tracker.

The repository does not currently claim validated accuracy, psychometric validity, diagnostic value or equivalence to a laboratory eye tracker.

## Research sources of truth

- PROJECT_STATE.yaml: current phase, blockers and next actions.
- docs/research_plan.md: scientific questions, experiments and progression gates.
- docs/measurement_contract.md: required measurements, units, provenance and data boundaries.
- app/: Android software.

## Privacy boundary

The intended default architecture is local camera processing. Raw face/room video is not a research outcome and should not be retained by default. Research exports should contain pseudonymous run/device identifiers, calibration/quality metadata and derived gaze measurements needed by the protocol.

## Next implementation gate

Before participant-facing validation:

- choose and version the gaze-estimation implementation;
- implement camera/landmark/gaze/calibration pipelines;
- implement explicit quality/failure states;
- add deterministic synthetic/unit tests for geometry and export;
- freeze the first engineering benchmark protocol;
- complete the applicable ethics/privacy route for any human validation.
