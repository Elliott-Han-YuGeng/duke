public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getDescription() {
        return description;
    }

    public String getStatusIcon() {
        return (isDone ? "[X] " : "[ ] "); // mark done task with X
    }

    public void markAsDone(){
        isDone = true;
        System.out.println("Nice! I've marked this task as done:\n"
                         + "  [X] " + this.description);
    }

    public void markAsNotDone(){
        isDone = false;
        System.out.println("OK, I've marked this task as not done yet:\n"
                + "  [ ] " + this.description);
    }
}