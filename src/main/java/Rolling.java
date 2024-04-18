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
                        + "What can I do for you?";
        System.out.println(greeting);
    }

    public static void userInput() {
        line = in.nextLine();

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
        if (l1.equals("todo")) {
            ss[loc] = new Todo(s.substring(5).trim());
        } else if (l1.equals("deadline") && lineSlashLength==2 && l4.toLowerCase().startsWith("by ")) {
            ss[loc] = new Deadline(l3.substring(9).trim(), l4.substring(3).trim());
        } else if (l1.equals("event") && lineSlashLength==3 && l4.toLowerCase().startsWith("from ") && l5.toLowerCase().startsWith("to ")) {
            ss[loc] = new Event(l3.substring(6).trim(), l4.substring(5).trim(), l5.substring(3).trim());
        } else {
            ss[loc] = new Task(s);
        }
        System.out.println("Sure! Just added, my lord!\n"
                         + "  " + ss[loc].getString() + "\n"
                         + "Task+1 Now you have " + (loc+1) + " tasks in the list.");
        loc++;
    }

    public static void printList(Task[] ss) {
        System.out.println("Sure! These are the tasks in your list:");
        int i = 1;
        for (Task t : ss) {
            if (i <= loc) {
                System.out.println(i + "." + t.getString());
            }
            i++;
        }
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
            if (line.equalsIgnoreCase("list")) {
                printList(todoList);
            } else if (lineLength==2 && l1.equals("mark") && l2!=0) {
                todoList[l2 - 1].markAsDone();
            } else if (lineLength==2 && l1.equals("unmark") && l2!=0) {
                todoList[l2 - 1].markAsNotDone();
            } else {
                addtoList(todoList, line);
            }
            userInput();
        }

        exit();
    }
}