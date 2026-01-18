(defproject clj-blog "0.1.0-SNAPSHOT"
  :description "A simple blog service built with Clojure"
  :url "http://localhost:3000"
  :license {:name "EPL-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}

  :dependencies [[org.clojure/clojure "1.12.0"]
                 ;; Web server
                 [ring/ring-core "1.12.2"]
                 [ring/ring-jetty-adapter "1.12.2"]
                 [ring/ring-defaults "0.5.0"]
                 ;; Routing
                 [compojure "1.7.1"]
                 ;; HTML templating
                 [hiccup "2.0.0-RC3"]
                 ;; JSON
                 [cheshire "5.13.0"]
                 ;; Date/Time
                 [clj-time "0.15.2"]]

  :main ^:skip-aot clj-blog.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}
             :dev {:dependencies [[ring/ring-devel "1.12.2"]]}})
