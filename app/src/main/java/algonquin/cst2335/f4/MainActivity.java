package algonquin.cst2335.f4;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

        generateImageButton.setOnClickListener(v -> {
            // TODO: Implement the logic to generate the image based on width and height
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            // TODO: Implement the delete logic
            return true;
        }
        return super.onOptionsItemSelected(item);
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
