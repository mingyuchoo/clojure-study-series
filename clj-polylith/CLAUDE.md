# CLAUDE.md

이 파일은 Claude Code (claude.ai/code)가 이 저장소의 코드를 다룰 때 참고하는 가이드입니다.

## 프로젝트 개요

이 프로젝트는 **Polylith 모노레포**입니다 - Polylith 아키텍처 패턴을 보여주는 JVM 25 + Clojure 풀스택 블로그 애플리케이션입니다. 백엔드는 Ring/Jetty를 통해 JSON API를 제공하고, 프론트엔드는 shadow-cljs로 컴파일되는 ClojureScript입니다.

## 빌드 및 실행 명령어

### 백엔드
```sh
cd projects/backend
clojure -M:run
# http://localhost:8080에서 Jetty 서버 시작
```

### 프론트엔드
```sh
cd projects/frontend
bun install
bun run dev      # 워치 모드, http://localhost:3000에서 서빙
bun run build    # 프로덕션 릴리스
```

## 아키텍처

**Polylith 패턴:** 컴포넌트 → 베이스 → 프로젝트

```
components/blog/        # 재사용 가능한 조각들
├── domain/            # 공유 도메인 로직 (샘플 데이터가 있는 post.clj)
├── backend/           # Ring HTTP 핸들러 (handler.clj)
└── frontend/          # 뷰 렌더링 (view.cljs)

bases/                  # 런타임 진입점 (연결 레이어)
├── backend/           # core.clj - Jetty 서버 시작
└── frontend/          # core.cljs - 앱 초기화, API에서 데이터 가져오기

projects/               # 배포 가능한 아티팩트
├── backend/           # :run alias가 있는 deps.edn
└── frontend/          # shadow-cljs.edn + package.json
```

**데이터 흐름:** 프론트엔드(ClojureScript)가 백엔드(Ring)에서 `/posts`를 가져옴 → 백엔드는 `blog.domain.post`에서 JSON 반환 → 프론트엔드가 HTML 렌더링.

## 주요 파일

- `workspace.edn` - Polylith 워크스페이스 설정 (최상위 네임스페이스: `blog`)
- `projects/backend/deps.edn` - 백엔드 의존성, `:run` alias 진입점
- `projects/frontend/shadow-cljs.edn` - ClojureScript 빌드 설정
- `components/blog/domain/src/blog/domain/post.clj` - 샘플 게시물이 있는 도메인 모델
- `components/blog/backend/src/blog/backend/handler.clj` - Ring 라우트, CORS 헤더
- `components/blog/frontend/src/blog/frontend/view.cljs` - 게시물에서 HTML 렌더링

## 기술 스택

- **Clojure 1.11.2** / **ClojureScript 1.11.132**
- **Ring 1.12.1** Jetty 어댑터 포함 (백엔드 HTTP)
- **shadow-cljs 2.28.10** (ClojureScript 컴파일러)
- **Bun** (프론트엔드 패키지 매니저)
- **JDK 25** (`.java-version` 참조)
