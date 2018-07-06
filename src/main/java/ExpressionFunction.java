import java.util.function.BiFunction;
import java.util.function.Function;

public interface ExpressionFunction<T, U, R> extends BiFunction{


    @Override
    default Object apply(Object o, Object o2) {
        return null;
    }

    @Override
    default BiFunction andThen(Function after) {
        return null;
    }
}
