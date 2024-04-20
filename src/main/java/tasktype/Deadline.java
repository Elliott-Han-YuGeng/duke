package tasktype;

public class Deadline extends Task {

    protected String by;

    public Deadline(String description, boolean isDone, String by) {
        super(description, isDone);
        this.by = by;
    }

    public Deadline(String description, String by) {
        this(description, false, by);
    }

    @Override
    public String getString() {
        return "[D]" + super.getString() + " (by: " + by + ")";
    }

    @Override
    public String getFileString() {
        return "D | " + super.getFileString() + " | " + by;
    }
}