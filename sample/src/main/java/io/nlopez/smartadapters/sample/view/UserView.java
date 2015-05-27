package io.nlopez.smartadapters.sample.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
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
import io.nlopez.smartadapters.views.BindableLayout;

/**
 * Created by mrm on 24/5/15.
 */
public class UserView extends BindableLayout<User> {

    @InjectView(R.id.user_image)
    ImageView userImage;

    @InjectView(R.id.user_text)
    TextView userText;

    public UserView(Context context) {
        super(context);
        initView(context);
    }

    public UserView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public UserView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    public void initView(Context context) {
        inflate(context, R.layout.view_user, (ViewGroup) getRootView());
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
