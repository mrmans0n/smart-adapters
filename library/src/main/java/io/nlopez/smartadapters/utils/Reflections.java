package io.nlopez.smartadapters.utils;

import android.text.TextUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple class to help caching reflection stuff from the generic layout builders
 */
public class Reflections {

    private static Map<String, Method> methods = new HashMap<>();
    private static Map<Class, Constructor> constructors = new HashMap<>();

    public static Method method(Class clazz, String name, Class<?>... params) throws NoSuchMethodException {
        List<String> paramClassNames = new ArrayList<>();
        for (Class<?> c : params) {
            paramClassNames.add(c.getCanonicalName());
        }

        final String fullName = clazz.getCanonicalName() + "." + name + "(" + TextUtils.join("+", paramClassNames) + ")";
        if (!methods.containsKey(fullName)) {
            Method method = clazz.getMethod(name, params);
            methods.put(fullName, method);
        }
        return methods.get(fullName);
    }

    public static Constructor constructor(Class clazz, Class<?>... params) throws NoSuchMethodException {
        if (!constructors.containsKey(clazz)) {
            Constructor constructor = clazz.getConstructor(params);
            constructors.put(clazz, constructor);
        }

        return constructors.get(clazz);
    }
}
