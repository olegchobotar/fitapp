package com.android.fitapp.settings;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class PreferenceCategory extends android.preference.PreferenceCategory {

    public PreferenceCategory(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PreferenceCategory(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);

    }
}
