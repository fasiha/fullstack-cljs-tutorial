(ns fullstack-cljs-tutorial.comm
  (:require [taoensso.sente :as sente]))

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

; Try in figwheel REPL:
; (#'fullstack-cljs-tutorial.comm/chsk-send! [:my/message ["hello" {:stranger "!"}]])
