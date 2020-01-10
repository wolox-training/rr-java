package wolox.training.validators;

import com.google.common.base.Preconditions;
import wolox.training.exceptions.SingletonInstanceException;

public final class YearValidator {

    // This class is a singleton
    private YearValidator() throws SingletonInstanceException {
        throw new SingletonInstanceException();
    }

    public static void validate(String year, String name) {
        Preconditions.checkNotNull(year, name + "can't be blank.");
        Preconditions.checkArgument(year.matches("^[0-9]+$"), name + " must be a number");
    }
}
