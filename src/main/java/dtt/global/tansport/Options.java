package dtt.global.tansport;

/**
 * Represents the available options for a vote or choice in a circulation.
 *  @author Hadi Abou Hassoun
 */
public enum Options {

    STIMME_ZU("Stimme zu"),  // Represents the option to vote in favor
    LEHNE_AB("Lehne ab"),  // Represents the option to vote against
    ENTHALTE_MICH("Enthalte mich") ; // Represents the option to abstain from voting
    private final String label;

    Options(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

