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
