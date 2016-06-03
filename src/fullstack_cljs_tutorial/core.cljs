(ns fullstack-cljs-tutorial.core
  (:require [sablono.core :as sab]
            [fullstack-cljs-tutorial.components :refer [like-seymore]]
            [taoensso.sente :as sente]))

(defonce app-state (atom { :likes 0 }))

(defn render! []
  (.render js/ReactDOM
           (like-seymore app-state)
           (.getElementById js/document "app")))

(add-watch app-state :on-change (fn [_ _ _ _] (render!)))

(render!)

;; Sente
(let [{:keys [chsk ch-recv send-fn state]}
      (sente/make-channel-socket! "/chsk" ; Note the same path as before
       {:type :auto ; e/o #{:auto :ajax :ws}
       })]
  (def chsk       chsk)
  (def ch-chsk    ch-recv) ; ChannelSocket's receive channel
  (def chsk-send! send-fn) ; ChannelSocket's send API fn
  (def chsk-state state)   ; Watchable, read-only atom
  )

#_(do
    ; in figwheel REPL:
    (in-ns 'fullstack-cljs-tutorial.core)
    (chsk-send! [:my/message ["hello" {:stranger "!"}]])
    )
