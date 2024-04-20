public class Task {

    protected String description;
    protected boolean isDone;

    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    public Task(String description) {
        this(description, false);
    }

    public String getString() {
        return (isDone ? "[X] " : "[ ] ") + description; // mark done task with X
    }

    public String getFileString() {
        return (isDone ? "1" : "0") + " | " + description;
    }

    public void markAsDone(){
        isDone = true;
        System.out.println("Good job :) mission completed!\n"
                         + "  " + this.getString());
    }

    public void markAsNotDone(){
        isDone = false;
        System.out.println("Oh no :( mission incomplete\n"
                         + "  " + this.getString());
    }
}