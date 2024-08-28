import java.util.ArrayList;
import java.util.Scanner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Tecna {
    private ArrayList<Task> taskList;
    private int todoSize;

    /**
     * A constructor of Tecna chatbot
     */
    public Tecna() {
        this.taskList = new ArrayList<>();
        this.todoSize = 0;
    }

    /**
     * Exits the chatbot by printing the goodbye lines
     */
    public void exitChatBot() {
        System.out.println("----------------------------------------------");
        System.out.println("Pleased to help you! See you again ^_^");
        System.out.println("----------------------------------------------");
    }

    /**
     * Repeats the input entered by the user
     */
    public void echo() {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        if (input.equalsIgnoreCase("bye")) {
            this.exitChatBot();
        } else {
            System.out.println("----------------------------------------------");
            System.out.println(input);
            System.out.println("----------------------------------------------");
        }
        sc.close();
    }

    /**
     * Receives requests entered by the user.
     * Accepts string input and processes accordingly
     */
    public void getRequest() {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        String[] input_words = input.split(" ");
        while (!input.equalsIgnoreCase("bye")) {
            System.out.println("----------------------------------------------");
            if (input_words[0].equalsIgnoreCase("list")) {
                this.listItems();
            } else if (input_words[0].equalsIgnoreCase("mark")) {
                int index = Integer.parseInt(input_words[1]);
                taskList.get(index - 1).markAsDone();
                System.out.println("Nice job! I've mark this as done. You deserve a short break <3");
                System.out.println(taskList.get(index - 1));
            } else if (input_words[0].equalsIgnoreCase("unmark")) {
                int index = Integer.parseInt(input_words[1]);
                taskList.get(index - 1).unMarkAsDone();
                System.out.println("I've mark this as undone. Keep going, my friend!");
                System.out.println(taskList.get(index - 1));
            } else if (input_words[0].equalsIgnoreCase("delete")) {
                int index = Integer.parseInt(input_words[1]);
                this.deleteItem(index - 1);
            } else {
                try {
                    this.addItem(input);
                } catch (InvalidRequestException ive) {
                    System.out.println("Oops! Your request sounds strange for me. Please enter a valid request ^^");
                } catch (TodoWrongFormatException tde) {
                    System.out.println(tde.getMessage());
                }
            }

            System.out.println("----------------------------------------------");

            input = sc.nextLine();
            input_words = input.split(" ");
        }
        this.exitChatBot();
        sc.close();
    }

    /**
     * Adds new item to the list of tasks
     * @param item extracted from the user input
     */
    public void addItem(String item) throws InvalidRequestException, TodoWrongFormatException {
        Task task = getTask(item);
        this.taskList.add(task);
        ++this.todoSize;
        System.out.println("Sure! I've added this task:");
        System.out.println(task);
        System.out.println(">> Now you have " + this.todoSize + (todoSize > 1 ? " tasks" : " task") + " in the list." );
    }

    /**
     * Creates the appropriate type of task
     * based on the input
     * @param input including type of task and task description
     * @return the corresponding task with correct type
     */
    private Task getTask(String input) throws InvalidRequestException,TodoWrongFormatException {
        int boundary = input.indexOf(" ");
        String category;
        try {
            category = input.substring(0, boundary);
        } catch (StringIndexOutOfBoundsException e) {
            category = input;
        }
        if (category.equalsIgnoreCase("todo")) {
            if (boundary == -1) {
                throw new TodoWrongFormatException("todo description cannot be empty!");
            }
            String des = input.substring(boundary + 1);
            if (des.isBlank()) {
                throw new TodoWrongFormatException("todo description cannot be empty!");
            }
            return new ToDo(des);
        } else if (category.equalsIgnoreCase("deadline")) {
            String[] description = input.substring(boundary + 1).split("/by");
            DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
            LocalDateTime by = LocalDateTime.parse(description[1].trim(), pattern);

            return new Deadline(description[0].trim(), by);
        } else if (category.equalsIgnoreCase("event")) {
            String[] description = input.substring(boundary + 1).split("/from | /to ");
            DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
            return new Event(description[0].trim(), LocalDateTime.parse(description[1].trim(), pattern), LocalDateTime.parse(description[2].trim(), pattern));
        } else {
            throw new InvalidRequestException();
        }
    }

    /**
     * Displays all the tasks in the task list
     */
    public void listItems() {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < this.todoSize; ++i) {
            System.out.println(i + 1 + ". " + this.taskList.get(i));
        }
    }

    public void deleteItem(int index) {
        String item = this.taskList.get(index).toString();
        this.taskList.remove(index);
        this.todoSize--;
        System.out.println("Sure! I've deleted this task:");
        System.out.println(item);
        System.out.println(">> Now you have " + this.todoSize + (todoSize > 1 ? " tasks" : " task") + " in the list." );
    }

    public static void main(String[] args) {
        String logo = " **          **\n" +
                      "*  *        *  *\n" +
                      "*   *      *   *\n" +
                      "*    *    *    *\n" +
                      "*     *  *     *\n" +
                      " *     **     *\n" +
                      "  *    **    *\n" +
                      "   *   **   *\n" +
                      "    *  **  *\n" +
                      "     ******\n" +
                      "      ****\n" +
                      "     * ** *\n" +
                      "    *  **  *\n" +
                      "    ***  ***\n";
        System.out.println(logo);
        System.out.println("I'm Tecna!\nHow can I help you?");
        System.out.println("----------------------------------------------");
        Tecna tecna = new Tecna();
        tecna.getRequest();
        // tecna.echo();
    }
}
