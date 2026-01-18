(ns clj-blog.core
  "Main entry point for the blog application"
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.reload :refer [wrap-reload]]
            [clj-blog.routes :refer [app-routes]]
            [clj-blog.db :as db])
  (:gen-class))

(def app
  "Main Ring application with middleware"
  (-> app-routes
      (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false))))

(def dev-app
  "Development app with auto-reload"
  (-> #'app
      wrap-reload))

(defn start-server
  "Start the web server"
  [& {:keys [port join?]
      :or {port 3000 join? true}}]
  (println "====================================")
  (println "   Clojure Blog Server Starting")
  (println "====================================")
  (println)
  (println "  Running on JVM:" (System/getProperty "java.version"))
  (println "  Clojure version:" (clojure-version))
  (println)

  ;; Seed sample posts
  (db/seed-sample-posts!)
  (println "  Sample posts loaded:" (count (db/get-all-posts)) "posts")
  (println)

  (println (str "  Server starting on http://localhost:" port))
  (println)
  (println "  Press Ctrl+C to stop the server")
  (println "====================================")

  (run-jetty dev-app {:port port :join? join?}))

(defn -main
  "Main function - entry point"
  [& args]
  (let [port (if (seq args)
               (parse-long (first args))
               3000)]
    (start-server :port port)))
