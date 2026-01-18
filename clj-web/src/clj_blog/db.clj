(ns clj-blog.db
  "In-memory blog post storage"
  (:require [clj-time.core :as t]
            [clj-time.format :as f]))

(def date-formatter (f/formatter "yyyy-MM-dd HH:mm"))

(defn now-str []
  (f/unparse date-formatter (t/now)))

;; In-memory atom for storing posts
(defonce posts-db (atom {}))

(defonce id-counter (atom 0))

(defn next-id []
  (swap! id-counter inc))

(defn create-post!
  "Create a new blog post"
  [{:keys [title content author]}]
  (let [id (next-id)
        post {:id id
              :title title
              :content content
              :author (or author "Anonymous")
              :created-at (now-str)
              :updated-at (now-str)}]
    (swap! posts-db assoc id post)
    post))

(defn get-post
  "Get a post by ID"
  [id]
  (get @posts-db (if (string? id) (parse-long id) id)))

(defn get-all-posts
  "Get all posts, sorted by creation date (newest first)"
  []
  (->> (vals @posts-db)
       (sort-by :id)
       reverse))

(defn update-post!
  "Update an existing post"
  [id {:keys [title content]}]
  (let [id (if (string? id) (parse-long id) id)]
    (when (get @posts-db id)
      (swap! posts-db update id merge
             {:title title
              :content content
              :updated-at (now-str)})
      (get @posts-db id))))

(defn delete-post!
  "Delete a post by ID"
  [id]
  (let [id (if (string? id) (parse-long id) id)]
    (swap! posts-db dissoc id)
    true))

;; Sample posts for demo
(defn seed-sample-posts! []
  (when (empty? @posts-db)
    (create-post! {:title "Welcome to My Blog!"
                   :content "This is my first blog post. Welcome to my Clojure-powered blog!

This blog is built with:
- **Clojure** - A powerful functional programming language
- **Ring** - Web application library
- **Compojure** - Routing library
- **Hiccup** - HTML templating

Feel free to explore and create your own posts!"
                   :author "Admin"})
    (create-post! {:title "Getting Started with Clojure"
                   :content "Clojure is a dynamic, functional programming language that runs on the JVM.

Here are some key features:
1. Immutable data structures
2. First-class functions
3. Lisp syntax with powerful macros
4. Excellent Java interop
5. Great for concurrent programming

Start your Clojure journey today!"
                   :author "Developer"})
    (create-post! {:title "Building Web Apps with Ring"
                   :content "Ring is the foundation for web development in Clojure.

Key concepts:
- **Handlers** - Functions that take a request map and return a response map
- **Middleware** - Functions that wrap handlers to add functionality
- **Adapters** - Connect Ring to web servers like Jetty

It's simple yet powerful!"
                   :author "Developer"})))
