<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);
// config.php 파일을 포함하여 데이터베이스 연결을 가져옴
include_once('config.php');

// URL 파라미터로 값 받기 (값이 없으면 NULL 처리)
$g_x = isset($_GET['G_X']) ? floatval($_GET['G_X']) : NULL;
$g_y = isset($_GET['G_Y']) ? floatval($_GET['G_Y']) : NULL;
$g_z = isset($_GET['G_Z']) ? floatval($_GET['G_Z']) : NULL;
$a_x = isset($_GET['A_X']) ? floatval($_GET['A_X']) : NULL;
$a_y = isset($_GET['A_Y']) ? floatval($_GET['A_Y']) : NULL;
$a_z = isset($_GET['A_Z']) ? floatval($_GET['A_Z']) : NULL;

// 받은 값 중 하나라도 NULL이면 에러 처리
if ($g_x === NULL || $g_y === NULL || $g_z === NULL || $a_x === NULL || $a_y === NULL || $a_z === NULL) {
    echo "Error: Missing X, Y, or Z parameter.";
    exit;  // 값이 없으면 더 이상 진행하지 않고 종료
}

// SQL 쿼리 준비 (Prepared Statement 사용)
$stmt = $conn->prepare("INSERT INTO SPPB_Normal (G_X, G_Y, G_Z, A_X, A_Y, A_Z, Time) VALUES (?, ?, ?, ?, ?, ?, NOW())");

// 파라미터 바인딩
$stmt->bind_param("dddddd", $g_x, $g_y, $g_z, $a_x, $a_y, $a_z);  // "dddddd"는 6개의 float 타입을 의미

// 쿼리 실행 및 에러 확인
if ($stmt->execute()) {
    echo "Data inserted successfully";
} else {
    // SQL 오류가 발생하면 상세하게 출력
    echo "Error: " . $stmt->error;
    error_log("SQL Error: " . $stmt->error);  // 서버 로그에 SQL 에러 기록
}

// 연결 종료
$stmt->close();
$conn->close();
?>
