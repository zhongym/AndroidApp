package com.zhong.mobilephonetools;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.zhong.mobilephonetools.utils.StreamUtiles;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

public class StartupActivity extends Activity {

	protected static final String TAG = "MainActivity";
	/** Message what:������ҳ */
	protected static final int ENTER_HOME = 0;
	/** Message what:���¶Ի��� */
	protected static final int UPDATE_DIALOG = 1;
	/** Message what:������� */
	protected static final int NETWORK_ERROR = 2;
	/** Message what:JSON���� */
	protected static final int JSON_ERROR = 3;

	private TextView tv_main_version;
	
	private String apkurl;
	
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case ENTER_HOME:// ������ҳ
				enterHome();
				break;
			case UPDATE_DIALOG:// ���¶Ի���
				Toast.makeText(getApplicationContext(), "�������", 0).show();
				break;
			case NETWORK_ERROR:// �������
				Toast.makeText(getApplicationContext(), "�������", 0).show();
				enterHome();
				break;
			case JSON_ERROR:// json����
				Toast.makeText(StartupActivity.this, "json����", 0).show();
				enterHome();
				break;
			}
		}

	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_startup);

		tv_main_version = (TextView) findViewById(R.id.tv_main_versionName);
		tv_main_version.setText("�汾" + getVersionName());

		// ������ҳ����Ӹ����䶯��
		AlphaAnimation animation = new AlphaAnimation(0.1f, 1.0f);
		animation.setDuration(1500);// ���ó���ʱ��
		findViewById(R.id.layout_startup).startAnimation(animation);
		checkVersionUpdate();

	}

	/**
	 * ���������ҳ
	 */
	private void enterHome() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();// �رյ�ǰ���ڣ���ֹ�û�����ʱ���ν������
	};

	/**
	 * �����߳��м�����
	 */
	private void checkVersionUpdate() {
		new Thread() {
			public void run() {

				Message message = Message.obtain();
				long startTime = System.currentTimeMillis();
				try {

					URL url = new URL(getString(R.string.updateurl));

					HttpURLConnection conne = (HttpURLConnection) url.openConnection();
					conne.setRequestMethod("GET");
					conne.setReadTimeout(4000);
					int code = conne.getResponseCode();
					if (code == 200) {
						InputStream in = conne.getInputStream();
						// �õ����������ص�json����
						String result = StreamUtiles.formateStream2String(in);
						Log.i(TAG, "�����ɹ�" + result);

						// ����json����
						JSONObject obj = new JSONObject(result);
						String version = (String) obj.get("version");
						String description = obj.getString("description");
						apkurl = obj.getString("apkurl");

						if (getVersionName().endsWith(version)) {// �汾һ�������ø���
							Log.i(TAG, "����Ҫ����");
							message.what = ENTER_HOME;

						} else {// ��Ҫ����
							Log.i(TAG, "��Ҫ����");
							message.what = UPDATE_DIALOG;
						}

					} else {
						Log.i(TAG, "ϵͳ��æ");
						message.what = ENTER_HOME;
					}

				} catch (IOException e) {
					Log.i(TAG, "����ʧ��");
					e.printStackTrace();
					message.what = NETWORK_ERROR;

				} catch (JSONException e) {
					Log.i(TAG, "JSONʧ��");
					e.printStackTrace();
					message.what = JSON_ERROR;

				} finally {

					long endTime = System.currentTimeMillis();
					long dtime = endTime - startTime;
					if (dtime < 2000) {
						try {
							Thread.sleep(2000 - dtime);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

					handler.sendMessage(message);
				}

			};
		}.start();

	}

	/**
	 * ��ȡ�嵥�ļ��еİ汾����
	 * 
	 * @return �汾����
	 */
	private String getVersionName() {
		try {
			PackageManager packageManager = getPackageManager();
			PackageInfo info = packageManager.getPackageInfo(getPackageName(), 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}
