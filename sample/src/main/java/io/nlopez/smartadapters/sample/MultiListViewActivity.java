package io.nlopez.smartadapters.sample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.nlopez.smartadapters.SmartAdapters;
import io.nlopez.smartadapters.sample.model.Place;
import io.nlopez.smartadapters.sample.model.User;
import io.nlopez.smartadapters.sample.util.DataGenerator;
import io.nlopez.smartadapters.sample.view.PlaceView;
import io.nlopez.smartadapters.sample.view.UserView;

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
        List mixedList = DataGenerator.generateMix(100);
        SmartAdapters.multi().map(User.class, UserView.class).map(Place.class, PlaceView.class).items(mixedList).into(listView);
    }

}
