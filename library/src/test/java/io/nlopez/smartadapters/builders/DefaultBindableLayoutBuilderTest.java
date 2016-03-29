package io.nlopez.smartadapters.builders;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RuntimeEnvironment;

import io.nlopez.smartadapters.mocks.MockLayout;
import io.nlopez.smartadapters.mocks.MockLayout2;
import io.nlopez.smartadapters.mocks.MockModel;
import io.nlopez.smartadapters.mocks.MockModel2;
import io.nlopez.smartadapters.utils.Mapper;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Tests default builder
 */
public class DefaultBindableLayoutBuilderTest {

    MockModel mockModel;

    @Mock
    ViewGroup parent;

    private Mapper mapper;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mapper = new Mapper()
                .add(MockModel.class, MockLayout.class)
                .add(MockModel2.class, MockLayout2.class);

        mockModel = new MockModel();

        when(parent.getContext()).thenReturn(RuntimeEnvironment.application);
    }

    @Test
    public void test_build() {
        DefaultBindableLayoutBuilder builder = new DefaultBindableLayoutBuilder();
        int viewType = mapper.viewTypeFromViewClass(builder.viewType(mockModel, 0, mapper));
        View bindableLayout = builder.build(parent, viewType, mockModel, mapper);
        assertNotNull(bindableLayout);
        assertTrue(bindableLayout instanceof MockLayout);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_crash_if_unknown_view_class() {
        DefaultBindableLayoutBuilder builder = new DefaultBindableLayoutBuilder();
        int viewType = mapper.viewTypeFromViewClass(builder.viewType(new Object(), 0, mapper));
        View bindableLayout = builder.build(parent, viewType, null, mapper);
    }

    @Test
    public void test_build_view_item_id() {
        DefaultBindableLayoutBuilder builder = new DefaultBindableLayoutBuilder(){
            @Override
            public boolean hasStableIds() {
                return true;
            }

            @Override
            public long viewItemId(@NonNull Object item, int position, @NonNull Mapper mapper) {
                return 100 + position;
            }
        };

        long itemId = builder.viewItemId(mockModel, 0, mapper);

        assertEquals(itemId,100);
        assertTrue(builder.hasStableIds());
    }

}
