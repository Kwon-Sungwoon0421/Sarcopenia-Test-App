# Sarcopenia_Test_App
## 프로젝트 배경
- 대한민국의 고령화로 인해 근감소증 환자 증가
- 조기 진단과 관리가 중요한 근감소증 문제를 해결하기 위해, 휴대용 장비와 모바일 어플리케이션을 통해 간단한 진단 도구를 제공

## 주요 기능
1. **로그인 및 회원가입**
   - 사용자 계정 생성 및 관리
2. **검사 항목**
   - 균형잡기, 의자 일어서기, 보행 검사 등 다양한 근감소증 평가
3. **데이터 저장 및 조회**
   - 센서 데이터 및 검사 결과를 MySQL 데이터베이스에 저장
   - 이전 데이터를 조회하여 분석

## 기술 스택
- **프론트엔드**: Android Studio (Java, XML)
- **백엔드**: PHP, MySQL
- **하드웨어**: Arduino, ESP8266, MPU6050
- **서버**: MySQL
- **통신**: Wi-Fi 모듈 (ESP8266)

## 코드 파일
1. **php**
   - config.php // 데이터베이스 정보
   - SPPB_login.php // 어플리케이션 로그인
   - SPPB_register // 어플리케이션 회원가입
   - SPPB_find_id.php // 어플리케이션 아이디 찾기
   - SPPB_update.php // 비밀번호 변경
   - SPPB_subjects.php // 대상자의 정보 저장
   - SPPB_insert.php // 센서의 x, y, z값 저장
   - SPPB_get_data.php // 센서의 x, y, z값 불러오기

2. **안드로이스 스튜디오**
   
   (1) **java**
   - BalanceActivity // 균형잡기 검사
   - Chair_Stand_UPActivity // 의자 일어서기 검사
   - Evaluation_ItemsActivity // 검사 항목
   - Grip_StrengthActivity // 악력 검사
   - ID_Search_ResultActivity // 아이디 찾기 결과
   - ID_SearchActivity // 아이디 찾기
   - LoginActivity // 로그인
   - MainActivity // 메인 화면
   - PW_Search_ResultActivity // 비밀번호 찾기 결과
   - PW_SearchActivity // 비밀번호 찾기
   - Question_MainActivity // 설문 메인 화면
   - Question_ResultActivity // 설문 결과
   - Question1Activity // 설문 문항1
   - Question2Activity // 설문 문항2
   - Question3Activity // 설문 문항3
   - Question4Activity // 설문 문항4
   - Question5Activity // 설문 문항5
   - Record_CheckActivity // 검사 기록 확인
   - RegisterActivity // 회원가입
   - SubjectsActivity // 대상자 정보 입력
   - Test_ListActivity // 대상자 메인 화면
   - Test_RecordActivity // 대상자 기록 검색
   - Test_ResultActivity // 검사 결과
   - WalkingActivity // 보행 검사
   - XYZActivity // MPU6050 센서 x, y, z값 확인
     
  
   (2) **XML**
   - activity_balance
   - activity_chair_stand_up
   - activity_evaluation_items
   - activity_grip_strength
   - activity_id_search
   - activity_id_search_result
   - activity_login
   - activity_main
   - activity_pw_search
   - activity_pw_search_result
   - activity_question_main
   - activity_question_result
   - activity_question1
   - activity_question2
   - activity_question3
   - activity_question4
   - activity_question5
   - activity_record_check
   - activity_register
   - activity_subjects
   - activity_test_list
   - activity_test_record
   - activity_test_result
   - activity_walking
   - activity_xyz

  
3. **Arduino**
   - ESP8266_MySQL_Server // MPU6050 센서 x, y, z값 서버 저장
