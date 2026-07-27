# TodoApp

FastAPI + SQLAlchemy로 만든 Todo REST API. JWT 인증, 사용자별 할 일 관리, 역할 기반 관리자 기능 포함.

## 스택

- FastAPI — 웹 프레임워크
- SQLAlchemy — ORM
- Alembic — DB 마이그레이션
- SQLite (`todosapp.db`) — DB
- JWT (`python-jose`) + bcrypt (`passlib`) — 인증/비밀번호 해싱

## 실행

```bash
uvicorn main:app --reload
```

문서: http://127.0.0.1:8000/docs

## 구조

```
TodoApp/
├── main.py          # 앱 진입점, 라우터 등록, 테이블 생성
├── database.py      # DB 엔진, 세션, Base
├── models.py        # Users / Todos 테이블 모델
├── alembic.ini      # Alembic 설정 (DB URL 등)
├── alembic/         # 마이그레이션 환경
│   └── versions/    # 마이그레이션 스크립트
└── routers/
    ├── auth.py      # 회원가입, 로그인, 토큰 발급
    ├── todos.py     # 할 일 CRUD
    ├── users.py     # 내 정보, 비밀번호/전화번호 변경
    └── admin.py     # 관리자 전용 (전체 조회/삭제)
```

## 모델

**Users** — id, email, username, first_name, last_name, hashed_password, is_active, role, phone_number

**Todos** — id, title, description, priority, completed, owner_id (→ users.id)

## 엔드포인트

### auth (`/auth`)
| 메서드 | 경로 | 설명 |
|---|---|---|
| POST | `/auth/` | 회원가입 |
| POST | `/auth/token` | 로그인, JWT 발급 |

### todos
| 메서드 | 경로 | 설명 |
|---|---|---|
| GET | `/` | 내 할 일 전체 조회 |
| GET | `/todos/{todo_id}` | 할 일 단건 조회 |
| POST | `/todo` | 할 일 추가 |
| PUT | `/todo/{todo_id}` | 할 일 수정 |
| DELETE | `/todo/{todo_id}` | 할 일 삭제 |

### users (`/user`)
| 메서드 | 경로 | 설명 |
|---|---|---|
| GET | `/user/` | 내 정보 조회 |
| PUT | `/user/password` | 비밀번호 변경 |
| PUT | `/user/phonenumber/{phone_number}` | 전화번호 변경 |

### admin (`/admin`)
| 메서드 | 경로 | 설명 |
|---|---|---|
| GET | `/admin/todo` | 전체 할 일 조회 (관리자) |
| DELETE | `/admin/todo/{todo_id}` | 할 일 삭제 (관리자) |

## 인증 흐름

1. `POST /auth/` 로 가입.
2. `POST /auth/token` 으로 로그인 → JWT 받음.
3. `/docs`의 Authorize 버튼에 토큰 입력.
4. 이후 요청에서 토큰으로 사용자 식별, 본인 할 일만 접근.

## DB 마이그레이션 (Alembic)

모델(`models.py`)을 바꾼 뒤 기존 DB에 반영할 때 사용. `create_all`은 없는 테이블만 만들고 컬럼 변경은 못 하므로, 스키마 변경은 Alembic으로 처리.

각 스크립트는 `upgrade()` / `downgrade()` 한 쌍으로 구성. DB URL은 `alembic.ini`의 `sqlalchemy.url`에서 설정.

## 테스트

`pytest` + FastAPI `TestClient`로 작성. 실제 DB 대신 별도 테스트 DB(`testdb.db`)를 씀.

```bash
pytest
```

### 구조

- `test/utils.py` — 테스트 공통 설정. 테스트용 엔진/세션, `override_get_db`(테스트 DB 연결), `override_get_current_user`(인증 우회), 데이터 fixture(`test_user`, `test_todo`).
- `test/test_*.py` — 라우터별 테스트. 각 파일에서 해당 라우터의 `get_db` / `get_current_user`를 `dependency_overrides`로 교체.

### 핵심 개념

- **의존성 오버라이드** — `app.dependency_overrides[get_db] = override_get_db` 로 실제 DB/인증을 테스트용으로 바꿈. 라우터마다 `get_db`가 따로라 파일별로 해당 라우터 것을 교체해야 함.
- **fixture** — DB 조회하는 테스트는 `test_user` / `test_todo`를 인자로 받아야 데이터가 삽입됨. 테스트 후 자동 삭제.
- **async 테스트** — `get_current_user` 등 async 함수 직접 호출 테스트는 `pytest-asyncio` 필요.

## 메모

- SQLite 파일은 첫 실행 시 자동 생성.
- 팀 작업 시: 동료가 마이그레이션 push → `git pull` 후 `alembic upgrade head`로 스키마 동기화.
