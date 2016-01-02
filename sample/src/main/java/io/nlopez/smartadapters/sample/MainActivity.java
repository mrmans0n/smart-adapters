package io.nlopez.smartadapters.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
    }

    @OnClick(R.id.single_listview_button)
    void launchSingleListView() {
        startActivity(new Intent(this, SingleListViewActivity.class));
    }

    @OnClick(R.id.single_recyclerview_button)
    void launchSingleRecyclerView() {
        startActivity(new Intent(this, SingleRecyclerViewActivity.class));
    }

    @OnClick(R.id.multi_listview_button)
    void launchMultiListView() {
        startActivity(new Intent(this, MultiListViewActivity.class));
    }

    @OnClick(R.id.multi_recyclerview_button)
    void launchMultiRecyclerView() {
        startActivity(new Intent(this, MultiRecyclerViewActivity.class));
    }

    @OnClick(R.id.multi_recyclerview_custom_builder_button)
    void launchMultiRecyclerViewCustomBuilder() {
        startActivity(new Intent(this, MultiRecyclerViewCustomBuilderActivity.class));
    }

    @OnClick(R.id.sticky_headers_recyclerview_custom_builder_button)
    void launchStickyHeadersRecyclerViewCustomBuilder() {
        startActivity(new Intent(this, StickyHeadersRecyclerViewActivity.class));
    }

}
