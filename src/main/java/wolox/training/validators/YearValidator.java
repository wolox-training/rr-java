package wolox.training.validators;

import com.google.common.base.Preconditions;

public final class YearValidator {

    public static void validate(String year, String name) {
        Preconditions.checkNotNull(year, name + "can't be blank.");
        Preconditions.checkArgument(year.matches("^[0-9]+$"), name + " must be a number");
    }
}
