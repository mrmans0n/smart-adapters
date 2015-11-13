package io.nlopez.smartadapters.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import io.nlopez.smartadapters.utils.ViewEventListener;

public abstract class BindableRelativeLayout<T> extends RelativeLayout implements IBindableLayout<T> {

    protected ViewEventListener<T> viewEventListener;
    protected T item;
    protected int position;

    public BindableRelativeLayout(Context context) {
        super(context);
        initView(context);
    }

    public BindableRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public BindableRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BindableRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    public void initView(Context context) {
        int layoutId = getLayoutId();
        if (layoutId != 0) {
            inflate(context, layoutId, this);
        }
        onViewInflated();
    }

    public void onViewInflated() {

    }

    @LayoutRes
    public abstract int getLayoutId();

    @Override
    public void bind(T item, int position) {
        this.item = item;
        this.position = position;
        bind(item);
    }

    @Override
    public abstract void bind(T item);

    @Override
    @Nullable
    public ViewEventListener<T> getViewEventListener() {
        return viewEventListener;
    }

    @Override
    public void setViewEventListener(ViewEventListener<T> viewEventListener) {
        this.viewEventListener = viewEventListener;
    }

    @Override
    public void notifyItemAction(int actionId, T theItem, View view) {
        if (viewEventListener != null) {
            viewEventListener.onViewEvent(actionId, theItem, position, view);
        }
    }

    public void notifyItemAction(int actionId, View view) {
        notifyItemAction(actionId, item, view);
    }

    public void notifyItemAction(int actionId) {
        notifyItemAction(actionId, item, this);
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
