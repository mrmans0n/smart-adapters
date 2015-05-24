package io.nlopez.smartadapters.sample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MultiListViewActivity extends Activity {

    @InjectView(R.id.list_view)
    ListView listView;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_listview);

        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        // TODO insert elements and blah blah
    }

}
