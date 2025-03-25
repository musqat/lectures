package zerobase.weather.error;

public class InvalidDate extends RuntimeException{
    private static final String MESSAGE = "너무 과거이거나 미래 입니다.";

    public InvalidDate() {
        super(MESSAGE);
    }
}
