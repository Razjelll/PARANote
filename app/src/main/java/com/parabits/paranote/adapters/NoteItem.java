package com.parabits.paranote.adapters;


public class NoteItem {
    private String m_title;
    private String m_creation_date;

    public String getTitle(){return m_title;}
    public String getCreationDate(){return m_creation_date;}

    public void setTitle(String title){m_title = title;}
    public void setCreationDate(String creationDate){m_creation_date = creationDate;}
}
