<?php

ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);
// config.php 파일을 포함하여 데이터베이스 연결을 가져옴
include_once('config.php');

// 입력 데이터 받기 및 검증
$username = isset($_POST["username"]) ? trim($_POST["username"]) : null;
$birthday = isset($_POST["birthday"]) ? trim($_POST["birthday"]) : null;
$phone = isset($_POST["phone"]) ? trim($_POST["phone"]) : null;
$id = isset($_POST["id"]) ? trim($_POST["id"]) : null;
$pw = isset($_POST["pw"]) ? trim($_POST["pw"]) : null;

// 로그에 POST 데이터 기록
error_log("Received data: " . json_encode($_POST));

// 필수 데이터가 비어있지 않은지 확인
if (empty($username) || empty($birthday) || empty($phone) || empty($id) || empty($pw)) {
    die(json_encode(array("success" => false, "message" => "모든 필드를 입력해야 합니다.")));
}

// 비밀번호 유효성 검증
if (strlen($pw) < 8) {
    die(json_encode(array("success" => false, "message" => "비밀번호는 최소 8자리 이상이어야 합니다.", "code" => 401)));
}

// 전화번호 및 생년월일 유효성 검증
if (!preg_match("/^\d{10,12}$/", $phone)) {
    die(json_encode(array("success" => false, "message" => "전화번호는 10~12자리 숫자여야 합니다.", "code" => 402)));
}

if (!preg_match("/^\d{6}$/", $birthday)) {
    die(json_encode(array("success" => false, "message" => "생년월일은 6자리 숫자여야 합니다.", "code" => 403)));
}

$passwordHash = password_hash($pw, PASSWORD_DEFAULT); // 비밀번호 해시화

// 중복된 ID 또는 전화번호 확인
$checkQuery = "SELECT * FROM SPPB_user WHERE id = ? OR phone = ?";
$checkStatement = mysqli_prepare($conn, $checkQuery);
mysqli_stmt_bind_param($checkStatement, "ss", $id, $phone);
mysqli_stmt_execute($checkStatement);
mysqli_stmt_store_result($checkStatement);

$response = array();
if (mysqli_stmt_num_rows($checkStatement) > 0) {
    // 중복된 정보가 있을 경우
    $response["success"] = false;
    $response["message"] = "이미 등록된 회원입니다.";
} else {
    // 중복된 정보가 없으면 회원가입 진행
    $statement = mysqli_prepare($conn, "INSERT INTO SPPB_user (username, birthday, phone, id, pw) VALUES (?, ?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement, "sssss", $username, $birthday, $phone, $id, $passwordHash);
    $result = mysqli_stmt_execute($statement);

    $response["success"] = $result;
    if ($result) {
        $response["message"] = "회원가입에 성공했습니다.";
    } else {
        $response["message"] = "회원가입에 실패했습니다. 오류: " . mysqli_error($conn);
    }
}

// 결과 응답
header('Content-Type: application/json'); // JSON 응답의 MIME 타입 설정
echo json_encode($response);

mysqli_close($conn);
?>
