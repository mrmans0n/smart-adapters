package io.nlopez.smartadapters.adapters.androidannotations;

import java.util.List;

import io.nlopez.smartadapters.adapters.RecyclerMultiAdapter;
import io.nlopez.smartadapters.utils.BindableLayoutBuilder;
import io.nlopez.smartadapters.utils.Mapper;

public class AARecyclerMultiAdapter extends RecyclerMultiAdapter {

    public AARecyclerMultiAdapter(Mapper mapper, List listItems) {
        super(mapper, listItems, new AAMultiBindableLayoutBuilder(mapper));
    }

    public AARecyclerMultiAdapter(Mapper mapper, List listItems, BindableLayoutBuilder builder) {
        super(mapper, listItems, builder == null ? new AAMultiBindableLayoutBuilder(mapper) : builder);
    }
}
