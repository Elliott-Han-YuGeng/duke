package tasktype;
/**
 * Represents a todo task.
 * Todo tasks inherit from the Task class.
 */
public class Todo extends Task {

    public Todo(String description, boolean isDone) {
        super(description, isDone);
    }

    public Todo(String description) {
        this(description, false);
    }

    @Override
    public String getString() {
        return "[T]" + super.getString();
    }

    @Override
    public String getFileString() {
        return "T | " + super.getFileString();
    }
}