<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

include_once('config.php'); // 데이터베이스 연결

// 입력 데이터 받기
$username = isset($_POST["username"]) ? trim($_POST["username"]) : null;
$phone = isset($_POST["phone"]) ? trim($_POST["phone"]) : null;

// 필수 데이터 검증
if (empty($username) || empty($phone)) {
    die(json_encode(array("success" => false, "message" => "모든 필드를 입력해주세요.")));
}

// 전화번호 유효성 검증
if (!preg_match("/^\d{10,12}$/", $phone)) {
    die(json_encode(array("success" => false, "message" => "전화번호는 10~12자리 숫자여야 합니다.")));
}

// 데이터베이스에서 아이디 검색
$query = "SELECT id FROM SPPB_user WHERE username = ? AND phone = ?";
$stmt = mysqli_prepare($conn, $query);
mysqli_stmt_bind_param($stmt, "ss", $username, $phone);
mysqli_stmt_execute($stmt);
mysqli_stmt_store_result($stmt);

$response = array();
if (mysqli_stmt_num_rows($stmt) > 0) {
    // 결과가 있으면 ID 반환
    mysqli_stmt_bind_result($stmt, $id);
    mysqli_stmt_fetch($stmt);
    $response["success"] = true;
    $response["message"] = "아이디를 찾았습니다.";
    $response["id"] = $id;
} else {
    // 결과가 없으면 에러 메시지 반환
    $response["success"] = false;
    $response["message"] = "해당 정보로 등록된 아이디가 없습니다.";
}

header('Content-Type: application/json');
echo json_encode($response);

mysqli_close($conn);
?>
