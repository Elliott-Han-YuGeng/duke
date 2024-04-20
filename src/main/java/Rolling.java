import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class Rolling {
    //  Initiate a user input
    private static final Scanner in = new Scanner(System.in);
    private static String line;            // user original input
    private static int lineLength;
    private static Command l1;              // keyword: todo, deadline, event, mark, unmark, list, bye
    private static int l2;                 // number to mark or unmark: 1, 2, 3, ...
    private static int lineSlashLength;
    private static String l3;              // contains task description
    private static String l4;              // by ? or from ?
    private static String l5;              // to ?

    //  Create a storage from path in OS dependent way
    private static final Path filePath = Paths.get(".", "data", "rolling.txt");
    private static final Storage file = new Storage(filePath.toString());
    //  Create a todo list
    private static ArrayList<Task> todoList = new ArrayList<>();

    //  Store Keywords
    public enum Command {
        TODO,
        DEADLINE,
        EVENT,
        MARK,
        UNMARK,
        DELETE,
        LIST,
        NULL
    }

//  ---------- Static Method --------------------------------------------------------

    public static void loadFile(Storage f) {
        try {
            todoList = f.load();
        } catch (FileNotFoundException e) {
            todoList = new ArrayList<>();
        }
    }

    public static void saveToFile(Storage f, ArrayList<Task> ss) {
        try {
            f.save(ss);
        } catch (IOException e) {
            System.out.println("Error saving tasks to file.");
        }
    }

    public static void start() {
        loadFile(file);
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
        try {
            l1 = Command.valueOf(lineSplit[0].toUpperCase());
        } catch (Exception e) {
            l1 = Command.NULL;
        }
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

    public static void addtoList(ArrayList<Task> ss, String s) {
        switch (l1) {
            case TODO:
                ss.add(new Todo(s.substring(5).trim()));
                break;
            case DEADLINE:
                ss.add(new Deadline(l3.substring(9).trim(), l4.substring(3).trim()));
                break;
            case EVENT:
                ss.add(new Event(l3.substring(6).trim(), l4.substring(5).trim(), l5.substring(3).trim()));
                break;
        }
        System.out.println("Sure! Just added to the task list!\n"
                         + "  " + ss.get(ss.size() - 1).getString() + "\n"
                         + "Task+1 | Now you have " + ss.size() + " task(s) in the list.\n");
        saveToFile(file, ss);
    }

    public static void printList(ArrayList<Task> ss) {
        if (ss.isEmpty()) {
            System.out.println("You have no task in the list now.");
        } else {
            System.out.println("Sure! These are the tasks in your list:");
            for (int i = 0; i < ss.size(); i++) {
                System.out.println((i+1) + "." + ss.get(i).getString());
            }
        }
        System.out.println();
    }

    public static void throwRollingException() throws RollingException {
        throw new RollingException(line, lineLength, l1, l2, l3, lineSlashLength, l4, l5, todoList.size());
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

    public static void handleMark() throws RollingException {
        if (lineLength==2 && l2!=0 && l2<=todoList.size()) {
            todoList.get(l2 - 1).markAsDone();
        } else throwRollingException();
        System.out.println();
        saveToFile(file, todoList);
    }

    public static void handleUnmark() throws RollingException {
        if (lineLength==2 && l2!=0 && l2<=todoList.size()) {
            todoList.get(l2 - 1).markAsNotDone();
        } else throwRollingException();
        System.out.println();
        saveToFile(file, todoList);
    }

    public static void handleDelete() throws RollingException {
        if (lineLength==2 && l2!=0 && l2<=todoList.size()) {
            System.out.println("Noted. This task is removed from the list:\n"
                    + "  " + todoList.get(l2 - 1).getString() + "\n"
                    + "Task-1 | Now you have " + (todoList.size()-1) + " task(s) in the list.\n");
            todoList.remove(l2 - 1);
            try {
                file.save(todoList);
            } catch (IOException e) {
                System.out.println("Error saving tasks to file.");
            }
        } else throwRollingException();
        saveToFile(file, todoList);
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
                    case TODO:
                        handleTodo();
                        break;
                    case DEADLINE:
                        handleDeadline();
                        break;
                    case EVENT:
                        handleEvent();
                        break;
                    case MARK:
                        handleMark();
                        break;
                    case UNMARK:
                        handleUnmark();
                        break;
                    case DELETE:
                        handleDelete();
                        break;
                    case LIST:
                        printList(todoList);
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