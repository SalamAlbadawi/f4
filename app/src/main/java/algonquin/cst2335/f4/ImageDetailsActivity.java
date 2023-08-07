package algonquin.cst2335.f4;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ImageDetailsActivity extends AppCompatActivity {

    ImageView detailImageView;
    TextView detailTextWidth, detailTextHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);

        detailImageView = findViewById(R.id.detailImageView);
        detailTextWidth = findViewById(R.id.detailTextWidth);
        detailTextHeight = findViewById(R.id.detailTextHeight);

        // Retrieve extras
        String imageUrl = getIntent().getStringExtra("imageURL");
        int imageWidth = getIntent().getIntExtra("imageWidth", 0);
        int imageHeight = getIntent().getIntExtra("imageHeight", 0);

        // Display details
        Glide.with(this)
                .load(imageUrl)
                .into(detailImageView);

        detailTextWidth.setText("Width: " + imageWidth);
        detailTextHeight.setText("Height: " + imageHeight);
    }
}
