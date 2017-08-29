package com.parabits.paranote;

import com.parabits.paranote.data.parser.NoteElement;
import com.parabits.paranote.data.parser.NoteParser;

import org.junit.Test;

import java.util.List;

public class NoteParserUnitTest {

    private final String NOTE_TEXT_ONLY = "<text> To jest tekst <text/><image>Obraz<image/>";

    @Test
    public void parseTest()
    {
        List<NoteElement> elements = NoteParser.parse(NOTE_TEXT_ONLY);
    }
}
