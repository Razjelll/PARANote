package com.parabits.paranote.data.parser;

/**
 * Created by Razjelll on 26.08.2017.
 */

abstract public class ElementParameter {

    private String m_name;
    private String m_value;

    public ElementParameter(String name, String value)
    {
        m_name = name;
        m_value = value;
    }

    public String getName() {return m_name;}
    public String getValue() {return m_value;}

    public void setName(String name) {m_name = name;}
    public void setValue(String value) {m_value = value;}
}
