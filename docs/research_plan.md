# Research plan

Status: protocol and implementation definition. No human validation data have been collected in this repository.

## Intended contribution

The first paper is planned as a measurement/engineering validation of on-device smartphone gaze estimation. The contribution is not the existence of an Android UI, but a reproducible characterization of calibration, screen-space error, failure modes and device/context sensitivity under a frozen pipeline.

Working publication label: **Mobile gaze measurement validation**.

## Research questions

- **RQ1 Calibration:** How repeatable is participant-specific calibration across repeated calibration runs on the same device and session?
- **RQ2 Accuracy and precision:** What screen-space angular/pixel error and sample dispersion are observed on held-out fixation targets under the frozen controlled protocol?
- **RQ3 Robustness:** How do prespecified changes in viewing distance, head pose and illumination affect error, missingness and quality-gate failures?
- **RQ4 Device effects:** How much performance varies across supported Android device/runtime classes?
- **RQ5 External agreement:** In a separately designed reference subset, what agreement is observed against a laboratory/reference eye tracker, if such hardware is available?

RQ5 is conditional. Internal screen-target evaluation must not be described as proof of equivalence to a laboratory eye tracker.

## Evidence classes

1. software: unit/integration tests and deterministic replay only;
2. engineering_target: instructed fixation targets under controlled conditions;
3. reference_comparison: simultaneous or appropriately synchronized external eye-tracker measurements;
4. ecological: later naturalistic use, only after controlled validity is established.

Do not promote a lower evidence class into a stronger claim.

## Experiment sequence

### E00 - implementation qualification

No participants required where synthetic/recorded fixtures suffice.

Exit:
- camera path implemented;
- estimator and calibration versions identifiable;
- coordinate transforms tested;
- invalid/low-quality states explicit;
- export schema versioned;
- no raw video retained by default.

### E01 - controlled calibration and held-out targets

Purpose: estimate repeatability, error, precision, missingness and failure rate on predefined screen targets.

Freeze before collection:
- target geometry;
- target order/randomization;
- calibration procedure;
- viewing-distance range;
- device orientation;
- acceptable head-pose envelope;
- quality exclusions;
- primary error metrics;
- stopping/repetition rule.

### E02 - robustness perturbations

Prespecified conditions may include viewing-distance bands, head-pose bands and illumination bands. Conditions must be frozen before outcome inspection.

### E03 - cross-device qualification

Use a device sample representing the intended deployment range. Device model is engineering provenance, not a participant identity.

### E04 - external-reference comparison

Conditional on access to a validated reference system and a separate synchronization/analysis protocol.

## Primary measurement principles

- Report both accuracy and precision.
- Preserve failed/invalid samples and reasons; do not delete failures to improve mean error.
- Separate calibration targets from held-out evaluation targets.
- Do not tune the estimator on the final held-out evaluation set.
- Report per-participant and per-device distributions before pooled summaries.
- Record timing and coordinate-frame definitions explicitly.

## Progression gates

The project may move from implementation to participant-facing E01 only when the implementation and measurement contract are versioned, privacy/ethics requirements are resolved for the actual protocol, and synthetic/integration validation passes.

Publication claims remain limited to the evidence actually collected.
