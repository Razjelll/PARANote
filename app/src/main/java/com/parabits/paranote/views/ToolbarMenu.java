package com.parabits.paranote.views;

import android.view.View;

import java.util.ArrayList;
import java.util.List;



public class ToolbarMenu
{
    private List<ToolbarMenuItem> m_items;

    public ToolbarMenu()
    {
        m_items = new ArrayList<>();
    }

    public List<ToolbarMenuItem> getItems()
    {
        return m_items;
    }

    public void addItem(int position, int imageResource, String name, View.OnClickListener listener)
    {
        ToolbarMenuItem item = new ToolbarMenuItem(imageResource, name, listener);
        m_items.add(position, item);
    }

    public void removeItem(int position)
    {
        m_items.remove(position);
    }

    public void removeItem(String name)
    {
        for(int i=0; i < m_items.size(); i++)
        {
            if(m_items.get(i).getText().equals(name))
            {
                m_items.remove(i);
                return;
            }
        }
    }

    public void addItems(List<ToolbarMenuItem> items)
    {
        m_items = items;
    }

    public void clear()
    {
        m_items.clear();
    }

}

class ToolbarMenuItem
{
    private int m_image_resource;
    private String m_text;
    private View.OnClickListener m_listener;

    public ToolbarMenuItem(int resource, String text, View.OnClickListener listener)
    {
        m_image_resource = resource;
        m_text = text;
        m_listener = listener;
    }

    public int getResource() {return m_image_resource;}
    public String getText() {return m_text;}
    public View.OnClickListener getListener() {return m_listener;}
}
