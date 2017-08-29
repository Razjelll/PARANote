package com.parabits.paranote.views;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.parabits.paranote.R;
import com.parabits.paranote.data.parser.NoteElement;
import com.parabits.paranote.data.parser.NoteElementDescriptor;
import com.parabits.paranote.data.parser.NoteParser;
import com.parabits.paranote.data.parser.NoteViewCreator;
import com.parabits.paranote.data.parser.PhotoElement;
import com.parabits.paranote.data.parser.TextElement;
import com.parabits.paranote.utils.UriUtils;

import java.util.ArrayList;
import java.util.List;


public class NoteContainer extends ScrollView {

    private final int CONTAINER_MARGIN = 8;
    private final int CONTAINER_PADDING = 8; //TODO można zrobić w dp

    //TODO zastanowić się, czy trzymać elementy, czy korzystać z nich tylko w razie potrzeby
    private LinearLayout m_note_container;
    private List<NoteElement> m_note_elements;
    private List<INoteView> m_note_views;
    //TODO może być konieczne dodanie listy widoków

    public NoteContainer(Context context) {
        super(context);
        init(context);
    }

    public NoteContainer(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
        //TOOD zadeklarować nowe atrybuty
    }

    public NoteContainer(Context context, AttributeSet attrs, int resID)
    {
        super(context, attrs, resID);
        init(context);
    }


    private void init(Context context)
    {
        m_note_container = new LinearLayout(context);
        m_note_container.setOrientation(LinearLayout.VERTICAL);
        m_note_container.setPadding(CONTAINER_PADDING, CONTAINER_PADDING, CONTAINER_PADDING, CONTAINER_PADDING);
        this.addView(m_note_container);

        m_note_elements = new ArrayList<>();
        m_note_views = new ArrayList<>();
    }

    public void init(String note)
    {
        m_note_elements = NoteParser.parse(note);
        INoteView view;
        for(NoteElement element : m_note_elements)
        {
            view = NoteViewCreator.create(element, getContext());
            m_note_container.addView((View)view);
            m_note_views.add(view);
            //TODO tutaj jeszcze prawdopodobnie będzie trzeba dodac wioki do tablicy, zeby później je usunąć
        }

    }

    public void addTextElement()
    {
        NoteElement element = new TextElement();
        INoteView view = NoteViewCreator.create(element,getContext());
        m_note_container.addView((View)view);
        m_note_elements.add(element);
        m_note_views.add(view);
    }

    public void addImageElement(Uri uri)
    {
        //NoteElement element = new PhotoElement(uri.toString());
        NoteElement element = new PhotoElement(UriUtils.getImagePath(uri, getContext()));
        INoteView view = NoteViewCreator.create(element, getContext());
        m_note_container.addView((View)view);
        m_note_elements.add(element);
        m_note_views.add(view);
    }

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        String elementString;
        for(int i =0; i<m_note_container.getChildCount(); i++)
        {
            elementString = NoteElementDescriptor.getString((INoteView)m_note_container.getChildAt(i));
            stringBuilder.append(elementString);
        }
        return stringBuilder.toString();
    }







}
