package com.parabits.paranote.data.parser;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.widget.ImageView;

import com.parabits.paranote.utils.BitmapUtils;
import com.parabits.paranote.views.INoteElementView;
import com.parabits.paranote.views.NoteImageView;
import com.parabits.paranote.views.NoteTextView;

import java.util.List;

public class ImageElement extends NoteElement {

    //TODO dorobić zmienną która określa czy obrazek jest załącznikiem czy ma zostać pobrany z

    public ImageElement()
    {
        super();
        m_type = Type.PHOTO;
    }

    public ImageElement(String content) {
        super(content);
        m_type = Type.PHOTO;
    }

    public ImageElement(String content, List<ElementParameter> parameters)
    {
        super(content, parameters);
        m_type = Type.PHOTO;
    }

    @Override
    public Creator getCreator()
    {
        return new Creator(this);
    }


    public class Creator implements INoteViewCreator
    {

        private ImageElement m_element;
        private boolean m_resized;
        private int m_image_width;
        private int m_image_height;
        private boolean m_keep_ratio;

        public Creator(ImageElement element)
        {
            m_element = element;
            m_keep_ratio = true;
        }

        public boolean isResized() {return m_resized; }
        public void setResized(boolean resized) {m_resized = resized;}
        public int getWidth(){return m_image_width;}
        public int getHeight(){return m_image_height;}
        public void setImageWidth(int imageWidth) { m_image_width = imageWidth;}
        public void setImageHeight(int imageHeight) {m_image_height = imageHeight;}
        public boolean isKeepRatio() {return m_keep_ratio;}
        public void setKeepRatio(boolean keepRatio) {m_keep_ratio = keepRatio;}

        //TODO zobaczyć czy wszystko dobrze działa
        @Override
        public NoteImageView createView(Context context) {
            NoteImageView imageView = new NoteImageView(context);
            Uri uri = Uri.parse(m_element.getContent());
            if(m_resized)
            {
                imageView.setImageURI(uri);
            } else { // jeżeli skalujemy rozdzielczość obrazka
                Bitmap orginalBitmap = BitmapUtils.getBitmap(context, uri);
                int width;
                int height;
                if(m_keep_ratio)
                {
                    float aspectRatio = (float)orginalBitmap.getWidth()/ orginalBitmap.getHeight();
                    width = (int)(m_image_width * aspectRatio);
                    height = (int)(m_image_width * 1/aspectRatio);
                } else {
                    width = m_image_width;
                    height = m_image_height;
                }
                Bitmap resizedBitmap = BitmapUtils.resize(orginalBitmap, width, height);

                imageView.setUri(uri);
                imageView.setImageBitmap(resizedBitmap);
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageView.setAdjustViewBounds(true);
            }

            return imageView;
        }
    }

    public static class Descriptor
    {
        public static String getDescription(INoteElementView view) {
            NoteImageView imageView = (NoteImageView)view;
            Uri uri = imageView.getImageUri();

            ImageElement element = new ImageElement();
            element.setContent(uri.toString());
            return element.toString(); // TODO zamiast toString można zrobić metodę w Descriptor, która będzie opisywała element
        }
    }
}
