# Future research and publication proposals | ARC Mobile Eye Tracking

**2026-10-09 · conditional opportunity register, not an active manuscript or research approval.** Scientific methods, protocol freezes, data/ethics provenance and claim ownership are governed by `PROJECT_STATE.yaml` and the canonical project files. Novelty has not undergone a systematic audit. Prefer simulation, lawful secondary datasets and own devices before human recruitment, without downgrading evidence quality.

## Existing publication
P01 is planned as the research-grade characterization of **on-device Android gaze estimation** (E00 implementation, E01 calibration/held-out targets, E02 robustness, E03 device effects and conditional E04 external tracker reference). Pipeline and human validity evidence are not yet implemented/collected. See [research plan](../docs/research_plan.md), [measurement contract](../docs/measurement_contract.md) and [low-cost strategy](../docs/LOW_COST_EVIDENCE_STRATEGY.md).

## F-GAZE-01: Quality-gated mobile gaze estimation under domain shift

**Working title:** *Quality-Gated Smartphone Gaze Estimation Under Device and Environmental Shift*.
**Priority:** medium; strong risk of overlap with P01 RQ3/RQ4.

**Research question:** Can a calibrated real-time abstention/quality-gating policy reduce the fraction of high-error reported gaze points under device, illumination, head pose and distance shift, while retaining useful data coverage?

**Cheapest method:** first deterministic/synthetic calibration, geometry and quality-state tests; then small *adequately designed* controlled target-fixation sessions with participant-owned/reference Android devices under the existing E01/E02 governance route. Reuse legitimately obtained, consented recordings only if calibration/held-out conditions and rights match; no open-ended online attitude survey.

**Independent endpoints:** risk–coverage and selective prediction curves, calibration/conditional error quantiles, abstention rates, per-device and held-out-participant transfer, false 'valid' output under shift. Compare naive confidence threshold, temperature/isotonic calibration where appropriate and device-aware baselines. Maintain disjoint tuning and held-out target/participant/device splits.

**Distinctness:** P01 already measures calibration accuracy and robustness. A *new* paper is warranted only for a **new transferable selective-prediction/quality-gate algorithm** with generalization across devices, not another descriptive accuracy/robustness table.

**GO:** implemented gaze pipeline, real reference/target validation, independent held-out devices/users, meaningful method novelty beyond baseline quality flags. **NO-GO:** no measured gaze points, only synthetic coordinate behavior, or changes fully subsumed under P01.

**TODO**
- [ ] P0 Finish E00 estimator, calibration and export, deterministic tests and data dictionary; no claims from UI.
- [ ] P0 Research selective prediction, uncertainty calibration, smartphone gaze cross-device/domain shift and gaze validity; document exact missing problem.
- [ ] P1 Define prospective E01/E02 outcomes and privacy-preserving reference protocol; simulate performance/precision.
- [ ] P1 Assess whether abstention is genuinely novel or belongs as a P01 section.
- [ ] P2 Only after gate approval, collect minimal valid target observations and independent validation.

## F-GAZE-02: General multimodal timestamp alignment (joint methods watch list)

Shared candidate with [HRI Safety](https://github.com/MatPomGit/arc-hri-safety) and [ARC-ADHD](https://github.com/MatPomGit/arc-adhd-game) to compare uncertainty in ROS/Android/camera/EEG clocks under dropped samples and jitter.

**GO:** genuinely independent, broadly applicable alignment method with real time-reference checks. **NO-GO:** device-specific synchronization already owned by ARC-ADHD S01 or no new inference beyond existing contracts.

- [ ] P0 Map ownership relative to ARC-ADHD S01 and HRI time-calibration work.
- [ ] P1 Build only simulation-ground-truth prototype at first; no personal camera records.
- [ ] P1 Promote to manuscript only if independent algorithms and cross-device evidence exist.

## Portfolio decision
Prioritize credible **P01 measurement validity** first; creating a second manuscript before a functioning gaze estimator would inflate the publication plan without evidence.
