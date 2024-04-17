import java.util.Scanner;

public class Rolling {
    //  Initiate an input
    private static final Scanner in = new Scanner(System.in);
    private static String line;
    private static int lineLength;
    private static String l1;
    private static int l2;

    //  Create a todo list
    private static final Task[] todoList = new Task[1000];
    private static int loc = 0;

    public static void start() {
        String greeting = "Hello! I'm Your Rolling Bear!\n"
                        + "What can I do for you?";
        System.out.println(greeting);
    }

    public static void userInput() {
        line = in.nextLine();
        String[] lineSplit = line.split(" ");
        lineLength = lineSplit.length;
        try {
            l1 = lineSplit[0].toLowerCase();
            l2 = Integer.parseInt(lineSplit[1]);
        } catch (Exception e) {
            l1 = "";
            l2 = 0;
        }
    }

    public static void addtoList(Task[] ss, String s) {
        ss[loc] = new Task(s);
        System.out.println("added: " + s);
        loc++;
    }

    public static void printList(Task[] ss) {
        for (int i = 0; i < loc; i++) {
            Task t = ss[i];
            System.out.println(i+1 + "." + t.getStatusIcon() + t.getDescription());
        }
    }

    public static void exit() {
        System.out.println("Bye. Hope to see you again soon!");
    }

//  ---------- Main Funciton --------------------------------------------------------

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