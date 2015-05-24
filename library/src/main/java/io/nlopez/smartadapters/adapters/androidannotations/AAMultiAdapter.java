package io.nlopez.smartadapters.adapters.androidannotations;

import java.util.List;

import io.nlopez.smartadapters.adapters.MultiAdapter;
import io.nlopez.smartadapters.utils.BindableLayoutBuilder;
import io.nlopez.smartadapters.utils.Mapper;

public class AAMultiAdapter extends MultiAdapter {

    public AAMultiAdapter(Mapper mapper, List listItems) {
        this(mapper, listItems, new AAMultiBindableLayoutBuilder(mapper));
    }

    public AAMultiAdapter(Mapper mapper, List listItems, BindableLayoutBuilder builder) {
        super(mapper, listItems, builder == null ? new AAMultiBindableLayoutBuilder(mapper) : builder);
    }
}
