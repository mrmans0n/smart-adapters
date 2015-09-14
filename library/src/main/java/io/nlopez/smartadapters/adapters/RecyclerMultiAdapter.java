package io.nlopez.smartadapters.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.nlopez.smartadapters.builders.BindableLayoutBuilder;
import io.nlopez.smartadapters.builders.DefaultBindableLayoutBuilder;
import io.nlopez.smartadapters.utils.Mapper;
import io.nlopez.smartadapters.utils.ThreadHelper;
import io.nlopez.smartadapters.utils.ViewEventListener;
import io.nlopez.smartadapters.views.BindableLayout;
import io.nlopez.smartadapters.views.BindableViewHolder;

/**
 * Adapter for {@code RecyclerView} based widgets
 */
public class RecyclerMultiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected Mapper mapper;
    protected List<Class> itemClassArray;
    protected List listItems;
    protected ViewEventListener viewEventListener;
    protected BindableLayoutBuilder builder;


    public RecyclerMultiAdapter(Mapper mapper, List listItems) {
        this(mapper, listItems, createDefaultBuilder());
    }

    public RecyclerMultiAdapter(Mapper mapper, List listItems, BindableLayoutBuilder builder) {
        this.listItems = listItems;
        this.mapper = mapper;
        if (builder == null) {
            this.builder = createDefaultBuilder();
        } else {
            this.builder = builder;
        }
        this.itemClassArray = new ArrayList<>(mapper.objectClasses());
    }

    public void setItems(List items) {
        ThreadHelper.crashIfBackgroundThread();
        listItems = items;
        notifyDataSetChanged();
    }

    public void addItem(Object item) {
        ThreadHelper.crashIfBackgroundThread();
        listItems.add(item);
        notifyDataSetChanged();
    }

    public void delItem(Object item) {
        ThreadHelper.crashIfBackgroundThread();
        listItems.remove(item);
        notifyDataSetChanged();
    }

    public void addItems(List items) {
        ThreadHelper.crashIfBackgroundThread();
        listItems.addAll(items);
        notifyDataSetChanged();
    }

    public void clearItems() {
        ThreadHelper.crashIfBackgroundThread();
        listItems.clear();
        notifyDataSetChanged();
    }

    public ViewEventListener getViewEventListener() {
        return viewEventListener;
    }

    public void setViewEventListener(ViewEventListener viewEventListener) {
        this.viewEventListener = viewEventListener;
    }

    public void notifyAction(int actionId, Object object, int position, View view) {
        if (viewEventListener != null) {
            viewEventListener.onViewEvent(actionId, object, position, view);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BindableLayout viewGroup = builder.build(parent, viewType, null, mapper);
        return new BindableViewHolder(viewGroup);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BindableViewHolder bindableViewHolder = (BindableViewHolder) holder;
        bindableViewHolder.setViewEventListener(viewEventListener);
        Object item = listItems.get(position);
        if (item != null) {
            bindableViewHolder.bind(item, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (listItems == null) {
            return 0;
        }
        Object object = listItems.get(position);
        return builder.viewType(object, position, mapper);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return listItems == null ? 0 : listItems.size();
    }

    private static BindableLayoutBuilder createDefaultBuilder() {
        return new DefaultBindableLayoutBuilder();
    }
}
