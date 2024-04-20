import java.nio.file.Paths;

import ui.Ui;
import storage.Storage;
import parser.Parser;
import tasktype.TaskList;
import command.CommandList;
import command.HandleCommand;
import exception.RollingException;

public class Rolling {
    private static Ui ui;
    private static Storage file;
    private static TaskList todoList;
    private static HandleCommand hCMD;

    //  User input data
    private static String line;            // user original input
    private static int lineLength;
    private static CommandList l1;         // keyword: todo, deadline, event, mark, unmark, list, bye
    private static int l2;                 // number to mark or unmark: 1, 2, 3, ...
    private static int lineSlashLength;
    private static String l3;              // contains task description
    private static String l4;              // by ? or from ?
    private static String l5;              // to ?

    public Rolling(String filePath) {
        ui = new Ui();
        file = new Storage(filePath);
        todoList = new TaskList(file.load());
    }

//  ---------- Running Method --------------------------------------------------------

    public static void getUserInput() {
        Parser parser = new Parser();
        parser.userInput();
        line = parser.getLine();
        lineLength = parser.getLineLength();
        l1 = parser.getL1();
        l2 = parser.getL2();
        lineSlashLength = parser.getLineSlashLength();
        l3 = parser.getL3();
        l4 = parser.getL4();
        l5 = parser.getL5();
        hCMD = new HandleCommand(file, todoList, line, lineLength, l1, l2, lineSlashLength, l3, l4, l5);
    }

    public void run() {
        ui.start();
        getUserInput();

//      Dialog
        while(!line.equalsIgnoreCase("bye")) {
            try {
                switch (l1) {
                    case TODO:
                        hCMD.handleTodo();
                        break;
                    case DEADLINE:
                        hCMD.handleDeadline();
                        break;
                    case EVENT:
                        hCMD.handleEvent();
                        break;
                    case MARK:
                        hCMD.handleMark();
                        break;
                    case UNMARK:
                        hCMD.handleUnmark();
                        break;
                    case DELETE:
                        hCMD.handleDelete();
                        break;
                    case LIST:
                        ui.printList(todoList.getTasks());
                        break;
                    default:
                        throw new RollingException(line, lineLength, l1, l2, l3, lineSlashLength, l4, l5, todoList.size());
                }
            } catch (RollingException e) {
                getUserInput();
                continue;
            }
            getUserInput();
        }

        ui.exit();
    }

//  ---------- Main Function --------------------------------------------------------

    public static void main(String[] args) {
        String filePath = Paths.get(".", "data", "rolling.txt").toString();
        new Rolling(filePath).run();
    }
}