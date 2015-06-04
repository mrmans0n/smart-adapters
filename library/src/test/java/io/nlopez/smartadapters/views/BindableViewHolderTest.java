package io.nlopez.smartadapters.views;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.nlopez.smartadapters.CustomTestRunner;
import io.nlopez.smartadapters.mocks.MockModel;
import io.nlopez.smartadapters.utils.ViewEventListener;

import static org.mockito.Mockito.verify;

/**
 * Tests for BindableViewHolder used in RecyclerLayout
 */
@RunWith(CustomTestRunner.class)
public class BindableViewHolderTest {

    @Mock
    BindableLayout<MockModel> mockLayout;

    @Mock
    ViewEventListener<MockModel> mockListener;

    MockModel mockModel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockModel = new MockModel();
    }

    @Test
    public void test_binding() {
        BindableViewHolder<MockModel> viewHolder = new BindableViewHolder<>(mockLayout);
        viewHolder.bind(mockModel, 0);
        verify(mockLayout).bind(mockModel, 0);
    }

    @Test
    public void test_set_listener() {
        BindableViewHolder<MockModel> viewHolder = new BindableViewHolder<>(mockLayout);
        viewHolder.setViewEventListener(mockListener);
        verify(mockLayout).setViewEventListener(mockListener);

    }
}
