# Meals App

카테고리별로 음식 레시피를 둘러보고 즐겨찾기, 필터링할 수 있는 Flutter 앱.
Flutter 학습용 프로젝트.

## 기능

- 카테고리 그리드에서 카테고리 선택 → 해당 카테고리 음식 목록 표시
- 음식 상세 화면에서 재료, 조리 단계, 소요 시간, 난이도, 가격대 확인
- 하트 아이콘으로 즐겨찾기 추가/삭제, 별도 탭에서 즐겨찾기 목록 확인
- 글루텐프리/유제품프리/채식/비건 등 조건으로 음식 필터링
- Drawer 메뉴로 Meals / Filters 화면 전환
- 하단 네비게이션으로 Categories / Favorites 탭 전환

## 화면 구성

- `TapsScreen` — 하단 탭(Categories/Favorites)과 Drawer를 관리하는 루트 화면 (ConsumerStatefulWidget)
- `CategoriesScreen` — 카테고리 그리드 화면
- `MealsScreen` — 선택된 카테고리(또는 즐겨찾기)의 음식 목록
- `MealDetailsScreen` — 음식 상세 정보 화면
- `FiltersScreen` — 필터 On/Off 설정 화면 (ConsumerWidget)
- `MainDrawer` — Drawer 메뉴 위젯
- `Meal` / `Category` — 음식/카테고리 데이터 모델

## 배운 개념

- **Riverpod** 상태 관리 (`ProviderScope`, `Provider`, `StateNotifierProvider`, `ConsumerWidget`, `ConsumerStatefulWidget`)
- `ref.watch()` / `ref.read()` 로 상태 구독 및 갱신
- 여러 Provider 조합 (`filteredMealsProvider` 가 `mealsProvider` + `filtersProvider` 를 조합해 파생 상태 생성)
- `StateNotifier` 로 즐겨찾기·필터 상태를 불변(immutable) 방식으로 갱신
- `enum` 을 활용한 필터/난이도/가격대 정의
- `Navigator.push` 를 통한 화면 전환과 결과값 반환
- `Drawer`, `BottomNavigationBar` 등 내비게이션 UI 구성
- `ThemeData`, `GoogleFonts` 를 이용한 전역 테마 설정
- 외부 패키지 사용: `flutter_riverpod` (상태 관리), `transparent_image` (이미지 로딩)

## 실행 방법

```bash
flutter pub get
flutter run
```

## 프로젝트 정보

- 패키지 이름: `meals_app`
- Android ID: `com.example.meals_app`
