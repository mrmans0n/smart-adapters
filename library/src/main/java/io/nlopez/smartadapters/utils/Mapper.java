package io.nlopez.smartadapters.utils;

import java.util.HashMap;
import java.util.Map;

import io.nlopez.smartadapters.views.BindableLayout;

/**
 * Created by mrm on 19/05/14.
 */
public class Mapper {

    private Map<Class, Class<? extends BindableLayout>> mapping;

    public Mapper() {
        mapping = new HashMap<>();
    }

    /**
     * Associates an object with its representing view
     *
     * @param objectClass
     * @param viewClass
     * @return this, so you can chain calls
     */
    public Mapper add(Class objectClass, Class<? extends BindableLayout> viewClass) {
        mapping.put(objectClass, viewClass);
        return this;
    }

    /**
     * Returns the object to view mapping as a Map object
     *
     * @return
     */
    public Map<Class, Class<? extends BindableLayout>> asMap() {
        return mapping;
    }
}
