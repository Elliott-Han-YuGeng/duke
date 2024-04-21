package exception;

import command.CommandList;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RollingException extends Exception {
    public RollingException(String line, int lineLength, CommandList l1, int l2, String l22, int lineSlashLength, String l3, String l4, String l5, int listSize) {

        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
        Matcher l4Matcher;
        Matcher l5Matcher;

        switch (l1) {
            case TODO:
                if (lineLength == 1) {
                    System.out.println("OOPS!!! The description of a todo cannot be empty.\n");
                } else if (line.contains("/")) {
                    System.out.println("OOPS!!! The description of a todo cannot contain '/'. \nIf you are trying to add a deadline or event, please start with 'deadline' or 'event' instead.\n");
                }
                break;
            case DEADLINE:
                if (lineLength == 1) {
                    System.out.println("OOPS!!! The description of a deadline cannot be empty and should be stated in the format of 'deadline do sth /by ...'.\n");
                } else if (!line.contains("/by ")) {
                    System.out.println("OOPS!!! The deadline time can not be empty and should be stated as '/by ...'.\nYou can input deadline time in the format of 'yyyy-mm-dd'.\n");
                } else if (l3.trim().equals("deadline")) {
                    System.out.println("OOPS!!! The description of a deadline cannot be empty, e.g. deadline do sth /by ...\nYou can input deadline time in the format of 'yyyy-mm-dd'.\n");
                } else if (lineSlashLength != 2) {
                    System.out.println("OOPS!!! The deadline must only contain one '/' which is '/by ...'.\nYou can input deadline time in the format of 'yyyy-mm-dd'.\n");
                } else if (l4.trim().equals("by")) {
                    System.out.println("OOPS!!! The deadline time cannot be empty.\n");
                }
                break;
            case EVENT:
                if (lineLength == 1) {
                    System.out.println("OOPS!!! The description of a event cannot be empty and should be stated in the format of 'event do sth /from ... /to ...'.\nYou can input event time in the format of 'yyyy-mm-dd'.\n");
                } else if (!line.contains("/from ") || !line.contains("/to ")) {
                    System.out.println("OOPS!!! The event time is necessary and must have '/from ... /to ...'.\nYou can input event time in the format of 'yyyy-mm-dd'.\n");
                } else if (l3.trim().equals("event")) {
                    System.out.println("OOPS!!! The description of a event cannot be empty, e.g. event do sth /from ... /to ...\nYou can input event time in the format of 'yyyy-mm-dd'.\n");
                } else if (lineSlashLength != 3) {
                    System.out.println("OOPS!!! The start and end time of the event must only contain two '/' which is '/from ... /to ...'.\nYou can input event time in the format of 'yyyy-mm-dd'.\n");
                } else if (l4.trim().equals("from")) {
                    System.out.println("OOPS!!! The start time of the event cannot be empty.\nYou can input event time in the format of 'yyyy-mm-dd'.\n");
                } else if (l5.trim().equals("to")) {
                    System.out.println("OOPS!!! The end time of the event cannot be empty.\nYou can input event time in the format of 'yyyy-mm-dd'.\n");
                } else if (l4.length() >= 5 && l5.length() >= 3) {
                    // Check if the date is in the correct format (yyyy-mm-dd)
                    l4Matcher = pattern.matcher(l4.substring(5).trim());
                    l5Matcher = pattern.matcher(l5.substring(3).trim());
                    if (l4Matcher.matches() && l5Matcher.matches()) {
                        LocalDate date1 = LocalDate.parse(l4.substring(5).trim());
                        LocalDate date2 = LocalDate.parse(l5.substring(3).trim());
                        if (date1.isAfter(date2)) {
                            System.out.println("OOPS!!! The start time of the event cannot be later than the end time.\n");
                        }
                    }
                }
                break;
            case MARK:
            case UNMARK:
            case DELETE:
                if (lineLength == 1) {
                    System.out.println("OOPS!!! Please indicate which task you want to " + l1 + ".\n");
                } else if (lineLength > 2) {
                    System.out.println("OOPS!!! Please state in the format of '" + l1 + " ?'.\n");
                } else if (l2 == 0) {
                    System.out.println("OOPS!!! The task to " + l1 + " must be stated as a digit number and larger than 0, e.g. " + l1 + " 1.\n");
                } else if (l2 > listSize) {
                    System.out.println("OOPS!!! You do not have Task " + l2 + ". \nNow you only have " + listSize + " task(s) in the list.\n");
                }
                break;
            case LIST:
                if (lineLength > 1) {
                    System.out.println("OOPS!!! Please start with 'list' only.\n");
                }
                break;
            case DATE:
                if (lineLength == 1) {
                    System.out.println("OOPS!!! Please indicate which date you want to check.\n");
                } else if (lineLength > 2) {
                    System.out.println("OOPS!!! Please state in the format of 'date yyyy-mm-dd'.\n");
                } else {
                    try {
                        LocalDate date = LocalDate.parse(l22);
                    } catch (DateTimeParseException e) {
                        System.out.println("OOPS!!! The date format must be 'yyyy-mm-dd'.\n");
                    }
                }
                break;
            default:
                System.out.println("Please start with a right keyword with space: todo, deadline, event, mark, unmark, list...\n"
                                 + "(type 'bye' to exit)\n");
                break;
        }
    }
}