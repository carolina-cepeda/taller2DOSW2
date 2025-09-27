package edu.dosw.services.strategies;

import edu.dosw.model.Task;

import java.util.Comparator;
import java.util.List;

public class DateFilterStrategy implements FilterStrategy {

    public List<Task> filter(List<Task> tasks, String extra) {
        tasks.sort(Comparator.comparing(Task::getDate));
        return tasks;
    }

}
