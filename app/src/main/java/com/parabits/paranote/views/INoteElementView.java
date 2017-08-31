package com.parabits.paranote.views;

/**
 * Created by Razjelll on 29.08.2017.
 */

public interface INoteElementView {

    enum Type
    {
        TEXT,
        IMAGE
    }

    Type getType();
}
