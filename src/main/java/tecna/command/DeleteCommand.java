package tecna.command;

import tecna.collection.TaskList;
import tecna.exception.WrongFormatException;
import tecna.storage.Storage;
import tecna.ui.Ui;

public class DeleteCommand extends Command {
    public DeleteCommand(String message) {
        super(message);
    }

    @Override
    public String execute(TaskList taskList, Storage storage, Ui ui) {
        super.execute(taskList, storage, ui);
        try {
            int index = parseDeleteCommand(taskList.getSize());
            return ui.printDeleteItemMsg(taskList, index);
        } catch (WrongFormatException e) {
            return ui.printError(e.getMessage());
        }
    }

    public int parseDeleteCommand(int taskListSize) throws WrongFormatException {
        String[] input_words = message.split("\\s+");

        if (input_words.length != 2) {
            throw new WrongFormatException("delete", "Delete command should be in the format of \"delete [index of the task from 1 to " + taskListSize +  "]\"");
        }

        try {
            int index =  Integer.parseInt(input_words[1]) - 1;
            if (index < 0 || index > taskListSize - 1) {
                throw new WrongFormatException("delete", "Delete command should be in the format of \"delete [index of the task from 1 to " + taskListSize +  "]\"");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new WrongFormatException("delete", "Delete command should be in the format of \"delete [index of the task from 1 to " + taskListSize +  "]\"");
        }
    }
}
