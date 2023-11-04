package net.lesscoding.link;

public abstract class BattleRequestHandler {

    protected BattleRequestHandler nextHandler;

    public void setNextHandler(BattleRequestHandler handler) {
        nextHandler = handler;
    }

    public abstract void handleRequest(BattleRequest request);
}
