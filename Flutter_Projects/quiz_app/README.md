# Quiz App

여러 문제에 순서대로 답하고 마지막에 결과(맞춘 개수)를 확인하는 간단한 Flutter 앱.
Flutter 학습용 프로젝트.

## 기능

- 시작 화면에서 **Start Quiz** 버튼을 누르면 문제 풀이 시작
- 문제와 선택지를 보여주고, 답을 고르면 다음 문제로 이동
- 마지막 문제까지 답하면 결과 화면으로 전환, 맞춘 개수 표시
- 문제별 요약(선택한 답 / 정답) 확인 가능

## 화면 구성

- `StartScreen` — 로고 + 시작 버튼 (StatelessWidget)
- `QuestionsScreen` — 현재 문제 + 선택지 버튼 (StatefulWidget)
- `AnswerButton` — 선택지 버튼 (StatelessWidget)
- `ResultScreen` — 점수 + 문제별 요약 (StatelessWidget)
- `Quiz` — 화면 전환과 답변 상태를 관리하는 루트 위젯 (StatefulWidget)

## 배운 개념

- `StatelessWidget` vs `StatefulWidget`
- `setState()` 로 화면 전환 및 상태 갱신
- 위젯 간 콜백 함수 전달 (부모 → 자식, 자식 → 부모)
- `List`, `Map` 등 컬렉션 다루기 (`.map()`, `.where()`)
- 커스텀 모델 클래스 설계 (`QuizQuestion`)
- `assets` (이미지) 등록과 `Image.asset` 사용
- `LinearGradient` 배경
- `google_fonts` 패키지로 폰트 적용

## 실행 방법

```bash
flutter pub get
flutter run
```

## 프로젝트 정보

- 패키지 이름: `quiz_app`
- Android ID: `com.example.quiz_app`
- 로고 이미지: `assets/images/quiz-logo.png`
