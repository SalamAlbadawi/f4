package algonquin.cst2335.f4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ImageActivity extends AppCompatActivity {

    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        String imageUrl = getIntent().getStringExtra("IMAGE_URL");

        // Initialize the ImageView and load the image using Glide
        ImageView imageView = findViewById(R.id.imageView);
        Glide.with(this)
                .load(imageUrl)
                .error(R.drawable.ic_launcher_background)
                .into(imageView);

        // Initialize and set up the Save button
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(ImageActivity.this);
            builder.setMessage("Do you want to save the image?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Save the image to the database in the background
                            new SaveImageTask().execute(imageUrl);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        });

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            // Logic to go back
            finish();
        });

        Button showListButton = findViewById(R.id.showListButton);
        showListButton.setOnClickListener(v -> {
            // Logic to show list
        });
    }

    private class SaveImageTask extends AsyncTask<String, Void, Long> {

        @Override
        protected Long doInBackground(String... params) {
            String imageUrl = params[0];
            appDatabase = AppDatabase.getDatabase(ImageActivity.this);

            Bitmap imageBitmap = downloadImage(imageUrl); // Download the image using Glide
            int width = 0;
            int height = 0;

            if (imageBitmap != null) {
                width = imageBitmap.getWidth();
                height = imageBitmap.getHeight();
            }

            ImageEntity image = new ImageEntity();
            image.setImageUrl(imageUrl);
            image.setWidth(width);
            image.setHeight(height);

            // Insert the image into the database and return the row ID
            return appDatabase.imageDao().insertImage(image);
        }

        @Override
        protected void onPostExecute(Long insertedRowId) {
            super.onPostExecute(insertedRowId);

            if (insertedRowId > 0) {
                // Image was saved successfully
                Toast.makeText(ImageActivity.this, "Image saved successfully.", Toast.LENGTH_SHORT).show();
            } else {
                // Image insertion failed
                Toast.makeText(ImageActivity.this, "Failed to save image.", Toast.LENGTH_SHORT).show();
            }
        }

        private Bitmap downloadImage(String imageUrl) {
            try {
                return Glide.with(ImageActivity.this)
                        .asBitmap()
                        .load(imageUrl)
                        .submit()
                        .get(); // Synchronously get the downloaded bitmap
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}