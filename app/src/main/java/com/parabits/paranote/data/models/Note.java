package com.parabits.paranote.data.models;

import android.os.Parcel;
import android.os.Parcelable;


import java.util.List;

public class Note implements Parcelable{

    private long mID;
    private String mTitle;
    private String m_content;
    /// przypomnienei notatki. Jeżeli nie jest null w okreslonym termine zostanie uruchomione przypomnienie
    private Reminder mReminder;
    private List<Label> mLabels;
    private Date mCreationDate;
    private Date mUpdateDate;

    private List<NoteElement> m_note_elements;

    public Note()
    {

    }

    protected Note(Parcel in) {
        mID = in.readLong();
        mTitle = in.readString();
        m_content = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public long getID(){return mID;}
    public String getTitle() {return mTitle;}
    public String getContent() {
        return m_content;
    }
    public Reminder getReminder() {return mReminder;}
    public List<Label> getLabels() {return mLabels;}
    public Date getCreationDate() {return mCreationDate;}
    public Date getUpdateDate() {return mUpdateDate;}

    public void setID(long id) {mID = id;}
    public void setTitle(String title) {mTitle = title;}
    public void setContent(String content) {m_content = content;}
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(mID);
        parcel.writeString(mTitle);
        parcel.writeString(m_content);
    }
}
