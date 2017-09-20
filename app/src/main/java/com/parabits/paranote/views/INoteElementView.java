package com.parabits.paranote.views;

/**
 * Created by Razjelll on 29.08.2017.
 */

public interface INoteElementView {

    enum Type
    {
        NONE,
        TEXT,
        IMAGE,
        LIST
    }

    Type getType();
    int getId();
    void setId(int id);
}
