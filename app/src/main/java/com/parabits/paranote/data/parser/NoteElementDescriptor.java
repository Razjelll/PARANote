package com.parabits.paranote.data.parser;

import android.net.Uri;

import com.parabits.paranote.views.INoteElementView;
import com.parabits.paranote.views.NoteImageView;
import com.parabits.paranote.views.NoteTextView;

public class NoteElementDescriptor {

    public static String getString(INoteElementView view)
    {
        switch (view.getType())
        {
            case TEXT:
                return getTextElementString(view);
            case IMAGE:
                return getImageElementString(view);

        }
        return null;
    }

    private static String getTextElementString(INoteElementView view)
    {
        NoteTextView textView = (NoteTextView) view;
        String text = textView.getText().toString();

        TextElement element = new TextElement();
        element.setContent(text);
        //TODO ustawienie parametrów

        return element.toString();
    }

    private static String getImageElementString(INoteElementView view)
    {
        NoteImageView imageView = (NoteImageView) view;
        Uri uri = imageView.getImageUri();

        ImageElement element = new ImageElement();
        //element.setContent(uri.toString());
        element.setContent(uri.toString());
        //TODO ustawienie parametrów

        return element.toString();
    }




}
