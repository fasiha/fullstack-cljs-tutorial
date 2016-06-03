(ns fullstack-cljs-tutorial.server
  (:require [org.httpkit.server :as http-kit]
            [compojure.core :refer [GET defroutes routes]]
            [compojure.route :refer [not-found]]
            [ring.middleware.defaults :refer [wrap-defaults
                                              site-defaults
                                              api-defaults]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.reload :refer [wrap-reload]]))

(def PORT 5300)

(defroutes app-routes
  (GET "/hi" [] "Hello there!")
  (not-found "Not found"))

(def handler (routes
               (-> #'app-routes
                   (wrap-resource "public")
                   (wrap-defaults site-defaults)
                   (wrap-reload))))

(defn -main [& args]
  (http-kit/run-server handler {:port PORT})
  (println (str "Server started: http://127.0.0.1:" PORT "/index.html")))

