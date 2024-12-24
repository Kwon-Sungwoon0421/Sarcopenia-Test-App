<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

// config.php 파일 포함
include_once('config.php');

// SQL 쿼리 준비 (최신 데이터 1개 가져오기)
$sql = "SELECT G_X, G_Y, G_Z, A_X, A_Y, A_Z, Time FROM SPPB_Normal ORDER BY Time DESC LIMIT 1";

// 쿼리 실행
$result = $conn->query($sql);

// 결과 확인 및 JSON으로 반환
if ($result->num_rows > 0) {
    $row = $result->fetch_assoc();
    echo json_encode([
        "status" => "success",
        "data" => $row
    ]);
} else {
    echo json_encode([
        "status" => "error",
        "message" => "No data found"
    ]);
}

// 연결 종료
$conn->close();
?>
