(ns fullstack-cljs-tutorial.components
  (:require [sablono.core :as sab]
            [fullstack-cljs-tutorial.comm :as comm]))

(defn like-seymore [state-atom]
  (sab/html [:div
             [:h1 "Seymore's quantified popularity: " (:likes @state-atom)]
             [:div [:a {:href "#"
                        :onClick #(comm/chsk-send! [:seymore/like 1])}
                    "Thumbs up"]]]))

