package main

import (
	"code.google.com/p/go.net/websocket"
	"encoding/json"
	"fmt"
	"net/http"
	"strconv"
)

func stateHandler(w http.ResponseWriter, r *http.Request) {
	state := NewSystemState()
	response, err := json.Marshal(state)
	if err != nil {
		fmt.Println(err)
		return
	}
	w.Write(response)
}

func wstestHandler(ws *websocket.Conn) {
	fmt.Println("wot?")
	ws.Write([]byte("helloooo"))
}

type SystemState struct {
	MemoryUsed int
	MemoryFree int
}

func NewSystemState() *SystemState {
	return &SystemState{
		100,
		150,
	}
}

func main() {
	port := 8080

	http.HandleFunc("/state", stateHandler)
	http.Handle("/wstest", websocket.Handler(wstestHandler))

	fmt.Println("Listening on port 8080")
	if err := http.ListenAndServe(":"+strconv.Itoa(port), nil); err != nil {
		fmt.Println(err.Error())
	}
}
