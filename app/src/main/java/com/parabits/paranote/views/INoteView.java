package com.parabits.paranote.views;

/**
 * Created by Razjelll on 29.08.2017.
 */

public interface INoteView {

    enum Type
    {
        TEXT,
        IMAGE
    }

    Type getType();
}
