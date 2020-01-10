package wolox.training.validators;

import com.google.common.base.Preconditions;
import wolox.training.exceptions.SingletonInstanceException;

public final class StringValidator {

    // This class is a singleton
    private StringValidator() throws SingletonInstanceException {
        throw new SingletonInstanceException();
    }

    public static void validate(String value, String name, int maxLength) {
        Preconditions.checkNotNull(value, name + " can't be empty");
        Preconditions.checkArgument(value.length() < 1, name + "can't be empty");
        Preconditions.checkArgument(value.length() > maxLength,
            String.format("%s can't be longer than %d characters.", name, maxLength);
    }
}
