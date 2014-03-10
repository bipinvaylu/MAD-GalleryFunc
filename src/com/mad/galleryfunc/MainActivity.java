package com.mad.galleryfunc;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MainActivity extends Activity
{

	public static final int REQ_SELECT_PICTURE = 10;
	public static final int REQ_TAKE_PICTURE = 20;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findViewById(R.id.selectPic).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQ_SELECT_PICTURE);
			}
		});

		findViewById(R.id.takePic).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(cameraIntent, REQ_TAKE_PICTURE);
			}
		});
	}

	Bitmap bmp;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK && data != null) {
			ImageView imageView = (ImageView) findViewById(R.id.picture);
			if(requestCode == REQ_SELECT_PICTURE) {
				try {
					imageView.setImageURI(data.getData());
				} catch(Exception e) {
					e.printStackTrace();
					imageView.setImageResource(R.drawable.no_image_error);
				}
			} else if(requestCode == REQ_TAKE_PICTURE) {
				try {
					Bitmap photo = (Bitmap) data.getExtras().get("data");
					imageView.setImageBitmap(photo);
				} catch(Exception e) {
					e.printStackTrace();
					imageView.setImageResource(R.drawable.no_image_error);
				}
			}

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
