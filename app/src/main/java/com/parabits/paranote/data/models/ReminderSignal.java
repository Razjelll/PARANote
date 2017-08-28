package com.parabits.paranote.data.models;

/** Klasa określająca sposób powiadomienia. Dla powiadomienia można wybrać dźwięk, który będzie odtwarzany,
 * a także rodzaj wibracji jaki ma zostać odtwarzany.
 */
public class ReminderSignal {

    private boolean mSound;
    //TODO dorobić dzwonek jaki ma zostać odtworzony
    private boolean mVibration;

    public boolean isSound() {return mSound;}
    public boolean isVibration() {return mVibration;}
}
