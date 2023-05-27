package dtt.global.tansport;

/**
 * Represents the state of a user in the system.
 *  @author Hadi Abou Hassoun
 */
public enum UserState {
    ADMIN,  // Represents an administrator user
    EXAMINCOMMITTEEMEMBERS,  // Represents members of the examination committee
    EXAMINER,  // Represents an examiner user
    DEANERY,  // Represents a deanery user
    PENDING  // Represents a user with a pending status
}
