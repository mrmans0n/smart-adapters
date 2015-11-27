package io.nlopez.smartadapters.sample.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.nlopez.smartadapters.sample.R;
import io.nlopez.smartadapters.sample.model.Place;
import io.nlopez.smartadapters.sample.util.Interactions;
import io.nlopez.smartadapters.views.BindableFrameLayout;
import io.nlopez.smartadapters.views.BindableRelativeLayout;

/**
 * Created by mrm on 24/5/15.
 */
public class PlaceAltView extends BindableRelativeLayout<Place> {

    @InjectView(R.id.place_image)
    ImageView placeImage;

    @InjectView(R.id.place_text)
    TextView placeText;

    public PlaceAltView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_place_alt;
    }

    @Override
    public void onViewInflated() {
        ButterKnife.inject(this);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void bind(Place item) {
        placeText.setText(item.getName());
        Picasso.with(getContext()).load(item.getImageUrl()).into(placeImage);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemAction(Interactions.PLACE_CLICKED);
            }
        });
    }
}
