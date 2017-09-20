package com.parabits.paranote.views;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.parabits.paranote.R;
import com.parabits.paranote.data.parser.ImageElement;
import com.parabits.paranote.data.parser.ListElement;
import com.parabits.paranote.data.parser.NoteElement;
import com.parabits.paranote.data.parser.NoteElementDescriptor;
import com.parabits.paranote.data.parser.NoteParser;
import com.parabits.paranote.data.parser.NoteViewCreator;
import com.parabits.paranote.data.parser.TextElement;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class NoteView extends ScrollView {

    private final int CONTAINER_MARGIN = 8;
    private final int CONTAINER_PADDING = 8; //TODO można zrobić w dp

    //TODO zastanowić się, czy trzymać elementy, czy korzystać z nich tylko w razie potrzeby
    private LinearLayout m_note_container;
    private List<NoteElement> m_note_elements;
    private List<INoteElementView> m_note_views;


    private int m_selected_view_position;

    /** Wartośc wykorzystywana do nadawania numerów identyfikacyjnych widokom dołączonym do NoteView
     * Numery identyfikacyjne służą do rozpoznawania, który z dołączonych widoków został kliknięty.
     */
    private int m_elements_id = 0;

    private OnNoteElementChangeListener m_listener;

    public interface OnNoteElementChangeListener
    {
        void onChange(INoteElementView.Type from, INoteElementView.Type to);
    }

    public void setOnNoteViewClickListener(OnNoteElementChangeListener listener)
    {
        m_listener = listener;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event)
    {
        return false;
    }

    //TODO może być konieczne dodanie listy widoków

    public NoteView(Context context) {
        super(context, null, R.style.NoteEditText);
        init(context);
    }

    public NoteView(Context context, AttributeSet attrs)
    {
        super(context, attrs, R.style.NoteEditText);
        init(context);
        //TOOD zadeklarować nowe atrybuty
    }

    public NoteView(Context context, AttributeSet attrs, int resID)
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

        this.setClickable(true);
        this.setFocusable(true);

        m_selected_view_position = -1; // początkowo żadna pozycja nie jest zaznaczona
    }

    public void init(String note)
    {
        m_note_elements = NoteParser.parse(note);
        INoteElementView view;
        for(NoteElement element : m_note_elements)
        {
            /*view = NoteViewCreator.create(element, getContext());
            m_note_container.addView((View)view);
            m_note_views.add(view);*/
            view = element.getCreator().createView(getContext());
            m_note_container.addView((View)view);
            m_note_views.add(view);
            //TODO tutaj jeszcze prawdopodobnie będzie trzeba dodac wioki do tablicy, zeby później je usunąć
        }

    }

    public View getSelectedView()
    {
        //return m_selected_view;
        return (View)m_note_views.get(m_selected_view_position);
    }

    public void selectView(long id)
    {
        /*for(int i=0; i < m_note_container.getChildCount(); i++)
        {
            if(m_note_container.getChildAt(i).getId() == id)
            {
                m_selected_view = m_note_container.getChildAt(i);
                return;
            }
        }
        m_selected_view = null;*/
        for(int i=0; i<m_note_views.size(); i++)
        {
            if(m_note_views.get(i).getId() == id)
            {
                m_selected_view_position = i;
                return;
            }
        }
    }

    public INoteElementView.Type getTypeSelectedView()
    {
        return m_note_views.get(m_selected_view_position).getType();
    }

    public void addTextElement()
    {
        NoteElement element = new TextElement();
        INoteElementView view = NoteViewCreator.create(element,getContext());
        view.setId(m_elements_id);
        m_elements_id++;
        setListener(view);
        ((View)view).setFocusable(false);
        m_note_container.addView((View)view);
        m_note_elements.add(element);
        m_note_views.add(view);

    }

    public void addImageElement(Uri uri)
    {
        NoteElement element = new ImageElement(uri.toString());
        //NoteElement element = new ImageElement(UriUtils.getImagePath(uri, getContext()));
        INoteElementView view = NoteViewCreator.create(element, getContext());
        view.setId(m_elements_id);
        m_elements_id++;
        setListener(view);
        m_note_container.addView((View)view);
        m_note_elements.add(element);
        m_note_views.add(view);
    }

    public void addListElement()
    {
        NoteListView view = new NoteListView(getContext());
        view.setId(m_elements_id);
        m_elements_id++;
        setListener(view);
        view.addItem(); // dodajemy pusty element
        m_note_container.addView(view);
        m_note_views.add(view);

    }

    private void setListener(INoteElementView view)
    {
        View elementView = (View)view;
        // kliknięcie na tekst - zaznaczenie elementu
        if(view.getType() == INoteElementView.Type.TEXT)
        {
            elementView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO można tutaj dodać sprawdzenie, czy widok jest zaznaczony, jeżeli tak nie robimy nic
                    view.setFocusable(true);
                    view.setFocusableInTouchMode(true);
                    view.requestFocus();
                    onNoteClick(view);
                    Toast.makeText(getContext(), "Klik na text", Toast.LENGTH_SHORT).show();
                }
            });
//            elementView.setOnTouchListener(new OnTouchListener() {
//                @Override
//                public boolean onTouch(View view, MotionEvent motionEvent) {
//                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
//                    {
//                        onNoteClick(view);
//                    }
//                    return view.onTouchEvent(motionEvent);
//                }
//            });


        }
        if(view.getType() == INoteElementView.Type.IMAGE)
        {
            ((View)view).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    onNoteClick(view);
                }
            });
        }
        if(view.getType() == INoteElementView.Type.LIST)
        {
            /*((View)view).setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if(b)
                    {
                        Log.d("Listener", "List focus");
                        onNoteClick(view);
                    }
                }
            });*/
            ((NoteListView)elementView).setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    onNoteClick(view);
                    Log.d("NoteList", "List item click");
                }
            });
        }

    }

    private void onNoteClick(View view)
    {

        INoteElementView.Type oldType;
        if(m_selected_view_position >=0)
        {
            oldType = m_note_views.get(m_selected_view_position).getType();
            // usunięcie focusu z edittextu. Potrzebne jest to podczas klikania na obrazek, który nie odbiera focusu pozostałym
            // elementom, dlatego focus z edittextów nie znika i ponowne kliknięcie na edittext nie działa
            //((View)m_note_views.get(m_selected_view_position)).clearFocus();
            //view.setFocusable(false);
            View oldView = (View)m_note_views.get(m_selected_view_position);
            oldView.setFocusable(false);
            oldView.setFocusableInTouchMode(false);

        } else {
            oldType = INoteElementView.Type.NONE;
        }
        int id = view.getId();
        selectView(id); //TODO zmienić na getViewPosition(int id)

        //TODO tutaj zrobić działania zaznaczające i tak dalej
        if(m_listener != null)
        {
            INoteElementView.Type newType = m_note_views.get(m_selected_view_position).getType();
            m_listener.onChange(oldType, newType);
        }
    }

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        String elementString;
        for(int i =0; i<m_note_container.getChildCount(); i++)
        {
            INoteElementView view = (INoteElementView)m_note_container.getChildAt(i);
            switch (view.getType())
            {
                case TEXT:
                    elementString = TextElement.Descriptor.getDescription(view);
                    break;
                case IMAGE:
                    elementString = ImageElement.Descriptor.getDescription(view);
                    break;
                case LIST:
                    elementString = ListElement.Descriptor.getDescription(view);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown element type ");
            }
            //elementString = NoteElementDescriptor.getString((INoteElementView)m_note_container.getChildAt(i));
            stringBuilder.append(elementString);
        }
        return stringBuilder.toString();
    }







}
