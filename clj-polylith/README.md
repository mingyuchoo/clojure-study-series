# Polylith Blog Workspace

JVM 25 + Clojure + Polylith monorepo for a simple blog service split into `frontend` and `backend`.

## Structure

- `components/` reusable domain/UI/backend pieces
- `bases/` runtime entry points
- `projects/` runnable deployable projects

## Requirements

- JDK 25 (see `.java-version`)
- Clojure CLI
- Bun (for frontend tooling)

## Run backend

```sh
cd projects/backend
clojure -M:run
```

## Run frontend

```sh
cd projects/frontend
bun install
bun run dev
```

Then open `http://localhost:8080` for the API and `http://localhost:3000` (shadow-cljs default) for the UI.
