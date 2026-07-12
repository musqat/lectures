# Shopping List App

Firebase Realtime Database와 연동해 장보기 목록을 관리하는 Flutter 앱.
Flutter 학습용 프로젝트.

## 기능

- 앱 실행 시 Firebase에 저장된 항목 목록 불러오기 (`GET`)
- `+` 버튼으로 새 항목 화면 이동, 이름/수량/카테고리 입력 후 Firebase에 저장 (`POST`)
- 항목을 좌우로 스와이프해서 삭제 (`DELETE`), 실패 시 목록 원복
- 카테고리별 색상 표시 (야채/과일/고기/유제품 등)
- 데이터 로딩 중 로딩 스피너, 에러 발생 시 에러 메시지, 빈 목록일 때 안내 문구 표시

## 화면 구성

- `GroceryList` — 목록 로딩/추가/삭제를 관리하는 루트 화면 (StatefulWidget)
- `NewItem` — 이름/수량/카테고리를 입력하는 폼 화면 (StatefulWidget)
- `GroceryItem` — 장보기 항목 데이터 모델
- `Category` — 카테고리 데이터 모델(이름 + 색상)

## 배운 개념

- `Future`, `async`/`await` 로 비동기 네트워크 요청 처리
- `FutureBuilder` 로 로딩/에러/데이터 상태에 따라 다른 UI 렌더링
- `http` 패키지로 REST API 호출 (`get`, `post`, `delete`)
- `json.encode`/`json.decode` 로 Dart 객체 ↔ JSON 변환 (커스텀 객체는 직렬화 전에 원시 타입으로 변환 필요)
- `Form`, `TextFormField`, `GlobalKey<FormState>` 를 이용한 폼 유효성 검사
- `DropdownButtonFormField` 로 커스텀 객체 목록 선택
- `Navigator.push` 로 화면 이동 후 결과값(`GroceryItem`) 받아오기
- `Dismissible` 위젯으로 스와이프 삭제 + 실패 시 상태 롤백
- Firebase Realtime Database REST API 연동

## 실행 방법

```bash
flutter pub get
flutter run
```

## 프로젝트 정보

- 패키지 이름: `shopping_list_app`
- Android ID: `com.example.shopping_list_app`
- 백엔드: Firebase Realtime Database (REST API)
