package ru.vlarp.musorg.mbzc.logic;

public class ProcessorApiResponse<T> {
    public enum State {
        NONE, PASS, ERROR
    }

    private final T result;
    private final State state;

    public T getResult() {
        return result;
    }

    public State getState() {
        return state;
    }

    public ProcessorApiResponse(T result, State state) {
        this.result = result;
        this.state = state;
    }

    public static <T> ProcessorApiResponse<T> emptyResponse() {
        return new ProcessorApiResponse<>(null, State.NONE);
    }
}
