package io.nlopez.smartadapters.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import io.nlopez.smartadapters.utils.ViewEventListener;

public class BindableViewHolder<T> extends RecyclerView.ViewHolder {

    private IBindableLayout<T> bindableLayout;

    public BindableViewHolder(IBindableLayout<T> itemView) {
        super((View) itemView);
        bindableLayout = itemView;

    }

    public void bind(T object, int position) {
        bindableLayout.bind(object, position);
    }

    public void setViewEventListener(ViewEventListener<T> listener) {
        bindableLayout.setViewEventListener(listener);
    }
}
