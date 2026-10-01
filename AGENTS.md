# Research agent instructions

This repository is a research programme for validating on-device Android gaze measurement.

## Evidence discipline

- Do not infer eye-tracking validity from a working UI, Android build, camera preview or estimator output.
- Keep software/synthetic validation, controlled human target validation and external-reference validation as distinct evidence classes.
- Never invent gaze accuracy, participant counts, ethics approvals, calibration quality or device support.
- A missing/invalid gaze sample is an explicit outcome, never a fake coordinate.
- Do not tune an estimator or calibration rule on the final held-out evaluation targets.
- Record material methodological decisions, deviations, risks, literature searches and null/negative findings under `research/`.
- Register reportable evidence before using it in publication claims.
- Generate manuscript numbers from versioned analysis outputs where possible.
- Raw face/room video is not retained or exported by default.

Before research changes, read `PROJECT_STATE.yaml`, `research/quality_manifest.yaml`, `docs/research_plan.md` and `docs/measurement_contract.md`.
