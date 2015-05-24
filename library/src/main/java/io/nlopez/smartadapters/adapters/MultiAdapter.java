package io.nlopez.smartadapters.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.nlopez.smartadapters.utils.BindableLayoutBuilder;
import io.nlopez.smartadapters.utils.Mapper;
import io.nlopez.smartadapters.utils.Reflections;
import io.nlopez.smartadapters.utils.ThreadHelper;
import io.nlopez.smartadapters.utils.ViewEventListener;
import io.nlopez.smartadapters.views.BindableLayout;

public class MultiAdapter extends BaseAdapter {

    protected Map<Class, Class<? extends BindableLayout>> itemViewMapping;
    protected List<Class> itemClassArray;
    protected List listItems;
    protected ViewEventListener viewEventListener;
    protected BindableLayoutBuilder builder;

    public MultiAdapter(Mapper mapper, List listItems) {
        this(mapper, listItems, createDefaultBuilder(mapper));
    }

    public MultiAdapter(Mapper mapper, List listItems, BindableLayoutBuilder builder) {
        this.listItems = listItems;
        this.itemViewMapping = mapper.asMap();
        if (builder == null) {
            builder = createDefaultBuilder(mapper);
        } else {
            this.builder = builder;
        }
        this.itemClassArray = new ArrayList<>(itemViewMapping.keySet());
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
    public int getItemViewType(int position) {
        if (listItems == null) {
            return 0;
        }
        Object object = getItem(position);
        Class itemClass = itemViewMapping.get(object.getClass());
        return itemViewMapping == null ? 0 : itemClassArray.indexOf(itemClass);
    }

    @Override
    public int getViewTypeCount() {
        return itemViewMapping == null ? 0 : itemViewMapping.size();
    }

    @Override
    public int getCount() {
        return listItems == null ? 0 : listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems == null ? null : listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BindableLayout viewGroup = (BindableLayout) convertView;
        if (viewGroup == null) {
            viewGroup = builder.build(parent.getContext(), getItem(position).getClass(), getItem(position));
        }

        if (viewGroup != null) {
            viewGroup.setViewEventListener(viewEventListener);
            viewGroup.bind(getItem(position), position);
        }
        return viewGroup;
    }

    private static BindableLayoutBuilder createDefaultBuilder(final Mapper mapper) {
        return new BindableLayoutBuilder() {
            @Override
            public BindableLayout build(Context context, Class aClass, Object item) {
                try {
                    Class viewClass = mapper.asMap().get(aClass);
                    Constructor constructor = Reflections.constructor(viewClass, Context.class);
                    return (BindableLayout) constructor.newInstance(context);
                } catch (Exception e) {
                    throw new RuntimeException("Something went wrong creating the views", e);
                }
            }
        };
    }
}
