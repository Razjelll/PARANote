package com.parabits.paranote.data.parser;

import java.util.List;

public class PhotoElement extends NoteElement {

    //TODO dorobić zmienną która określa czy obrazek jest załącznikiem czy ma zostać pobrany z
    public PhotoElement(String content) {
        super(content);
        m_type = Type.PHOTO;
    }

    public PhotoElement(String content, List<ElementParameter> parameters)
    {
        super(content, parameters);
        m_type = Type.PHOTO;
    }


}
