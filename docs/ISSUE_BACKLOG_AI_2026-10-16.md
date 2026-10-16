# Proposed GitHub Issues: ARC Mobile Eye Tracking (2026-10-16)

**Actual blocker:** Issues creation returned GitHub HTTP 410, `Issues has been disabled in this repository`. These are unpublished, reviewable issue drafts. Do not assume GitHub issue numbers until migrated. Current state is protocol-only, with no camera/estimator/calibration or validation data.

Read `AGENTS.md`, `PROJECT_STATE.yaml`, `docs/measurement_contract.md`, `docs/research_plan.md`, `docs/LOW_COST_EVIDENCE_STRATEGY.md`.

## [P0] P01-CORE: real camera/estimator/calibration contract

<!-- agent-work-classification:v1 -->
work_types: [software, testing, research]
execution_mode: autonomous_with_runtime
human_approval_required: false
human_approval_reasons: []
resource_requirements: [github, standard_compute]
autonomous_scope: "Implement typed camera acquisition, frame timestamps, estimator adapter and gaze quality state with deterministic offline validation."
human_required_scope: "none; real physical device qualification is separate."
<!-- /agent-work-classification -->

- [ ] Audit current Android scaffold and typed measurement contract.
- [ ] Implement acquisition → estimator → calibration → quality/missingness flow; never emit fabricated coordinates.
- [ ] Unit/integration tests for timing, geometry, low confidence, no face, lifecycle and camera permission failure.
**DoD:** tested end-to-end code and truthful UI statuses, no claim of measured accuracy. **Blocks:** physical device qualification and E01 validation.

## [P0] P01-EXPORT: explicit privacy-safe research export and provenance

<!-- agent-work-classification:v1 -->
work_types: [software, data, security, testing]
execution_mode: autonomous
human_approval_required: false
human_approval_reasons: []
resource_requirements: [github, standard_compute]
autonomous_scope: "Implement consent-aware local research export, quality flags, schema validation and deterministic negative tests."
human_required_scope: "none; release or participant data collection requires separate governance."
<!-- /agent-work-classification -->

- [ ] Define minimal versioned output: target/time/device/build/calibration/status, units and missingness reason.
- [ ] Raw face or room video remains off by default; test denial and app restarts.
- [ ] Verify immutable source/build SHA provenance and reproducible parsing.
**DoD:** validated export fixtures and failure cases, no real participant-level data in Git. **Related:** P01-CORE.

## [P0] P01-E01: prospective human gaze-target validation readiness

<!-- agent-work-classification:v1 -->
work_types: [research, documentation, approval, external_communication]
execution_mode: mixed
human_approval_required: true
human_approval_reasons: [legal_or_compliance, external_communication, personal_decision]
resource_requirements: [github, standard_compute]
autonomous_scope: "Define fixation targets, holdout split, conditions, exclusions, dropout handling and interval precision simulation without human data."
human_required_scope: "Resolve institutional ethics/consent before recruitment, approve target protocol and recruitment independently."
<!-- /agent-work-classification -->

- [ ] Predefine geometric target accuracy, precision, robustness, missingness, calibration and device-stratified estimands.
- [ ] Run simulation and verify target leakage safeguards; document stop/go limits.
- [ ] Human confirms authorized ethics and independent device/operator availability before data collection.
**DoD:** frozen E01 protocol and real governance status; no claims of empirical gaze validity. **Depends on:** P01-CORE and P01-EXPORT.

## [P1] P01-DEVICES: Android timing/thermal/camera qualification and reference comparison

<!-- agent-work-classification:v1 -->
work_types: [testing, hardware, research]
execution_mode: mixed
human_approval_required: true
human_approval_reasons: [physical_access, personal_decision]
resource_requirements: [github, physical_device, standard_compute]
autonomous_scope: "Prepare nonintrusive instrumentation, device matrix, benchmark scripts and analysis."
human_required_scope: "Select and operate real smartphones/reference eye tracker; explicitly approve any additional human measurement protocol."
<!-- /agent-work-classification -->

- [ ] Verify runtime range, camera cadence, timestamps, thermals, memory, lifecycle and quality-state integrity on real phone(s).
- [ ] Establish reference-device sync and independent held-out comparison only with approved data.
- [ ] Record build/devices/calibration provenance, outliers and failed test attempts.
**DoD:** empirical engineering device qualification only for actually tested devices; not population eye-tracking validation. **Depends on:** P01-CORE; E01 requires governance first.

## [P1] P01-MANUSCRIPT: literature, measurement-validity and generated-results readiness

<!-- agent-work-classification:v1 -->
work_types: [research, documentation]
execution_mode: autonomous_with_runtime
human_approval_required: false
human_approval_reasons: []
resource_requirements: [github, internet_access]
autonomous_scope: "Verify gaze-estimation and measurement-validity prior art, claim ledger and reproducible manuscript figure generation."
human_required_scope: "none until author publication decision."
<!-- /agent-work-classification -->

- [ ] Compare accuracy/precision/calibration/missingness definitions and report relevant validation threats.
- [ ] Ensure device-transfer claims and clinical interpretations require real external evidence.
- [ ] Prepare tables/figures from versioned evidence only, never hand-made numerical Results.
**DoD:** source-verified literature matrix and evidence-calibrated manuscript draft; no submission without consent.

Suggested migration labels: `priority:P0/P1`, `ai:autonomous`, `ai:runtime`, `ai:mixed`, `human:physical`, `human:ethics`, `work:software`, `work:data`, `work:hardware`.
