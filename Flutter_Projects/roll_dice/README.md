# Roll Dice

버튼을 누르면 주사위(1~6)가 무작위로 굴러가는 간단한 Flutter 앱.
Flutter 학습용 프로젝트.

## 기능

- **Roll dice** 버튼을 누르면 1~6 사이 랜덤 값이 나옴
- 결과에 맞는 주사위 이미지 표시
- 그라디언트 배경

## 화면 구성

- `GradientContainer` — 그라디언트 배경 컨테이너
- `DiceRoller` — 주사위 이미지 + 버튼 (StatefulWidget)

## 배운 개념

- `StatelessWidget` vs `StatefulWidget`
- `setState()` 로 UI 갱신
- `dart:math` 의 `Random` 으로 난수 생성
- `assets` (이미지) 등록과 `Image.asset` 사용
- `LinearGradient` 배경

## 실행 방법

```bash
flutter pub get
flutter run
```

## 프로젝트 정보

- 패키지 이름: `roll_dice`
- Android ID: `com.example.roll_dice`
- 주사위 이미지: `assets/images/dice-1.png` ~ `dice-6.png`
