package io.nlopez.smartadapters.builders;

import android.content.Context;

import java.lang.reflect.Constructor;

import io.nlopez.smartadapters.utils.Mapper;
import io.nlopez.smartadapters.utils.Reflections;
import io.nlopez.smartadapters.views.BindableLayout;

/**
 * Basic layout builder for most of the cases. It handles the reflection caching so the impact
 * done by it is very little.
 */
public class DefaultBindableLayoutBuilder implements BindableLayoutBuilder {

    @Override
    public BindableLayout build(Context context, Mapper mapper, Class aClass, Object item) {
        try {
            Class viewClass = mapper.get(aClass);
            Constructor constructor = Reflections.constructor(viewClass, Context.class);
            return (BindableLayout) constructor.newInstance(context);
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong creating the views", e);
        }
    }
}
