package ua.com.alevel.exception;

import java.io.IOException;

public class IncorrectStringException extends IOException {
    public IncorrectStringException(String message) {
        super(message);
    }
}
