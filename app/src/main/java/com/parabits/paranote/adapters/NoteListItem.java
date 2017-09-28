package com.parabits.paranote.adapters;


/**
 * Model przechowujący element listy notatki. Element zawiera informacje o zaznaczeniu oraz tekście
 */
public class NoteListItem {
    /** Określa, czy element jest zaznaczony */
    private boolean m_checked;
    /** Tekst elementu */
    private String m_text;

    public NoteListItem(boolean selected, String text)
    {
        m_checked = selected;
        m_text = text;
    }

    public boolean isChecked() {return m_checked;}
    public String getText() {return m_text;}

    public void setChecked(boolean checked) {
        m_checked = checked;}
    public void setText(String text) {m_text = text;}
}
