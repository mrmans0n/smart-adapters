package io.nlopez.smartadapters.adapters;

import android.view.View;

import java.util.List;

import io.nlopez.smartadapters.utils.ViewEventListener;

/**
 * Common methods for adapters added by the library
 */
public interface BasicSmartAdapter {
    void setItems(List items);

    void addItem(Object item);

    void delItem(Object item);

    void addItems(List items);

    void clearItems();

    ViewEventListener getViewEventListener();

    void setViewEventListener(ViewEventListener viewEventListener);

    void notifyAction(int actionId, Object object, int position, View view);

    void setAutoDataSetChanged(boolean enabled);
}
