package io.nlopez.smartadapters.builders;

import android.content.Context;

import java.lang.reflect.Constructor;

import io.nlopez.smartadapters.utils.Mapper;
import io.nlopez.smartadapters.utils.Reflections;
import io.nlopez.smartadapters.views.BindableLayout;

/**
 * Created by mrm on 24/5/15.
 */
public class DefaultBindableLayoutBuilder extends BindableLayoutBuilder {

    public DefaultBindableLayoutBuilder(Mapper mapper) {
        super(mapper);
    }

    @Override
    public BindableLayout build(Context context, Class aClass, Object item) {
        try {
            Class viewClass = getMapper().asMap().get(aClass);
            Constructor constructor = Reflections.constructor(viewClass, Context.class);
            return (BindableLayout) constructor.newInstance(context);
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong creating the views", e);
        }
    }
}
