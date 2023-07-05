package dtt.global.tansport;

/**
 * Represents the state of a user in the system.
 *  @author Hadi Abou Hassoun
 */
public enum UserState {
    ADMIN(4),  // Represents an administrator user
    EXAMINCOMMITTEEMEMBERS(2),  // Represents members of the examination committee
    EXAMINER(1),  // Represents an examiner user
    DEANERY(3),  // Represents a deanery user
    PENDING(0);  // Represents a user with a pending status

    private final int priority;

    UserState(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}

