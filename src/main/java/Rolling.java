import java.util.Scanner;

public class Rolling {
    //  Create a todo list
    private static String[] todoList = new String[1000];
    private static int loc = 0;

    public static void start() {
        String greeting = "Hello! I'm Your Rolling Bear!\n"
                + "What can I do for you?";
        System.out.println(greeting);
    }

    public static void addtoList(String[] ss, String s) {
        ss[loc] = s;
        System.out.println("added: " + s);
        loc++;
    }

    public static void printList(String[] ss) {
        for (int i = 0; i < loc; i++) {
            System.out.println(i + 1 + ". " + ss[i]);
        }
    }

    public static void exit() {
        System.out.println("Bye. Hope to see you again soon!");
    }

//  ---------- Main Funciton --------------------------------------------------------

    public static void main(String[] args) {
        start();

//      User input
        Scanner in = new Scanner(System.in);
        String line = in.nextLine();

        int i = 0;

//      Echo
        while(!line.toLowerCase().equals("bye")) {
            if (line.toLowerCase().equals("list")) {
                printList(todoList);
            } else {
                addtoList(todoList, line);
            }
            line = in.nextLine();
        }

        exit();
    }
}