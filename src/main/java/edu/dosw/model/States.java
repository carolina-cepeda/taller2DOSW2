package edu.dosw.model;

/**
 * Represents the possible states that a task can be in during its lifecycle.
 */
public enum States {
    /** Task has been created but work has not yet started */
    PENDING,
    
    /** Task is currently being worked on */
    IN_PROGRESS,
    
    /** Task has been completed */
    COMPLETE
}
