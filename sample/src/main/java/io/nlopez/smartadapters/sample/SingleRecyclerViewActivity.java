package io.nlopez.smartadapters.sample;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import io.nlopez.smartadapters.SmartAdapter;
import io.nlopez.smartadapters.adapters.RecyclerMultiAdapter;
import io.nlopez.smartadapters.builders.DefaultBindableLayoutBuilder;
import io.nlopez.smartadapters.sample.model.User;
import io.nlopez.smartadapters.sample.util.DataGenerator;
import io.nlopez.smartadapters.sample.view.UserView;
import io.nlopez.smartadapters.utils.Mapper;

import java.util.List;

public class SingleRecyclerViewActivity extends Activity {

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
        List<User> userList = DataGenerator.generateUsers(100);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerMultiAdapter adapter = SmartAdapter.items(userList).map(User.class, UserView.class)
                .builder(new DefaultBindableLayoutBuilder() {
                    @Override
                    public long viewItemId(@NonNull Object item, int position, @NonNull Mapper mapper) {
                        System.out.println(String.format("position: %d,item:%s",position,item.toString()));
                        return super.viewItemId(item, position, mapper);
                    }

                    @Override
                    public boolean hasStableIds() {
                        return true;
                    }
                }).into(recyclerView);



    }

}
