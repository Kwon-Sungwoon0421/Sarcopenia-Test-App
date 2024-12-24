<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

// config.php 파일을 포함하여 데이터베이스 연결을 가져옴
include_once('config.php');

// 입력 데이터 받기 및 검증
$id = isset($_POST["id"]) ? trim($_POST["id"]) : null;
$pw = isset($_POST["pw"]) ? trim($_POST["pw"]) : null;

// 로그에 POST 데이터 기록
error_log("Received data: " . json_encode($_POST));

// 필수 데이터가 비어있지 않은지 확인
if (empty($id) || empty($pw)) {
    die(json_encode(array("success" => false, "message" => "ID와 비밀번호를 입력해야 합니다.")));
}

// ID와 비밀번호 확인
$query = "SELECT * FROM SPPB_user WHERE id = ?";  
$stmt = mysqli_prepare($conn, $query);
mysqli_stmt_bind_param($stmt, "s", $id);
mysqli_stmt_execute($stmt);
$result = mysqli_stmt_get_result($stmt);

$response = array();
if ($row = mysqli_fetch_assoc($result)) {
    // 비밀번호 비교
    if (password_verify($pw, $row['pw'])) {  
        // 로그인 성공
        $response["success"] = true;
        $response["message"] = "로그인 성공";
        $response["username"] = $row["username"];
    } else {
        // 비밀번호 불일치
        $response["success"] = false;
        $response["message"] = "잘못된 비밀번호입니다.";
    }
} else {
    // 아이디가 존재하지 않음
    $response["success"] = false;
    $response["message"] = "등록되지 않은 아이디입니다.";
}

// 결과 응답
header('Content-Type: application/json'); // JSON 응답의 MIME 타입 설정
echo json_encode($response);

mysqli_close($conn);
?>
