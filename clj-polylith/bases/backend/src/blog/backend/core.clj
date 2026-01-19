(ns blog.backend.core
  (:require [blog.backend.handler :as handler]
            [ring.adapter.jetty :as jetty])
  (:gen-class))

(defonce server (atom nil))

(defn start! []
  (reset! server
          (jetty/run-jetty handler/routes {:port 8080 :join? false}))
  (println "Backend running on http://localhost:8080"))

(defn stop! []
  (when-let [instance @server]
    (.stop instance)
    (reset! server nil)))

(defn -main [& _args]
  (start!))
