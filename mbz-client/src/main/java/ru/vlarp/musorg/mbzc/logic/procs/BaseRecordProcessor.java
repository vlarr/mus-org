package ru.vlarp.musorg.mbzc.logic.procs;

public abstract class BaseRecordProcessor implements RecordProcessor {
    protected State state;
    protected Result result;

    @Override
    public State getState() {
        return state;
    }

    @Override
    public Result getResult() {
        return result;
    }

    public BaseRecordProcessor() {
        this.state = State.UNKNOWN;
        this.result = Result.UNKNOWN;
    }
}
