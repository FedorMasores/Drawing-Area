package com.drawingarea.appdrawareamasoresogfrtyub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.drawingarea.appdrawareamasoresogfrtyub.display.current_brush;
import static com.drawingarea.appdrawareamasoresogfrtyub.display.colorList;
import static com.drawingarea.appdrawareamasoresogfrtyub.display.pathList;


public class MainActivity extends AppCompatActivity {

    ImageButton imageButtonPaint;
    ImageButton imageButtonLastic;
    display display;

    public static Path path = new Path();
    public static Paint paint_brush = new Paint();

    @SuppressLint({"UseCompatLoadingForDrawables", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.app_bar));

        imageButtonPaint = findViewById(R.id.imageButtonPaint);
        imageButtonLastic = findViewById(R.id.imageButtonLastic);
        display = new display(MainActivity.this);

        imageButtonPaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paint_brush.setColor(Color.BLACK);
                currentColor(paint_brush.getColor());
            }
        });

        imageButtonLastic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paint_brush.setColor(Color.WHITE);
                currentColor(paint_brush.getColor());
            }
        });


    }

    public  void currentColor(int c) {
        current_brush = c;
        path = new Path();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clear:
                pathList.clear();
                path.reset();
                colorList.clear();
                paint_brush.setColor(Color.BLACK);
                currentColor(paint_brush.getColor());
                break;
            case R.id.save:
//                View content = display;
//                content.setDrawingCacheEnabled(true);
//                content.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
////                Bitmap bitmap = Bitmap.createBitmap(120, 300, Bitmap.Config.ARGB_8888);
//                Bitmap bitmap = content.getDrawingCache();
//                String path = this.getExternalFilesDir("/").getAbsolutePath();
//                File file = new File(path + "/image.jpeg");
//                FileOutputStream ostream;
//                try {
//                    file.createNewFile();
//                    ostream = new FileOutputStream(file);
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
//                    ostream.flush();
//                    ostream.close();
//                    Toast.makeText(getApplicationContext(), "image saved", Toast.LENGTH_SHORT).show();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
//                }

                View content = display;
                Bitmap bitmap = Bitmap.createBitmap(405, 810, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                content.draw(canvas);

                File dir = new File(this.getExternalFilesDir("/").getAbsolutePath());
                if (!dir.isDirectory()) {
                    dir.mkdirs();
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.ROOT);
                Date date = new Date();

                File outputFile = new File(dir, "image" + dateFormat.format(date) + ".jpeg");
                OutputStream fout = null;
                try {
                    fout = new FileOutputStream(outputFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fout);
                try {
                    fout.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Toast.makeText(this, "SAVE IT", Toast.LENGTH_SHORT).show();
                break;
            case R.id.back:
                Toast.makeText(this, "Back", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}