package com.parabits.paranote.data.parser;


import android.content.Context;

import com.parabits.paranote.views.INoteElementView;
import com.parabits.paranote.views.NoteTextView;

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

    public Creator getCreator()
    {
        return new Creator(this);
    }

    public class Creator implements INoteViewCreator
    {
        private TextElement m_element;

        public Creator(TextElement element)
        {
            m_element = element;
        }

        @Override
        public NoteTextView createView(Context context)
        {
            NoteTextView view = new NoteTextView(context);
            view.setText(m_element.getContent());
            return view;
        }
    }

    public static class Descriptor
    {
        public static String getDescription(INoteElementView view)
        {
            NoteTextView textView = (NoteTextView)view;
            String text = textView.getText().toString();
            TextElement element = new TextElement();
            element.setContent(text);

            return element.toString();
        }
    }
}
