package ui;

import tasktype.Task;
import java.util.ArrayList;

public class Ui {

    public Ui() {
    }

    public void start() {
        String greeting = "Hello! I'm Your Rolling Bear!\n"
                + "What can I do for you? todo, deadline, event...\n"
                + "(type 'bye' to exit)";
        System.out.println(greeting);
    }

    public void exit() {
        System.out.println("Ciao! See you next time!");
    }

    public void printList(ArrayList<Task> ss) {
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
}