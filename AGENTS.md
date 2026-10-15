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

## GitHub Actions: reguły dla agentów AI

- **Manual-first:** wywołuj `workflow_dispatch` wyłącznie z `main`. Nie uruchamiaj CI w GitHub Actions dla gałęzi roboczych ani dla PR; testuj lokalnie.
- Każdy job wymaga `github.ref == 'refs/heads/main'`; nie zmieniaj na `push` na innych gałęziach ani tagach, `pull_request`, `workflow_run` czy `repository_dispatch`.
- Wyjątek automatyczny dopiero po ocenie budżetu: `push` wyłącznie `branches: [main]` z filtrem `paths` i uzasadnieniem; nie testuj sprzętu badawczego na ślepo.
- W formularzu Run workflow zawsze wybieraj `main` (stare definicje na innych gałęziach mogą nie być zabezpieczone).

## GitHub Actions execution policy (mandatory for AI agents)

1. **Manual by default.** Use `workflow_dispatch` for costly CI, model training, publications, Android builds, manuscript compilation, GPU jobs, benchmarks and research experiments. Do not add automatic runs unless explicitly justified.
2. Automatic workflows, where necessary, may run only on `push` to `main`, with `branches: [main]` and narrow `paths` filters where possible. Never use `pull_request`, `pull_request_target`, `merge_group`, or feature-branch push triggers.
3. Every job must have an effective `github.ref == 'refs/heads/main'` condition, including manual jobs. Retain all current approval inputs, disabled gates, permissions, runner labels, concurrency and secrets.
4. Preserve essential scheduled backups, critical service monitoring and scheduled maintenance after checking their purpose. They operate on the default `main` branch; do not silently disable them.
5. Run tests locally on development branches; GitHub Actions verification happens on `main`. Agents must not trigger Actions on feature branches. GitHub may record a skipped manual run, and old branch copies of workflows may not carry the new guards.
6. Before introducing or changing a workflow, inventory existing YAML files, verify triggers and job guards, and document any automatic-run exception. Check branch protection requirements when removing PR checks. Never claim successful CI without inspecting the real run.
