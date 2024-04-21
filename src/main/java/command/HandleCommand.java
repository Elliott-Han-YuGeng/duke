package command;

import storage.Storage;
import tasktype.Task;
import tasktype.TaskList;
import exception.RollingException;
import ui.Ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HandleCommand {
    private static Storage file;
    private static TaskList todoList;
    private static String line;
    private static int lineLength;
    private static CommandList l1;
    private static int l2;
    private static String l22;
    private static int lineSlashLength;
    private static String l3;
    private static String l4;
    private static String l5;

    Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");

    private static final Ui ui = new Ui();

    public HandleCommand(Storage file, TaskList todoList, String line, int lineLength, CommandList l1, int l2, String l22, int lineSlashLength, String l3, String l4, String l5) {
        HandleCommand.file = file;
        HandleCommand.todoList = todoList;
        HandleCommand.line = line;
        HandleCommand.lineLength = lineLength;
        HandleCommand.l1 = l1;
        HandleCommand.l2 = l2;
        HandleCommand.l22 = l22;
        HandleCommand.lineSlashLength = lineSlashLength;
        HandleCommand.l3 = l3;
        HandleCommand.l4 = l4;
        HandleCommand.l5 = l5;
    }

    public static void throwRollingException() throws RollingException {
        throw new RollingException(line, lineLength, l1, l2, l22, lineSlashLength, l3, l4, l5, todoList.size());
    }

    public void handleTodo() throws RollingException {
        if (lineLength>1 && !line.contains("/")) {
            todoList.addTask(file, line, l1, l3, l4, l5);
        } else throwRollingException();
    }

    public void handleDeadline() throws RollingException {
        if (lineLength>1 && line.contains("/by ") && !l3.trim().equals("deadline") && lineSlashLength==2 && !l4.trim().equals("by")) {
            todoList.addTask(file, line, l1, l3, l4, l5);
        } else throwRollingException();
    }

    public void handleEvent() throws RollingException {
        if (lineLength>1 && line.contains("/from ") && line.contains("/to ") && !l3.trim().equals("event") && lineSlashLength==3 && !l4.trim().equals("from") && !l5.trim().equals("to")) {
            if (l4.length() >= 5 && l5.length() >= 3) {
                // Check if the date is in the correct format (yyyy-mm-dd)
                String from = l4.substring(5).trim();
                String to = l5.substring(3).trim();
                Matcher l4Matcher = pattern.matcher(from);
                Matcher l5Matcher = pattern.matcher(to);

                if (l4Matcher.matches() && l5Matcher.matches()) {
                    LocalDate date1 = LocalDate.parse(from);
                    LocalDate date2 = LocalDate.parse(to);
                    if (date1.isAfter(date2)) {
                        throwRollingException();
                    } else {
                        todoList.addTask(file, line, l1, l3, l4, l5);
                    }
                } else {
                    todoList.addTask(file, line, l1, l3, l4, l5);
                }
            } else {
                todoList.addTask(file, line, l1, l3, l4, l5);
            }
        } else throwRollingException();
    }

    public void handlePeriod() throws RollingException {
        if (lineLength>1 && line.contains("/between ") && line.contains("/and ") && !l3.trim().equals("period") && lineSlashLength==3 && !l4.trim().equals("between") && !l5.trim().equals("and")) {
            if (l4.length() >= 8 && l5.length() >= 4) {
                // Check if the date is in the correct format (yyyy-mm-dd)
                String between = l4.substring(8).trim();
                String and = l5.substring(4).trim();
                Matcher l4Matcher = pattern.matcher(between);
                Matcher l5Matcher = pattern.matcher(and);

                if (l4Matcher.matches() && l5Matcher.matches()) {
                    LocalDate date1 = LocalDate.parse(between);
                    LocalDate date2 = LocalDate.parse(and);
                    if (date1.isAfter(date2)) {
                        throwRollingException();
                    } else {
                        todoList.addTask(file, line, l1, l3, l4, l5);
                    }
                } else {
                    todoList.addTask(file, line, l1, l3, l4, l5);
                }
            } else {
                todoList.addTask(file, line, l1, l3, l4, l5);
            }
        } else throwRollingException();
    }

    public void handleMark() throws RollingException {
        if (lineLength==2 && l2!=0 && l2<=todoList.size()) {
            todoList.get(l2 - 1).markAsDone();
        } else throwRollingException();
        System.out.println();
        file.save(todoList.getTasks());
    }

    public void handleUnmark() throws RollingException {
        if (lineLength==2 && l2!=0 && l2<=todoList.size()) {
            todoList.get(l2 - 1).markAsNotDone();
        } else throwRollingException();
        System.out.println();
        file.save(todoList.getTasks());
    }

    public void handleDelete() throws RollingException {
        if (lineLength==2 && l2!=0 && l2<=todoList.size()) {
            todoList.removeTask(file, l2 - 1);
        } else throwRollingException();
    }

    public void handleDailyTask() throws RollingException {
        ArrayList<Task> tasks = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
        Matcher matcher = pattern.matcher(l22);
        int i = 0;
        if (todoList.isEmpty()) {
            System.out.println("You have no task in the list now.\n");
        } else if (matcher.matches()) {
            while (i < todoList.size()) {
                Task t = todoList.get(i);
                if (t.getFileString().contains(" | " + l22)) {
                    tasks.add(todoList.get(i));
                } else if (!t.getDates().equals("")) {
                    String[] dateSplits = t.getDates().split(" ");
                    LocalDate d0 = LocalDate.parse(l22);
                    LocalDate d1 = LocalDate.parse(dateSplits[0]);
                    LocalDate d2 = LocalDate.parse(dateSplits[1]);
                    if ((d0.isAfter(d1) || d0.equals(d1)) && (d0.isBefore(d2) || d0.equals(d2))) {
                        tasks.add(todoList.get(i));
                    }
                }
                i++;
            }
            ui.printList(tasks, l22);
        } else throwRollingException();
    }

    public void handleFind() throws RollingException {
        ArrayList<Task> tasks = new ArrayList<>();
        if (lineLength>1) {
            String keyword = line.substring(5).trim();
            for (int i = 0; i < todoList.size(); i++) {
                if (todoList.get(i).getDescription().contains(keyword)) {
                    tasks.add(todoList.get(i));
                }
            }
            ui.printList(tasks, keyword);
        } else throwRollingException();
    }
}
