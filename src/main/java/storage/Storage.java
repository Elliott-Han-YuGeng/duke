package storage;

import tasktype.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Storage {
    private final String filePath;
    private static final ArrayList<String> unreadableLines = new ArrayList<>();

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    private static boolean checkInputValidity (String line) {
        String[] lineSplits = line.split("\\|");
        if (lineSplits.length < 3) {
            return false;
        }

        String doneFlag = lineSplits[1].trim();
        if (!(doneFlag.equals("1") || doneFlag.equals("0"))) {
            return false;
        }

        String type = lineSplits[0].trim();
        if (type.equals("T") && lineSplits.length != 3) {
            return false;
        } else if (type.equals("D") && lineSplits.length != 4) {
            return false;
        } else if (type.equals("E") && lineSplits.length != 5) {
            return false;
        }

        return true;
    }


    public ArrayList<Task> load() {
        File file = new File(filePath);
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        }

        ArrayList<Task> tasks = new ArrayList<>();

        while (scanner.hasNext()) {
            String line = scanner.nextLine().trim();
            if (!checkInputValidity(line)) {
                unreadableLines.add(line);
                continue; // Skip this line
            }

            String[] lineSplits = line.split("\\|");
            boolean isDone = lineSplits[1].trim().equals("1");
            String description = lineSplits[2].trim();
            switch (lineSplits[0].trim()) {
                case "T":
                    tasks.add(new Todo(description, isDone));
                    break;
                case "D":
                    tasks.add(new Deadline(description, isDone, lineSplits[3].trim()));
                    break;
                case "E":
                    tasks.add(new Event(description, isDone, lineSplits[3].trim(), lineSplits[4].trim()));
                    break;
                default:
            }
        }
        return tasks;
    }

    public void save(ArrayList<Task> tasks) {
        File file = new File(filePath);

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        try {
            FileWriter writer = new FileWriter(file);
            for (Task t : tasks) {
                writer.write(t.getFileString() + "\n");
            }
            for (String line : unreadableLines) {
                if (!line.isEmpty()) {
                    writer.write(line + "\n");
                }
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks to file.");
        }
    }
}