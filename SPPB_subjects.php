<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

// config.php 파일을 포함하여 데이터베이스 연결을 가져옴
include_once('config.php');

// 연결 확인
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// 요청 데이터 가져오기
$username = isset($_GET['name']) ? $_GET['name'] : null;
$birthdate = isset($_GET['birthdate']) ? $_GET['birthdate'] : null;
$height = isset($_GET['height']) ? $_GET['height'] : null;
$weight = isset($_GET['weight']) ? $_GET['weight'] : null;
$gender = isset($_GET['gender']) ? $_GET['gender'] : null;
$id = isset($_GET['id']) ? $_GET['id'] : null;

// 받은 값 중 하나라도 NULL이면 에러 처리
if ($username === NULL || $birthdate === NULL || $height === NULL || $weight === NULL || $gender === NULL || $id === NULL) {
    echo json_encode(array('status' => 'error', 'message' => 'Missing parameter.'));
    exit;
}

// 이름과 생년월일이 동일한 사용자 존재 여부 체크
$check_sql = "SELECT * FROM SPPB_subjects WHERE username = ? AND birthdate = ? AND id = ?";
$check_stmt = $conn->prepare($check_sql);
$check_stmt->bind_param("sss", $username, $birthdate, $id);
$check_stmt->execute();
$check_result = $check_stmt->get_result();

if ($check_result->num_rows > 0) {
    // 동일한 이름과 생년월일이 존재하면 기존 데이터를 업데이트
    $update_sql = "UPDATE SPPB_subjects SET height = ?, weight = ?, gender = ? WHERE username = ? AND birthdate = ? AND id = ?";
    $update_stmt = $conn->prepare($update_sql);
    $update_stmt->bind_param("ddssss", $height, $weight, $gender, $username, $birthdate, $id);

    if ($update_stmt->execute()) {
        $response = array('status' => 'success', 'message' => 'Data updated successfully');
        echo json_encode($response);  // 성공적으로 업데이트된 데이터에 대한 응답을 JSON 형식으로 반환
    } else {
        $response = array('status' => 'error', 'message' => 'Error: ' . $update_stmt->error);
        echo json_encode($response);  // 오류가 발생하면 오류 메시지 반환
    }

    $update_stmt->close();
    $check_stmt->close();
    $conn->close();
    exit;
} else {
    // 동일한 데이터가 없으면 새로운 사용자 데이터를 삽입
    $insert_sql = "INSERT INTO SPPB_subjects (username, birthdate, height, weight, gender, id) VALUES (?, ?, ?, ?, ?, ?)";
    $insert_stmt = $conn->prepare($insert_sql);
    $insert_stmt->bind_param("ssddsd", $username, $birthdate, $height, $weight, $gender, $id);

    if ($insert_stmt->execute()) {
        $response = array('status' => 'success', 'message' => 'Data inserted successfully');
        echo json_encode($response);  // 성공적으로 삽입된 데이터에 대한 응답을 JSON 형식으로 반환
    } else {
        $response = array('status' => 'error', 'message' => 'Error: ' . $insert_stmt->error);
        echo json_encode($response);  // 오류가 발생하면 오류 메시지 반환
    }

    $insert_stmt->close();
}

$check_stmt->close();
$conn->close();
?>
