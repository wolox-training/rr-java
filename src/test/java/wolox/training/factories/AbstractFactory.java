package wolox.training.factories;

import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFactory<T> {

    private static Object SYNC = new Object();

    // All factories should use the same faker instance
    private static Faker faker;

    public static Faker getFaker() {
        synchronized (SYNC) {
            if (faker == null) {
                faker = new Faker();
            }
        }
        return faker;
    }

    public Faker faker() {
        return getFaker();
    }

    public abstract T build();

    public List<T> buildList(int count) {
        List<T> result = new ArrayList<>();
        for (int i = 0; i < count; ++i) {
            result.add(build());
        }
        return result;
    }
}
