public class Event extends Task {

    protected String begin;
    protected String end;

    public Event(String description, String begin, String end) {
        super(description);
        this.begin = begin;
        this.end = end;
    }

    @Override
    public String getString() {
        return "[E]" + super.getString() + " (from: " + begin + " to: " + end + ")";
    }
}