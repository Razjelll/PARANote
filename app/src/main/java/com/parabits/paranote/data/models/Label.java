package com.parabits.paranote.data.models;

public class Label {

    private long m_id;
    private String m_name;
    private boolean m_checked;

    public Label(){

    }

    public Label(String name)
    {
        m_name = name;
        m_checked = false;
    }

    public long getID() {return m_id;}
    public String getName() {return m_name;}
    public boolean isChecked(){return m_checked;}

    public void setID(long id) {
        m_id = id;}
    public void setName(String name) {
        m_name = name;}
    public void setChecked(boolean checked) {m_checked = checked;}
}
