# MiSnap SDK v5.9.1 Download Sizes

The following tables provide the APK download size for various SDK configurations. These values represent
the maximum size increase, however due to potential shared dependencies the actual increase in size may
be lower. To avoid bundling redundant architectures, see [the FAQ](../README.md#how-can-i-reduce-the-size-of-my-application).
All sizes are in MiB.

### **Common Integration Sizes**
These sizes include the UI and represent an "out of the box" integration.
<!-- USECASE_SIZE_TABLE_START -->
| Use Case                         | armeabi_v7a | arm64_v8a | All Arm ABIs | x86   | x86_64 | All x86 ABIs | All ABIs | 
| :------------------------------- | ----------: | --------: | -----------: | ----: | -----: | -----------: | -------: |
| Document                         | 6.44        | 6.66      | 7.88         | 6.76  | 6.75   | 8.29         | 10.95    |
| Document and Barcode             | 9.08        | 9.81      | 13.66        | 9.44  | 9.85   | 14.05        | 22.48    |
| Document and Biometric           | 13.84       | 14.67     | 18.89        | 15.18 | 15.14  | 20.7         | 29.97    |
| Document, Barcode, and Biometric | 16.48       | 17.81     | 24.67        | 17.86 | 18.24  | 26.47        | 41.51    |
| Document, Biometric, and NFC     | 19.09       | 20.27     | 26.45        | 20.73 | 20.62  | 28.43        | 41.96    |
| Document Classification          | 13.33       | 14.74     | 21.11        | 14.92 | 14.73  | 22.69        | 36.84    |
<!-- USECASE_SIZE_TABLE_END -->

### **Feature Sizes**
UI size listed separately from other features.
<!-- SCIENCE_SIZE_TABLE_START -->
| Feature       | armeabi_v7a | arm64_v8a | All Arm ABIs | x86   | x86_64 | All x86 ABIs | All ABIs |
| :------------ | ----------: | --------: | -----------: | ----: | -----: | -----------: | -------: |
| Document      | 4.24        | 4.46      | 5.68         | 4.56  | 4.55   | 6.09         | 8.74     |
| Barcode       | 6.29        | 6.92      | 10.2         | 6.52  | 6.92   | 10.43        | 17.61    |
| Face          | 10.34       | 10.98     | 14.32        | 11.44 | 11.39  | 15.84        | 23.16    |
| Voice         | 3.2         | 3.35      | 4.11         | 3.45  | 3.41   | 4.41         | 6.07     |
| Classifier    | 11.25       | 12.66     | 19.03        | 12.84 | 12.65  | 20.61        | 34.76    |
| NFC           | 5.81        | 5.9       | 6.49         | 5.99  | 5.97   | 6.74         | 8.0      |
| Combined NFC  | 9.46        | 10.04     | 13.21        | 10.08 | 10.0   | 13.79        | 20.71    |
| Workflow (UI) | 4.49        | 4.56      | 5.0          | 4.63  | 4.6    | 5.18         | 6.14     | 
<!-- SCIENCE_SIZE_TABLE_END -->
