package com.secondwind.dedenjji.common.enumerate;

public enum Level {
    GOSU("3"),
    JOONGSU("2"),
    CHOBO("1");

    private String level;

    Level(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return level;
    }
}
