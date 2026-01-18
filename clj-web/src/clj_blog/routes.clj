(ns clj-blog.routes
  "Application routes"
  (:require [compojure.core :refer [defroutes GET POST context]]
            [compojure.route :as route]
            [ring.util.response :as response]
            [clj-blog.db :as db]
            [clj-blog.views :as views]))

;; Home page
(defn home-handler [_]
  {:status 200
   :headers {"Content-Type" "text/html; charset=utf-8"}
   :body (views/home-page (db/get-all-posts))})

;; Show single post
(defn show-post-handler [{:keys [params]}]
  (if-let [post (db/get-post (:id params))]
    {:status 200
     :headers {"Content-Type" "text/html; charset=utf-8"}
     :body (views/post-page post)}
    {:status 404
     :headers {"Content-Type" "text/html; charset=utf-8"}
     :body (views/post-not-found)}))

;; New post form
(defn new-post-handler [_]
  {:status 200
   :headers {"Content-Type" "text/html; charset=utf-8"}
   :body (views/new-post-page)})

;; Create post
(defn create-post-handler [{:keys [params]}]
  (let [post (db/create-post! {:title (:title params)
                               :content (:content params)
                               :author (:author params)})]
    (response/redirect (str "/posts/" (:id post)))))

;; Edit post form
(defn edit-post-handler [{:keys [params]}]
  (if-let [post (db/get-post (:id params))]
    {:status 200
     :headers {"Content-Type" "text/html; charset=utf-8"}
     :body (views/edit-post-page post)}
    {:status 404
     :headers {"Content-Type" "text/html; charset=utf-8"}
     :body (views/post-not-found)}))

;; Update post
(defn update-post-handler [{:keys [params]}]
  (if-let [_post (db/update-post! (:id params)
                                  {:title (:title params)
                                   :content (:content params)})]
    (response/redirect (str "/posts/" (:id params)))
    {:status 404
     :headers {"Content-Type" "text/html; charset=utf-8"}
     :body (views/post-not-found)}))

;; Delete post
(defn delete-post-handler [{:keys [params]}]
  (db/delete-post! (:id params))
  (response/redirect "/"))

;; Define all routes
(defroutes app-routes
  (GET "/" [] home-handler)
  (GET "/posts/new" [] new-post-handler)
  (POST "/posts" [] create-post-handler)
  (GET "/posts/:id" [] show-post-handler)
  (GET "/posts/:id/edit" [] edit-post-handler)
  (POST "/posts/:id" [] update-post-handler)
  (POST "/posts/:id/delete" [] delete-post-handler)
  (route/resources "/")
  (route/not-found "Page not found"))
