# learn-nextjs14

[노마드코더 NextJS for Beginners](https://nomadcoders.co/nextjs-for-beginners) 강의 실습. Next.js 14 App Router 로 영화 정보 앱을 만들며 기초를 익힙니다.

## Stack

- **Next.js** 14.0.4 (App Router)
- **React** 18
- **TypeScript**

## 다루는 내용

- App Router 구조와 라우팅 (route groups: `(home)`, `(movies)`)
- 서버 컴포넌트 / 클라이언트 컴포넌트
- `layout` · `loading` · 동적 라우트 (`[id]`)
- 데이터 패칭 (영화 API) 과 `Suspense` 기반 스트리밍
- `metadata` 로 SEO 처리
- `about-us` 정적 페이지

## 폴더

| 경로 | 내용 |
|------|------|
| `app/(home)` | 홈 — 영화 목록 |
| `app/(movies)` | 영화 상세 / 동적 라우트 |
| `app/about-us` | 소개 페이지 |
| `components` | 재사용 컴포넌트 |
| `styles` | 스타일 |

## 실행

```bash
npm install
npm run dev      # http://localhost:3000
```

```bash
npm run build && npm run start   # 프로덕션
```
