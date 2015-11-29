package io.nlopez.smartadapters.builders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.util.List;

import io.nlopez.smartadapters.utils.Mapper;
import io.nlopez.smartadapters.utils.Reflections;
import io.nlopez.smartadapters.views.BindableLayout;

/**
 * Basic layout builder for most of the cases. It handles the reflection caching so the impact
 * done by it is very little.
 */
public class DefaultBindableLayoutBuilder implements BindableLayoutBuilder {

    @Override
    public View build(@NonNull ViewGroup parent, int viewType, Object item, @NonNull Mapper mapper) {

        Class<? extends BindableLayout> viewClass = mapper.viewClassFromViewType(viewType);
        if (viewClass == null) {
            throw new IllegalArgumentException("viewType not present in the mapper");
        }
        try {
            Constructor constructor = Reflections.constructor(viewClass, Context.class);
            return (ViewGroup) constructor.newInstance(parent.getContext());
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong creating the views. Please review your BindableLayout implementation.", e);
        }
    }

    @Override
    public Class<? extends BindableLayout> viewType(@NonNull Object item, int position, @NonNull Mapper mapper) {
        List<Class<? extends BindableLayout>> classes = mapper.get(item.getClass());
        if (classes == null) {
            throw new IllegalArgumentException("Object class " + item.getClass() + "not found in mapper");
        }
        if (classes.size() == 1) {
            return classes.get(0);
        } else if (classes.size() > 1) {
            throw new RuntimeException("There are more than 1 view classes associated to the same object class. Please write a custom BindableLayoutBuilder for this case, and ensure allowsMultimapping returns true.");
        } else {
            throw new RuntimeException("There are no view classes associated to the object class.");
        }
    }

    @Override public boolean allowsMultimapping() {
        return false;
    }
}
