package tecna.command;

import tecna.collection.TaskList;
import tecna.exception.WrongFormatException;
import tecna.storage.Storage;
import tecna.ui.Ui;

/**
 * Represents a Command of type MarkCommand (mark a specified task as done).
 *
 * @author Solution below inspired by https://github.com/Feng1231/ip.
 */
public class MarkCommand extends Command {
    /**
     * Constructs a MarkCommand instance.
     *
     * @param message The whole command input in String.
     */
    public MarkCommand(String message) {
        super(message);
    }

    @Override
    public String execute(TaskList taskList, Storage storage, Ui ui) {
        super.execute(taskList, storage, ui);
        try {
            int index = parseMarkCommand(taskList.getSize());
            taskList.mark(index);
            return ui.printMarkMsg(taskList.getTask(index));
        } catch (WrongFormatException e) {
            return ui.printError(e.getMessage());
        }
    }

    /**
     * Interprets a command of type "mark".
     *
     * @param taskListSize The size of the current TaskList in the app
     *                     for checking the validity of the input.
     * @return The index of the item needs to be marked as done.
     * @throws WrongFormatException If the command is in wrong format.
     */
    public int parseMarkCommand(int taskListSize) throws WrongFormatException {
        if (taskListSize < 1) {
            throw new WrongFormatException("mark", "You have no tasks to mark.\nAdd some tasks, my dear!");
        }
        String[] input_words = message.split("\\s+");

        if (input_words.length != 2) {
            throw new WrongFormatException("mark", "Mark command should be in the format of \"mark [index of the task from 1 to " + taskListSize +  "]\"");
        }

        try {
            int index =  Integer.parseInt(input_words[1]) - 1;
            if (index < 0 || index > taskListSize - 1) {
                throw new WrongFormatException("mark", "Mark command should be in the format of \"mark [index of the task from 1 to " + taskListSize +  "]\"");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new WrongFormatException("mark", "Mark command should be in the format of \"mark [index of the task from 1 to " + taskListSize +  "]\"");
        }
    }
}
