package io.nlopez.smartadapters.builders;

import android.content.Context;

import java.lang.reflect.Method;

import io.nlopez.smartadapters.utils.Mapper;
import io.nlopez.smartadapters.utils.Reflections;
import io.nlopez.smartadapters.views.BindableLayout;

/**
 * Specific layout builder for Android Annotations @EViewGroup annotated classes.
 * <p/>
 * Those classes are build by calling a static build(Context) method so that's what we need to do
 * in order to create properly those elements.
 */
public class AABindableLayoutBuilder implements BindableLayoutBuilder {

    @Override
    public BindableLayout build(Context context, Mapper mapper, Class aClass, Object item) {
        try {
            Class modelClass = (item == null) ? aClass : item.getClass();
            Class viewClass = mapper.get(modelClass);
            Method method = Reflections.method(viewClass, "build", Context.class);
            return (BindableLayout) method.invoke(null, context);
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong creating the views", e);
        }
    }
}