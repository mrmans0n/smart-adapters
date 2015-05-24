package io.nlopez.smartadapters.utils;

import android.content.Context;

import io.nlopez.smartadapters.views.BindableLayout;

public interface BindableLayoutBuilder<T, Q extends BindableLayout<T>> {
    Q build(Context context, Class aClass, T item);
}
