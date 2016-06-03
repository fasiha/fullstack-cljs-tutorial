(ns fullstack-cljs-tutorial.server
  (:require [org.httpkit.server :as http-kit]
            [compojure.core :refer [GET POST defroutes routes]]
            [compojure.route :refer [not-found]]
            [ring.middleware.defaults :refer [wrap-defaults
                                              site-defaults
                                              api-defaults]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.reload :refer [wrap-reload]]

            [clojure.core.async :as a :refer [go-loop <!]]
            [taoensso.sente :as sente]
            [taoensso.sente.server-adapters.http-kit
             :refer (sente-web-server-adapter)]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.middleware.params :refer [wrap-params]])
  (:gen-class))

(def PORT 5300)


;; Sente stuff
(let [{:keys [ch-recv send-fn ajax-post-fn ajax-get-or-ws-handshake-fn
              connected-uids]}
      (sente/make-channel-socket! sente-web-server-adapter {})]
  (def ring-ajax-post                ajax-post-fn)
  (def ring-ajax-get-or-ws-handshake ajax-get-or-ws-handshake-fn)
  (def ch-chsk                       ch-recv) ; ChannelSocket's receive channel
  (def chsk-send!                    send-fn) ; ChannelSocket's send API fn
  (def connected-uids                connected-uids) ; Watchable, read-only atom
  )

(defroutes sente-routes
  (GET  "/chsk" req (ring-ajax-get-or-ws-handshake req))
  (POST "/chsk" req (ring-ajax-post                req)))

(go-loop []
         (println "Server received: " (<! ch-chsk))
         (recur))

;; Application routes
(defroutes app-routes
  (GET "/hi" [] "Hello there!")
  (not-found "Not found"))

(def handler (routes
               (-> #'sente-routes
                   (wrap-defaults api-defaults)
                   (wrap-keyword-params)
                   (wrap-params))
               (-> #'app-routes
                   (wrap-resource "public")
                   (wrap-defaults site-defaults)
                   (wrap-reload))))

(defn -main [& args]
  (http-kit/run-server handler {:port PORT})
  (println (str "Server started: http://127.0.0.1:" PORT "/index.html")))

