package com.parabits.paranote.data.models;

public class Label {

    private long mID;
    private String mName;
    private boolean m_checked;


    public long getID() {return mID;}
    public String getName() {return mName;}
    public boolean isChecked(){return m_checked;}

    public void setID(long id) {mID = id;}
    public void setName(String name) {mName = name;}
    public void setChecked(boolean checked) {m_checked = checked;}
}
