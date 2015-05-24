package io.nlopez.smartadapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;

import java.util.ArrayList;
import java.util.List;

import io.nlopez.smartadapters.adapters.MultiAdapter;
import io.nlopez.smartadapters.adapters.RecyclerMultiAdapter;
import io.nlopez.smartadapters.builders.BindableLayoutBuilder;
import io.nlopez.smartadapters.utils.Mapper;
import io.nlopez.smartadapters.utils.ViewEventListener;
import io.nlopez.smartadapters.views.BindableLayout;

/**
 * Managing class for SmartAdapters library.
 */
public class SmartAdapter {
    public static MultiAdaptersCreator items(List<?> items) {
        return new MultiAdaptersCreator(items);
    }

    public static MultiAdaptersCreator empty() {
        return new MultiAdaptersCreator(new ArrayList<>());
    }

    public static class MultiAdaptersCreator {
        private Mapper mapper;
        private List elements;
        private BindableLayoutBuilder builder;
        private ViewEventListener listener;

        public MultiAdaptersCreator(List<?> elements) {
            this.mapper = new Mapper();
            this.elements = elements;
        }

        public MultiAdaptersCreator map(@NonNull Class objectClass, @NonNull Class<? extends BindableLayout> viewClass) {
            mapper.add(objectClass, viewClass);
            return this;
        }

        public MultiAdaptersCreator mapper(@NonNull Mapper mapper) {
            this.mapper = mapper;
            return this;
        }

        public MultiAdaptersCreator builder(BindableLayoutBuilder builder) {
            this.builder = builder;
            return this;
        }

        public MultiAdaptersCreator listener(@NonNull ViewEventListener listener) {
            this.listener = listener;
            return this;
        }

        public MultiAdapter adapter() {
            MultiAdapter response = new MultiAdapter(mapper, elements, builder);
            response.setViewEventListener(listener);
            return response;
        }

        public RecyclerMultiAdapter recyclerAdapter() {
            RecyclerMultiAdapter response = new RecyclerMultiAdapter(mapper, elements, builder);
            response.setViewEventListener(listener);
            return response;
        }

        public MultiAdapter into(@NonNull AbsListView widget) {
            MultiAdapter adapter = adapter();
            widget.setAdapter(adapter);
            return adapter;
        }

        public RecyclerMultiAdapter into(@NonNull RecyclerView recyclerView) {
            RecyclerMultiAdapter adapter = recyclerAdapter();
            recyclerView.setAdapter(adapter);
            return adapter;
        }
    }
}
