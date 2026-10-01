# Reproducibility

A reportable run must preserve:

- source commit and app version;
- Android/API version and engineering device profile;
- estimator/model version;
- calibration algorithm/configuration and calibration ID;
- protocol and target-set versions;
- screen geometry/orientation and coordinate conventions;
- quality/failure-state rules;
- randomization seed when applicable;
- immutable input/export identifier or checksum;
- analysis code/configuration;
- generated result artifacts.

Calibration targets and final evaluation targets must remain separable. Reanalysis with interpretation-changing changes creates a new artifact and a deviation record.

Publication values should be generated from versioned result artifacts rather than manually transcribed.
