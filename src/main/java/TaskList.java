import java.util.ArrayList;

public class TaskList {
    private static final Ui ui = new Ui();
    private final ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public int size() {
        return tasks.size();
    }

    public Task get(int i) {
        return tasks.get(i);
    }

    public void removeTask(Storage file, int i) {
        ui.showRemoveTaskSuccess(tasks, i);
        tasks.remove(i);
        file.save(tasks);
    }

    public void addTask(Storage file, String line, CommandList l1, String l3, String l4, String l5) {
        switch (l1) {
            case TODO:
                tasks.add(new Todo(line.substring(5).trim()));
                break;
            case DEADLINE:
                tasks.add(new Deadline(l3.substring(9).trim(), l4.substring(3).trim()));
                break;
            case EVENT:
                tasks.add(new Event(l3.substring(6).trim(), l4.substring(5).trim(), l5.substring(3).trim()));
                break;
        }
        ui.showAddTaskSuccess(tasks);
        file.save(tasks);
    }
}