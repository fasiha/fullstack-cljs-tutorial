(defproject fullstack-cljs-tutorial "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.9.0-alpha3"]
                 [org.clojure/clojurescript "1.9.36"]
                 [com.cognitect/transit-clj "0.8.285"]
                 [sablono "0.7.2"]
                 [cljsjs/react "15.0.0-0"]
                 [cljsjs/react-dom "15.0.0-0"]

                 [http-kit "2.2.0-alpha1"]
                 [compojure "1.5.0"]
                 [ring "1.4.0"]
                 [ring/ring-defaults "0.2.0"]
                 [org.clojure/core.async "0.2.374"]
                 [com.taoensso/sente "1.8.1"]
                 [org.clojure/tools.nrepl "0.2.11"]
                 ]
  :min-lein-version "2.5.3"
  :source-paths ["src"]
  :main fullstack-cljs-tutorial.server
  :plugins [[lein-cljsbuild "1.1.3"]]
  :clean-targets ^{:protect false} [:target-path "resources/public/cljs"]
  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src"]
                        :figwheel true
                        :compiler {:main fullstack-cljs-tutorial.core
                                   :asset-path "cljs/out"
                                   :output-to  "resources/public/cljs/main.js"
                                   :output-dir "resources/public/cljs/out"}}
                       {:id "min"
                        :jar true
                        :source-paths ["src"]
                        :compiler {:main fullstack-cljs-tutorial.core
                                   :output-to "resources/public/cljs/main.js"
                                   :optimizations :advanced
                                   :closure-defines {goog.DEBUG false}
                                   :pretty-print false}}]
              }
  :figwheel {:server-port 5309}
  :profiles {:uberjar {:aot :all
                       :prep-tasks ["compile" ["cljsbuild" "once" "min"]]}
             :dev {:plugins [[lein-figwheel "0.5.3"]]}}
  )

