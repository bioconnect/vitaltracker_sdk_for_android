카메라, 얼굴 detector, skin segmentation, 서버 통신 코드 & AAR 파일 생성 안드로이드 스튜디오 프로젝트 


* https가 아닌 서버접속을 위해 프로젝트별 res/xml/network_security_config.xml 파일의 
<network-security-config>
    <base-config cleartextTrafficPermitted="true" />
</network-security-config>
로 설정 

* local.properties 
base_url="http://118.128.153.171:8088"  //사내 파일서버 경로

