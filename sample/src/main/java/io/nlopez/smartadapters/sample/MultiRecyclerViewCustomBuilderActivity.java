package io.nlopez.smartadapters.sample;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.nlopez.smartadapters.SmartAdapter;
import io.nlopez.smartadapters.builders.DefaultBindableLayoutBuilder;
import io.nlopez.smartadapters.sample.model.Place;
import io.nlopez.smartadapters.sample.model.User;
import io.nlopez.smartadapters.sample.util.DataGenerator;
import io.nlopez.smartadapters.sample.view.PlaceView;
import io.nlopez.smartadapters.sample.view.UserAltView;
import io.nlopez.smartadapters.sample.view.UserView;
import io.nlopez.smartadapters.utils.Mapper;
import io.nlopez.smartadapters.views.BindableLayout;

public class MultiRecyclerViewCustomBuilderActivity extends Activity {

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
        List mixedList = DataGenerator.generateMix(100);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SmartAdapter.items(mixedList)
                .map(User.class, UserView.class)
                .map(User.class, UserAltView.class)
                .map(Place.class, PlaceView.class)
                .builder(new DefaultBindableLayoutBuilder() {

                    @Override
                    public Class<? extends BindableLayout> viewType(@NonNull Object item, int position, @NonNull Mapper mapper) {
                        if (item instanceof User) {
                            User user = (User) item;
                            if (user.getFirstName().length() % 2 == 1) {
                                return UserView.class;
                            } else {
                                return UserAltView.class;
                            }
                        } else {
                            return super.viewType(item, position, mapper);
                        }
                    }
                })
                .into(recyclerView);
    }
}
