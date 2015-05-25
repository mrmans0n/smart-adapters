package io.nlopez.smartadapters.builders;

import android.content.Context;

import io.nlopez.smartadapters.utils.Mapper;
import io.nlopez.smartadapters.views.BindableLayout;

public interface BindableLayoutBuilder<T, Q extends BindableLayout<T>> {
    Q build(Context context, Mapper mapper, Class aClass, T item);
}
