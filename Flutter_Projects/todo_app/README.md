# Todo App (Flutter Internals Demo)

할 일 목록을 예시로 Flutter의 내부 동작(Key, 위젯 리빌드)을 실습하는 Flutter 앱.
Flutter 학습용 프로젝트.

## 기능

- 우선순위(긴급/보통/낮음)별 아이콘이 표시되는 할 일 목록
- 체크박스로 완료 여부 토글
- 정렬 버튼으로 목록 오름차순/내림차순 전환
- `ObjectKey` 를 사용해 정렬 시에도 각 항목의 위젯 상태(체크 여부)가 올바르게 유지되는지 실습
- 위젯 리빌드 시점을 확인하는 별도 데모(`UIUpdatesDemo`, `DemoButtons`) 포함

## 화면 구성

- `Keys` — 할 일 목록과 정렬 상태를 관리하는 루트 위젯 (StatefulWidget)
- `CheckableTodoItem` — 체크박스가 있는 할 일 항목 (StatefulWidget)
- `TodoItem` — 체크박스 없는 단순 할 일 항목 (StatelessWidget, 참고용)
- `DemoButtons` / `UIUpdatesDemo` — `setState()` 호출 시 어떤 위젯이 리빌드되는지 확인하는 별도 데모

## 배운 개념

- `Key` (`ObjectKey`, `ValueKey`) 로 위젯 상태를 식별자 기준으로 유지하기
- 리스트 재정렬 시 Key 없이는 상태가 엉키는 문제와 해결 방법
- `StatelessWidget` vs `StatefulWidget`
- `setState()` 호출 범위와 위젯 리빌드 관계 이해
- `enum` 을 활용한 우선순위 정의
- `List.sort()` 로 커스텀 정렬 로직 구현

## 실행 방법

```bash
flutter pub get
flutter run
```

## 프로젝트 정보

- 패키지 이름: `flutter_internals`
- Android ID: `com.example.todo_app`
