package com.parabits.paranote.data.parser;


import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.parabits.paranote.views.INoteElementView;

import java.util.ArrayList;
import java.util.List;

abstract public class NoteElement{

    public static final  String MARKER_START = "<";
    public static final String MARKER_END = "/>";
    public static final String CONTENT_START = ">";
    public static final String CONTENT_END = "<";
    public static final String TEXT_MARKER = "text";
    public static final String PHOTO_MARKER = "photo";
    public static final String LIST_MARKER = "list";
    public static final String RECORD_MARKER = "record";
    public static final String HANDWRITTING_MARKER = "hand";
    public static final String CODE_MARKER = "code";

    public enum Type
    {
        TEXT(TEXT_MARKER),
        PHOTO(PHOTO_MARKER),
        LIST(LIST_MARKER),
        RECORD(RECORD_MARKER),
        HANDWRITTING(HANDWRITTING_MARKER),
        CODE(CODE_MARKER);

        private String m_name;
        private Type(String name) {m_name = name;}

        public String getName() {return m_name;}
    }
    //TODO zastanowić się jeszcze nad możliwymi rodzajami notatki

    /** Typ elementu. Typ elementu wpływa na sposób przetwarzania danego elementu */
    protected Type m_type;
    /** Zawartość elementu. Zawartość będzie zależała od typu elementu. Dla elementu tekstowego
     * zawartość będzie treścią notatki, dla obrazków i nagrań będzie to ścieżka do pliku */
    protected String m_content;
    /** Lista parametrów dla elementu. Parametry określają właściwości elementu i wpływają na sposób
     * przetwarzania elementu. Parametrem może być na przykład informacja o pogrubieniu tekstu lub
     * rozmiarach obrazka */
    protected List<ElementParameter> m_parameters;
    /** Okresla czy element został edytowany. Informacja ta przydatna jest podczas zapisu elementu. */
    protected boolean m_changed;

    public NoteElement()
    {
        m_parameters = new ArrayList<>();
    }

    public NoteElement(String content)
    {
        m_content = content;
        m_parameters = new ArrayList<>();
    }

    public NoteElement(String content, List<ElementParameter> parameters)
    {
        m_content = content;
        m_parameters =parameters;
    }
    public Type getType() {return m_type;}
    public String getContent() {return m_content;}
    public List<ElementParameter> getParameters() {return m_parameters;}
    public String getParameterValue(String parameterName)
    {
        for(int i=0; i<m_parameters.size(); i++)
        {
            if(m_parameters.get(i).getName().equals(parameterName))
            {
                return m_parameters.get(i).getValue();
            }
        }
        return null; //nie znaleziono parametru o podanej nazwie
    }
    public boolean isChanged() {return m_changed;}

    public void setType(Type type){m_type = type;}
    public void setContent(String content) {m_content = content;}
    public void addParameter(ElementParameter parameter){m_parameters.add(parameter);}
    public void removeParameter(ElementParameter parameter) {m_parameters.remove(parameter);}
    public void changeParameter(ElementParameter parameter) {
        boolean changed = false;
        for(int i = 0; i< m_parameters.size() && !changed; i++)
        {
            if(m_parameters.get(i).getName().equals(parameter.getName()))
            {
                m_parameters.get(i).setValue(parameter.getValue());
                changed = true;
            }
        }
    }
    public void setChanged(boolean changed) {m_changed = changed;}

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append(MARKER_START);
        builder.append(m_type.getName());
        for (ElementParameter parameter: m_parameters) {
            builder.append(" ");
            builder.append(parameter.getName());
            builder.append("=");
            builder.append(parameter.getValue());
        }

        builder.append(CONTENT_START);
        builder.append(m_content);
        builder.append(CONTENT_END);
        builder.append(m_type.getName());
        builder.append(MARKER_END);

        return builder.toString();
    }

    public abstract INoteViewCreator getCreator();

    public interface INoteViewCreator
    {
        INoteElementView createView(Context context);
        //TODO dodać wszystko co jest potrzebne
    }

    public interface INoteElementDescriptor<View extends INoteElementView>
    {
        String getDescription(View view);
    }

}
