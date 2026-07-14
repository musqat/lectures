# Favorite Places

내가 좋아하는 장소를 사진·위치와 함께 저장하고 목록으로 관리하는 Flutter 앱.
Flutter 학습용 프로젝트.

## 기능

- `+` 버튼으로 새 장소 추가 (제목 입력 + 사진 촬영 + 위치 선택)
- 카메라로 사진을 찍어 장소 이미지로 등록
- 현재 위치를 GPS로 가져오거나, 지도에서 직접 위치 선택
- 좌표를 주소로 변환(역지오코딩)해 함께 저장
- 장소 목록에서 항목 선택 시 상세 화면으로 이동
- 상세 화면에서 사진, 지도 미리보기, 주소 확인
- 앱을 껐다 켜도 저장된 장소 유지 (로컬 DB에 영구 저장)

## 화면 구성

- `PlacesScreen` — 저장된 장소 목록 화면. 시작 시 DB에서 불러오기 (ConsumerStatefulWidget + FutureBuilder)
- `AddPlaceScreen` — 제목·사진·위치를 입력받는 장소 추가 화면 (ConsumerStatefulWidget)
- `PlaceDetailScreen` — 사진·지도·주소를 보여주는 상세 화면
- `MapScreen` — Google 지도에서 위치를 선택/확인하는 화면
- `ImageInput` — 카메라 촬영 위젯
- `LocationInput` — 현재 위치 가져오기 / 지도 선택 위젯
- `PlacesList` — 장소 목록 위젯
- `Place` / `PlaceLocation` — 장소·위치 데이터 모델

## 배운 개념

- **Riverpod** 상태 관리 (`ProviderScope`, `StateNotifierProvider`, `ConsumerWidget`, `ConsumerStatefulWidget`)
- `StateNotifier` 로 장소 목록을 불변(immutable) 방식으로 갱신
- 앱 시작 시 `FutureBuilder` 로 비동기 DB 로딩 처리
- **sqflite** 로 로컬 SQLite DB에 장소 영구 저장 (`getDatabasesPath`, `openDatabase`, `onCreate`, `query`, `insert`)
- `path_provider` / `path` 로 파일 경로 처리 및 촬영 이미지를 앱 문서 폴더로 복사해 영구 보관
- **image_picker** 로 카메라 촬영, `dart:io` 의 `File` 로 이미지 파일 다루기
- **location** 패키지로 기기 위치 서비스·권한 처리 후 현재 좌표 획득
- **http** 로 Google Geocoding API 호출 → 좌표를 주소로 역지오코딩
- **google_maps_flutter** 로 지도 표시·마커·위치 선택
- Google Maps Static API 로 상세 화면의 지도 미리보기 이미지 생성
- `uuid` 로 각 장소의 고유 id 생성

## 실행 방법

```bash
flutter pub get
flutter run
```

> Google Maps / Geocoding 기능을 쓰려면 소스의 `YOUR_API_KEY` 자리에 본인의 Google Maps API 키를 넣어야 합니다.
> (`lib/widgets/location_input.dart`, `lib/screens/place_detail.dart`)

## 프로젝트 정보

- 패키지 이름: `favorite_places_app`
- Android ID: `com.example.favorite_places_app`
- 주요 패키지: `flutter_riverpod`, `sqflite`, `path_provider`, `image_picker`, `location`, `google_maps_flutter`, `http`, `uuid`
