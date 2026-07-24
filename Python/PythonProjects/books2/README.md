# Books API (Project 2)

`books`에 Pydantic 검증을 더한 버전. 데이터 검증, 상태 코드, 예외 처리 포함.

## 실행

```bash
uvicorn books:app --reload
```

문서: http://127.0.0.1:8000/docs

## 엔드포인트

| 메서드 | 경로 | 설명 |
|---|---|---|
| GET | `/books` | 전체 조회 |
| GET | `/books/{book_id}` | id로 조회 |
| GET | `/books/?book_rating=` | 평점으로 조회 |
| GET | `/books/publish/?published_date=` | 출판연도로 조회 |
| POST | `/create_book` | 추가 |
| PUT | `/books/update_book` | 수정 |
| DELETE | `/books/{book_id}` | 삭제 |

## books 대비 추가된 것

- `BookRequest` (Pydantic `BaseModel`) + `Field`로 입력 검증 (min_length, gt/lt 등).
- `status_code`로 응답 코드 명시 (201, 204 등).
- 못 찾으면 `HTTPException` 404.
- `Path`, `Query`로 경로/쿼리 파라미터 검증.

## 메모

- 데이터는 메모리(`BOOKS` 리스트) — 재시작 시 초기화.
