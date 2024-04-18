public class Todo extends Task {

    public Todo(String description) {
        super(description);
    }

    @Override
    public String getString() {
        return "[T]" + super.getString();
    }
}