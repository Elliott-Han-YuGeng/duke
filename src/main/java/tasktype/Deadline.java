package tasktype;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {

    protected String by;

    public Deadline(String description, boolean isDone, String by) {
        super(description, isDone);
        this.by = by;
    }

    public Deadline(String description, String by) {
        this(description, false, by);
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
        return "[D]" + super.getString() + " (by: " + tryStringToDate(by) + ")";
    }

    @Override
    public String getFileString() {
        return "D | " + super.getFileString() + " | " + by;
    }
}