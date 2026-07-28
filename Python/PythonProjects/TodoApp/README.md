# TodoApp

FastAPI + SQLAlchemy로 만든 Todo 웹앱. JWT 인증, 사용자별 할 일 관리, 역할 기반 관리자 기능, Jinja2 기반 프론트엔드 페이지 포함.

## 스택

- FastAPI — 웹 프레임워크
- SQLAlchemy — ORM
- Alembic — DB 마이그레이션
- SQLite (`todosapp.db`) — DB
- JWT (`python-jose`) + bcrypt (`passlib`) — 인증/비밀번호 해싱
- Jinja2 + Bootstrap — 서버 렌더링 프론트엔드
- pytest — 테스트

## 실행

상대 import(`from .models`)를 쓰므로 **상위 폴더(`PythonProjects`)에서 패키지로 실행**한다.

```bash
uvicorn TodoApp.main:app --reload
```

- 웹: http://127.0.0.1:8000/ (→ `/todos/todo-page` 리다이렉트)
- API 문서: http://127.0.0.1:8000/docs

## 구조

```
TodoApp/
├── main.py          # 앱 진입점, 라우터 등록, static 마운트, 테이블 생성
├── database.py      # DB 엔진, 세션, Base
├── models.py        # Users / Todos 테이블 모델
├── alembic.ini      # Alembic 설정 (DB URL 등)
├── alembic/         # 마이그레이션 환경 (versions/에 스크립트)
├── routers/
│   ├── auth.py      # 회원가입, 로그인, 토큰 발급 + 로그인/가입 페이지
│   ├── todos.py     # 할 일 CRUD + 할 일 페이지
│   ├── users.py     # 내 정보, 비밀번호/전화번호 변경
│   └── admin.py     # 관리자 전용 (전체 조회/삭제)
├── templates/       # Jinja2 페이지 (layout 상속 구조)
├── static/          # css / js (base.js가 폼 제출을 fetch로 처리)
└── test/            # pytest 테스트
```

## 모델

**Users** — id, email, username, first_name, last_name, hashed_password, is_active, role, phone_number

**Todos** — id, title, description, priority, complete, owner_id (→ users.id)

## 페이지 (Jinja2)

| 경로 | 설명 |
|---|---|
| `/` | `/todos/todo-page`로 리다이렉트 |
| `/auth/login-page` | 로그인 |
| `/auth/register-page` | 회원가입 |
| `/todos/todo-page` | 내 할 일 목록 |
| `/todos/add-todo-page` | 할 일 추가 |
| `/todos/edit-todo-page/{todo_id}` | 할 일 수정 |

페이지 인증은 **쿠키의 JWT**(`access_token`)로 확인. 토큰 없으면 로그인 페이지로 리다이렉트.

## API 엔드포인트

### auth (`/auth`)
| 메서드 | 경로 | 설명 |
|---|---|---|
| POST | `/auth/` | 회원가입 |
| POST | `/auth/token` | 로그인, JWT 발급 |

### todos (`/todos`)
| 메서드 | 경로 | 설명 |
|---|---|---|
| GET | `/todos/` | 내 할 일 전체 조회 |
| GET | `/todos/todo/{todo_id}` | 단건 조회 |
| POST | `/todos/todo` | 추가 |
| PUT | `/todos/todo/{todo_id}` | 수정 |
| DELETE | `/todos/todo/{todo_id}` | 삭제 |

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

모든 todos/users/admin 엔드포인트는 인증 필요. 본인 소유 할 일만 접근하며, admin은 role이 admin일 때만 허용.

## 인증 흐름

- **웹 페이지** — 로그인 시 받은 JWT를 쿠키에 저장. 페이지 요청마다 쿠키에서 읽어 사용자 식별.
- **API 직접 호출** — `POST /auth/token`으로 토큰 받아 `Authorization: Bearer <token>` 헤더로 전달. `/docs`에선 Authorize 버튼에 입력.

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
