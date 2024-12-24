#include <ESP8266WiFi.h>          
#include <ESP8266HTTPClient.h>    
#include <Adafruit_MPU6050.h>    
#include <Adafruit_Sensor.h>     
#include <Wire.h>               

WiFiClient wifiClient;  // WiFiClient 객체를 사용하여 서버와 연결
Adafruit_MPU6050 mpu;   // MPU6050 센서 객체 생성

char ssid[] = "iptimea704";   
char pass[] = "rodemeng2019"; 

void setup() {
  Serial.begin(115200);                         
  Serial.println("Initializing connection...");
  
  WiFi.begin(ssid, pass);
  while (WiFi.status() != WL_CONNECTED) {
    delay(200);         
    Serial.print(".");  
  }

  Serial.println("WiFi Connected"); 
  Serial.print("Assigned IP: ");
  Serial.println(WiFi.localIP());   
  
  if (!mpu.begin()) {
    Serial.println("Failed to find MPU6050 chip");  
    while (1) {
      delay(10); 
    }
  }
  Serial.println("MPU6050 Found!"); 
  
  // 가속도계 범위 설정 (측정할 수 있는 최대 범위 지정 / 8G로 설정)
  mpu.setAccelerometerRange(MPU6050_RANGE_8_G);
  Serial.print("Accelerometer range set to: ");
  switch (mpu.getAccelerometerRange()) {
  case MPU6050_RANGE_2_G:
    Serial.println("+-2G");
    break;
  case MPU6050_RANGE_4_G:
    Serial.println("+-4G");
    break;
  case MPU6050_RANGE_8_G:
    Serial.println("+-8G");
    break;
  case MPU6050_RANGE_16_G:
    Serial.println("+-16G");
    break;
  }
  
  // 자이로 범위 설정 (회전 속도 측정 범위 / 500도/s로 설정)
  mpu.setGyroRange(MPU6050_RANGE_500_DEG);
  Serial.print("Gyro range set to: ");
  switch (mpu.getGyroRange()) {
  case MPU6050_RANGE_250_DEG:
    Serial.println("+- 250 deg/s");
    break;
  case MPU6050_RANGE_500_DEG:
    Serial.println("+- 500 deg/s");
    break;
  case MPU6050_RANGE_1000_DEG:
    Serial.println("+- 1000 deg/s");
    break;
  case MPU6050_RANGE_2000_DEG:
    Serial.println("+- 2000 deg/s");
    break;
  }

  // 필터 대역폭 설정 (노이즈를 걸러주는 필터 설정 / 5Hz로 설정)
  mpu.setFilterBandwidth(MPU6050_BAND_5_HZ);
  Serial.print("Filter bandwidth set to: ");
  switch (mpu.getFilterBandwidth()) {
  case MPU6050_BAND_260_HZ:
    Serial.println("260 Hz");
    break;
  case MPU6050_BAND_184_HZ:
    Serial.println("184 Hz");
    break;
  case MPU6050_BAND_94_HZ:
    Serial.println("94 Hz");
    break;
  case MPU6050_BAND_44_HZ:
    Serial.println("44 Hz");
    break;
  case MPU6050_BAND_21_HZ:
    Serial.println("21 Hz");
    break;
  case MPU6050_BAND_10_HZ:
    Serial.println("10 Hz");
    break;
  case MPU6050_BAND_5_HZ:
    Serial.println("5 Hz");
    break;
  }
}

void loop() {
  sensors_event_t a, g, temp;   // 가속도계, 자이로, 온도 데이터 저장 변수
  mpu.getEvent(&a, &g, &temp);  // 가속도계, 자이로, 온도 데이터 값 읽기

  float a_x = a.acceleration.x;
  float a_y = a.acceleration.y;
  float a_z = a.acceleration.z;

  Serial.print("Accelerometer x = ");
  Serial.println(a_x);
  Serial.print("Accelerometer y = ");
  Serial.println(a_y);
  Serial.print("Accelerometer z = ");
  Serial.println(a_z);

  float g_x = g.gyro.x;
  float g_y = g.gyro.y;
  float g_z = g.gyro.z;

  Serial.print("Gyro x = ");
  Serial.println(g_x);
  Serial.print("Gyro y = ");
  Serial.println(g_y);
  Serial.print("Gyro z = ");
  Serial.println(g_z);

  // Wi-Fi 연결 상태 확인 후 HTTP 요청
  if (WiFi.status() == WL_CONNECTED) {
    HTTPClient http;  

    String serverPath = "http://www.all-tafp.org/SPPB_insert.php?G_X=" + 
                          String(g_x) + "&G_Y=" + String(g_y) + "&G_Z=" + String(g_z) + 
                          "&A_X=" + String(a_x) + "&A_Y=" + String(a_y) + "&A_Z=" + String(a_z);

    // URL 출력
    Serial.print("Request URL: ");
    Serial.println(serverPath);

    http.begin(wifiClient, serverPath);  // HTTP 요청 초기화
    http.setTimeout(10000);              // 타임아웃 설정 (10초)
    
    int httpResponseCode = http.GET();  // GET 요청

    // 응답 코드 확인
    Serial.print("HTTP Response Code: ");
    Serial.println(httpResponseCode);

    if (httpResponseCode > 0) {
      // 성공
      String response = http.getString();               // 서버에서 받은 응답 내용
      Serial.println("Server response: " + response);   // 서버 응답 출력
    } else {
      Serial.println("Error in HTTP request");          // 오류 발생 시 메시지 출력
      Serial.print("Error code: ");                     // 오류 코드 출력
      Serial.println(http.errorToString(httpResponseCode).c_str());
    }

    // HTTP 연결 종료
    http.end();
  } else {
    Serial.println("WiFi not connected");
  }

  delay(1000);  
}