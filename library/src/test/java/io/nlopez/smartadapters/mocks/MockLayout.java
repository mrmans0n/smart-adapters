package io.nlopez.smartadapters.mocks;

import android.content.Context;

import io.nlopez.smartadapters.views.BindableFrameLayout;

/**
 * Simple view layout
 */
public class MockLayout extends BindableFrameLayout<MockModel> {

    public MockLayout(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void bind(MockModel item) {

    }
}
