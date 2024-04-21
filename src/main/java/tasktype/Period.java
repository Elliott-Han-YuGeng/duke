package tasktype;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Period extends Task {

    protected String between;
    protected String and;

    public Period(String description, boolean isDone, String between, String and) {
        super(description, isDone);
        this.between = between;
        this.and = and;
    }

    public Period(String description, String between, String and) {
        this(description, false, between, and);
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
        return "[P]" + super.getString() + " (between: " + tryStringToDate(between) + " and: " + tryStringToDate(and) + ")";
    }

    @Override
    public String getFileString() {
        return "P | " + super.getFileString() + " | " + between + " | " + and;
    }

    @Override
    public String getDates() {
        if (!tryStringToDate(between).equals(between) && !tryStringToDate(and).equals(and))
            return between + " " + and;
        else return "";
    }
}