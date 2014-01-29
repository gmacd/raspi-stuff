(ns client.core
  (:require [cljs.core.async :refer [chan <! >! put!]]
            [cljs.reader :as reader])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(enable-console-print!)
(println "Hello world")

(def receive (chan))

(def ws-url "ws://echo.websocket.org")
(def ws (new js/WebSocket ws-url))

(defn add-message []
  (go
   (while true
     (let [msg (<! receive)
           raw-data (.-data msg)
           data (reader/read-string raw-data)]
       (println (str ">> " data))))))

(defn make-receiver []
  (set! (.-onopen ws) (fn [msg] (.send ws "hello")))
  (set! (.-onmessage ws) (fn [msg] (put! receive msg)))
  (add-message))


(defn init! []
	(make-receiver))

(set! (.-onload js/window) init!)
