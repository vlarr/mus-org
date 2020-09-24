package ru.vlarp.musorg.mbzc.logic.procs;

import ru.vlarp.musorg.mbzc.logic.ProcessorApi;

public interface RecordProcessor {
    enum State {
        UNKNOWN, IN_PROGRESS, COMPLETE
    }

    enum Result {
        UNKNOWN, PASS, FAILURE
    }

    State getState();

    Result getResult();

    void process(ProcessorApi processorApi);
}
