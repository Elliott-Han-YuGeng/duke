package command;

import storage.Storage;
import tasktype.TaskList;
import exception.RollingException;

public class HandleCommand {
    private static Storage file;
    private static TaskList todoList;
    private static String line;
    private static int lineLength;
    private static CommandList l1;
    private static int l2;
    private static int lineSlashLength;
    private static String l3;
    private static String l4;
    private static String l5;
    
    public HandleCommand(Storage file, TaskList todoList, String line, int lineLength, CommandList l1, int l2, int lineSlashLength, String l3, String l4, String l5) {
        HandleCommand.file = file;
        HandleCommand.todoList = todoList;
        HandleCommand.line = line;
        HandleCommand.lineLength = lineLength;
        HandleCommand.l1 = l1;
        HandleCommand.l2 = l2;
        HandleCommand.lineSlashLength = lineSlashLength;
        HandleCommand.l3 = l3;
        HandleCommand.l4 = l4;
        HandleCommand.l5 = l5;
    }

    public static void throwRollingException() throws RollingException {
        throw new RollingException(line, lineLength, l1, l2, l3, lineSlashLength, l4, l5, todoList.size());
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
            todoList.addTask(file, line, l1, l3, l4, l5);
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
