# Chat App

Firebase 기반 실시간 채팅 Flutter 앱.
Flutter 학습용 프로젝트.

## 기능

- 이메일/비밀번호 회원가입 및 로그인 (Firebase Authentication)
- 회원가입 시 프로필 이미지 촬영 및 업로드 (카메라 → Firebase Storage)
- 실시간 채팅 메시지 목록 표시 및 전송 (Cloud Firestore)
- 같은 사용자가 연속으로 보낸 메시지는 말풍선을 묶어서 표시
- 로그인 상태에 따라 스플래시/로그인/채팅 화면 자동 전환
- 푸시 알림을 위한 FCM 토큰 발급 (Firebase Cloud Messaging)
- 로그아웃 기능

## 화면 구성

- `App` (main.dart) — 로그인 상태(`authStateChanges`)에 따라 화면 분기하는 루트 위젯
- `SplashScreen` — 인증 상태 확인 중 표시되는 화면
- `AuthScreen` — 로그인/회원가입 폼 화면 (StatefulWidget)
- `ChatScreen` — 채팅 목록 + 메시지 입력창을 감싸는 화면
- `ChatMessages` — Firestore 실시간 스트림으로 메시지 목록 렌더링
- `NewMessage` — 메시지 입력 및 전송
- `MessageBubble` — 말풍선 UI (named constructor `.first` / `.next` 로 연속 메시지 구분)
- `UserImagePicker` — 카메라로 프로필 이미지 촬영

## 배운 개념

- **Firebase Authentication** — 이메일/비밀번호 회원가입, 로그인, 로그아웃
- **Cloud Firestore** — `collection().snapshots()` 로 실시간 데이터 구독, `add()`/`get()`/`set()` 으로 문서 읽고 쓰기
- **Firebase Storage** — 이미지 업로드(`putFile`) 및 다운로드 URL 조회(`getDownloadURL`)
- **Firebase Cloud Messaging** — 알림 권한 요청 및 디바이스 토큰 발급
- `StreamBuilder` 로 인증 상태/채팅 메시지 등 실시간 스트림을 UI에 반영
- Named constructor (`MessageBubble.first`, `MessageBubble.next`) 로 같은 클래스의 다른 생성 패턴 정의
- `image_picker` 로 카메라 접근 및 이미지 촬영
- `Form`, `GlobalKey<FormState>` 를 이용한 로그인/회원가입 폼 유효성 검사
- `FlutterFire CLI` 로 프로젝트에 Firebase 연동 설정

## 실행 방법

```bash
flutter pub get
flutter run
```

Firebase 프로젝트 연동이 필요합니다. `lib/firebase_options.dart`, `android/app/google-services.json` (iOS는 `ios/Runner/GoogleService-Info.plist`)은 프로젝트별 설정값이라 `.gitignore` 처리돼 있고 저장소에 포함돼 있지 않습니다. 아래 명령으로 본인 Firebase 프로젝트를 연결해서 직접 생성해야 합니다.

```bash
dart pub global activate flutterfire_cli
flutterfire configure
```

## 프로젝트 정보

- 패키지 이름: `chat_app`
- Android ID: `com.example.chat_app`
- 백엔드: Firebase (Authentication, Firestore, Storage, Cloud Messaging)
