(ns fullstack-cljs-tutorial.components
  (:require [sablono.core :as sab]))

(defn like-seymore [data-atom]
  (sab/html [:div
             [:h1 "Seymore's quantified popularity: " (:likes @data-atom)]
             [:div [:a {:href "#"
                        :onClick #(swap! data-atom update-in [:likes] inc)}
                    "Thumbs up"]]]))

