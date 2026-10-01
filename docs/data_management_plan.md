# Data management plan

## Data classes

- camera frames: transient processing input by default;
- derived gaze samples: research measurement output;
- calibration records: versioned measurement metadata;
- device profile: engineering provenance, excluding direct hardware identifiers;
- protocol/target metadata: versioned research configuration;
- analysis outputs: derived result artifacts;
- optional external-reference data: governed separately when such validation is performed.

## Privacy boundary

Raw face/room video is not retained or uploaded by default. IMEI, Android ID, advertising ID, MAC address and hardware serial are not participant identifiers.

Human-study exports use pseudonymous participant/session IDs and include only fields necessary for the frozen research purpose.

## Integrity

Raw research exports referenced by an analysis are immutable. Corrections create documented derived versions. Missing/invalid gaze samples and technical failures are retained with reason/status codes.

Every reportable dataset/result bundle is registered in `research/evidence_registry.yaml` with source commit, app/estimator/calibration/protocol versions and an immutable identifier/checksum.

## Storage, retention and withdrawal

Before human E01 collection, the study must freeze authoritative storage location, access roles, retention, backup/restore, withdrawal/deletion behavior and any external transfer. These are governance decisions and must not be invented by code.

## Public release

Release only aggregated or sufficiently de-identified derived data permitted by the applicable participant/governance materials. Audit timestamps, free text, file paths, device metadata and raw media for indirect-identification risk.
