package com.hali.xiaoyangchun.tonguepicture;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hali.xiaoyangchun.tonguepicture.dao.Manager.UserInfo;
import com.hali.xiaoyangchun.tonguepicture.presenter.MinePresenter;
import com.hali.xiaoyangchun.tonguepicture.ui.activity.SingleFAHelper;
import com.hali.xiaoyangchun.tonguepicture.ui.base.BaseActivity;
import com.hali.xiaoyangchun.tonguepicture.ui.fragment.TongueNoteListFragment;
import com.hali.xiaoyangchun.tonguepicture.utils.FileUtil;
import com.tonguePicture.support.Permission.PermissionHelper;
import com.tonguePicture.support.Permission.PermissionRequestListener;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, MinePresenter.MineCallback, PermissionRequestListener {

    private MinePresenter minePresenter;
    private DrawerLayout drawer;
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews() {
        Toolbar toolbar = findView(R.id.toolbar);
        drawer = findView(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findView(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        TongueNoteListFragment fragment = new TongueNoteListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_main_fragment, fragment).show(fragment).commitNow();
    }

    @Override
    public void initData() {
        minePresenter = new MinePresenter();
        minePresenter.attach(this);
        minePresenter.setCallback(this);
        drawer.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                String path = UserInfo.getInstance(MainActivity.this).getHeadImg();
                if (!TextUtils.isEmpty(path)) {
                    ImageView iv = drawer.findViewById(R.id.iv_head);
                    Glide.with(MainActivity.this).load(path).into(iv);
                }
                String nick = UserInfo.getInstance(MainActivity.this).getNick();
                if (!TextUtils.isEmpty(nick)) {
                    TextView tvNick = drawer.findViewById(R.id.tv_nick);
                    tvNick.setText(nick);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode,data);
        minePresenter.onActivityResult(requestCode, resultCode,data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            if (PermissionHelper.hasCameraPermissions(this)) {
                SingleFAHelper.INSTANCE.gotoCameraFragment(this);
            } else {
                PermissionHelper.requestCameraPermission(this, this);
            }
        } else if (id == R.id.nav_gallery) {
            if (PermissionHelper.hasStoragePermissions(this)) {
                minePresenter.gotoPhoto();
            } else {
                PermissionHelper.requestStoragePermission(this, this);
            }
        } else if (id == R.id.nav_slideshow) {
            minePresenter.changeNick();
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onImgPicked(Uri uri) {
        ImageView iv = findView(R.id.iv_head);
        String path = FileUtil.INSTANCE.getRealFilePathFromUri(this, uri);
        UserInfo.getInstance(this).setHeadImg(path);
        Glide.with(this).load(path).into(iv);
        Toast.makeText(this, "修改成功!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNickChange(String nick) {
        TextView tvNick = findView(R.id.tv_nick);
        tvNick.setText(nick);
    }

    @Override
    public void onGranted(Activity context, int requestCode) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS_CAMERA:
                SingleFAHelper.INSTANCE.gotoCameraFragment(this);
                break;
            case REQUEST_CODE_ASK_PERMISSIONS_STORAGE:
                minePresenter.gotoPhoto();
                break;
        }
    }

    @Override
    public void onAsked(Activity context, int requestCode) {

    }

    @Override
    public void onDenied(Activity context, int requestCode) {

    }
}
