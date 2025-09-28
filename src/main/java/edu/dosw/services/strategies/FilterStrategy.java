package edu.dosw.services.strategies;

import edu.dosw.model.Task;

import java.util.List;

public interface FilterStrategy {
    List<Task> filter(List<Task> tasks, String param);
}
