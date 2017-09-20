package com.parabits.paranote.data.parser;


import java.util.ArrayList;
import java.util.List;

public class NoteParser {

    public static List<NoteElement> parse(String note)
    {
        List<NoteElement> elements = new ArrayList<>();
        int index = 0;
        int noteLength = note.length();
        int openMarkerIndex = 0;
        int startContentIndex = 0;
        int endContentIndex = 0;
        int closeMarkerIndex = 0;
        int endMarkerNameIndex = 0;
        int spaceIndex = 0;
        String token;
        String parameters;
        String content;
        while(index < noteLength)
        {
            openMarkerIndex = note.indexOf(NoteElement.MARKER_START, index);
            startContentIndex = note.indexOf(NoteElement.CONTENT_START, openMarkerIndex);
            endContentIndex = note.indexOf(NoteElement.CONTENT_END, startContentIndex);
            closeMarkerIndex = note.indexOf(NoteElement.MARKER_END, endContentIndex);

            spaceIndex = note.indexOf(" ", openMarkerIndex);
            if(spaceIndex > 0)
            {
                endMarkerNameIndex = Math.min(startContentIndex, spaceIndex);
            } else
            {
                endMarkerNameIndex = startContentIndex;
            }
            token = note.substring(openMarkerIndex+1, endMarkerNameIndex);

            content = note.substring(startContentIndex + 1, endContentIndex);
            NoteElement element = null;
            switch(token)
            {
                case NoteElement.TEXT_MARKER:
                    parameters = note.substring(openMarkerIndex + NoteElement.TEXT_MARKER.length() + 1, startContentIndex);
                    element = parseTextElement(parameters, content);
                    break;
                case NoteElement.PHOTO_MARKER:
                    parameters = note.substring(openMarkerIndex + NoteElement.PHOTO_MARKER.length() + 1, startContentIndex);
                    element = parseImageElement(parameters, content);
                    break;
                case NoteElement.LIST_MARKER:
                    parameters = note.substring(openMarkerIndex + NoteElement.LIST_MARKER.length() + 1, startContentIndex);
                    element = parseListElement(parameters, content);
                    break;
                    //TODO w przeciwnym przypadku można wyżuciś jakiś błąd
            }
            if(element != null)
            {
                elements.add(element);
            }
            index = closeMarkerIndex + NoteElement.MARKER_END.length();
        }
        //TODO
        return elements;
    }

    private static NoteElement parseTextElement(String parameters, String content)
    {
        //TODO dorobić parsowanie parametrów
        TextElement element = new TextElement();
        element.setContent(content);
        return element;
    }

    private static NoteElement parseImageElement(String parameters, String content)
    {
        //TODO dorobić parsowanie parametrów
        ImageElement element = new ImageElement();
        element.setContent(content);
        return element;
    }

    private static ListElement parseListElement(String parameters, String content)
    {
        //TODO dorobić parsowanie parametrów
        ListElement element = new ListElement();
        element.setContent(content);
        return element;
    }
}
