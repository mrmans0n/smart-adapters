package io.nlopez.smartadapters.sample;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MultiRecyclerViewActivity extends Activity {

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_recyclerview);

        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        // TODO insert elements and blah blah
    }

}
