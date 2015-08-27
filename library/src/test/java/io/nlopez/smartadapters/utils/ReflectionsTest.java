package io.nlopez.smartadapters.utils;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.nlopez.smartadapters.CustomTestRunner;
import io.nlopez.smartadapters.mocks.SomeModel;

import static junit.framework.Assert.assertEquals;

/**
 * Tests for Reflections helper class
 */

@RunWith(CustomTestRunner.class)
public class ReflectionsTest {

    @Test
    public void test_constructor_no_params() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        SomeModel byHandModel = new SomeModel();
        Constructor constructor = Reflections.constructor(SomeModel.class);
        SomeModel autoModel = (SomeModel) constructor.newInstance();
        assertEquals(byHandModel, autoModel);

        // Check the caching
        Constructor constructor2 = Reflections.constructor(SomeModel.class);
        assertEquals(constructor, constructor2);
    }

    @Test
    public void test_constructor_with_params() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        SomeModel byHandModel = new SomeModel(1);
        Constructor constructor = Reflections.constructor(SomeModel.class, int.class);
        SomeModel autoModel = (SomeModel) constructor.newInstance(1);
        assertEquals(byHandModel, autoModel);

        // Check the caching and clashing with other constructors
        Reflections.constructor(SomeModel.class);
        Constructor constructor2 = Reflections.constructor(SomeModel.class, int.class);
        assertEquals(constructor, constructor2);
    }

    @Test
    public void test_method_no_params_object() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        SomeModel byHandModel = new SomeModel();
        Method method = Reflections.method(SomeModel.class, "getA");
        assertEquals(method.invoke(byHandModel), byHandModel.getA());

        Reflections.method(SomeModel.class, "setA", int.class);
        Method method2 = Reflections.method(SomeModel.class, "getA");
        assertEquals(method, method2);
    }

    @Test
    public void test_method_with_params_object() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        SomeModel byHandModel = new SomeModel();
        byHandModel.setA(10);
        SomeModel autoModel = new SomeModel();
        Method method = Reflections.method(SomeModel.class, "setA", int.class);
        method.invoke(autoModel, 10);
        assertEquals(autoModel.getA(), byHandModel.getA());
    }

    @Test
    public void test_method_static() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = Reflections.method(SomeModel.class, "staticZero");
        assertEquals(method.invoke(null), SomeModel.staticZero());
    }
}
