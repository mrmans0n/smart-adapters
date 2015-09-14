package io.nlopez.smartadapters.utils;

import org.junit.Test;
import org.junit.runner.RunWith;

import io.nlopez.smartadapters.CustomTestRunner;
import io.nlopez.smartadapters.mocks.MockLayout;
import io.nlopez.smartadapters.mocks.MockLayout2;
import io.nlopez.smartadapters.mocks.MockModel;
import io.nlopez.smartadapters.mocks.MockModel2;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

/**
 * Tests for Mapper
 */
@RunWith(CustomTestRunner.class)
public class MapperTest {

    @Test
    public void test_add() {
        Mapper mapper = new Mapper();
        assertThat(mapper.asMap()).hasSize(0);
        assertEquals(mapper.objectClasses(), 0);
        mapper.add(MockModel.class, MockLayout.class);
        assertThat(mapper.asMap())
                .hasSize(1)
                .containsExactly(entry(MockModel.class, MockLayout.class));
        assertEquals(mapper.objectClasses(), 1);
        assertTrue(mapper.containsObjectClass(MockModel.class));
        assertTrue(mapper.containsViewClass(MockLayout.class));
    }

    @Test(expected = AssertionError.class)
    public void test_adding_duplicates_throws_exception() {
        Mapper mapper = new Mapper()
                .add(MockModel.class, MockLayout.class)
                .add(MockModel.class, MockLayout.class);
    }

    @Test
    public void test_get() {
        Mapper mapper = new Mapper();
        mapper.add(MockModel.class, MockLayout.class);
        assertEquals(mapper.get(MockModel.class), MockLayout.class);
    }
}
