(ns fullstack-cljs-tutorial.components
  (:require [sablono.core :as sab]
            [fullstack-cljs-tutorial.comm :as comm]))

(defn like-seymore [data-atom]
  (sab/html [:div
             [:h1 "Seymore's quantified popularity: " (:likes @data-atom)]
             [:div [:a {:href "#"
                        :onClick #(comm/chsk-send! [:seymore/like 1])}
                    "Thumbs up"]]]))

