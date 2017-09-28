package com.parabits.paranote.data.models;

public class NoteElement {

    public static final int TEXT = 0;
    public static final int IMAGE = 1;
    public static final int LIST = 2;

    private long m_id;
    private int m_position;
    private int m_type;
    private String m_content;
    //TODO można oddać jeszcze jakąś listę parametrów

    public long getID(){return m_id;}
    public int getPosition() {return m_position;}
    public int getType() {return m_type;}
    public String getContent() {return m_content;}

    public void setID(long id) {m_id = id;}
    public void setPosition(int position) {m_position = position;}
    public void setType(int type) {m_type = type;}
    public void setContent(String content) {m_content = content;}

}
