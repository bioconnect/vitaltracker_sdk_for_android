VitalTracker SDK for Android
========================
This library allows you to integrate VitalTracker into your Android app.<br/>
VitalTracker SDK를 안드로이드 앱에 탑재하여 생체지표를 추출할 수 있도록 지원합니다.<br/>
Remote PPG 기술을 이용하여 5가지의 생체 지표인 심박수, 스트레스, 호흡수, 산소포화도, 혈압을 측정할 수 있습니다.<br/>

<br/>

FEATURES
--------
* 원활한 동작을 위해 아래의 환경을 지원해야 합니다.<br/>
  |항목|세부내용|
  |------|---|
  |OS|Android 8.1 Oreo 이상 ( API Level 27이상 ), Compose UI|
  |카메라|전면카메라|
  |CPU|Arm64 (64bit)|
  |촬영시 권장사항|주변이 너무 심하게 어둡지 않아야 함. 촬영 시 움직임 및 빛 변화가 없어야 함.|

<br/>

STRUCTURE
---------
Vital Tracker의 제품은 Mobile용 SDK와 생체지표를 분석하는 서버로 구성됩니다.<br/>
이 SDK는 Mobile SDK 중 Android OS의 서비스 앱에 탑재하기 위한 모듈입니다.<br/>
생체지표 분석 서버는 별도의 구축이 필요하며 서비스앱에서 연동이 필요합니다.<br/>

  ![sdk library settings](https://github.com/bioconnect/filestorage/blob/main/service_structure.png)

<br/>

USAGE
-----
* 동작 시나리오  
  -	앱 동작 -> 카메라 권한 요청 -> 얼굴 감지 -> 측정 버튼 활성화 -> 측정 버튼 누름 및 측정 (15초) -> 생체지표 분석 서버와 통신 -> 측정 결과 출력

<br/>

INSTALLATION
------------
VitalTracker SDK를 안드로이드 앱에 적용/설치 하기 위한 과정을 설명합니다.<br/>

1. 빈 프로젝트 생성<br/>
   - minSdk = 27이상
   - targetSdk = 34 
<br/>

2. library 적용<br/>
   1)	app/libs 에 VitalTracker SDK 라이브러리인 bioconnect_debug.aar 파일을 붙여넣기합니다.<br/>
   2)	Android Studio IDE 기준으로 설명합니다. 메뉴의 File - Project Structure 클릭합니다.<br/>
   3)	사이드의 Dependencies를 누르고, 중간에 Declared Dependencies 바로 밑 + 버튼을 누른 후, JAR/AAR Dependency를 선택합니다.<br/>
   4)	step1칸에 /libs/bioconnect_debug.aar 을 입력후, ok버튼을 누릅니다.<br/>
    ![sdk library settings](https://github.com/bioconnect/filestorage/blob/main/external_lib_settings.png)
  
   5)	모듈:app단위 build.gradle파일의 dependencies에 implementation files('libs/bioconnect_debug.aar') 이 있는지 확인합니다.<br/>
<br/>

3. 빌드 및 배포<br/>
   1) 빌드 및 배포 도구인 gradle을 기준으로 설명합니다.<br/>
   2) 아래 build.gradle 의 설정을 참조하여 dependencies를 추가하도록 합니다.<br/>
   
```gradle
plugins {
      id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
}

android {
    namespace 'com.bioconnect.vitaltracker'
    compileSdk 34

    defaultConfig {
        applicationId "com.bioconnect.vitaltracker"
        minSdk 27
        targetSdk 34
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary true
        }
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
            signingConfig signingConfigs.debug
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = '1.8'
    }
    buildFeatures {
        compose true
        buildConfig true
    }
    composeOptions {
        kotlinCompilerExtensionVersion '1.3.2'
    }
    packagingOptions {
        resources {
            excludes += '/META-INF/{AL2.0,LGPL2.1}'
        }
    }
    ndkVersion '25.1.8937393'
}

dependencies {
    implementation files('/libs/bioconnect-debug.aar')

    implementation 'androidx.core:core-ktx:1.8.0'
    implementation platform('org.jetbrains.kotlin:kotlin-bom:1.8.0')
    implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.3.1'
    implementation 'androidx.activity:activity-compose:1.5.1'
    implementation platform('androidx.compose:compose-bom:2022.10.00')
    implementation 'androidx.compose.ui:ui'
    implementation 'androidx.compose.ui:ui-graphics'
    implementation 'androidx.compose.ui:ui-tooling-preview'
    implementation 'androidx.compose.material3:material3:1.1.1'
    implementation "androidx.compose.material:material-icons-extended"
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
    androidTestImplementation platform('androidx.compose:compose-bom:2022.10.00')
    androidTestImplementation 'androidx.compose.ui:ui-test-junit4'
    debugImplementation 'androidx.compose.ui:ui-tooling'
    debugImplementation 'androidx.compose.ui:ui-test-manifest'

    // CameraX
    implementation "androidx.camera:camera-lifecycle:1.3.0-alpha06"
    implementation "androidx.camera:camera-video:1.3.0-alpha06"
    implementation "androidx.camera:camera-view:1.3.0-alpha06"
    implementation "androidx.camera:camera-extensions:1.3.0-alpha06"
    implementation "androidx.camera:camera-core:1.3.0-alpha06"

    // viewmodel
    implementation "androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1"

    // permission
    implementation "com.google.accompanist:accompanist-permissions:0.24.9-beta"
    // retrofit
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
}
```
<br/>

4. Activity에 적용<br/>
   1)	사용하려는 Activity에서 아래 소스 코드를 참조하여 개발하도록 합니다.<br/>
   
   2) onCreate 안에 MeasureView() 및 측정 결과를 저장할 변수를 선언해 줍니다.<br/>
    ``` 
    ESTIMATE_TIME : 측정 시간(default = 15 초로 특별한 이슈가 없으면 변경하지 않습니다.)
    GET_LOG : 로그 확인
       
     
    MeasureView 파라미터: 
    @ activity (ComponentActivity)
    @ baseUrl (String) = 측정지표 분석 서버의 Url 
    @ showResultTable (Boolean) = 측정 결과 및 에러를 View UI에 출력 여부
    @ result (HealthData) = 라이브러리 내에 선언된 측정 결과 데이터 클래스로 측정 결과를 받는 변수
    ```

   3) 측정완료 후 결과는 result를 리스너 이용하여 결과치를 활용할 수 있도록 제공합니다.<br/>
    ``` 
    ${result.value.second} 의 리턴 결과치 
      HealthData(bpm=83, rr=9, stress=436, spo2=95, bp=93/124)


    심박수: ${result.value.second!!.bpm}
    호흡수: ${result.value.second!!.rr}
    산소포화도: ${result.value.second!!.spo2}
    혈압: ${result.value.second!!.bp}
    스트레스(수치값): ${result.value.second!!.stress}
    스트레스(의미값): stressToLevel(${result.value.second!!.stress})
    ```   
  
  ![main_activity](https://github.com/bioconnect/filestorage/blob/main/main_activity.png)

   <br/>



5. 기타 설정
 * https가 아닌 서버접속을 위해 프로젝트별 res/xml/network_security_config.xml 파일의 설정<br/>
```XML
<network-security-config>
    <base-config cleartextTrafficPermitted="true" />
</network-security-config>
```


