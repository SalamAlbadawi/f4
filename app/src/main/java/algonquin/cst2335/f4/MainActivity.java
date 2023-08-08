package algonquin.cst2335.f4;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import algonquin.cst2335.f4.R;

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

        initializeComponents();
        setListeners();
        loadSavedDimensions();
    }

    private void initializeComponents() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        widthEditText = findViewById(R.id.widthEditText);
        heightEditText = findViewById(R.id.heightEditText);
        generateImageButton = findViewById(R.id.generateImageButton);
        imageView = findViewById(R.id.imageView);
    }

    private void setListeners() {
        generateImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateImage();
            }
        });

        widthEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                validateInput(widthEditText);
            }
        });

        heightEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                validateInput(heightEditText);
            }
        });
    }

    private void generateImage() {
        String widthString = widthEditText.getText().toString().trim();
        String heightString = heightEditText.getText().toString().trim();

        int width, height;
        try {
            width = Integer.parseInt(widthString);
            height = Integer.parseInt(heightString);
        } catch (NumberFormatException e) {
            Toast.makeText(MainActivity.this, "Please enter valid numbers.", Toast.LENGTH_SHORT).show();
            return;
        }

        String imageUrl = "https://placebear.com/" + width + "/" + height;
        Intent intent = new Intent(MainActivity.this, ImageActivity.class);
        intent.putExtra("IMAGE_URL", imageUrl);
        startActivity(intent);
    }

    private void loadSavedDimensions() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String savedWidth = settings.getString(WIDTH_KEY, "");
        String savedHeight = settings.getString(HEIGHT_KEY, "");
        widthEditText.setText(savedWidth);
        heightEditText.setText(savedHeight);
    }

    private void saveDimensions() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(WIDTH_KEY, widthEditText.getText().toString());
        editor.putString(HEIGHT_KEY, heightEditText.getText().toString());
        editor.apply();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.help_view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Help Section");
            builder.setMessage("This is the Help section for 'Bear Image'. In this app, you can enter desired width and height values to generate an image. You can view all your saved images and observe their details. Should you require further information or assistance, don't hesitate to reach out to our support.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
            return true;


    }
        else if (id == R.id.about_view) {  // Assuming that the "About" menu item has the id of "about_view"
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("About - Final Project");
            builder.setMessage(
                    "App type: Final Project\n" +
                            "Author: Salam ALbadawi\n" +
                            "Instructor: Adewole Adewumi\n" +
                            "Course Name: CST2335"
            );
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
            return true;
        }else if (id == R.id.showListButton) {
            // Code to handle the show list action.
            // For example: navigate to the activity that shows the list.
            Intent intent = new Intent(this, SavedImagesActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }






    private void clearData() {
        widthEditText.setText("");
        heightEditText.setText("");
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(WIDTH_KEY);
        editor.remove(HEIGHT_KEY);
        editor.apply();
        Toast.makeText(this, "Data cleared", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveDimensions();
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
