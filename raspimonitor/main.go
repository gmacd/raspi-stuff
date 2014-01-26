package main

import (
	"encoding/json"
	"fmt"
	"net/http"
)

func handler(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "Hi there, I love %s!", r.URL.Path[1:])
}

func stateHandler(w http.ResponseWriter, r *http.Request) {
	state := NewSystemState()
	response, err := json.Marshal(state)
	if err != nil {
		fmt.Println(err)
		return
	}
	w.Write(response)
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
	http.HandleFunc("/", handler)
	http.HandleFunc("/state", stateHandler)
	http.ListenAndServe(":8080", nil)
}
