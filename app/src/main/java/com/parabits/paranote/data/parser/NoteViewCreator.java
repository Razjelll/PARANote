package com.parabits.paranote.data.parser;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.parabits.paranote.utils.BitmapUtils;
import com.parabits.paranote.views.INoteElementView;
import com.parabits.paranote.views.NoteImageView;
import com.parabits.paranote.views.NoteTextView;

public class NoteViewCreator {

    public static INoteElementView create(NoteElement element, Context context)
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

    private static INoteElementView createTextView(NoteElement element, Context context)
    {
        NoteTextView editText = new NoteTextView(context);
        editText.setText(element.getContent());
        //TODO tutaj zrobić parametry
        return editText;
    }

    private static final int IMAGE_SIZE = 500;

    private static INoteElementView createImageView(NoteElement element, Context context)
    {
        NoteImageView imageView = new NoteImageView(context);
        Uri uri = Uri.parse(element.getContent());
        Bitmap orginalBitmap = BitmapUtils.getBitmap(context, uri); //bitmapa z orginalnego obrazka
        float aspectRatio = (float)orginalBitmap.getWidth() / orginalBitmap.getHeight(); //TODO zobaczyć czy te proporcje działają dobrze
        Bitmap resizedBitmap = BitmapUtils.resize(orginalBitmap, (int)(IMAGE_SIZE * aspectRatio), (int)(IMAGE_SIZE * 1/aspectRatio)); // bitmapa pomniejszonego obrazka\

        //imageView.setImageURI(uri);
        imageView.setUri(uri); //zapisujemy uri do orginalnego obrazka
        imageView.setImageBitmap(resizedBitmap); //ustawiamy zmniejszoną bitmapę do wyświetlenia
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setAdjustViewBounds(true);

        return imageView;
    }
}
