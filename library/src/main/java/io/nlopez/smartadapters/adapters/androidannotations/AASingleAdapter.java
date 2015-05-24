package io.nlopez.smartadapters.adapters.androidannotations;

import java.util.List;

import io.nlopez.smartadapters.adapters.SingleAdapter;
import io.nlopez.smartadapters.utils.BindableLayoutBuilder;
import io.nlopez.smartadapters.views.BindableLayout;

public class AASingleAdapter<T, Q extends BindableLayout<T>> extends SingleAdapter {
    public AASingleAdapter(Class viewClass, List listItems) {
        super(viewClass, listItems, new AASingleBindableLayoutBuilder(viewClass));
    }

    public AASingleAdapter(Class viewClass, List listItems, BindableLayoutBuilder builder) {
        super(viewClass, listItems, builder == null ? new AASingleBindableLayoutBuilder(viewClass) : builder);
    }
}
