package com.hali.xiaoyangchun.tonguepicture;


import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.hali.xiaoyangchun.tonguepicture.ui.fragment.TongueNoteListFragment;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TongueNoteListFragment fragment = new TongueNoteListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_main_fragment, fragment).show(fragment).commitNow();
    }

}
