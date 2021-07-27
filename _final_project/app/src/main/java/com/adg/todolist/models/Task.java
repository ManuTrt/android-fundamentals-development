package com.adg.todolist.models;

public class Task {
    //region Task types
    public static final int URGENT = 0;
    public static final int NORMAL = 1;
    //endregion

    //region Status types
    public static final int ON_GOING = 0;
    public static final int DONE = 1;
    //endregion

    private String title;
    private String description;
    private int type;
    private int status;

    public Task(String title, String description) {
        this(title, description, NORMAL);
    }

    public Task(String title, String description, int type) {
        this(title, description, type, ON_GOING);
    }

    public Task(String title, String description, int type, int status) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.status = status;
    }

    //region Getters
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getType() {
        return type;
    }

    public int getStatus() {
        return status;
    }
    //endregion

    //region Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    //endregion

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", status=" + status +
                '}';
    }
}
