package io.nlopez.smartadapters.mocks;

import android.content.Context;

import io.nlopez.smartadapters.views.BindableLayout;

/**
 * Mimics a AA generated @EViewGroup
 */
public class MockLayout2 extends BindableLayout<MockModel> {

    public boolean fromBuild = false;

    public MockLayout2(Context context) {
        super(context);
    }

    public static MockLayout2 build(Context context) {
        MockLayout2 layout = new MockLayout2(context);
        layout.fromBuild = true;
        return layout;
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void bind(MockModel item) {

    }
}
