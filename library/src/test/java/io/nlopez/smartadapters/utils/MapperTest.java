package io.nlopez.smartadapters.utils;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.List;

import io.nlopez.smartadapters.CustomTestRunner;
import io.nlopez.smartadapters.mocks.MockLayout;
import io.nlopez.smartadapters.mocks.MockLayout2;
import io.nlopez.smartadapters.mocks.MockModel;
import io.nlopez.smartadapters.views.BindableFrameLayout;
import io.nlopez.smartadapters.views.BindableLayout;

import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for Mapper
 */
@RunWith(CustomTestRunner.class)
public class MapperTest {

    @Test
    public void test_add() {
        Mapper mapper = new Mapper(new HashMap<Class, List<Class<? extends BindableLayout>>>(),
                new HashMap<Integer, Class<? extends BindableLayout>>());
        assertThat(mapper.asMap()).hasSize(0);
        assertThat(mapper.objectClasses()).hasSize(0);
        mapper.add(MockModel.class, MockLayout.class);
        assertThat(mapper.asMap())
                .hasSize(1)
                .containsKey(MockModel.class);
        assertThat(mapper.objectClasses()).hasSize(1);
        assertTrue(mapper.containsObjectClass(MockModel.class));
        assertTrue(mapper.containsViewClass(MockLayout.class));
        assertSame(mapper.get(MockModel.class).get(0), MockLayout.class);
    }

    @Test
    public void test_adding_duplicates_chains_objects() {
        Mapper mapper = new Mapper()
                .add(MockModel.class, MockLayout.class)
                .add(MockModel.class, MockLayout2.class);
        assertThat(mapper.asMap()).hasSize(1);
        List<Class<? extends BindableLayout>> classes = mapper.get(MockModel.class);
        assertThat(classes)
                .contains(MockLayout.class, MockLayout2.class);
        assertThat(mapper.viewClasses()).hasSize(2);
    }

    @Test
    public void test_get() {
        Mapper mapper = new Mapper();
        mapper.add(MockModel.class, MockLayout.class);
        List<Class<? extends BindableLayout>> classes = mapper.get(MockModel.class);
        assertThat(classes)
                .hasSize(1)
                .contains(MockLayout.class);
    }
}
