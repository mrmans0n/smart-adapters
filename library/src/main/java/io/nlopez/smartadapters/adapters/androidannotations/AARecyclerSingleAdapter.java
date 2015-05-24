package io.nlopez.smartadapters.adapters.androidannotations;

import java.util.List;

import io.nlopez.smartadapters.adapters.RecyclerSingleAdapter;
import io.nlopez.smartadapters.utils.BindableLayoutBuilder;
import io.nlopez.smartadapters.views.BindableLayout;


public class AARecyclerSingleAdapter<T, Q extends BindableLayout<T>> extends RecyclerSingleAdapter {
    public AARecyclerSingleAdapter(Class viewClass, List listItems) {
        super(viewClass, listItems, new AASingleBindableLayoutBuilder(viewClass));
    }

    public AARecyclerSingleAdapter(Class viewClass, List listItems, BindableLayoutBuilder builder) {
        super(viewClass, listItems, builder == null ? new AASingleBindableLayoutBuilder(viewClass) : builder);
    }
}
