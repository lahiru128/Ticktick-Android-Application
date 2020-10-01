package com.example.ticktick.Model;

public class Todo {

    String todoId;
    String todotitle;
    String tododate;
    String todonote;

    public Todo() {
    }

    public Todo(String todoId, String todotitle, String tododate, String todonote) {
        this.todoId = todoId;
        this.todotitle = todotitle;
        this.tododate = tododate;
        this.todonote = todonote;
    }

    public String getTodoId() {
        return todoId;
    }

    public String getTodotitle() {
        return todotitle;
    }

    public String getTododate() {
        return tododate;
    }

    public String getTodonote() {
        return todonote;
    }

}
