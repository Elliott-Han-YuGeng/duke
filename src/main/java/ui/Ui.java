package ui;
/**
 * Represents the user interface of the application.
 * Contains methods to interact with the user and showing outputs.
 */
import tasktype.Task;
import java.util.ArrayList;

public class Ui {

    public Ui() {
    }

    public void start() {
        String greeting = "Hello! I'm Your Rolling Bear!\n"
                        + "       /\\__/\\ \n"
                        + "      /`    '\\ \n"
                        + "    === 0  0 ===\n"
                        + "      \\  --  / \n"
                        + "     /        \\ \n"
                        + "    /          \\ \n"
                        + "   |            |\n"
                        + "    \\  ||  ||  / \n"
                        + "     \\_oo__oo_/#######o\n\n"
                        + "What can I do for you? todo, deadline, event...\n"
                        + "(type 'bye' to exit)";
        System.out.println(greeting);
    }

    public void exit() {
        System.out.println("Ciao! See you next time!");
    }

    public void showAddTaskSuccess(ArrayList<Task> tasks) {
        System.out.println("Sure! Just added to the task list!\n"
                + "  " + tasks.get(tasks.size() - 1).getString() + "\n"
                + "Task+1 | Now you have " + tasks.size() + " task(s) in the list.\n");
    }

    public void showRemoveTaskSuccess(ArrayList<Task> ss, int i) {
        System.out.println("Noted. This task is removed from the list:\n"
                + "  " + ss.get(i).getString() + "\n"
                + "Task-1 | Now you have " + (ss.size()-1) + " task(s) in the list.\n");
    }

    public void printList(ArrayList<Task> ss, String keyword) {
        if (ss.isEmpty()) {
            System.out.println("You have no task " + (keyword.isEmpty() ? "now" : ("on/with '" + keyword + "'")) + " in the list.");
        } else {
            System.out.println("Sure! These are the tasks " + (keyword.isEmpty() ? "" : ("on/with '" + keyword + "' ")) + "in the list:");
            for (int i = 0; i < ss.size(); i++) {
                System.out.println((i+1) + "." + ss.get(i).getString());
            }
        }
        System.out.println();
    }
}