package ru.javawebinar.topjava.util.exception;

public class ErrorInfo {
    private final String url;
    private final ErrorType type;
    private final String[] errors;

    public ErrorInfo(CharSequence url, ErrorType type, String... errors) {
        this.url = url.toString();
        this.type = type;
        this.errors = errors;
    }
}