(ns fullstack-cljs-tutorial.core
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require [sablono.core :as sab]
            [fullstack-cljs-tutorial.components :refer [like-seymore]]
            [fullstack-cljs-tutorial.comm :as comm]
            [cljs.core.async :as a :refer [<!]]))

; for `println` to work
(def debug?
  ^boolean js/goog.DEBUG)
(when debug?
  (enable-console-print!))

; React!

(defonce app-state (atom { :likes 0 }))

(defn render! []
  (.render js/ReactDOM
           (like-seymore app-state)
           (.getElementById js/document "app")))

(add-watch app-state :on-change (fn [_ _ _ _] (render!)))

(render!)

; Sente & update

(go-loop []
         (let [msg (<! comm/ch-chsk)]
           (println "Browser received" (select-keys msg [:event :?data]))
           (when (some-> msg
                         :?data
                         first
                         (= :seymore/likes))
             (swap! app-state assoc :likes (-> msg :?data second))))
         (recur))

; ask for current status
(comm/chsk-send! [:seymore/likes?])

