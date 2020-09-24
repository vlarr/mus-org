package ru.vlarp.musorg.mbzc.logic.nodes;

public abstract class BaseNode implements Node {
    protected State state = State.NONE;

    @Override
    public State getState() {
        return state;
    }
}
