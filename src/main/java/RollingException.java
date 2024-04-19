public class RollingException extends Exception {
    public RollingException(String line, int lineLength, String l1, int l2, String l3, int lineSlashLength, String l4, String l5, int listSize) {
        switch (l1) {
            case "todo":
                if (lineLength == 1) {
                    System.out.println("OOPS!!! The description of a todo cannot be empty.\n");
                } else if (line.contains("/")) {
                    System.out.println("OOPS!!! The description of a todo cannot contain '/'. \nIf you are trying to add a deadline or event, please start with 'deadline' or 'event' instead.\n");
                }
                break;
            case "deadline":
                if (lineLength == 1) {
                    System.out.println("OOPS!!! The description of a deadline cannot be empty and should be stated in the format of 'deadline do sth /by ...'.\n");
                } else if (!line.contains("/by ")) {
                    System.out.println("OOPS!!! The deadline time can not be empty and should be stated as '/by ...'.\n");
                } else if (l3.trim().equals("deadline")) {
                    System.out.println("OOPS!!! The description of a deadline cannot be empty, e.g. deadline do sth /by ...\n");
                } else if (lineSlashLength != 2) {
                    System.out.println("OOPS!!! The deadline must only contain one '/' which is '/by ...'.\n");
                } else if (l4.trim().equals("by")) {
                    System.out.println("OOPS!!! The deadline time cannot be empty.\n");
                }
                break;
            case "event":
                if (lineLength == 1) {
                    System.out.println("OOPS!!! The description of a event cannot be empty and should be stated in the format of 'event do sth /from ... /to ...'.\n");
                } else if (!line.contains("/from ") || !line.contains("/to ")) {
                    System.out.println("OOPS!!! The event time is necessary and must have '/from ... /to ...'.\n");
                } else if (l3.trim().equals("event")) {
                    System.out.println("OOPS!!! The description of a event cannot be empty, e.g. event do sth /from ... /to ...\n");
                } else if (lineSlashLength != 3) {
                    System.out.println("OOPS!!! The start and end time of the event must only contain two '/' which is '/from ... /to ...'.\n");
                } else if (l4.trim().equals("from")) {
                    System.out.println("OOPS!!! The start time of the event cannot be empty.\n");
                } else if (l5.trim().equals("to")) {
                    System.out.println("OOPS!!! The end time of the event cannot be empty.\n");
                }
                break;
            case "mark":
            case "unmark":
            case "delete":
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
            default:
                System.out.println("Please start with a right keyword with space: todo, deadline, event, mark, unmark, list...\n"
                                 + "(type 'bye' to exit)\n");
                break;
        }
    }
}