package wolox.training.validators;

import com.google.common.base.Preconditions;

public final class StringValidator {

    private StringValidator() {
    }

    public static void validate(String value, String name, int maxLength) {
        Preconditions.checkNotNull(value, name + " can't be empty");
        Preconditions.checkArgument(value.length() > 0, name + " can't be blank");
        Preconditions.checkArgument(value.length() < maxLength,
            String.format("%s can't be longer than %d characters.", name, maxLength));
    }
}
