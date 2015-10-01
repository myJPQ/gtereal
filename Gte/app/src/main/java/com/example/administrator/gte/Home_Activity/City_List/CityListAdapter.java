package com.example.administrator.gte.Home_Activity.City_List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.administrator.gte.R;

import java.util.List;

/**
 * Created by Administrator on 2015/9/5.
 */
public class CityListAdapter extends ArrayAdapter<City> implements SectionIndexer {
    private int resourceId;
    private String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWLMN";

    @Override
    public Object[] getSections() {
        String[] sections = new String[mSections.length()];
        for (int i = 0; i < mSections.length(); i++) {
            sections[i] = String.valueOf(mSections.charAt(i));
        }
        return sections;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = sectionIndex; i >= 0; i--) {
            for (int j = 0; j < getCount(); j++) {
                //查询数字
                if (i == 0) {
                    for (int k = 0; k <= 9; k++) {
                        if (StringMatcher.match(String.valueOf(String.valueOf(getItem(j).getName().
                                charAt(0))), String.valueOf(k))) ;
                        {
                            return j;
                        }
                    }
                } else {

                    if (StringMatcher.match(String.valueOf(String.valueOf(getItem(j).getName().
                            charAt(0))), String.valueOf(mSections.charAt(i)))) {

                        return j;

                    }
                    //查询字母
                }
            }
        }
        return 0;
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    public CityListAdapter(Context context, int textViewResourceId, List<City> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        City city = getItem(position);
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        } else {
            view = convertView;

        }
        TextView cityname = (TextView) view.findViewById(R.id.city_name);
        cityname.setText(city.getName());
        return view;
    }


} 