package io.nlopez.smartadapters.sample.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.nlopez.smartadapters.sample.R;
import io.nlopez.smartadapters.sample.model.User;
import io.nlopez.smartadapters.sample.util.Interactions;
import io.nlopez.smartadapters.views.BindableFrameLayout;

/**
 * Created by mrm on 24/5/15.
 */
public class UserView extends BindableFrameLayout<User> {

    @InjectView(R.id.user_image)
    ImageView userImage;

    @InjectView(R.id.user_text)
    TextView userText;

    public UserView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_user;
    }

    @Override
    public void onViewInflated() {
        ButterKnife.inject(this);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void bind(User item) {
        userText.setText(TextUtils.concat(item.getFirstName(), " ", item.getLastName(), "\n", item.getRole()));
        Picasso.with(getContext()).load(item.getAvatar()).into(userImage);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemAction(Interactions.USER_CLICKED);
            }
        });
    }
}
