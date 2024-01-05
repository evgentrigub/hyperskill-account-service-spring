package account.services.validators;

public class FormattingConstraints {
    public static boolean isValidPeriod(String period) {
        return period.matches("(0[1-9]|1[0-2])-[0-9]{4}");
    }
}