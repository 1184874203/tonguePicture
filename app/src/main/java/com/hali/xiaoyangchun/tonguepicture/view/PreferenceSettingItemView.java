package com.hali.xiaoyangchun.tonguepicture.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hali.xiaoyangchun.tonguepicture.R;

public class PreferenceSettingItemView extends RelativeLayout {

    private ImageView mViewIcon;
    private TextView mViewTitle;
    private TextView mViewAddition;
    private ImageView mViewArrow;

    private int mIconResid;
    private String mTitle;
    private String mAdditionHint;
    private boolean mShowAddition;
    private boolean mShowArrow;
    private int mTitleStyle;

    public PreferenceSettingItemView(Context context) {
        super(context);
        initView(context);
    }

    public PreferenceSettingItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
        initAttrs(context, attrs);
    }

    public PreferenceSettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initAttrs(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mViewIcon = findViewById(R.id.icon);
        mViewTitle = findViewById(R.id.title);
        mViewAddition = findViewById(R.id.addition);
        mViewArrow = findViewById(R.id.iv_arrow);
        setParams();
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.setting_pref_item, this);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typed = context.obtainStyledAttributes(attrs,
                R.styleable.SettingItem);
        try {
            mTitle = typed.getString(R.styleable.SettingItem_title);
            mIconResid = typed.getResourceId(R.styleable.SettingItem_itemIcon, 0);
            mShowAddition = typed.getBoolean(R.styleable.SettingItem_showAddition, false);
            mShowArrow = typed.getBoolean(R.styleable.SettingItem_showArrow, true);
            mAdditionHint = typed.getString(R.styleable.SettingItem_additionHint);
            mTitleStyle = typed.getResourceId(R.styleable.SettingItem_titleStyle, 0);
        } finally {
            typed.recycle();
        }
    }

    private void setParams() {
        mViewTitle.setText(mTitle);
        mViewIcon.setImageResource(mIconResid);
        mViewAddition.setText(mAdditionHint);
        if (mShowAddition) {
            mViewAddition.setVisibility(View.VISIBLE);
        } else {
            mViewAddition.setVisibility(View.GONE);
        }
        if (mTitleStyle != 0) {
            mViewTitle.setTextAppearance(getContext(), mTitleStyle);
        }
        mViewArrow.setVisibility(mShowArrow ? View.VISIBLE : View.INVISIBLE);

    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
        setParams();
    }

    public boolean ismShowAddition() {
        return mShowAddition;
    }

    public void setShowArrow(boolean showAddition) {
        this.mShowAddition = showAddition;
        setParams();
    }

    public void setIcon(int id) {
        mViewIcon.setImageResource(id);
    }

    public void setTextAddition(String textAddition) {
        mViewAddition.setText(textAddition);
    }

    /**
     * 用于设置红色文字
     *
     * @param textAddition 文字内容
     */
    public void setRedTextAddition(String textAddition) {
        mViewAddition.setTextColor(getResources().getColor(R.color.coupon_addition_text));
        mViewAddition.setText(textAddition);
    }
}
