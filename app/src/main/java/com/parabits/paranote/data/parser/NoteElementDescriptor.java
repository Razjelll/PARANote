package com.parabits.paranote.data.parser;

import android.net.Uri;
import android.view.View;

import com.parabits.paranote.utils.UriUtils;
import com.parabits.paranote.views.INoteView;
import com.parabits.paranote.views.NoteImageView;
import com.parabits.paranote.views.NoteTextView;

public class NoteElementDescriptor {

    public static String getString(INoteView view)
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

    private static String getTextElementString(INoteView view)
    {
        NoteTextView textView = (NoteTextView) view;
        String text = textView.getText().toString();

        TextElement element = new TextElement();
        element.setContent(text);
        //TODO ustawienie parametrów

        return element.toString();
    }

    private static String getImageElementString(INoteView view)
    {
        NoteImageView imageView = (NoteImageView) view;
        Uri uri = imageView.getImageUri();

        PhotoElement element = new PhotoElement();
        //element.setContent(uri.toString());
        element.setContent(uri.toString());
        //TODO ustawienie parametrów

        return element.toString();
    }
}
