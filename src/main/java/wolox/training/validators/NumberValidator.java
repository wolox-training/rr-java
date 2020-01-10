package wolox.training.validators;

import com.google.common.base.Preconditions;

public class NumberValidator {

    public static void validate(int value, String name, int min, int max) {
        Preconditions
            .checkArgument(value >= min, String.format("%s can't be lower than %d", name, min));
        Preconditions
            .checkArgument(value <= max, String.format("%s can't be bigger than %d", name, max));
    }
}
