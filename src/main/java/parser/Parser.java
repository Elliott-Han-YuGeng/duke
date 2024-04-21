package parser;

import command.CommandList;
import java.util.Scanner;

public class Parser {
    private static Scanner in;
    private static String line;                // user original input
    private static int lineLength;
    private static CommandList l1;             // keyword: todo, deadline, event, mark, unmark, list, bye
    private static int l2;                     // number to mark or unmark: 1, 2, 3, ...
    private static String l22;                 // Dateformat yyyy-mm-dd
    private static int lineSlashLength;
    private static String l3;                  // contains task description
    private static String l4;                  // by ? or from ?
    private static String l5;                  // to ?

    public Parser() {
        in = new Scanner(System.in);
        line = "";
        lineLength = 0;
        l1 = CommandList.NULL;
        l2 = 0;
        l22 = "";
        lineSlashLength = 0;
        l3 = "";
        l4 = "";
        l5 = "";
    }

    public void userInput() {
        line = in.nextLine().trim().replaceAll("\\s+", " ");

//      Split by space
        String[] lineSplit = line.split(" ");
        lineLength = lineSplit.length;
        try {
            l1 = CommandList.valueOf(lineSplit[0].toUpperCase());
        } catch (Exception e) {
            l1 = CommandList.NULL;
        }
        try {
            l2 = Integer.parseInt(lineSplit[1]);
        } catch (Exception e) {
            l2 = 0;
        }
        try {
            l22 = lineSplit[1];
        } catch (Exception e) {
            l22 = "";
        }

//      Split by slash
        String[] lineSplitS = line.split("/");
        lineSlashLength = lineSplitS.length;
        l3 = lineSplitS[0];
        try {
            l4 = lineSplitS[1];
        } catch (Exception e) {
            l4 = "";
        }
        try {
            l5 = lineSplitS[2];
        } catch (Exception e) {
            l5 = "";
        }
    }

    public String getLine() {
        return line;
    }

    public int getLineLength() {
        return lineLength;
    }

    public CommandList getL1() {
        return l1;
    }

    public int getL2() {
        return l2;
    }

    public String getL22() {
        return l22;
    }

    public int getLineSlashLength() {
        return lineSlashLength;
    }

    public String getL3() {
        return l3;
    }

    public String getL4() {
        return l4;
    }

    public String getL5() {
        return l5;
    }
}