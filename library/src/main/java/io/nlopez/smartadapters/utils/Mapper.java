package io.nlopez.smartadapters.utils;

import android.util.SparseArray;

import java.util.HashMap;
import java.util.Map;

import io.nlopez.smartadapters.views.BindableLayout;

/**
 * Created by mrm on 19/05/14.
 */
public class Mapper {

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
     * @param objectClass
     * @param viewClass
     * @return this, so you can chain calls
     */
    public Mapper add(Class objectClass, Class<? extends BindableLayout> viewClass) {
        mapping.put(objectClass, viewClass);
        positions.put(objectClass, current);
        positionsInverse.put(current, objectClass);
        current++;
        return this;
    }

    public int position(Class objectClass) {
        if (!positions.containsKey(objectClass)) {
            throw new RuntimeException("Could not find object class " + objectClass.toString());
        }
        return positions.get(objectClass);
    }

    public Class fromPosition(int position) {
        if (!positionsInverse.containsKey(position)) {
            throw new RuntimeException("Could not find position "+position);
        }
        return positionsInverse.get(position);
    }

    public boolean containsObjectClass(Class objectClass) {
        return mapping.containsKey(objectClass);
    }

    public boolean containsViewClass(Class<? extends BindableLayout> viewClass) {
        return mapping.containsValue(viewClass);
    }

    public int size() {
        return mapping.size();
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
