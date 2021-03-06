package com.zhong.mobilephonetools.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhong.mobilephonetools.R;

public class SettingItemView extends RelativeLayout {

	private TextView tv_content;
	private TextView tv_description;
	private CheckBox cb_status;

	private void iniView(Context context) {
		View.inflate(context, R.layout.setting_item, this);
		tv_content = (TextView) findViewById(R.id.tv_content_setting_item);
		tv_description = (TextView) findViewById(R.id.tv_description_setting_item);
		cb_status = (CheckBox) findViewById(R.id.cb_status);

	}

	public SettingItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		iniView(context);
	}

	public SettingItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		iniView(context);

	}

	public SettingItemView(Context context) {
		super(context);
		iniView(context);

	}

	/**
	 * 校验组合控件是否选中,单选框的状态就是这个自定义组件的状态
	 */
	public boolean isChecked() {
		return cb_status.isChecked();
	}

	/**
	 * 设置组合控件的状态,单选框的状态就是这个自定义组件的状态
	 */
	public void setCheck(boolean b) {
		cb_status.setChecked(b);
	}

	/**
	 * 设置 组合控件的描述信息
	 */
	public void setDescription(String description) {
		tv_description.setText(description);
	}

}
