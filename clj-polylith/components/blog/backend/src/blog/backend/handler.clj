(ns blog.backend.handler
  (:require [blog.domain.post :as post]
            [clojure.data.json :as json]
            [ring.util.response :as response]))

(defn- cors-headers [resp]
  (-> resp
      (response/header "Access-Control-Allow-Origin" "*")
      (response/header "Access-Control-Allow-Methods" "GET, POST, OPTIONS")
      (response/header "Access-Control-Allow-Headers" "Content-Type")))

(defn routes [request]
  (if (= :options (:request-method request))
    (-> (response/response nil)
        (response/status 204)
        cors-headers)
    (-> (response/response (json/write-str {:posts (post/sample-posts)}))
        cors-headers
        (response/content-type "application/json"))))
