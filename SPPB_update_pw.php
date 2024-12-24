<?php

ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

include_once('config.php'); // 데이터베이스 연결

// 클라이언트에서 데이터 받기
$id = isset($_POST["id"]) ? trim($_POST["id"]) : null;
$phone = isset($_POST["phone"]) ? trim($_POST["phone"]) : null;
$new_pw = isset($_POST["new_pw"]) ? trim($_POST["new_pw"]) : null;

if (empty($id) || empty($phone) || empty($new_pw)) {
    die(json_encode(array("success" => false, "message" => "모든 필드를 입력해주세요.")));
}

if (strlen($new_pw) < 8) {
    die(json_encode(array("success" => false, "message" => "비밀번호는 최소 8자리 이상이어야 합니다.")));
}

$new_pw_hashed = password_hash($new_pw, PASSWORD_DEFAULT); // 새 비밀번호 해시화

// 사용자 확인 및 비밀번호 업데이트
$query = "SELECT * FROM SPPB_user WHERE id = ? AND phone = ?";
$stmt = mysqli_prepare($conn, $query);
mysqli_stmt_bind_param($stmt, "ss", $id, $phone);
mysqli_stmt_execute($stmt);
mysqli_stmt_store_result($stmt);

if (mysqli_stmt_num_rows($stmt) > 0) {
    $update_query = "UPDATE SPPB_user SET pw = ? WHERE id = ? AND phone = ?"; //  WHERE id = ? AND phone = ? 조건에 맞는 pw 업데이트
    $update_stmt = mysqli_prepare($conn, $update_query); // 쿼리를 실행하기 전에 미리 준비(prepare)하는 단계
    mysqli_stmt_bind_param($update_stmt, "sss", $new_pw_hashed, $id, $phone); // 쿼리에 있는 ?에 해당하는 값을 바인딩
    $result = mysqli_stmt_execute($update_stmt); // sss": 각 ?의 데이터 타입을 지정합니다. [s: 문자열(string)]

    if ($result) {
        echo json_encode(array("success" => true, "message" => "비밀번호가 성공적으로 변경되었습니다."));
    } else {
        echo json_encode(array("success" => false, "message" => "비밀번호 변경에 실패했습니다."));
    }
} else {
    echo json_encode(array("success" => false, "message" => "일치하는 사용자를 찾을 수 없습니다."));
}

mysqli_close($conn);
?>
