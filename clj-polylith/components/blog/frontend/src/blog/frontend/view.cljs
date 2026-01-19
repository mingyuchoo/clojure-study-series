(ns blog.frontend.view)

(defn render-posts [posts]
  (let [root (.getElementById js/document "app")]
    (set! (.-innerHTML root)
          (str "<main class='wrap'>"
               "<h1>Polylith Blog</h1>"
               (apply str
                      (for [{:keys [title body]} posts]
                        (str "<article class='card'>"
                             "<h2>" title "</h2>"
                             "<p>" body "</p>"
                             "</article>")))
               "</main>"))))
