package io.nlopez.smartadapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;

import java.util.ArrayList;
import java.util.List;

import io.nlopez.smartadapters.adapters.MultiAdapter;
import io.nlopez.smartadapters.adapters.RecyclerMultiAdapter;
import io.nlopez.smartadapters.adapters.RecyclerSingleAdapter;
import io.nlopez.smartadapters.adapters.SingleAdapter;
import io.nlopez.smartadapters.adapters.androidannotations.AAMultiAdapter;
import io.nlopez.smartadapters.adapters.androidannotations.AARecyclerMultiAdapter;
import io.nlopez.smartadapters.adapters.androidannotations.AARecyclerSingleAdapter;
import io.nlopez.smartadapters.adapters.androidannotations.AASingleAdapter;
import io.nlopez.smartadapters.utils.BindableLayoutBuilder;
import io.nlopez.smartadapters.utils.Mapper;
import io.nlopez.smartadapters.utils.ViewEventListener;
import io.nlopez.smartadapters.views.BindableLayout;

/**
 * Managing class for SmartAdapters library.
 */
public class SmartAdapters {
    public static <T, Q extends BindableLayout> SingleAdaptersCreator single(Class<Q> viewClass) {
        return new SingleAdaptersCreator<T, Q>(viewClass);
    }

    public static class SingleAdaptersCreator<T, Q extends BindableLayout<T>> {
        private final Class<Q> viewClass;
        private List<T> elements;
        private boolean aa;
        private BindableLayoutBuilder<T, Q> builder;
        private ViewEventListener<T> listener;

        public SingleAdaptersCreator(Class<Q> viewClass) {
            this.viewClass = viewClass;
            elements = new ArrayList<>();
            aa = false;
        }

        public SingleAdaptersCreator<T, Q> items(@NonNull List<T> elements) {
            this.elements = elements;
            return this;
        }

        public SingleAdaptersCreator<T, Q> builder(BindableLayoutBuilder<T, Q> builder) {
            this.builder = builder;
            return this;
        }

        public SingleAdaptersCreator<T, Q> aa(boolean aa) {
            this.aa = aa;
            return this;
        }

        public SingleAdaptersCreator<T, Q> listener(@NonNull ViewEventListener<T> listener) {
            this.listener = listener;
            return this;
        }

        public SingleAdapter<T, Q> get() {
            SingleAdapter<T, Q> response = aa ? new AASingleAdapter<>(viewClass, elements, builder) : new SingleAdapter<>(viewClass, elements, builder);
            response.setViewEventListener(listener);
            return response;
        }

        public RecyclerSingleAdapter<T, Q> getRecycler() {
            RecyclerSingleAdapter<T, Q> response = aa ? new AARecyclerSingleAdapter<>(viewClass, elements, builder) : new RecyclerSingleAdapter<>(viewClass, elements, builder);
            response.setViewEventListener(listener);
            return response;
        }

        public SingleAdapter<T, Q> into(@NonNull AbsListView widget) {
            SingleAdapter<T, Q> adapter = get();
            widget.setAdapter(adapter);
            return adapter;
        }

        public RecyclerSingleAdapter<T, Q> into(@NonNull RecyclerView recyclerView) {
            RecyclerSingleAdapter<T, Q> adapter = getRecycler();
            recyclerView.setAdapter(adapter);
            return adapter;
        }
    }


    public static MultiAdaptersCreator multi() {
        return new MultiAdaptersCreator();
    }

    public static class MultiAdaptersCreator {
        private Mapper mapper;
        private List elements;
        private boolean aa;
        private BindableLayoutBuilder builder;
        private ViewEventListener listener;

        public MultiAdaptersCreator() {
            this.mapper = new Mapper();
            elements = new ArrayList<>();
            aa = false;
        }

        public MultiAdaptersCreator map(@NonNull Class objectClass, @NonNull Class<? extends BindableLayout> viewClass) {
            mapper.add(objectClass, viewClass);
            return this;
        }

        public MultiAdaptersCreator mapper(@NonNull Mapper mapper) {
            this.mapper = mapper;
            return this;
        }

        public MultiAdaptersCreator items(@NonNull List elements) {
            this.elements = elements;
            return this;
        }

        public MultiAdaptersCreator builder(BindableLayoutBuilder builder) {
            this.builder = builder;
            return this;
        }

        public MultiAdaptersCreator aa(boolean aa) {
            this.aa = aa;
            return this;
        }

        public MultiAdaptersCreator listener(@NonNull ViewEventListener listener) {
            this.listener = listener;
            return this;
        }

        public MultiAdapter get() {
            MultiAdapter response = aa ? new AAMultiAdapter(mapper, elements, builder) : new MultiAdapter(mapper, elements, builder);
            response.setViewEventListener(listener);
            return response;
        }

        public RecyclerMultiAdapter getRecycler() {
            RecyclerMultiAdapter response = aa ? new AARecyclerMultiAdapter(mapper, elements, builder) : new RecyclerMultiAdapter(mapper, elements, builder);
            response.setViewEventListener(listener);
            return response;
        }

        public MultiAdapter into(@NonNull AbsListView widget) {
            MultiAdapter adapter = get();
            widget.setAdapter(adapter);
            return adapter;
        }

        public RecyclerMultiAdapter into(@NonNull RecyclerView recyclerView) {
            RecyclerMultiAdapter adapter = getRecycler();
            recyclerView.setAdapter(adapter);
            return adapter;
        }
    }


}
