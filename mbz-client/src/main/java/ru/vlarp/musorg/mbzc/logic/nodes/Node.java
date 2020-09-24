package ru.vlarp.musorg.mbzc.logic.nodes;

import ru.vlarp.musorg.mbzc.logic.ProcessorApi;

public interface Node {
    enum State {
        NONE, FILL_RESULT, COMPLETE
    }

    State getState();

    void process(ProcessorApi processorApi);
}
