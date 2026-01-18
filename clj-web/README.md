# Clojure Blog

Clojure, Ring, Compojure, Hiccup을 사용하여 만든 간단한 웹 블로그 서비스입니다.

## 기술 스택

- **Clojure 1.12.0** - 함수형 프로그래밍 언어
- **Ring 1.12.2** - 웹 애플리케이션 라이브러리
- **Compojure 1.7.1** - 라우팅 라이브러리
- **Hiccup 2.0.0-RC3** - HTML 템플릿 DSL
- **Jetty** - 웹 서버
- **JVM 25** - Java 런타임

## 프로젝트 구조

```
clj-web/
├── project.clj                    # Leiningen 프로젝트 설정
├── src/clj_blog/
│   ├── core.clj                   # 메인 진입점, 서버 설정
│   ├── routes.clj                 # HTTP 라우팅
│   ├── views.clj                  # Hiccup HTML 템플릿
│   └── db.clj                     # 인메모리 데이터 저장소
└── resources/public/css/
    └── style.css                  # CSS 스타일
```

## 요구사항

- Java 25+
- Leiningen 2.x

## 실행 방법

```bash
# 의존성 다운로드
lein deps

# 서버 실행 (기본 포트 3000)
lein run

# 커스텀 포트로 실행
lein run 8080
```

서버 실행 후 브라우저에서 <http://localhost:3000> 접속

## 기능

- 블로그 포스트 목록 보기
- 개별 포스트 상세 보기
- 새 포스트 작성
- 포스트 수정
- 포스트 삭제

## API 엔드포인트

| Method | Path | 설명 |
|--------|------|------|
| GET | `/` | 홈페이지 (포스트 목록) |
| GET | `/posts/new` | 새 포스트 작성 폼 |
| POST | `/posts` | 포스트 생성 |
| GET | `/posts/:id` | 포스트 상세 보기 |
| GET | `/posts/:id/edit` | 포스트 수정 폼 |
| POST | `/posts/:id` | 포스트 수정 |
| POST | `/posts/:id/delete` | 포스트 삭제 |

## 라이선스

EPL-2.0
