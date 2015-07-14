package cn.edu.cqu.netmusicplayer;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.edu.cqu.musicplayer.R;

public class MainActivity extends Activity {
	private EditText et_path;
	private SurfaceView sv;
	private MediaPlayer mediaPlayer;
	private Button bt_play,bt_pause,bt_stop,bt_replay;
	private SurfaceHolder holder;
	private String filepath;
	private File path;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		et_path = (EditText) findViewById(R.id.et_path);
		bt_play = (Button) findViewById(R.id.play);
		bt_pause = (Button) findViewById(R.id.pause);
		bt_stop = (Button) findViewById(R.id.stop);
		bt_replay = (Button) findViewById(R.id.replay);
		sv = (SurfaceView) findViewById(R.id.sv);
		
//		filepath = et_path.getText().toString().trim();
		holder = sv.getHolder();
		holder.addCallback(new Callback() {
			
			private int position;

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				position = mediaPlayer.getCurrentPosition();
				if (mediaPlayer != null && mediaPlayer.isPlaying()) {
					mediaPlayer.stop();
					mediaPlayer.release();
					mediaPlayer = null;
				}
			}
			
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				// ��С�������´���
				if (position > 0) {
					try {
						mediaPlayer = new MediaPlayer();
						mediaPlayer.setDataSource(filepath);//���ò��ŵ�����Դ��
						mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
						mediaPlayer.prepare();// ͬ����׼���������������û��׼��������Ĳ���ִ��
						mediaPlayer.setDisplay(holder);
						mediaPlayer.seekTo(position);
						mediaPlayer.start();
						bt_play.setEnabled(false);
						mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
							@Override
							public void onCompletion(MediaPlayer mp) {
								bt_play.setEnabled(true);
							}
						});
					} catch (Exception e) {
						e.printStackTrace();
						Toast.makeText(getApplicationContext(), "����ʧ��", Toast.LENGTH_SHORT).show();
					}
				}
			}
			
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width,
					int height) {
				// ��Ļ�����仯��ת��
				
			}
		});
	}
	/**
	 * ����
	 * @param view
	 */
	public void play(View v) {
		filepath = et_path.getText().toString().trim();
//		path = Environment.getExternalStorageDirectory();
//		File file = new File(path + "/" + filepath);
		// ����ļ�����
		if(filepath.startsWith("http://")){
			try {
				mediaPlayer = new MediaPlayer();
				mediaPlayer.setDataSource(filepath);//���ò��ŵ�����Դ��
				mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
				mediaPlayer.prepare();// ͬ����׼���������������û��׼��������Ĳ���ִ��
				mediaPlayer.setDisplay(holder);
				mediaPlayer.start();
				bt_play.setEnabled(false);
				mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
					@Override
					public void onCompletion(MediaPlayer mp) {
						bt_play.setEnabled(true);
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(this, "����ʧ��", 0).show();
			}
		}else{
			Toast.makeText(this, "�ļ������ڣ������ļ���·��", 0).show();
		}
	}
	/**
	 * ��ͣ
	 * @param view
	 */
	public void pause(View v) {
		if("����".equals(bt_pause.getText().toString())){
			mediaPlayer.start();
			bt_pause.setText("��ͣ");
			return;
		}
		if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
			mediaPlayer.pause();
			bt_pause.setText("����");
		}
	}
	/**
	 * ֹͣ
	 * @param view
	 */
	public void stop(View v) {
		if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}
		bt_pause.setText("��ͣ");
		bt_play.setEnabled(true);
	}
	/**
	 * �ز�
	 * @param view
	 */
	public void replay(View v) {
		if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
			mediaPlayer.seekTo(0);
		}else{
			play(v);
		}
		bt_pause.setText("��ͣ");
	}

}
