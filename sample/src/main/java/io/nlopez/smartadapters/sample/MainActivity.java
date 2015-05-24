package io.nlopez.smartadapters.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import io.nlopez.smartadapters.SmartAdapters;
import io.nlopez.smartadapters.utils.ViewEventListener;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);

        // TODO some examples of use
        SmartAdapters.single(String.class, String.class).listener(new ViewEventListener<MyObject>() {
            @Override
            public void onViewEvent(int actionId, MyObject item, View view) {

            }
        });

    }

}
