package io.nlopez.smartadapters.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.util.List;

import io.nlopez.smartadapters.utils.BindableLayoutBuilder;
import io.nlopez.smartadapters.utils.Reflections;
import io.nlopez.smartadapters.utils.ThreadHelper;
import io.nlopez.smartadapters.utils.ViewEventListener;
import io.nlopez.smartadapters.views.BindableLayout;
import io.nlopez.smartadapters.views.BindableViewHolder;

public class RecyclerSingleAdapter<T, Q extends BindableLayout<T>> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected Class viewClass;

    protected List<T> listItems;
    protected ViewEventListener<T> viewEventListener;
    protected BindableLayoutBuilder<T, Q> builder;

    public RecyclerSingleAdapter(Class<Q> viewClass, List<T> listItems) {
        this(viewClass, listItems, RecyclerSingleAdapter.<T, Q>createDefaultBuilder(viewClass));
    }

    public RecyclerSingleAdapter(Class<Q> viewClass, List<T> listItems, BindableLayoutBuilder<T, Q> builder) {
        this.listItems = listItems;
        this.viewClass = viewClass;
        if (builder == null) {
            this.builder = RecyclerSingleAdapter.createDefaultBuilder(viewClass);
        } else {
            this.builder = builder;
        }
    }

    public void setItems(List<T> items) {
        ThreadHelper.crashIfBackgroundThread();
        listItems = items;
        notifyDataSetChanged();
    }

    public void addItem(T item) {
        ThreadHelper.crashIfBackgroundThread();
        listItems.add(item);
        notifyDataSetChanged();
    }

    public void delItem(T item) {
        ThreadHelper.crashIfBackgroundThread();
        listItems.remove(item);
        notifyDataSetChanged();
    }

    public void addItems(List<T> items) {
        ThreadHelper.crashIfBackgroundThread();
        listItems.addAll(items);
        notifyDataSetChanged();
    }

    public void clearItems() {
        ThreadHelper.crashIfBackgroundThread();
        listItems.clear();
        notifyDataSetChanged();
    }

    public ViewEventListener<T> getViewEventListener() {
        return viewEventListener;
    }

    public void setViewEventListener(ViewEventListener<T> viewEventListener) {
        this.viewEventListener = viewEventListener;
    }

    public void notifyAction(int actionId, T object, int position, View view) {
        if (viewEventListener != null) {
            viewEventListener.onViewEvent(actionId, object, position, view);
        }
    }

    private T getItem(int position) {
        return listItems == null ? null : listItems.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BindableLayout<T> viewGroup = builder.build(parent.getContext(), viewClass, null);
        return new BindableViewHolder<>(viewGroup);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BindableViewHolder bindableViewHolder = (BindableViewHolder) holder;
        bindableViewHolder.setViewEventListener(viewEventListener);
        Object item = getItem(position);
        if (item != null) {
            bindableViewHolder.bind(item, position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return listItems == null ? 0 : listItems.size();
    }

    private static <T, Q extends BindableLayout<T>> BindableLayoutBuilder<T, Q> createDefaultBuilder(
            final Class viewClass) {
        return new BindableLayoutBuilder<T, Q>() {
            @Override
            public Q build(Context context, Class aClass, T item) {
                try {
                    Constructor constructor = Reflections.constructor(viewClass, Context.class);
                    return (Q) constructor.newInstance(context);
                } catch (Exception e) {
                    throw new RuntimeException("Something went wrong creating the views", e);
                }
            }
        };
    }
}
