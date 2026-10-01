# Measurement contract

Status: pre-data contract for the mobile gaze measurement programme.

## Coordinate systems

Every gaze sample must identify its coordinate frame. At minimum distinguish camera/image coordinates, normalized screen coordinates, physical screen coordinates when device geometry is known, and target coordinates used for calibration/evaluation.

Never mix portrait/landscape or front-camera mirror conventions implicitly.

## Required provenance

Every reportable run must identify:

- pseudonymous participant code;
- random run/session ID;
- engineering device profile ID;
- Android/API version;
- app version and Git commit;
- estimator/model version;
- calibration algorithm/configuration version;
- screen dimensions and orientation;
- protocol and target-set versions;
- timestamps and monotonic timing domain.

Do not derive participant identifiers from IMEI, serial number, Android ID, advertising ID or MAC address.

## Sample fields

A derived gaze sample should be able to represent monotonic timestamp, estimated x/y, estimator quality/confidence if semantically defined, face/eye validity flags, head-pose estimates if used, calibration validity and an explicit status/reason code.

A missing or invalid gaze estimate is a first-class outcome, not a coordinate such as (0,0).

## Calibration record

Record calibration ID/version, target coordinates and order, accepted/rejected samples with reason, fitted calibration parameters or durable hash/reference, diagnostics, start/end time and software/model/config hashes.

Calibration targets and final evaluation targets must remain separable.

## Evaluation outputs

At minimum plan for:

- pointwise target error;
- participant/run median error;
- 90th/95th percentile error;
- precision/dispersion during nominal fixation;
- valid-sample fraction;
- calibration failure rate;
- tracking-loss rate;
- condition-specific failure/error distributions.

Metric definitions and units must be frozen before participant outcome analysis.

## Privacy

Default research behavior:

- process camera frames on device;
- do not persist raw video or face crops;
- do not upload room imagery;
- export only scientifically necessary derived measurements and provenance.

Any retained imagery requires a separate explicit scientific justification, governance decision and participant-facing disclosure.
