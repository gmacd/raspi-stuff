(ns client.core
  (:require [cljs.core.async :refer [chan <! >! put!]])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(enable-console-print!)
(println "Hello world!")

(def receive (chan))

(def ws-url "ws://localhost:8080/wstest")
(def ws (new js/WebSocket ws-url))

(defn add-message []
  (go
   (while true
     (let [msg (<! receive)
           raw-data (.-data msg)
           data (reader/read-string raw-data)]
       (println data)))))
       ;(dommy/append! (sel1 :#foo) [:li (str data)])
       ;(set! (.-scrollTop (sel1 :#foo)) (.-scrollHeight (sel1 :#foo)))))))

(defn make-receiver []
  (set! (.-onopen ws) (fn [msg] (put! receive msg)))
  (set! (.-onmessage ws) (fn [msg] (put! receive msg)))
  (add-message))


(defn init! []
	(make-receiver))

(defn ^:export init []
	(set! (.-onload js/window) init!))
