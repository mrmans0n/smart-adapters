package io.nlopez.smartadapters;

import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RuntimeEnvironment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import io.nlopez.smartadapters.adapters.MultiAdapter;
import io.nlopez.smartadapters.adapters.RecyclerMultiAdapter;
import io.nlopez.smartadapters.builders.BindableLayoutBuilder;
import io.nlopez.smartadapters.mocks.MockLayout;
import io.nlopez.smartadapters.mocks.MockLayout2;
import io.nlopez.smartadapters.mocks.MockModel;
import io.nlopez.smartadapters.utils.Mapper;
import io.nlopez.smartadapters.utils.ViewEventListener;
import io.nlopez.smartadapters.views.BindableLayout;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Tests SmartAdapter builder class
 */
@RunWith(CustomTestRunner.class)
public class SmartAdapterTest {

    public static final int NOTIFY_ACTION_ID = 1;
    public static final int NOTIFY_POSITION = 0;
    @Mock
    RecyclerView mockRecycler;

    @Mock
    AbsListView mockListView;

    @Mock
    Mapper mockMapper;

    @Mock
    ViewEventListener mockListener;

    @Mock
    List<MockModel> mockList;

    @Mock
    BindableLayoutBuilder mockBuilder;

    @Mock
    MockLayout mockLayout;

    @Mock
    MockLayout2 mockLayout2;

    private Mapper trueMapper;

    public static final MockModel NOTIFY_MODEL = new MockModel();
    public static final MockLayout NOTIFY_VIEW = new MockLayout(RuntimeEnvironment.application);

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        trueMapper = new Mapper(new HashMap<Class, List<Class<? extends BindableLayout>>>(),
                new HashMap<Integer, Class<? extends BindableLayout>>());
    }

    @Test
    public void test_item_operations_adapter() {
        MultiAdapter adapter = SmartAdapter.items(mockList).adapter();
        MockModel model = new MockModel();
        adapter.addItem(model);
        verify(mockList).add(model);

        List<MockModel> modelList = Arrays.asList(new MockModel(), new MockModel());
        adapter.addItems(modelList);
        verify(mockList).addAll(modelList);

        adapter.delItem(model);
        verify(mockList).remove(model);

        adapter.clearItems();
        verify(mockList).clear();
    }

    @Test
    public void test_item_operations_recycler_adapter() {
        RecyclerMultiAdapter adapter = SmartAdapter.items(mockList).recyclerAdapter();

        MockModel model = new MockModel();
        adapter.addItem(model);
        verify(mockList).add(model);

        List<MockModel> modelList = Arrays.asList(new MockModel(), new MockModel());
        adapter.addItems(modelList);
        verify(mockList).addAll(modelList);

        adapter.delItem(model);
        verify(mockList).remove(model);

        adapter.clearItems();
        verify(mockList).clear();
    }

    @Test
    public void test_map_add() {
        SmartAdapter.MultiAdaptersCreator creator = SmartAdapter.empty()
                .mapper(mockMapper);

        creator.map(MockModel.class, MockLayout.class);
        verify(mockMapper).add(MockModel.class, MockLayout.class);

        // Indirectly we are testing that the new mapper setup works too
        creator.mapper(new Mapper());
        verifyNoMoreInteractions(mockMapper);
    }

    @Test
    public void test_listener_adapter() {
        MultiAdapter adapter = SmartAdapter.empty().listener(mockListener).adapter();
        Assert.assertEquals(adapter.getViewEventListener(), mockListener);

        adapter.notifyAction(NOTIFY_ACTION_ID, NOTIFY_MODEL, NOTIFY_POSITION, NOTIFY_VIEW);
        verify(mockListener).onViewEvent(NOTIFY_ACTION_ID, NOTIFY_MODEL, NOTIFY_POSITION, NOTIFY_VIEW);
    }

    @Test
    public void test_listener_recycler_adapter() {
        RecyclerMultiAdapter adapter = SmartAdapter.empty().listener(mockListener).recyclerAdapter();
        Assert.assertEquals(adapter.getViewEventListener(), mockListener);

        adapter.notifyAction(NOTIFY_ACTION_ID, NOTIFY_MODEL, NOTIFY_POSITION, NOTIFY_VIEW);
        verify(mockListener).onViewEvent(NOTIFY_ACTION_ID, NOTIFY_MODEL, NOTIFY_POSITION, NOTIFY_VIEW);
    }

    @Test
    public void test_into_abslistview() {
        MultiAdapter adapter = SmartAdapter.empty().into(mockListView);
        verify(mockListView).setAdapter(adapter);
    }

    @Test
    public void test_into_recycler() {
        RecyclerMultiAdapter adapter = SmartAdapter.empty().into(mockRecycler);
        verify(mockRecycler).setAdapter(adapter);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_multimapping_fail_listview() {
        when(mockBuilder.allowsMultimapping()).thenReturn(false);
        SmartAdapter.empty()
                .mapper(trueMapper)
                .map(MockModel.class, MockLayout.class)
                .map(MockModel.class, MockLayout2.class)
                .builder(mockBuilder)
                .into(mockListView);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_multimapping_fail_recyclerview() {
        when(mockBuilder.allowsMultimapping()).thenReturn(false);
        SmartAdapter.empty()
                .mapper(trueMapper)
                .map(MockModel.class, MockLayout.class)
                .map(MockModel.class, MockLayout2.class)
                .builder(mockBuilder)
                .into(mockRecycler);
    }

    @Test
    public void test_multimapping_success() {
        when(mockBuilder.allowsMultimapping()).thenReturn(true);
        SmartAdapter.MultiAdaptersCreator builder = SmartAdapter.empty()
                .map(MockModel.class, MockLayout.class)
                .map(MockModel.class, MockLayout2.class)
                .builder(mockBuilder);

        builder.into(mockListView);
        builder.into(mockRecycler);
    }

}
