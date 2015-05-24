package io.nlopez.smartadapters.adapters.androidannotations;

import android.content.Context;

import java.lang.reflect.Method;
import java.util.Map;

import io.nlopez.smartadapters.utils.BindableLayoutBuilder;
import io.nlopez.smartadapters.utils.Mapper;
import io.nlopez.smartadapters.utils.Reflections;
import io.nlopez.smartadapters.views.BindableLayout;

public class AAMultiBindableLayoutBuilder implements BindableLayoutBuilder {

    protected Map<Class, Class<? extends BindableLayout>> itemViewMapping;

    public AAMultiBindableLayoutBuilder(final Mapper mapper) {
        this.itemViewMapping = mapper.asMap();
    }

    @Override
    public BindableLayout build(Context context, Class aClass, Object item) {
        try {
            Class modelClass = (item == null) ? aClass : item.getClass();
            Class viewClass = itemViewMapping.get(modelClass);
            Method method = Reflections.method(viewClass, "build", Context.class);
            return (BindableLayout) method.invoke(null, context);
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong creating the views", e);
        }
    }
}
