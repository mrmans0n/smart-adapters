package io.nlopez.smartadapters.builders;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import io.nlopez.smartadapters.utils.Mapper;
import io.nlopez.smartadapters.views.BindableLayout;

public interface BindableLayoutBuilder<T> {
    <Q extends View & BindableLayout> Q build(@NonNull ViewGroup parent, int viewType, T item, @NonNull Mapper mapper);

    Class<? extends BindableLayout> viewType(@NonNull T item, int position, @NonNull Mapper mapper);

    boolean allowsMultimapping();

    boolean hasStableIds();

    long viewItemId(@NonNull T item, int position, @NonNull Mapper mapper);
}
