package command;

import storage.Storage;
import tasktype.TaskList;
import exception.RollingException;

import java.time.LocalDate;
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
    private static Matcher l4Matcher;
    private static Matcher l5Matcher;

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
                l4Matcher = pattern.matcher(l4.substring(5).trim());
                l5Matcher = pattern.matcher(l5.substring(3).trim());
                if (l4Matcher.matches() && l5Matcher.matches()) {
                    LocalDate date1 = LocalDate.parse(l4.substring(5).trim());
                    LocalDate date2 = LocalDate.parse(l5.substring(3).trim());
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
}
