package io.nlopez.smartadapters.builders;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import io.nlopez.smartadapters.utils.Mapper;
import io.nlopez.smartadapters.views.BindableFrameLayout;
import io.nlopez.smartadapters.views.IBindableLayout;

public interface BindableLayoutBuilder<T> {
    <Q extends ViewGroup & IBindableLayout> Q build(@NonNull ViewGroup parent, int viewType, T item, @NonNull Mapper mapper);

    Class<? extends BindableFrameLayout> viewType(@NonNull T item, int position, @NonNull Mapper mapper);
}
