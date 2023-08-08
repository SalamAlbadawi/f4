package algonquin.cst2335.f4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    EditText widthEditText, heightEditText;
    Button generateImageButton;
    ImageView imageView;

    // Constants for SharedPreferences keys
    private static final String PREFS_NAME = "ImageSizePrefs";
    private static final String WIDTH_KEY = "width_key";
    private static final String HEIGHT_KEY = "height_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        widthEditText = findViewById(R.id.widthEditText);
        heightEditText = findViewById(R.id.heightEditText);
        generateImageButton = findViewById(R.id.generateImageButton);
        imageView = findViewById(R.id.imageView);

        generateImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Fetch the user's specified width and height values from the EditText
                String widthString = widthEditText.getText().toString().trim();
                String heightString = heightEditText.getText().toString().trim();

                // Try to parse the width and height values from the EditTexts into integers
                int width, height;
                try {
                    width = Integer.parseInt(widthString);
                    height = Integer.parseInt(heightString);
                } catch (NumberFormatException e) {
                    // If there's an error (e.g., the user didn't enter a number or left an EditText empty),
                    // show a toast notification to alert them and exit the method early
                    Toast.makeText(MainActivity.this, "Please enter valid numbers.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Construct the URL for generating the image using the Placebear API
                String imageUrl = "https://placebear.com/" + width + "/" + height;

                // Create an Intent to navigate to ImageActivity
                Intent intent = new Intent(MainActivity.this, ImageActivity.class);

                // Pass the imageUrl as an extra to ImageActivity so it knows which image to display
                intent.putExtra("IMAGE_URL", imageUrl);

                // Start the ImageActivity
                startActivity(intent);
            }
        });


        widthEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                validateInput(widthEditText);
            }
        });

        heightEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                validateInput(heightEditText);
            }
        });

        // Load saved data from SharedPreferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String savedWidth = settings.getString(WIDTH_KEY, "");
        String savedHeight = settings.getString(HEIGHT_KEY, "");
        widthEditText.setText(savedWidth);
        heightEditText.setText(savedHeight);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }




    @Override
    protected void onPause() {
        super.onPause();

        // Save user input to SharedPreferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(WIDTH_KEY, widthEditText.getText().toString());
        editor.putString(HEIGHT_KEY, heightEditText.getText().toString());
        editor.apply();
    }

    private void validateInput(EditText editText) {
        String text = editText.getText().toString().trim();
        if (!text.isEmpty()) {
            try {
                int value = Integer.parseInt(text);
                if (value <= 0) {
                    editText.setError("Please enter a positive whole number.");
                }
            } catch (NumberFormatException e) {
                editText.setError("Invalid number.");
            }
        }
    }
}
