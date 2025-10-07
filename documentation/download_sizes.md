# MiSnap SDK v5.9.0 Download Sizes

The following tables provide the APK download size for various SDK configurations. These values represent
the maximum size increase, however due to potential shared dependencies the actual increase in size may
be lower. To avoid bundling redundant architectures, see [the FAQ](../README.md#how-can-i-reduce-the-size-of-my-application).
All sizes are in MiB.

### **Common Integration Sizes**
These sizes include the UI and represent an "out of the box" integration.
<!-- USECASE_SIZE_TABLE_START -->
| Use Case                         | armeabi_v7a | arm64_v8a | All Arm ABIs | x86   | x86_64 | All x86 ABIs | All ABIs | 
| :------------------------------- | ----------: | --------: | -----------: | ----: | -----: | -----------: | -------: |
| Document                         | 6.33        | 6.53      | 7.65         | 6.63  | 6.62   | 8.03         | 10.46    | 
| Document and Barcode             | 8.97        | 9.68      | 13.43        | 9.3   | 9.72   | 13.8         | 21.99    | 
| Document and Biometric           | 13.74       | 14.53     | 18.66        | 15.05 | 15.01  | 20.44        | 29.49    | 
| Document, Barcode, and Biometric | 16.37       | 17.68     | 24.44        | 17.72 | 18.11  | 26.21        | 41.02    | 
| Document, Biometric, and NFC     | 18.98       | 20.14     | 26.21        | 20.59 | 20.48  | 28.17        | 41.47    | 
| Document Classification          | 13.23       | 14.61     | 20.89        | 14.79 | 14.6   | 22.43        | 36.36    | 
<!-- USECASE_SIZE_TABLE_END -->

### **Feature Sizes**
UI size listed separately from other features.
<!-- SCIENCE_SIZE_TABLE_START -->
| Feature       | armeabi_v7a | arm64_v8a | All Arm ABIs | x86   | x86_64 | All x86 ABIs | All ABIs | 
| :------------ | ----------: | --------: | -----------: | ----: | -----: | -----------: | -------: |
| Document      | 4.14        | 4.33      | 5.45         | 4.43  | 4.42   | 5.83         | 8.26     | 
| Barcode       | 6.18        | 6.79      | 9.96         | 6.39  | 6.79   | 10.17        | 17.13    | 
| Face          | 10.23       | 10.85     | 14.08        | 11.31 | 11.26  | 15.58        | 22.67    | 
| Voice         | 3.2         | 3.35      | 4.11         | 3.45  | 3.41   | 4.41         | 6.07     | 
| Classifier    | 11.15       | 12.53     | 18.81        | 12.71 | 12.51  | 20.35        | 34.28    | 
| NFC           | 5.81        | 5.9       | 6.49         | 5.99  | 5.97   | 6.74         | 8.0      | 
| Combined NFC  | 9.35        | 9.91      | 12.98        | 9.94  | 9.87   | 13.53        | 20.23    | 
| Workflow (UI) | 4.49        | 4.56      | 5.0          | 4.63  | 4.6    | 5.18         | 6.14     | 
<!-- SCIENCE_SIZE_TABLE_END -->
