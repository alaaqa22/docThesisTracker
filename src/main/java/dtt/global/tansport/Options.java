package dtt.global.tansport;

/**
 * Represents the available options for a vote or choice in a circulation.
 *  @author Hadi Abou Hassoun
 */
public enum Options {

    STIMME_ZU(1, "Stimme zu"),  // Represents the option to vote in favor
    LEHNE_AB(2, "Lehne ab"),  // Represents the option to vote against
    ENTHALTE_MICH(3, "Enthalte mich"); // Represents the option to abstain from voting

    private final String label;
    private final int value;

    Options(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public int getValue() {
        return value;
    }

    // This method will return an Options object given a value
    public static Options fromValue(int value) {
        for (Options option : Options.values()) {
            if (option.getValue() == value) {
                return option;
            }
        }
        throw new IllegalArgumentException("Invalid value for Options: " + value);
    }
}
