package algonquin.cst2335.f4;


import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity; // Ensure you import the necessary classes

import com.bumptech.glide.Glide;

public class ImageActivity extends AppCompatActivity { // Extend AppCompatActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        String imageUrl = getIntent().getStringExtra("IMAGE_URL");
        // ... rest of your initialization logic
// Initialize the ImageView and load the image using Glide
        ImageView imageView = findViewById(R.id.imageView); // Assuming your ImageView has the id "imageView"
        Glide.with(this)
                .load(imageUrl)
                .error(R.drawable.ic_launcher_background)  // This will display if there's an error loading the imageUrl
                .into(imageView);

        // Initialize and set up the Save button


        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> {
            // Logic to save
        });

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            // Logic to go back
            finish();  // This will close the current activity and return to the previous one
        });

        Button showListButton = findViewById(R.id.showListButton);
        showListButton.setOnClickListener(v -> {
            // Logic to show list
        });

    }
}























