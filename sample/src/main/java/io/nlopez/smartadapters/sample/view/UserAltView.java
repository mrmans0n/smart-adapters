package io.nlopez.smartadapters.sample.view;

import android.content.Context;
import android.view.View;
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
public class UserAltView extends BindableFrameLayout<User> {

    @InjectView(R.id.user_image)
    ImageView userImage;

    @InjectView(R.id.user_text)
    TextView userText;

    public UserAltView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_user_alt;
    }

    @Override
    public void onViewInflated() {
        ButterKnife.inject(this);
    }

    @Override
    public void bind(User item) {
        userText.setText(item.getFirstName() + " " + item.getLastName() + "\n" + item.getRole());
        Picasso.with(getContext()).load(item.getAvatar()).into(userImage);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemAction(Interactions.USER_CLICKED);
            }
        });
    }
}
