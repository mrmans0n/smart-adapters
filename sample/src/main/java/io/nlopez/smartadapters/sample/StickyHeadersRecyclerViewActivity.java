package io.nlopez.smartadapters.sample;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersTouchListener;

import java.security.SecureRandom;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.nlopez.smartadapters.SmartAdapter;
import io.nlopez.smartadapters.adapters.RecyclerMultiAdapter;
import io.nlopez.smartadapters.builders.BindableLayoutBuilder;
import io.nlopez.smartadapters.sample.model.User;
import io.nlopez.smartadapters.sample.util.DataGenerator;
import io.nlopez.smartadapters.sample.view.UserView;
import io.nlopez.smartadapters.utils.Mapper;

public class StickyHeadersRecyclerViewActivity extends Activity {

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
        MyRecyclerMultiAdapter adapter = (MyRecyclerMultiAdapter) SmartAdapter.items(userList).map(User.class, UserView.class)
                .recyclerAdapter(new SmartAdapter.ConstructorRecyclerAdapter() {
                    @Override
                    public RecyclerMultiAdapter constructor(Mapper mapper, List listItems, BindableLayoutBuilder builder) {

                        return new MyRecyclerMultiAdapter(mapper,listItems,builder);
                    }
                });

        recyclerView.setAdapter(adapter);

        // Add the sticky headers decoration
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(adapter);
        recyclerView.addItemDecoration(headersDecor);

        // Add decoration for dividers between list items
//        recyclerView.addItemDecoration(new DividerDecoration(this));

        // Add touch listeners
        StickyRecyclerHeadersTouchListener touchListener =
                new StickyRecyclerHeadersTouchListener(recyclerView, headersDecor);
        touchListener.setOnHeaderClickListener(
                new StickyRecyclerHeadersTouchListener.OnHeaderClickListener() {
                    @Override
                    public void onHeaderClick(View header, int position, long headerId) {
                        Toast.makeText(StickyHeadersRecyclerViewActivity.this, "Header position: " + position + ", id: " + headerId,
                                Toast.LENGTH_SHORT).show();
                    }
                });
        recyclerView.addOnItemTouchListener(touchListener);

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                headersDecor.invalidateHeaders();
            }
        });


    }

    public class MyRecyclerMultiAdapter extends RecyclerMultiAdapter implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

        public MyRecyclerMultiAdapter(Mapper mapper, List listItems, BindableLayoutBuilder builder) {
            super(mapper, listItems, builder);
        }

        @Override
        public long getHeaderId(int position) {
            if (position == 0) {
                return -1;
            } else {
                return 'A';
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_header, parent, false);
            return new RecyclerView.ViewHolder(view) {
            };
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
            TextView textView = (TextView) holder.itemView;
            textView.setText(String.valueOf('A'));
            holder.itemView.setBackgroundColor(getRandomColor());
        }

        private int getRandomColor() {
            SecureRandom rgen = new SecureRandom();
            return Color.HSVToColor(150, new float[]{
                    rgen.nextInt(359), 1, 1
            });
        }

    }



}
