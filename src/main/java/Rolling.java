import java.util.Scanner;

public class Rolling {
    //  Initiate a user input
    private static final Scanner in = new Scanner(System.in);
    private static String line;            // user original input
    private static int lineLength;
    private static String l1;              // keyword: todo, deadline, event, list, bye, mark, unmark
    private static int l2;                 // number to mark or unmark: 1, 2, 3, ...
    private static int lineSlashLength;
    private static String l3;              // contains task description
    private static String l4;              // by ? or from ?
    private static String l5;              // to ?

    //  Create a todo list
    private static final Task[] todoList = new Task[1000];
    private static int loc = 0;

//  ---------- Static Method --------------------------------------------------------

    public static void start() {
        String greeting = "Hello! I'm Your Rolling Bear!\n"
                        + "What can I do for you? todo, deadline, event...\n"
                        + "(type 'bye' to exit)";
        System.out.println(greeting);
    }

    public static void userInput() {
        line = in.nextLine().trim().replaceAll("\\s+", " ");

//      Split by space
        String[] lineSplit = line.split(" ");
        lineLength = lineSplit.length;
        l1 = lineSplit[0].toLowerCase();
        try {
            l2 = Integer.parseInt(lineSplit[1]);
        } catch (Exception e) {
            l2 = 0;
        }

//      Split by slash
        String[] lineSplitS = line.split("/");
        lineSlashLength = lineSplitS.length;
        l3 = lineSplitS[0];
        try {
            l4 = lineSplitS[1];
        } catch (Exception e) {
            l4 = "";
        }
        try {
            l5 = lineSplitS[2];
        } catch (Exception e) {
            l5 = "";
        }
    }

    public static void addtoList(Task[] ss, String s) {
        switch (l1) {
            case "todo":
                ss[loc] = new Todo(s.substring(5).trim());
                break;
            case "deadline":
                ss[loc] = new Deadline(l3.substring(9).trim(), l4.substring(3).trim());
                break;
            case "event":
                ss[loc] = new Event(l3.substring(6).trim(), l4.substring(5).trim(), l5.substring(3).trim());
                break;
        }
        System.out.println("Sure! Just added to the task list!\n"
                         + "  " + ss[loc].getString() + "\n"
                         + "Task+1 | Now you have " + (loc+1) + " task(s) in the list.\n");
        loc++;
    }

    public static void printList(Task[] ss) {
        if (loc==0) {
            System.out.println("You have no task in the list now.");
        } else {
            System.out.println("Sure! These are the tasks in your list:");
            int i = 1;
            for (Task t : ss) {
                if (i <= loc) {
                    System.out.println(i + "." + t.getString());
                }
                i++;
            }
            System.out.println();
        }
    }

    public static void throwRollingException() throws RollingException {
        throw new RollingException(line, lineLength, l1, l2, l3, lineSlashLength, l4, l5, loc);
    }

    public static void handleMark() throws RollingException {
        if (lineLength==2 && l2!=0 && l2<=loc) {
            todoList[l2 - 1].markAsDone();
        } else throwRollingException();
        System.out.println();
    }

    public static void handleUnmark() throws RollingException {
        if (lineLength==2 && l2!=0 && l2<=loc) {
            todoList[l2 - 1].markAsNotDone();
        } else throwRollingException();
        System.out.println();
    }

    public static void handleTodo() throws RollingException {
        if (lineLength>1 && !line.contains("/")) {
            addtoList(todoList, line);
        } else throwRollingException();
    }

    public static void handleDeadline() throws RollingException {
        if (lineLength>1 && line.contains("/by ") && !l3.trim().equals("deadline") && lineSlashLength==2 && !l4.trim().equals("by")) {
            addtoList(todoList, line);
        } else throwRollingException();
    }

    public static void handleEvent() throws RollingException {
        if (lineLength>1 && line.contains("/from ") && line.contains("/to ") && !l3.trim().equals("event") && lineSlashLength==3 && !l4.trim().equals("from") && !l5.trim().equals("to")) {
            addtoList(todoList, line);
        } else throwRollingException();
    }

    public static void exit() {
        System.out.println("Ciao! See you soon!");
    }

//  ---------- Main Function --------------------------------------------------------

    public static void main(String[] args) {
        start();
        userInput();

//      Dialog
        while(!line.equalsIgnoreCase("bye")) {
            try {
                switch (l1) {
                    case "list":
                        printList(todoList);
                        break;
                    case "mark":
                        handleMark();
                        break;
                    case "unmark":
                        handleUnmark();
                        break;
                    case "todo":
                        handleTodo();
                        break;
                    case "deadline":
                        handleDeadline();
                        break;
                    case "event":
                        handleEvent();
                        break;
                    default:
                        throwRollingException();
                        break;
                }
            } catch (RollingException e) {
                userInput();
                continue;
            }
            userInput();
        }
        exit();
    }
}