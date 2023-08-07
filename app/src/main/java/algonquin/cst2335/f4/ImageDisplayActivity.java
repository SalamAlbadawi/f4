package algonquin.cst2335.f4;


import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ImageDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);

        ImageView imageView = findViewById(R.id.displayImageView);
        String imageUrl = getIntent().getStringExtra("IMAGE_URL");

        // Use Glide or any other image loading library to display the image
        Glide.with(this)
                .load(imageUrl)
                .into(imageView);
    }
}
