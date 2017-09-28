package com.parabits.paranote.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.parabits.paranote.utils.BitmapUtils;


public class PhotoView extends android.support.v7.widget.AppCompatImageView {

    private int m_resolution_width;
    private int m_resolution_height;

    private String m_current_uri;

    public void setResolutionWidth(int width) {m_resolution_width = width;}
    public void setResolutionHeight(int height) {m_resolution_height = height;}

    public PhotoView(Context context) {
        super(context);
        init(context);
    }

    public PhotoView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public PhotoView(Context context, AttributeSet attrs, int style)
    {
        super(context, attrs, style);
        init(context);
    }

    private void init(Context context)
    {
        setAdjustViewBounds(true);
        setScaleType(ScaleType.FIT_CENTER);
        m_current_uri = ""; //początkowo uri jest puste
    }

    public void loadImage(String uriString)
    {
        if(uriString.equals(m_current_uri)) //jeżeli nie nastąpiła zmiana obrazka, nie przeładowujemy go ponownie
        {
            return;
        }
        Uri uri = Uri.parse(uriString);
        Bitmap orginalBitmap = BitmapUtils.getBitmap(getContext(), uri);
        int width;
        int height;
//            if(m_keep_ratio)
//            {
        float aspectRatio = (float)orginalBitmap.getWidth()/ orginalBitmap.getHeight();
        width = (int)(m_resolution_width * aspectRatio);
        height = (int)(m_resolution_height * 1/aspectRatio);
//            } else {
//                width = m_image_width;
//                height = m_image_height;
//            }
        Bitmap resizedBitmap = BitmapUtils.resize(orginalBitmap, width, height);
        System.gc();
//            m_image_view.setImageURI(uri);
        setImageBitmap(resizedBitmap);

        m_current_uri = uriString;
    }
}
