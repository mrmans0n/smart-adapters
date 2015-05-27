package io.nlopez.smartadapters.builders;

import android.view.ViewGroup;

import io.nlopez.smartadapters.utils.Mapper;
import io.nlopez.smartadapters.views.BindableLayout;

public interface BindableLayoutBuilder<T, Q extends BindableLayout<T>> {
    Q build(ViewGroup parent, Mapper mapper, Class aClass, T item);
}
