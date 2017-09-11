package com.parabits.paranote.activities;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RadioButton;

import com.parabits.paranote.R;
import com.parabits.paranote.data.models.Repetition;
import com.parabits.paranote.data.models.RepetitionPattern;
import com.parabits.paranote.views.SelectableButton;

import java.util.ArrayList;
import java.util.List;

public class RepetitionDialog extends DialogFragment {

    private RadioButton m_weekly_radio_button;
    private RadioButton m_monthly_radio_button;
    private GridView m_grid_view;
    private SelectableButton m_last_day_button;

    private RepetitionAdapter m_adapter;
    private RepetitionPattern m_repetition_pattern;
    private Callback m_callback;

    public interface Callback{
        void onDialogOk(List<Integer> selectedIndexList, boolean monthly);
    }

    public void setCallback(Callback callback)
    {
        m_callback = callback;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        init();
    }

    private void init()
    {
        if(m_repetition_pattern == null)
        {
            m_repetition_pattern = new RepetitionPattern();
        }
    }

    public void setRepetition(List<Integer> selected, boolean monthly)
    {
        m_repetition_pattern = new RepetitionPattern(selected, monthly);
    }

    private View getView(LayoutInflater inflater)
    {
        View view = inflater.inflate(R.layout.dialog_repetition, null);
        //TODO ustawienie kontrolek w przypadku kiedy wcześniej powtórzenie było już ustawione
        setupControls(view);
        return view;
    }

    private void setupControls(View view)
    {
        m_weekly_radio_button = view.findViewById(R.id.weekly_radio_button);
        m_monthly_radio_button = view.findViewById(R.id.monthly_radio_button);
        m_last_day_button = view.findViewById(R.id.last_day_button);
        m_grid_view = view.findViewById(R.id.days_grid_view);


        m_weekly_radio_button.setChecked(!m_repetition_pattern.isMonthly());
        m_monthly_radio_button.setChecked(m_repetition_pattern.isMonthly());

        m_weekly_radio_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_repetition_pattern.setDays(m_adapter.getSelected());
                m_repetition_pattern.setMonthly(false);
                setupWeeklyGridView();
                m_monthly_radio_button.setChecked(false);
            }
        });
        m_monthly_radio_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_repetition_pattern.setDays(m_adapter.getSelected());
                m_repetition_pattern.setMonthly(true);
                setupMonthlyGridView();
                m_weekly_radio_button.setChecked(false);
            }
        });
        setupGridView();
    }

    private void setupGridView()
    {
        if(m_repetition_pattern.isMonthly())
        {
            setupMonthlyGridView();
        } else {
            setupWeeklyGridView();
        }
    }

    private void setupWeeklyGridView()
    {
        if(m_adapter != null)
        {
            m_adapter.clear();
        }
        String[] daysOfTheWeeks = getResources().getStringArray(R.array.days_of_the_week_abbr);
        m_adapter = new RepetitionAdapter(getActivity(), R.layout.dialog_repetition, daysOfTheWeeks);
        m_adapter.setSelectedIndexes(m_repetition_pattern.getDays());
        m_grid_view.setAdapter(m_adapter);
        m_last_day_button.setVisibility(View.GONE);
    }

    private void setupMonthlyGridView()
    {
        if(m_adapter != null)
        {
            m_adapter.clear();
        }

        final String[] daysOfTheMonth = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13",
        "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
        "30", "31"};
        m_adapter = new RepetitionAdapter(getActivity(), R.layout.dialog_repetition, daysOfTheMonth);
        m_adapter.setSelectedIndexes(m_repetition_pattern.getDays());

        m_grid_view.setAdapter(m_adapter);
        m_last_day_button.setVisibility(View.VISIBLE);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle(getString(R.string.repetition_2));
        dialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                List<Integer> selectedIndexList = m_adapter.getSelected();
                if(m_callback != null)
                {
                    m_callback.onDialogOk(selectedIndexList, m_repetition_pattern.isMonthly());
                }
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });
        View view = getView(LayoutInflater.from(getActivity()));
        dialogBuilder.setView(view);
        return dialogBuilder.create();
    }

    private class RepetitionAdapter extends ArrayAdapter
    {
        private class Item
        {
            private String m_text;
            private boolean m_selected;

            public Item(String text)
            {
                m_text = text;
                m_selected = false;
            }

            public String getText() {return m_text;}
            public boolean isSelected() {return m_selected;}

            public void setText(String text){m_text = text;}
            public void setSelected(boolean selected) {m_selected = selected;}
        }
        private List<Item> m_items;
        private Context m_context;

        public RepetitionAdapter(@NonNull Context context, @LayoutRes int resource, String[] data) {
            super(context, resource);
            m_items = new ArrayList<>();
            for(int i=0; i<data.length; i++)
            {
                m_items.add(new Item(data[i]));
            }
            m_context = context;
        }

        @Override
        public int getCount()
        {
            return m_items.size();
        }

        @Override
        public String getItem(int position)
        {
            return m_items.get(position).getText();
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        public boolean isSelected(int position)
        {
            return m_items.get(position).isSelected();
        }

        public void setSelected(int position, boolean selected)
        {
            m_items.get(position).setSelected(selected);
        }

        public void setSelectedIndexes(List<Integer> selectedIndexes)
        {
            for(Integer i : selectedIndexes)
            {
                m_items.get(i).setSelected(true);
            }
        }

        public List<Integer> getSelected()
        {
            List<Integer> selectedIndexList = new ArrayList<>();
            for(int i=0; i< m_items.size(); i++)
            {
                if(m_items.get(i).isSelected())
                {
                    selectedIndexList.add(i);
                }
            }
            return selectedIndexList;
        }


        @NonNull
        @Override
        public View getView(int position, View view, ViewGroup viewGroup)
        {
            View rowView = view;
            ViewHolder viewHolder;
            if(rowView == null)
            {
                LayoutInflater inflater = LayoutInflater.from(m_context);
                rowView = inflater.inflate(R.layout.item_repetition_dialog, null);
                viewHolder = new ViewHolder(rowView, position);
                rowView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder)rowView.getTag();
            }
            viewHolder.setButtonText(m_items.get(position).getText());
            viewHolder.setSelected(m_items.get(position).isSelected());

            return rowView;
        }

        private class ViewHolder
        {
            private Button m_button;

            ViewHolder(View view, final int position)
            {
                m_button = view.findViewById(R.id.button);
                m_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean selected = m_items.get(position).isSelected();
                        m_items.get(position).setSelected(!selected);
                        notifyDataSetChanged();
                    }
                });
            }

            void setButtonText(String text)
            {
                m_button.setText(text);
            }

            public String getButtonText()
            {
                return m_button.getText().toString();
            }

            public boolean isSelected()
            {
                return m_button.isSelected();
            }

            void setSelected(boolean selected)
            {
                m_button.setSelected(selected);
            }

        }
    }







}
