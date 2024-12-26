# Sarcopenia_Test_App
개발기간: 2024-11-11~2024-12-18  
개발인원: 1명
## 프로젝트 배경
- 전 세계적으로 65세 이상의 노인 인구가 전체 인구의 20% 이상을 차지하는 초고령화 사회로 진행 중
- 노인들은 신체기능을 평가하기 위해서 병원을 방문해야 하는 번거로움이 존재
- 조기 진단과 관리가 중요한 근감소증 문제를 해결하기 위해, 휴대용 장비와 모바일 어플리케이션을 통해 간단한 진단 도구 제공

## 기술 스택
- **프론트엔드**: Android Studio (Java, XML)
- **백엔드**: PHP, MySQL
- **하드웨어**: Arduino, ESP8266, MPU6050(6축 IMU센서)
- **서버**: MySQL
- **통신**: Wi-Fi 모듈 (ESP8266)

## 주요 기능
1. **로그인 및 회원가입**
   - 사용자 계정 생성 및 관리
2. **검사 및 점수 집계**
   - 균형잡기, 의자 일어서기, 보행 검사 등 다양한 근감소증 평가
3. **데이터 저장 및 조회**
   - 센서 데이터 및 검사 결과를 MySQL 데이터베이스에 저장
   - 검사 기록을 조회하여 분석
   - 센서 데이터를 초마다 조회하여 실시간 분석

## 시스템 구성 요소
1. **사용자**
   - 모바일 앱을 통해 로그인하고 대상자 정보를 입력 및 검사 수행
2. **모바일 앱**
   - 로그인, 회원가입, 검사 항목, 결과 조회, 대상자 정보 입력 등 수행
   - Wi-Fi를 통해 서버에 검사 결과 전송
3. **센서**
   - MPU6050 센서를 통해 X, Y, Z값을 읽고 Wi-Fi를 통해 데이터를 서버로 전송
4. **서버**
   - 데이터베이스 저장 및 조회 (검사 기록, 센서 데이터)
   - PHP파일을 통해 데이터베이스와 상호작용
5. **데이터베이스**
   - 검사자, 대상자 정보와 센서 데이터 저장

### 구조
 ![SPPB 관계도](https://github.com/user-attachments/assets/b037a019-3152-44c2-a19e-22c859ec8ff7)
 
### DFD
 ![SPPB_DFD](https://github.com/user-attachments/assets/bcf3305a-e28e-4de0-856b-4af14b486e21)

 ### FlowChart
![SPPB_F_C](https://github.com/user-attachments/assets/544630ab-d5c4-4384-84c7-b79e35c76803)


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
- Activity와 XML은 같은 제목끼리 연결

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
   - ESP8266_MySQL_Server // 센서 값을 읽고 WiFi를 통해 데이터베이스에 전달

## 데이터베이스 테이블 구조
  
### SPPB_user 테이블

- 검사자의 정보
  
| 필드명           | 데이터 타입   | 설명            |
|------------------|---------------|------------------|
| username         | VARCHAR(50)   | 검사자 이름      |
| birthdaty        | CHAR(6)       | 생년월일(6자리)  |
| phone            | VARCHAR(12)   | 핸드폰 번호      |
| id (PRIMARY KEY) | VARCHAR(50)   | 아이디           |
| pw               | VARCHAR(255)  | 비밀번호         |



- 대상자의 정보
  
### SPPB_subjects 테이블
| 필드명               | 데이터 타입   | 설명               |
|----------------------|---------------|--------------------|
| id (PRIMARY KEY)     | INT(11)       | 식별번호           |
| username             | VARCHAR(50)   | 대상자 이름        |
| birthdate            | CHAR(6)       | 생년월일 (6자리)   |
| height               | INT(11)       | 키                 |
| weight               | INT(11)       | 체중               |
| gender               | VARCHAR(10)   | 성별               |
| QuestionResult       | INT(11)       | 설문 점수          |
| AVG_GripStrength     | INT(11)       | 악력 점수          |
| Walking_Score        | INT(11)       | 보행 점수          |
| StandUp_Score        | INT(11)       | 의자 일어서기 점수 |
| Balance_Score        | INT(11)       | 균형 점수          |
| Total_Score          | INT(11)       | 총 점수            |
| Last_Inspection_Date | DATE          | 마지막 검사 날짜   |



- MPU6050 센서의 데이터 정보

### SPPB_Normal 테이블
| 필드명        | 데이터 타입   | 설명              |
|---------------|---------------|--------------------|
| Count         | INT(50)       | 데이터 삽입 카운트 |
| Name          | VARCHAR(20)   | 사용 안함          |
| G_X           | FLOAT         | 자이로 X값         |
| G_Y           | FLOAT         | 자이로 Y값         |
| G_Z           | FLOAT         | 자이로 Z값         |
| Time          | DATETIME      | 데이터 삽입 시간   |
| A_X           | FLOAT         | 가속도 X값         |
| A_Y           | FLOAT         | 가속도 Y값         |
| A_Z           | FLOAT         | 가속도 Z값         |

## 사진

### 설문 조사
![그림1](https://github.com/user-attachments/assets/390615c9-0bc4-4668-bf7f-a236cfa827f8)  ![그림2](https://github.com/user-attachments/assets/4b0d58a9-253a-4036-a977-a42dc9490468)  ![그림3](https://github.com/user-attachments/assets/297cacd3-2b71-44a2-8d5b-e3e02dd6b21a)

### 근감소증 평가
![그림4](https://github.com/user-attachments/assets/4c64d0c6-8db3-4612-9865-0cfeec865a62)  ![그림5](https://github.com/user-attachments/assets/f1fe5315-1438-4cc2-81c6-79e5754f76f2)

### 대상자 정보
![그림6](https://github.com/user-attachments/assets/d3b00018-0c6a-4d13-8e8c-a0a98fab6427)  ![그림7](https://github.com/user-attachments/assets/2f0d8a07-83db-46ec-baf6-ce495fa6adee)  ![그림8](https://github.com/user-attachments/assets/39f4c124-155c-45d8-aed7-775506ddc428)

### 테이블
- 대상자 테이블
![SPPB_subjects](https://github.com/user-attachments/assets/5dc5afc6-2933-4729-bd72-82805bfdb799)


- 검사자 테이블
![SPPB_user](https://github.com/user-attachments/assets/71544cb7-3c86-4b8b-a08c-f0ce4ff974ec)


- 센서 데이터 테이블
![SPPB_sensor](https://github.com/user-attachments/assets/08f38515-0676-408f-b0c3-a077c70c8e7f)


## 마무리
이 프로젝트는 대학교 4학년 2학기 현장실습 기간[1] 동안 제작한 프로젝트이다.  
실습 기관에서의 도움 없이 오롯이 내가 기존에 경험하여 얻은 지식과 서칭을 통해 개발하였다.  
서버에 관련해서는 지식이 거의 없었기에 서칭을 하는 데에 많은 시간이 소요되었다.  
서버와 클라이언트를 연결하는 것에 첫 시도는 무료 호스팅 서버였다. 이 때 FileZilla를 알게되었고 서버에 php파일을 이동시키는 것을 알았다.  
phpMyAdmin을 통해 데이터베이스를 구축하였고 테이블 생성까지 성공하였다.  
하지만 PHP파일을 만들면서 서버 연결이 되지 않는 문제에 맞닥뜨렸고, 문제 원인을 제대로 파악하지 못했다.[2]  
두 번째 시도는 AWS를 이용하였다. AWS를 사용해본 적이 한 번도 없었기에 EC2와 RDS를 만들고 연결하는 데에 정말 많은 시간을 소요하였다.  
여기서는 phpMyAdmin을 사용하지 않았기에 MySQL Workbench를 이용하였다.[3][4]  
PHP파일을 만드는 것을 계속 서칭하다보니 AWS로 서버와 클라이언트의 연결을 성공하였다.  
그러나 AWS를 계속 이용하는 데에 문제가 생겨 다른 방법을 찾게 되었고[5], 실습기관 대표의 도움으로 사내 서버를 이용하게 되었다.  
사내 서버는 유료 호스팅 서버로 후이즈 호스팅을 이용하고 있었다. 이를 바탕으로 다시 서버와 클라이언트를 연결하였고,  
이를 끝으로 서버와 클라이언트의 연결을 끝내었다.  
이 프로젝트를 진행하면서 제일 많이 마주했던 문제는 서버와 클라이언트 간의 상호작용이였다.   
원인은 Java코드에서 PHP파일로 보내줄 때 사용한 변수명과 PHP파일이 요청받은 변수명의 설정을 다르게 하였거나,  
테이블의 컬럼과 PHP파일의 쿼리문에서 컬럼의 이름이 일치하지 않았기 때문에 문제가 발생하였다.  
같은 실수를 반복하지 않기 위해 가능한 테이블의 컬럼 이름, Java코드의 변수명, PHP파일의 요청받은 변수명들을 모두 동일하게 작성하였다.  
PHP파일에 대해서 아직 많이 알지 못하지만, 이번 프로젝트를 통해 사용했던 PHP파일들을 토대로 다른 프로젝트에서도  
사용할 수 있겠다는 자신감이 생겼고, 하나의 지식을 터득하게 된 것에 만족하고 이 프로젝트를 마친다.


[1]. 2024-09-01~2024-12-31  
[2]. 문제의 원인을 그저 무료 호스팅의 문제라고 생각했다.  
[3]. MySQL Workbench의 사용 방법을 몰랐기에 테이블을 생성하는 PHP파일을 만들고, 웹페이지에 PHP파일의 URL로 접속하여 만들었다.  
[4]. 테이블의 컬럼과 튜플을 확인하는 용도로만 MySQL Workbench를 사용하였다.  
[5]. 이용금이 달마다 청구되는 것에 부담되어 EC2와 RDS를 삭제하였다.  
