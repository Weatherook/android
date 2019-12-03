## 웨더룩
> 과거, 현재, 미래의 날씨정보를 제공해주는 패션 SNS 

## Overview
* 기간 : 2018.06.30 ~ 2018.07.14
* 참여한 사람 
  원정빈
  김현희
  유현영
## About
* Technology Stack
  - Tools : Android Studio 3.4
  - Language : Kotlin
  
* Library
```
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    implementation 'com.android.support:design:27.1.1'
    implementation 'io.reactivex.rxjava2:rxkotlin:2.2.0'
    implementation 'io.reactivex.rxjava2:rxjava:2.1.9'
    implementation 'io.reactivex.rxjava2:rxandroid:2.0.2'
    implementation 'org.greenrobot:eventbus:3.1.1'
    implementation 'com.jakewharton.rxbinding2:rxbinding:2.1.1'
    implementation 'com.android.support.constraint:constraint-layout:1.1.2'
    implementation 'com.shawnlin:number-picker:2.4.6'
    implementation 'com.android.support:appcompat-v7:27.1.0'
    implementation 'com.github.bumptech.glide:glide:4.7.1'
    implementation 'de.hdodenhof:circleimageview:2.2.0'
    implementation 'com.tbuonomo.andrui:viewpagerdotsindicator:2.0.0'
    implementation("com.github.bumptech.glide:recyclerview-integration:4.7.1") {
        // Excludes the support library because it's already included by Glide.
        transitive = false
    }
    annotationProcessor 'com.github.bumptech.glide:compiler:4.7.1'
    kapt 'com.github.bumptech.glide:compiler:4.7.1'
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'com.android.support.test:runner:1.0.2'
    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'
    implementation 'gun0912.ted:tedpermission:2.2.0'
    implementation 'com.google.android.gms:play-services-location:15.0.1'

    implementation 'com.squareup.retrofit2:retrofit:2.3.0'
    implementation "com.squareup.retrofit2:adapter-rxjava2:2.3.0"
    implementation 'com.squareup.retrofit2:converter-gson:2.1.0'

    implementation 'com.merhold.extensiblepageindicator:extensiblepageindicator:1.0.1'
    implementation 'com.jakewharton:butterknife:8.8.1'
    annotationProcessor 'com.jakewharton:butterknife-compiler:8.8.1'
    implementation 'com.google.code.gson:gson:2.8.2'
    implementation 'com.davemorrissey.labs:subsampling-scale-image-view:3.10.0'
}
```
## WireFrame
[Weatherook.pdf](https://github.com/Weatherook/android/files/2827063/Weatherook.pdf)
