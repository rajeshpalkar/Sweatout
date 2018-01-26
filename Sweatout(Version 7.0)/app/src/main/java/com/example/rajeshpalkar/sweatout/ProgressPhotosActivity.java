package com.example.rajeshpalkar.sweatout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.net.Inet4Address;

public class ProgressPhotosActivity extends AppCompatActivity {

    FloatingActionButton fbutton;
    ImageView photoImage;
    static final int CAM_REQUEST =1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_photos);


        fbutton = (FloatingActionButton) findViewById(R.id.fab);
      //  photoImage = (ImageView)findViewById(R.id.photo);

        fbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = getFile();
                camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(camera_intent,CAM_REQUEST);*/
               Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(takePictureIntent.resolveActivity(getPackageManager())!=null)
                {
                    startActivityForResult(takePictureIntent,CAM_REQUEST);
                }

            }
        });
    }

    private File getFile()
    {
        File folder= new File("sdcard/camera_app");
        if(!folder.exists())
        {
            folder.mkdir();
        }
        File image_file = new File(folder,"cam_image.jpg");
        return image_file;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==CAM_REQUEST && resultCode == RESULT_OK)
        {
            Bundle extra = data.getExtras();
            Bitmap imageBitmap= (Bitmap) extra.get("data");
            photoImage.setImageBitmap(imageBitmap);
        }
    }

    /*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String path = "sdcard/camera_app/cam_image.jpg";
        photoImage.setImageDrawable(Drawable.createFromPath(path));

    }*/
}
