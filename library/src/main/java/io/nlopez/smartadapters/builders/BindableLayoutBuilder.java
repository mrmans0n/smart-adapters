package io.nlopez.smartadapters.builders;

import android.content.Context;

import io.nlopez.smartadapters.utils.Mapper;
import io.nlopez.smartadapters.views.BindableLayout;

public abstract class BindableLayoutBuilder<T, Q extends BindableLayout<T>> {
    private Mapper mapper;

    public BindableLayoutBuilder(Mapper mapper) {
        this.mapper = mapper;
    }

    public Mapper getMapper() {
        return mapper;
    }

    public abstract Q build(Context context, Class aClass, T item);

}
