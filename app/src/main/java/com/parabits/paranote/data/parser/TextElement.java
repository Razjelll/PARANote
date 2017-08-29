package com.parabits.paranote.data.parser;


import java.util.List;

public class TextElement extends NoteElement {

    public TextElement()
    {
        super();
        m_type = Type.TEXT;
    }

    public TextElement(String content) {
        super(content);
        m_type = Type.TEXT;
    }

    public TextElement(String content, List<ElementParameter> parameters)
    {
        super(content, parameters);
        m_type = Type.TEXT;
    }


}
