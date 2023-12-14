VitalTracker SDK for Android
========================
This library allows you to integrate VitalTracker into your Android app.  

Remote PPG를 이용한 심박수, 스트레스, 호흡수, 산소포화도 측정 라이브러리인 VitalTracker SDK를 안드로이드 앱에 탑재하여 개발하는 것을 돕기 위해 작성되었습니다

TRY IT OUT
----------
1. Check-out the tutorials available online at https://developers.facebook.com/docs/android/getting-started
2. Start coding! Visit https://developers.facebook.com/docs/android/ for tutorials and reference documentation.

FEATURES
--------
* 원활한 동작을 위해 아래의 환경을 지원해야 합니다.
  |항목|세부내용|
  |------|---|
  |OS|Android 8.1 Oreo 이상 ( API Level 27이상 ), Compose UI|
  |카메라|전면카메라|
  |CPU|Arm64 (64bit)|
  |촬영시 권장사항|주변이 너무 심하게 어둡지 않아야 함. 촬영 시 움직임 및 빛 변화가 없어야 함.|


STRUCTURE
---------
The SDK is separated into modules with the following structure.

    +----------------------------------------------------+
    |                                                    |
    | Facebook-android-sdk                               |
    |                                                    |
    +----------------------------------------------------+
    +----------+ +----------+ +------------+ +-----------+
    |          | |          | |            | |           |
    | Facebook | | Facebook | | Facebook   | | Facebook  |
    | -Login : | | -Share   | | -Messenger | | -Applinks |
    |          | |          | |            | |           |
    +----------+ +----------+ |            | |           |
    +-----------------------+ |            | |           |
    |                       | |            | |           |
    | Facebook-Common       | |            | |           |
    |                       | |            | |           |
    +-----------------------+ +------------+ +-----------+
    +----------------------------------------------------+
    |                                                    |
    | Facebook-Core                                      |
    |                                                    |
    +----------------------------------------------------+

USAGE
-----
* 동작 시나리오  

-	앱 동작 -> 카메라 권한 요청 -> 얼굴 감지 -> 측정 버튼 활성화 -> 측정 버튼 누름 및 측정 (15초) -> 생체지표 분석 서버와 통신 -> 측정 결과 출력

* 동작 이미지
  

INSTALLATION
------------
Facebook SDKs are published to Maven as independent modules. To utilize a feature listed above
include the appropriate dependency (or dependencies) listed below in your `app/build.gradle` file.

1. 빈 프로젝트 생성 ( minSdk = 27이상, targetSdk = 34 )

2. library 적용
1)	app/libs 에 .aar 파일을 복사 & 붙여넣기합니다.
2)	File - Project Structure 클릭
 

3)	사이드의 Dependencies를 누르고, 중간에 Declared Dependencies 바로 밑 + 버튼을 누른 후, JAR/AAR Dependency를 선택합니다.
 

4)	step1칸에 /libs/파일이름.aar 을 입력후, ok버튼을 누릅니다.
  
5)	모듈:app단위 build.gradle파일의 dependencies에 implementation files('libs/파일이름.aar') 이 있는지 확인합니다. (없다면, 작성)

6)	아래의 이미지를 참고하여 다른 라이브러리 dependencies와 다른 속성들을 추가합니다.
 
 

7)	local.properies에 base_url = “https://서버url/” 을 추가합니다. (특수문자는 앞에 역슬래시를 입력해야합니다.)  


8)	사용하려는 Activity에서 
com.example.bioconnect.MeasureView 
com.example.bioconnect.HealthData
+) option
import com.example.bioconnect.utils.ESTIMATE_TIME
import com.example.bioconnect.utils.GET_LOG
import com.example.bioconnect.utils.stressToLevel
를 import해줍니다.

9)	onCreate 안에 MeasureView() 및 측정 결과를 저장할 변수를 선언해줍니다.
MeasureView 파라미터: (다음 장 스크린샷 첨부)
@ activity (ComponentActivity)
@ baseUrl (String) = BaseUrl
@ showResultTable (Boolean) = 측정 결과 및 에러 뷰 출력 여부
@ resultOk (Boolean) = 측정 결과 상태 변수
@ result (HealthData) = 라이브러리 내에 선언된 측정 결과 데이터 클래스로 측정 결과를 받는 변수

+) ESTIMATE_TIME : 측정 시간 변경(단위, 초 / Int / default = 15)
+) GET_LOG : 통신 로그 확인(logcat tag = Bioconnect, Boolean / default = false)
+) stressToLevel : 측정 결과로 나온 stressIndex를 범위로 반환
/**
 * stress index: 변경하고자 하는 값,
 * 정상 < 200
 * 200 <= 약한 스트레스 < 900
 * 900 <= 강한 스트레스*/


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


* https가 아닌 서버접속을 위해 프로젝트별 res/xml/network_security_config.xml 파일의 설정
```XML
<network-security-config>
    <base-config cleartextTrafficPermitted="true" />
</network-security-config>
```


