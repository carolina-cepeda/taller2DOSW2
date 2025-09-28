package edu.dosw.services.strategies;

import edu.dosw.model.States;
import edu.dosw.model.Task;

import java.util.List;

public class StatusFilterStrategy implements FilterStrategy{

    public List<Task> filter(List<Task> tasks, String status) {
        States state = States.valueOf(status.toUpperCase());
        return tasks.stream().filter(task -> task.getState().equals(state)).toList();
    }
}
