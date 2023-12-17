# <p align="center"> Budget App  💰 </p>


This application is developed for budget calculation purposes. You can easily monitor your income and expenses.

<!-- Screenshots -->
## 📸 Screenshots
<p align="center">
  <img src="https://github.com/selincengiz41/budgetApp/assets/60012262/40de4905-e8e0-4639-847d-09d7ea2fda82" width="130" height="300"/> 
  <img src="https://github.com/selincengiz41/budgetApp/assets/60012262/4d31e9b3-2c9e-4d9b-9ebd-c233cd911357" width="130" height="300"/> 
  <img src="https://github.com/selincengiz41/budgetApp/assets/60012262/91c54596-4a6b-420c-9565-329dac4ddf9f" width="130" height="300"/>
  <img src="https://github.com/selincengiz41/budgetApp/assets/60012262/3a36ea50-e2af-461f-bd5f-18ad73dbb1a2" width="130" height="300"/>
  <img src="https://github.com/selincengiz41/budgetApp/assets/60012262/edcf2dd5-fc7f-424d-9528-146a114220fd" width="130" height="300"/>
  <img src="https://github.com/selincengiz41/budgetApp/assets/60012262/ea916b4c-af55-495c-9df8-0973a2a3f0a4" width="130" height="300"/>
  <img src="https://github.com/selincengiz41/budgetApp/assets/60012262/d7890343-5e35-4063-862d-cbafad36577a" width="130" height="300"/>





</p>



## :point_down: Structures 
- Lottie
- Firebase 
- Navigation
- Room 
- Data Binding 
- Glide
- SDP/SSP Library



## :pencil2: Dependency
```
    dependencies {

 
    // SSP-SDP library
    implementation 'com.intuit.ssp:ssp-android:1.1.0'
    implementation 'com.intuit.sdp:sdp-android:1.1.0'

    // Navigation
    implementation 'androidx.navigation:navigation-fragment-ktx:2.6.0'
    implementation 'androidx.navigation:navigation-ui-ktx:2.6.0'

    //Retrofit
    implementation "com.squareup.retrofit2:retrofit:2.9.0"
    implementation "com.squareup.retrofit2:converter-gson:2.9.0"

    //Glide
    implementation "com.github.bumptech.glide:glide:4.15.1"
    implementation 'jp.wasabeef:glide-transformations:4.3.0'

    //Roundable Layout
    implementation 'com.github.zladnrms:RoundableLayout:1.1.4'

    //Lottie
    implementation 'com.airbnb.android:lottie:6.1.0'

    // Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation platform('com.google.firebase:firebase-bom:32.2.2')
    // Add the dependency for the Firebase Authentication library
    implementation("com.google.firebase:firebase-auth-ktx")
    //   // Declare the dependency for the Cloud Firestore library
    implementation("com.google.firebase:firebase-firestore-ktx")


}
```

app build.gradle:

```
plugins {
   id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
    id 'kotlin-parcelize'
    id 'androidx.navigation.safeargs'
    //For the annotations
    id 'kotlin-kapt'
    // Add the Google services Gradle plugin
    id 'com.google.gms.google-services'
}

buildFeatures {
      dataBinding = true
 }
```
project build.gradle:

```
plugins {
 id 'com.android.application' version '8.0.2' apply false
    id 'com.android.library' version '8.0.2' apply false
    id 'org.jetbrains.kotlin.android' version '1.8.20' apply false
    id 'androidx.navigation.safeargs.kotlin' version '2.5.1' apply false
    id 'com.google.gms.google-services' version '4.3.15' apply false
}
```





