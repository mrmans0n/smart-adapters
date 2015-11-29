package io.nlopez.smartadapters.utils;

import android.support.annotation.VisibleForTesting;
import android.support.v4.util.ArrayMap;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.nlopez.smartadapters.views.BindableLayout;

/**
 * Helper class wrapping a @{code Map} for assigning object classes to their adequate view classes.
 * It is optimized with the assumption that all the adds are going to be made before using the
 * rest of the methods. It can be used afterwards but it will be slower if there are a ton of views.
 * <p/>
 * It is valid to have one object class mapped to various view classes, but it is not possible for
 * now to have one view class to be mapped to various object classes.
 */
public class Mapper {
    // TODO create a Shadow for Robolectric
    private Map<Class, List<Class<? extends BindableLayout>>> mapping;
    private Set<Class<? extends BindableLayout>> cachedViewClasses;
    // TODO change for SparseArray + do a Shadow for Robolectric
    private Map<Integer, Class<? extends BindableLayout>> viewTypes;

    public Mapper() {
        mapping = new ArrayMap<>();
        viewTypes = new ArrayMap<>();
    }

    /**
     * Please do not use this outside of testing.
     *
     * @param mockMapping
     * @param mockViewTypes
     */
    @VisibleForTesting
    public Mapper(Map<Class, List<Class<? extends BindableLayout>>> mockMapping, Map<Integer, Class<? extends BindableLayout>> mockViewTypes) {
        this.mapping = mockMapping;
        this.viewTypes = mockViewTypes;
    }

    /**
     * Associates an object with its representing view
     *
     * @param objectClass model object
     * @param viewClass   view object that should inherit from BindableLayout
     * @return this, so you can chain calls
     */
    public Mapper add(Class objectClass, Class<? extends BindableLayout> viewClass) {
        if (mapping.containsKey(objectClass)) {
            List<Class<? extends BindableLayout>> classes = new ArrayList<>();
            classes.addAll(mapping.get(objectClass));
            classes.add(viewClass);
            mapping.put(objectClass, classes);
        } else {
            List<Class<? extends BindableLayout>> list = new ArrayList<>();
            list.add(viewClass);
            mapping.put(objectClass, list);
        }
        viewTypes.put(viewTypeFromViewClass(viewClass), viewClass);
        clearCachedData();
        return this;
    }

    /**
     * Checks if a model class is present in the mapping
     *
     * @param objectClass model class
     * @return TRUE if it is present, FALSE otherwise
     */
    public boolean containsObjectClass(Class objectClass) {
        return mapping.containsKey(objectClass);
    }

    /**
     * Checks if a view class is present in the mapping
     *
     * @param viewClass view class
     * @return TRUE if it is present, FALSE otherwise
     */
    public boolean containsViewClass(Class<? extends BindableLayout> viewClass) {
        return viewClasses().contains(viewClass);
    }

    /**
     * Returns the view classes associated to the given object class
     *
     * @param objectClass model object
     * @return view classes associated to the model class given
     */
    public List<Class<? extends BindableLayout>> get(Class objectClass) {
        return mapping.get(objectClass);
    }

    /**
     * Returns the number of views mapped done this far
     *
     * @return number of view mappings
     */
    public int viewSize() {
        return viewClasses().size();
    }

    /**
     * Returns the number of objects mapped done this far
     *
     * @return number of object mappings
     */
    public int objectSize() {
        return objectClasses().size();
    }

    /**
     * Returns all the object classes used in the mapper
     *
     * @return
     */
    public Set<Class> objectClasses() {
        return mapping.keySet();
    }

    /**
     * Returns all the view classes used in the mapper. The results are cached, so the first
     * execution will be slower than the rest.
     *
     * @return
     */
    public Set<Class<? extends BindableLayout>> viewClasses() {
        if (cachedViewClasses == null) {
            cachedViewClasses = new LinkedHashSet<>();
            for (Class classKey : mapping.keySet()) {
                List<Class<? extends BindableLayout>> classes = mapping.get(classKey);
                cachedViewClasses.addAll(classes);
            }
        }
        return cachedViewClasses;
    }

    /**
     * Returns a unique identifier for each view class
     *
     * @param viewClass
     * @return
     */
    public static int viewTypeFromViewClass(Class<? extends BindableLayout> viewClass) {
        return viewClass.getCanonicalName().hashCode();
    }

    /**
     * Returns a view class based on the view type. See {@link #viewTypeFromViewClass(Class)}.
     *
     * @param viewType
     * @return
     */
    public Class<? extends BindableLayout> viewClassFromViewType(int viewType) {
        return viewTypes.get(viewType);
    }

    private void clearCachedData() {
        cachedViewClasses = null;
    }

    @VisibleForTesting Map<Class, List<Class<? extends BindableLayout>>> asMap() {
        return mapping;
    }
}
