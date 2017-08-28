package com.parabits.paranote.data.models;

import java.util.List;

public class Note {

    private long mID;
    private String mTitle;
    private String mContent;
    /// przypomnienei notatki. Jeżeli nie jest null w okreslonym termine zostanie uruchomione przypomnienie
    private Reminder mReminder;
    private List<Label> mLabels;
    private Date mCreationDate;
    private Date mUpdateDate;

    public long getID(){return mID;}
    public String getTitle() {return mTitle;}
    public String getContent() {return mContent;}
    public Reminder getReminder() {return mReminder;}
    public List<Label> getLabels() {return mLabels;}
    public Date getCreationDate() {return mCreationDate;}
    public Date getUpdateDate() {return mUpdateDate;}

    public void setID(long id) {mID = id;}
    public void setTitle(String title) {mTitle = title;}
    public void setContent(String content) {mContent = content;}
    public void setReminder(Reminder reminder) {mReminder = reminder;}
    public void addLabel(Label label) {mLabels.add(label);}
    public void removeLabel(long labelId) {
        for (Label label : mLabels) {
            if(label.getID() == labelId)
            {
                mLabels.remove(label);
                return; // po usunięciu elementu przerywanie działanie pętli
            }
        }
    }
    public void removeLabel(String name)
    {
        for(Label label : mLabels)
        {
            if(label.getName().equals(name))
            {
                mLabels.remove(label);
                return; //po usunięciu elementu przerywamy działanie pętli
            }
        }
    }
    public void setCreationDate(Date date) {mCreationDate = date;}
    public void setUpdateDate(Date date) {mUpdateDate = date;}


}
