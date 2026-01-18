(ns clj-blog.views
  "HTML views using Hiccup"
  (:require [hiccup2.core :as h]
            [hiccup.util :as util]))

(defn layout
  "Base layout template"
  [title & content]
  (str
   (h/html
    {:mode :html}
    (h/raw "<!DOCTYPE html>")
    [:html {:lang "ko"}
     [:head
      [:meta {:charset "UTF-8"}]
      [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
      [:title title " | Clojure Blog"]
      [:link {:rel "stylesheet" :href "/css/style.css"}]]
     [:body
      [:header.header
       [:div.container
        [:h1 [:a {:href "/"} "Clojure Blog"]]
        [:nav
         [:a {:href "/"} "Home"]
         [:a {:href "/posts/new"} "New Post"]]]]
      [:main.container
       content]
      [:footer.footer
       [:div.container
        [:p "Built with Clojure, Ring, Compojure & Hiccup"]
        [:p "Running on JVM 25"]]]]])))

(defn home-page
  "Home page showing all posts"
  [posts]
  (layout "Home"
          [:h2 "Latest Posts"]
          (if (seq posts)
            [:div.posts
             (for [post posts]
               [:article.post-card
                [:h3 [:a {:href (str "/posts/" (:id post))} (:title post)]]
                [:div.post-meta
                 [:span.author "By " (:author post)]
                 [:span.date (:created-at post)]]
                [:p.excerpt (let [content (:content post)]
                              (if (> (count content) 200)
                                (str (subs content 0 200) "...")
                                content))]])]
            [:p.no-posts "No posts yet. " [:a {:href "/posts/new"} "Create the first one!"]])))

(defn post-page
  "Single post view"
  [post]
  (layout (:title post)
          [:article.post-full
           [:h2 (:title post)]
           [:div.post-meta
            [:span.author "By " (:author post)]
            [:span.date "Created: " (:created-at post)]
            (when (not= (:created-at post) (:updated-at post))
              [:span.date " | Updated: " (:updated-at post)])]
           [:div.post-content
            (for [paragraph (clojure.string/split (:content post) #"\n\n")]
              [:p paragraph])]
           [:div.post-actions
            [:a.btn {:href (str "/posts/" (:id post) "/edit")} "Edit"]
            [:form {:action (str "/posts/" (:id post) "/delete") :method "POST" :style "display:inline"}
             [:button.btn.btn-danger {:type "submit" :onclick "return confirm('Are you sure you want to delete this post?')"} "Delete"]]
            [:a.btn.btn-secondary {:href "/"} "Back to Home"]]]))

(defn post-not-found []
  (layout "Not Found"
          [:div.error
           [:h2 "Post Not Found"]
           [:p "The post you're looking for doesn't exist."]
           [:a.btn {:href "/"} "Go Home"]]))

(defn new-post-page
  "Form for creating new post"
  []
  (layout "New Post"
          [:h2 "Create New Post"]
          [:form.post-form {:action "/posts" :method "POST"}
           [:div.form-group
            [:label {:for "title"} "Title"]
            [:input {:type "text" :id "title" :name "title" :required true :placeholder "Enter post title"}]]
           [:div.form-group
            [:label {:for "author"} "Author"]
            [:input {:type "text" :id "author" :name "author" :placeholder "Your name (optional)"}]]
           [:div.form-group
            [:label {:for "content"} "Content"]
            [:textarea {:id "content" :name "content" :rows 15 :required true :placeholder "Write your post content here..."}]]
           [:div.form-actions
            [:button.btn {:type "submit"} "Publish Post"]
            [:a.btn.btn-secondary {:href "/"} "Cancel"]]]))

(defn edit-post-page
  "Form for editing existing post"
  [post]
  (layout (str "Edit: " (:title post))
          [:h2 "Edit Post"]
          [:form.post-form {:action (str "/posts/" (:id post)) :method "POST"}
           [:div.form-group
            [:label {:for "title"} "Title"]
            [:input {:type "text" :id "title" :name "title" :required true :value (:title post)}]]
           [:div.form-group
            [:label {:for "content"} "Content"]
            [:textarea {:id "content" :name "content" :rows 15 :required true} (:content post)]]
           [:div.form-actions
            [:button.btn {:type "submit"} "Update Post"]
            [:a.btn.btn-secondary {:href (str "/posts/" (:id post))} "Cancel"]]]))
