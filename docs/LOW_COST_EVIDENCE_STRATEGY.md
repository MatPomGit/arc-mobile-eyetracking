# Low-cost evidence strategy — 2026-10-09

**Status: planning guidance only.** This document supplements, but does not supersede, `PROJECT_STATE.yaml`, active protocols, preregistrations, ethics and safety gates. It does **not** authorize recruiting people, alter locked estimands, assert collection, or create publication evidence.

## Selection rule
Prefer the **lowest-cost method that can answer the *specific* primary research question with sufficient validity and precision**:
1. Formal theory, literature verification, reproducible simulation and code testing.
2. Legitimately reusable public/secondary datasets with provenance, lawful rights and matched constructs.
3. Measurements with existing hardware, within technical and physical safety rules.
4. Remote questionnaires, randomized online vignettes or phone-based ESM **only when the intended estimand requires human reports/choices**.
5. On-site participant work **only when embodiment, physical interaction, clinical observation or ground truth cannot be replaced validly**.

Free software is not free research: recruitment, attrition, respondent bias, ethics, privacy, reliability, accessible delivery and statistical power all impose effort. Small pilot != independent validation or confirmatory evidence. No sample size/response scale should be chosen solely to fit a budget. Report no-go/defer when an adequately powered design cannot be resourced.

## Cost controls across the programme
- Run synthetic pipeline, sample-size simulation, provenance and instrument/source audit before recruitment.
- Reuse verified, authorized pre-existing data where valid, keeping exploratory and confirmatory claims distinct.
- Use participant-owned devices and **institutionally approved** digital forms only when acceptable for privacy and study needs. Do not assume a public Google Form is automatically compliant.
- Minimize redundant questionnaires; retain measures necessary for distinguishing competing hypotheses and record actual survey burden.
- Separate identities, consent and analysis identifiers; never commit personal data, contact lists or secrets to Git.
- Avoid coerced participation by students the investigator teaches or assesses; document voluntary sampling and external validity limits.
- A study requiring human participants remains subject to appropriate institutional/ethics determination regardless of venue or payment.

## P01: technical validation, not population attitude research
This manuscript's claims concern actual Android gaze estimation, calibration, accuracy and robustness. Prefer deterministic geometry tests and existing hardware, then accurately timed fixation-target data with **external reference**. A browser questionnaire can only assess usability or comfort, not point-of-gaze error, dropout, drift or calibration transfer. No need to recruit for attitude surveys.

- [ ] P0: finish camera/estimator/calibration/export and test using synthetic fixtures.
- [ ] P0: freeze E01 metrics, target schedule, device conditions, repeat policy and applicable ethics route.
- [ ] P1: collect **minimal adequately designed** valid participant fixation sessions on existing devices/reference hardware when approved; report precision/accuracy and failure strata.
- [ ] P2: optional very short usability survey only after real measurement task, with separate subjective claims.
