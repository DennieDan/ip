package tecna;

public class ToDo extends Task {

    public ToDo(String taskName) {
        super(taskName);
    }

    public ToDo(String taskName, boolean isDone) {
        super(taskName);
        if (isDone) {
            super.markAsDone();
        }
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
