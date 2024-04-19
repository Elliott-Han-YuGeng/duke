public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getString() {
        return (isDone ? "[X] " : "[ ] ") + this.description; // mark done task with X
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