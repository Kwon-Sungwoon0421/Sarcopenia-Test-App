# Sarcopenia_Test_App
대한민국의 고령화 현상으로 인한 사회경제적 문제 중 하나인 근감소증을 관리하기 위해
근육량, 근력, 신체활동 능력 평가를 기반으로 한 모바일 근감소증 시스템 개발

## 기능
- 로그인 및 회원가입
- 타이머
- 대상자의 기록 저장 및 불러오기
- 

## 코드 파일
1. php
   - config.php // 데이터베이스 정보
   - SPPB_login.php // 어플리케이션 로그인
   - SPPB_register // 어플리케이션 회원가입
   - SPPB_find_id.php // 어플리케이션 아이디 찾기
   - SPPB_update.php // 비밀번호 변경
   - SPPB_subjects.php // 대상자의 정보 저장
   - SPPB_insert.php // 센서의 x, y, z값 저장
   - SPPB_get_data.php // 센서의 x, y, z값 불러오기

2. 안드로이스 스튜디오
   - AndroidManifest
   - BalanceActivity
   - Chair_Stand_UPActivity
   - Evaluation_ItemsActivity
   - Grip_StrengthActivity
   - ID_Search_ResultActivity
   - ID_SearchActivity
   - LoginActivity
   - MainActivity
   - PW_Search_ResultActivity
   - PW_SearchActivity
   - Question_MainActivity
   - Question_ResultActivity
   - Question1Activity
   - Question2Activity
   - Question3Activity
   - Question4Activity
   - Question5Activity
   - Record_CheckActivity
   - RegisterActivity
   - SubjectsActivity
   - Test_ListActivity
   - Test_RecordActivity
   - Test_ResultActivity
   - WalkingActivity
   - XYZActivity
