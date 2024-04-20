public class Event extends Task {

    protected String begin;
    protected String end;

    public Event(String description, boolean isDone, String begin, String end) {
        super(description, isDone);
        this.begin = begin;
        this.end = end;
    }

    public Event(String description, String begin, String end) {
        this(description, false, begin, end);
    }

    @Override
    public String getString() {
        return "[E]" + super.getString() + " (from: " + begin + " to: " + end + ")";
    }

    @Override
    public String getFileString() {
        return "E | " + super.getFileString() + " | " + begin + " | " + end;
    }
}