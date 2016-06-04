(ns fullstack-cljs-tutorial.core
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require [sablono.core :as sab]
            [fullstack-cljs-tutorial.components :refer [like-seymore]]
            [fullstack-cljs-tutorial.comm :as comm]
            [cljs.core.async :as a :refer [<!]]))

; for `println` to work
(def debug?
  ^boolean js/goog.DEBUG)
(enable-console-print!)

; React!

(defonce client-state (atom { :likes 0 }))

(defn render! []
  (.render js/ReactDOM
           (like-seymore client-state)
           (.getElementById js/document "app")))

(add-watch client-state :on-change (fn [_ _ _ _] (render!)))

(render!)

; Sente & update

(go-loop []
         (let [msg (<! comm/ch-chsk)]
           (println "Browser received" (select-keys msg [:event :?data]))
           (when (some-> msg
                         :?data
                         first
                         (= :seymore/likes))
             (swap! client-state assoc :likes (-> msg :?data second))))
         (recur))

; ask for current status: make this explicily depend on chsk-state because,
; while just sending this message willy-nilly seems to work in debug mode, it
; doesn't in optimized mode.
(add-watch comm/chsk-state
           :on-open
           (fn [_ _ _ {:keys [open?]}]
             (when open?
               (comm/chsk-send! [:seymore/likes?]))))

