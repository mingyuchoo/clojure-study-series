(ns blog.domain.post)

(defn sample-posts []
  [{:id 1
    :title "Hello, Polylith"
    :body "First post from the JVM 25 monorepo."}
   {:id 2
    :title "ClojureScript + Bun"
    :body "Frontend compiled by shadow-cljs, served with Bun."}])
