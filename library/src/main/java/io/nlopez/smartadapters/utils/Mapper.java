package io.nlopez.smartadapters.utils;

import java.util.HashMap;
import java.util.Map;

import io.nlopez.smartadapters.views.BindableLayout;

/**
 * Helper class wrapping a @{code Map} for assigning object classes to their adequate view classes.
 */
public class Mapper {

    // TODO this is a mess - switch this implementation to use a LinkedHashMap instead
    private Map<Class, Class<? extends BindableLayout>> mapping;
    private Map<Class, Integer> positions;
    private Map<Integer, Class> positionsInverse;
    private int current;

    public Mapper() {
        mapping = new HashMap<>();
        positions = new HashMap<>();
        positionsInverse = new HashMap<>();
        current = 0;
    }

    /**
     * Associates an object with its representing view
     *
     * @param objectClass model object
     * @param viewClass   view object that should inherit from BindableLayout
     * @return this, so you can chain calls
     */
    public Mapper add(Class objectClass, Class<? extends BindableLayout> viewClass) {
        if (containsObjectClass(objectClass)) {
            throw new AssertionError("Can't repeat object classes in the mapping");
        }
        mapping.put(objectClass, viewClass);
        positions.put(objectClass, current);
        positionsInverse.put(current, objectClass);
        current++;
        return this;
    }

    /**
     * Returns the position in which the given model class was inserted
     *
     * @param objectClass model object
     * @return order in which this class was inserted
     */
    public int position(Class objectClass) {
        if (!positions.containsKey(objectClass)) {
            throw new RuntimeException("Could not find object class " + objectClass.toString());
        }
        return positions.get(objectClass);
    }

    /**
     * Gets the class that matches the position given
     *
     * @param position order position in which the model class was included
     * @return this, so you can chain calls
     */
    public Class fromPosition(int position) {
        if (!positionsInverse.containsKey(position)) {
            throw new RuntimeException("Could not find position " + position);
        }
        return positionsInverse.get(position);
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
        return mapping.containsValue(viewClass);
    }

    /**
     * Returns the view class associated to the given object class
     *
     * @param objectClass model object
     * @return view class associated to the model class given
     */
    public Class<? extends BindableLayout> get(Class objectClass) {
        return mapping.get(objectClass);
    }

    /**
     * Returns the number of mappings done this far
     *
     * @return number of mappings
     */
    public int size() {
        return mapping.size();
    }

    /**
     * Returns the object to view mapping as a Map object
     *
     * @return map representing the call
     */
    public Map<Class, Class<? extends BindableLayout>> asMap() {
        return mapping;
    }
}
