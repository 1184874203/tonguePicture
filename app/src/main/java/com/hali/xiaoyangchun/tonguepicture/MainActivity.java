package com.hali.xiaoyangchun.tonguepicture;

import android.support.v7.widget.Toolbar;

import com.hali.xiaoyangchun.tonguepicture.ui.base.BaseActivity;
import com.hali.xiaoyangchun.tonguepicture.ui.fragment.TongueNoteListFragment;

public class MainActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews() {
        Toolbar toolbar = findView(R.id.common_toolbar);
        initToolBar(toolbar, "舌像档案", false);
        TongueNoteListFragment fragment = new TongueNoteListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_main_fragment, fragment).show(fragment).commitNow();
    }

    @Override
    public void initData() {

    }
}
