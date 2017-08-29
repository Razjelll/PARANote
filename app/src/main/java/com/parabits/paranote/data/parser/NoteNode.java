package com.parabits.paranote.data.parser;

import java.util.List;


public class NoteNode {

    private List<NoteElement> m_elements;

    public void addElement(NoteElement element)
    {
        m_elements.add(element);
    }

    public void deleteElement(int position)
    {
        m_elements.remove(position);
    }


}
