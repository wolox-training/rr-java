package wolox.training.validators;

import com.google.common.base.Preconditions;
import java.net.MalformedURLException;
import java.net.URL;

public class URLValidator {

    public static void validate(String url, String name) {
        Preconditions.checkNotNull(url, name + " is required.");
        Preconditions.checkArgument(isUrl(url), name + " is not an URL.");
    }

    private static boolean isUrl(String url) {
        try {
            new URL(url);
        } catch (MalformedURLException e) {
            return false;
        }
        return true;
    }
}
