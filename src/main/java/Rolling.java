import java.util.Scanner;

public class Rolling {
    public static void main(String[] args) {
//      Start
        String greeting = "Hello! I'm Your Rolling Bear!\n"
                        + "What can I do for you?";
        System.out.println(greeting);

//      User input
        Scanner in = new Scanner(System.in);
        String line = in.nextLine();

//      Echo
        while(!line.toLowerCase().equals("bye")){
            System.out.println(line);
            line = in.nextLine();
        }

//      Exit
        System.out.println("Bye. Hope to see you again soon!");
    }
}
