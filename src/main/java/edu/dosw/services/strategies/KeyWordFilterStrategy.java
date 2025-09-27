package edu.dosw.services.strategies;

import edu.dosw.model.Task;

import java.util.List;

public class KeyWordFilterStrategy implements FilterStrategy{

    public List<Task> filter(List<Task> tasks, String keyword) {
        return tasks.stream().filter(task -> task.getTitle().contains(keyword)).toList();
    }
}
