/**
 * This is the main class of the application.
 * Running this class will start the application.
 */
import ui.Ui;
import storage.Storage;
import parser.Parser;
import tasktype.TaskList;
import command.CommandList;
import command.HandleCommand;
import exception.RollingException;
import java.nio.file.Paths;

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
    private static String l22;             // Dateformat yyyy-mm-dd
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

    /**
     * Gets the user input.
     */
    public static void getUserInput() {
        Parser parser = new Parser();
        parser.userInput();
        line = parser.getLine();
        lineLength = parser.getLineLength();
        l1 = parser.getL1();
        l2 = parser.getL2();
        l22 = parser.getL22();
        lineSlashLength = parser.getLineSlashLength();
        l3 = parser.getL3();
        l4 = parser.getL4();
        l5 = parser.getL5();
        hCMD = new HandleCommand(file, todoList, line, lineLength, l1, l2, l22, lineSlashLength, l3, l4, l5);
    }
    
    /**
     * Runs the application.
     */
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
                    case PERIOD:
                        hCMD.handlePeriod();
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
                    case DATE:
                        hCMD.handleDailyTask();
                        break;
                    case FIND:
                        hCMD.handleFind();
                        break;
                    case LIST:
                        ui.printList(todoList.getTasks(), "");
                        break;
                    default:
                        throw new RollingException(line, lineLength, l1, l2, l22, lineSlashLength, l3, l4, l5, todoList.size());
                }
            } catch (RollingException e) {
                getUserInput();
                continue;
            }
            getUserInput();
        }

        ui.exit();
    }

//  -------------------------------------------------------------------------------------------------
    /**
     * The main method of the application.
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        String filePath = Paths.get(".", "data", "rolling.txt").toString();
        new Rolling(filePath).run();
    }
}