package tasktype;
/**
 * Represents an event task.
 * Event tasks inherit from the Task class, with an additional begin and end date.
 */
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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

    public String tryStringToDate(String s) {
        try {
            LocalDate date = LocalDate.parse(s);
            return date.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        } catch (DateTimeParseException e) {
            return s;
        }
    }

    @Override
    public String getString() {
        return "[E]" + super.getString() + " (from: " + tryStringToDate(begin) + " to: " + tryStringToDate(end) + ")";
    }

    @Override
    public String getFileString() {
        return "E | " + super.getFileString() + " | " + begin + " | " + end;
    }

    @Override
    public String getDates() {
        if (!tryStringToDate(begin).equals(begin) && !tryStringToDate(end).equals(end))
            return begin + " " + end;
        else return "";
    }
}