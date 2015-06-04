package io.nlopez.smartadapters.mocks;

import android.content.Context;
import android.util.AttributeSet;

import io.nlopez.smartadapters.views.BindableLayout;

/**
 * Mimics a AA generated @EViewGroup
 */
public class MockLayout2 extends BindableLayout<MockModel> {

    public MockLayout2(Context context) {
        super(context);
    }

    public MockLayout2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MockLayout2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }



    @Override
    public void bind(MockModel item) {

    }
}
