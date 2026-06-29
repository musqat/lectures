# LangChain Udemy Course

LangChain 강의를 따라가며 실습한 코드 모음입니다. `01` ~ `13` 폴더가 OpenAI API 기초부터 RAG, Agents, LangSmith 평가, 마이크로서비스 배포, LCEL 까지 순서대로 이어집니다.

> 원본 강의 repo: [Coding-Crashkurse/LangChain-Udemy-Course](https://github.com/Coding-Crashkurse/LangChain-Udemy-Course)
> 이 repo 는 강의를 들으며 최신 라이브러리에 맞게 일부 코드를 수정한 버전입니다 (아래 [Troubleshooting](#troubleshooting) 참고).

## Directories

| 폴더 | 주제 | 내용 |
|------|------|------|
| `01_OpenAI_API` | OpenAI API | 생성형 AI 앱을 위한 OpenAI API 기본 사용법 |
| `02_LangChain_Inputs_and_Outputs` | I/O | LangChain 의 입력·출력 메커니즘 |
| `03_Prompt_Templates` | Prompting | 프롬프트 템플릿과 작성 베스트 프랙티스 |
| `04_Chains` | Chains | 다양한 use case 별 Chain 구성 + Output Parser |
| `05_Callbacks` | Callbacks | 콜백으로 실행 흐름 후킹 |
| `06_Memory` | Memory | 대화 메모리 구현 (chatbot 예제 포함) |
| `07_OpenAI_Functions` | Tool Calling | Function/Tool Calling (`tool_calling.ipynb` 가 최신) |
| `08_RAG` | RAG | Retrieval Augmented Generation + FAISS 인덱스, FastAPI |
| `09_Agents` | Agents | 자율 Agent 구성 |
| `10_Hybrid_Search_and_Indexing_API` | Hybrid Search | 하이브리드 검색 + Indexing API (Postgres/pgvector) |
| `11_LangSmith` | Observability | LangSmith Tracing · Dataset · Evaluation |
| `12_MicroServiceArchitecture` | Deployment | k8s 기반 LLM 마이크로서비스 (자체 README 있음) |
| `13_LangChain_ExpressionLanguage` | LCEL | Runnable 인터페이스와 LCEL 심화 |

## Setup

```bash
# 1. 가상환경
python -m venv udemycourse
# Windows
.\udemycourse\Scripts\Activate.ps1
# macOS/Linux
source udemycourse/bin/activate

# 2. .env 준비 — .env.example 복사 후 키 입력
cp .env.example .env
```

`.env` 에 필요한 값:

```
OPENAI_API_KEY=sk-...
# LangSmith 쓸 경우 (11_LangSmith)
LANGSMITH_TRACING=true
LANGSMITH_ENDPOINT=https://api.smith.langchain.com   # APAC 워크스페이스면 https://apac.api.smith.langchain.com
LANGSMITH_API_KEY=lsv2_pt_...
LANGSMITH_PROJECT=default
```

>  실제 키가 든 `.env` 와 `12_MicroServiceArchitecture/secrets.yaml` 은 `.gitignore` 에 있습니다. 커밋하지 마세요. k8s 용은 `secrets.yaml.example` 을 복사해서 채우세요.

## Troubleshooting

강의 녹화 이후 라이브러리가 바뀌어 강의 그대로면 깨지는 부분 + 해결법:

**11_LangSmith — `LangChainStringEvaluator` ImportError**
`langsmith` 0.4+ 에서 제거됨. `openevals` 로 대체.
```python
# 옛날
from langsmith.evaluation import LangChainStringEvaluator
# 지금
from openevals.llm import create_llm_as_judge
from openevals.prompts import CORRECTNESS_PROMPT
judge = create_llm_as_judge(prompt=CORRECTNESS_PROMPT, feedback_key="correctness", model="openai:gpt-4o-mini")
```
설치: `pip install openevals`

**12 / insert_data.py — `PGVector.__init__() got an unexpected keyword argument 'connection_string'`**
새 `langchain_postgres` PGVector 는 `connection=` 사용 (옛 `connection_string=` 아님).
```python
PGVector.from_documents(docs, embeddings, collection_name=COLLECTION_NAME, connection=CONNECTION_STRING)
```

**12 / service3 CrashLoopBackOff — `No module named 'langchain_postgres'`**
service3 `Dockerfile` 의 pip install 목록에 `langchain-postgres` 추가 후 이미지 재빌드 → push → `kubectl rollout restart deployment/service3`.

## Notebook output 정리

커밋 전 노트북 출력 비우기:

```bash
# Linux/macOS
find . -name "*.ipynb" -exec jupyter nbconvert --ClearOutputPreprocessor.enabled=True --inplace {} \;
```
```bat
:: Windows (cmd)
for /r %i in (*.ipynb) do jupyter nbconvert --to notebook --ClearOutputPreprocessor.enabled=True --inplace "%i"
```
