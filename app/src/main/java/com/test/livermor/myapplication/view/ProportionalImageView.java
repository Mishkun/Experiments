package com.test.livermor.myapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.test.livermor.myapplication.R;

/**
 * @author dumchev on 26.04.2018.
 */

public class ProportionalImageView extends android.support.v7.widget.AppCompatImageView {
    // Height = width * proportion
    private float proportion = 3.0f / 4.0f;

    public ProportionalImageView(Context context) {
        super(context);
    }

    public ProportionalImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme()
                              .obtainStyledAttributes(attrs, R.styleable.ProportionalImageView, 0,
                                                      0);
        try {
            proportion = a.getFloat(R.styleable.ProportionalImageView_proportion, proportion);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = View.MeasureSpec.getSize(widthMeasureSpec);
        int proportionalHeight = (int) (width * proportion);

        setMeasuredDimension(width, proportionalHeight);
    }
}

