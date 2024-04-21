package ui;

import command.CommandList;
import exception.RollingException;
import tasktype.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

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

    public void printDailyTask(ArrayList<Task> ss, int lineLength, String date) throws RollingException {
        ArrayList<String> dateList = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
        Matcher matcher = pattern.matcher(date);
        int i = 0;
        if (ss.isEmpty()) {
            System.out.println("You have no task in the list now.\n");
        } else if (matcher.matches()) {
            while (i < ss.size()) {
                Task t = ss.get(i);
                if (t.getFileString().contains(" | " + date)) {
                    dateList.add(ss.get(i).getString());
                } else if (t.getDates() != "") {
                    String[] dateSplits = t.getDates().split(" ");
                    LocalDate d0 = LocalDate.parse(date);
                    LocalDate d1 = LocalDate.parse(dateSplits[0]);
                    LocalDate d2 = LocalDate.parse(dateSplits[1]);
                    if ((d0.isAfter(d1) || d0.equals(d1)) && (d0.isBefore(d2) || d0.equals(d2))) {
                        dateList.add(ss.get(i).getString());
                    }
                }
                i++;
            }
            if (dateList.isEmpty()) {
                System.out.println("You have no task on " + date + " in the list now.\n");
            } else {
                System.out.println("Sure! These are the tasks on " + date + ":");
                for (int j = 0; j < dateList.size(); j++) {
                    System.out.println((j+1) + "." + dateList.get(j));
                }
                System.out.println();
            }
        } else {
            throw new RollingException("", lineLength, CommandList.DATE, 0, date, 0, "", "", "", 0);
        }
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