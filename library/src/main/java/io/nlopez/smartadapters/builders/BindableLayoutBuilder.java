package io.nlopez.smartadapters.builders;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import io.nlopez.smartadapters.utils.Mapper;
import io.nlopez.smartadapters.views.BindableLayout;

public interface BindableLayoutBuilder<T, Q extends BindableLayout<T>> {
    Q build(@NonNull ViewGroup parent, int viewType, T item, Mapper mapper);

    int viewType(@NonNull T item, int position, Mapper mapper);
}
