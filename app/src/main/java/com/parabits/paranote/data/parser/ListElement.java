package com.parabits.paranote.data.parser;

import android.content.Context;

import com.parabits.paranote.views.INoteElementView;
import com.parabits.paranote.views.NoteListView;

import java.util.ArrayList;
import java.util.List;


public class ListElement extends NoteElement{

    public ListElement()
    {
        super();
        m_type = Type.LIST;
    }

    public ListElement(String content)
    {
        super(content);
        m_type = Type.LIST;
    }

    public ListElement(String content, List<ElementParameter> parameters)
    {
        super(content, parameters);
        m_type = Type.LIST;
    }


    @Override
    public INoteViewCreator getCreator() {
        return new Creator(this);
    }

    private static final String START_ITEM = "(";
    private static final String END_ITEM = ")";
    private static final char TRUE_VALUE = '1';
    private static final char FALSE_VALUE = '0';
    private static final char PARAMETERS_SEPARATOR = ';';

    public class Creator implements INoteViewCreator
    {
        private ListElement m_element;

        public Creator(ListElement element)
        {
            m_element = element;
        }

        @Override
        public INoteElementView createView(Context context) {
            NoteListView listView = new NoteListView(context);
            List<Item> items = parse(m_element.getContent());
            for(Item item : items)
            {
                listView.addItem(item.isSelected(), item.getText());
            }

            return listView;
        }

        private List<Item> parse(String content)
        {
            List<Item> items = new ArrayList<>();

            int currentPosition = 0;
            int contentLength = content.length();
            StringBuilder stringBuilder = new StringBuilder(); //będzie potrzebny do składania wartości
            boolean selected;
            int length;
            char temp;
            Item item;
            while(currentPosition != contentLength)
            {
                stringBuilder.setLength(0); // czyszczenie stringbuildera
                currentPosition++; //przewijamy do pozycji za otwarciem elementu
                temp = content.charAt(currentPosition++);
                selected = temp == TRUE_VALUE;
                currentPosition++; //przewijamy znak separator
                while((temp = content.charAt(currentPosition++)) != PARAMETERS_SEPARATOR)
                {
                    stringBuilder.append(temp); //dołączmy
                }
                length = Integer.parseInt(stringBuilder.toString()); //uzyskujemy długość zawartości
                stringBuilder.setLength(0); // czyścimy stringbuilder
                int endContentPosition = length + currentPosition;
                for(; currentPosition < endContentPosition; currentPosition++) //zczytujemy zawartość
                {
                    stringBuilder.append(content.charAt(currentPosition));
                }
                currentPosition++; //przeskakujemy znak zamknięcia lini

                item = new Item(selected, stringBuilder.toString());
                items.add(item);
            }

            return items;
        }

        private class Item
        {
            private boolean m_selected;
            private String m_text;

            public Item(boolean selected, String text)
            {
                m_selected = selected;
                m_text = text;
            }

            public boolean isSelected() {return m_selected;}
            public String getText(){return m_text;}
        }

    }

    public static class Descriptor
    {
        public static String getDescription(INoteElementView view) {
            //TODO zobaczyć czy nie da rady zrobić tego troszkę inaczej
            NoteListView listView = (NoteListView)view;
            ListElement element = new ListElement();
            NoteListView.NoteListItem item;
            StringBuilder stringBuilder = new StringBuilder();
            for(int i=0; i<listView.getItemsCount(); i++)
            {
                item = listView.getItem(i);
                stringBuilder.append(describeItem(item));
            }
            element.setContent(stringBuilder.toString());
            return element.toString();
        }

        private static String describeItem(NoteListView.NoteListItem item)
        {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(START_ITEM);
            stringBuilder.append(item.isSelected() ? TRUE_VALUE : FALSE_VALUE).append(PARAMETERS_SEPARATOR);
            String content = item.getText();
            int contentLength =content.length();
            stringBuilder.append(contentLength).append(PARAMETERS_SEPARATOR).append(content)
                    .append(END_ITEM);
            return stringBuilder.toString();
        }
    }


}
