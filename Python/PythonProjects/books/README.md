# Books API (Project 1)

FastAPI 기초. dict 리스트로 책 데이터 관리, 검증 없음.

## 실행

```bash
uvicorn books:app --reload
```

문서: http://127.0.0.1:8000/docs

## 엔드포인트

| 메서드 | 경로 | 설명 |
|---|---|---|
| GET | `/books` | 전체 조회 |
| GET | `/books/{book_title}` | 제목으로 조회 |
| GET | `/books/?category=` | 카테고리로 조회 (쿼리) |
| GET | `/books/byauthor/{author}` | 저자로 조회 (경로) |
| GET | `/books/{book_author}/?category=` | 저자+카테고리 조회 |
| POST | `/books/create_book` | 추가 (Body) |
| PUT | `/books/update_book` | 수정 (Body) |
| DELETE | `/books/delete_book/{book_title}` | 삭제 |

## 메모

- 데이터는 메모리(`BOOKS` 리스트) — 재시작 시 초기화.
- Pydantic 검증 없음. 다음 단계는 `books2`.
