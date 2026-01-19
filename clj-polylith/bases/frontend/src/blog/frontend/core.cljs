(ns blog.frontend.core
  (:require [blog.frontend.view :as view]))

(defn ^:private fetch-posts []
  (-> (js/fetch "http://localhost:8080")
      (.then (fn [resp]
               (if (.-ok resp)
                 (.json resp)
                 (throw (js/Error. (str "HTTP error: " (.-status resp)))))))
      (.then (fn [^js data]
               (js->clj (.-posts data) :keywordize-keys true)))))

(defn init []
  (-> (fetch-posts)
      (.then view/render-posts)
      (.catch (fn [err]
                (js/console.error "Failed to load posts" err)))))
