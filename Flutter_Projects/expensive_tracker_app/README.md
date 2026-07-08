# Expense Tracker

지출 내역을 등록하고 카테고리별 차트와 목록으로 확인하는 Flutter 앱.
Flutter 학습용 프로젝트.

## 기능

- 지출 추가 (제목, 금액, 날짜, 카테고리 입력)
- 하단 모달 시트로 새 지출 입력 폼 표시
- 카테고리별(식비/여행/여가/업무) 막대 차트 표시
- 지출 목록에서 좌우 스와이프로 삭제, 스낵바로 실행 취소(Undo) 가능
- 화면 너비에 따라 좁은 화면은 세로 배치, 넓은 화면은 가로 배치로 반응형 처리
- 라이트/다크 모드 테마 지원

## 화면 구성

- `Expenses` — 지출 목록/차트를 관리하는 루트 위젯 (StatefulWidget)
- `NewExpense` — 지출 입력 폼, 유효성 검사 및 다이얼로그 표시 (StatefulWidget)
- `Chart` / `ChartBar` — 카테고리별 합계를 막대 그래프로 표시
- `ExpensesList` / `ExpensesItem` — 지출 목록과 개별 항목 카드
- `Expense` / `ExpenseBucket` — 지출 데이터 모델 및 카테고리별 그룹핑

## 배운 개념

- `StatelessWidget` vs `StatefulWidget`
- `setState()` 로 목록 추가/삭제 상태 관리
- 위젯 간 콜백 함수 전달 (`onAddExpense`, `onRemoveExpense`)
- `showModalBottomSheet`, `showDatePicker`, `showDialog`/`showCupertinoDialog` 등 다이얼로그/오버레이 처리
- `TextEditingController` 와 폼 유효성 검사
- `enum` 을 활용한 카테고리 정의
- 커스텀 모델 클래스 설계 (`Expense`, `ExpenseBucket`)
- `MediaQuery`, `LayoutBuilder` 로 반응형 레이아웃 구성
- `ThemeData` 를 이용한 라이트/다크 테마 설정
- 외부 패키지 사용: `uuid` (고유 ID 생성), `intl` (날짜 포맷)

## 실행 방법

```bash
flutter pub get
flutter run
```

## 프로젝트 정보

- 패키지 이름: `expensive_tracker_app`
- Android ID: `com.example.expensive_tracker_app`
