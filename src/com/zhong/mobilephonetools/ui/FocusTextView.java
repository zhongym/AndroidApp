package com.zhong.mobilephonetools.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
/**
 * �Զ���һ��view����ֻҪ������Ծ͵�view����
 * @author zhong
 *
 */
public class FocusTextView extends TextView {

	/**
	 * �����view����Ĭ�Ͼͽ���
	 */
	@Override
	public boolean isFocused() {
		return true;
	}
	
	public FocusTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public FocusTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public FocusTextView(Context context) {
		super(context);
	}

}
