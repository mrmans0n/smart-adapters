package io.nlopez.smartadapters.adapters.androidannotations;

import android.content.Context;

import java.lang.reflect.Method;

import io.nlopez.smartadapters.utils.BindableLayoutBuilder;
import io.nlopez.smartadapters.utils.Reflections;
import io.nlopez.smartadapters.views.BindableLayout;

public class AASingleBindableLayoutBuilder<T, Q extends BindableLayout<T>> implements BindableLayoutBuilder<T, Q> {

    protected Class viewClass;

    public AASingleBindableLayoutBuilder(Class viewClass) {
        this.viewClass = viewClass;
    }

    @Override
    public Q build(Context context, Class aClass, T item) {
        try {
            Method method = Reflections.method(viewClass, "build", Context.class);
            return (Q) method.invoke(null, context);
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong creating the views", e);
        }
    }
}
