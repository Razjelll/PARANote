package com.parabits.paranote.data.parser;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.ImageView;

import com.parabits.paranote.views.INoteView;
import com.parabits.paranote.views.NoteImageView;
import com.parabits.paranote.views.NoteTextView;

public class NoteViewCreator {

    public static INoteView create(NoteElement element, Context context)
    {
        switch (element.getType())
        {
            case TEXT:
                return createTextView(element, context);
            case PHOTO:
                return createImageView(element, context);
            default:
                throw new IllegalArgumentException("Unknown elementy type " + element.getType().toString());
        }
    }

    private static INoteView createTextView(NoteElement element, Context context)
    {
        NoteTextView editText = new NoteTextView(context);
        editText.setText(element.getContent());
        //TODO tutaj zrobić parametry
        return editText;
    }

    private static INoteView createImageView(NoteElement element, Context context)
    {
        NoteImageView imageView = new NoteImageView(context);
        //Uri uri = Uri.parse(element.getContent());
        //Uri uri = Uri.parse("content://media/internal/images/media/107");
        Uri uri = Uri.parse(element.getContent());
        boolean isValid = URLUtil.isValidUrl(element.getContent());
        imageView.setImageURI(uri);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setAdjustViewBounds(true);

        return imageView;
    }
}
