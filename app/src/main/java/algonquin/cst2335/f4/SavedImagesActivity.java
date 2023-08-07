package algonquin.cst2335.f4;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SavedImagesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_images);

        appDatabase = AppDatabase.getDatabase(this);

        recyclerView = findViewById(R.id.recyclerView);
        imageAdapter = new ImageAdapter(new ArrayList<>());
        recyclerView.setAdapter(imageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch the list of saved images from the database and update the adapter
        new FetchSavedImagesTask().execute();
    }

    private class FetchSavedImagesTask extends AsyncTask<Void, Void, List<ImageEntity>> {
        @Override
        protected List<ImageEntity> doInBackground(Void... voids) {
            // Query the database to get the list of saved images
            return appDatabase.imageDao().getAllImages();
        }

        @Override
        protected void onPostExecute(List<ImageEntity> savedImages) {
            super.onPostExecute(savedImages);
            // Update the adapter with the fetched list of saved images
            imageAdapter.setImageList(savedImages);
        }
    }
    // Method to fetch the list of saved images from the database
    private List<ImageEntity> getListOfSavedImages() {
        // Initialize the database instance
        AppDatabase appDatabase = AppDatabase.getDatabase(this);

        // Fetch the list of saved images from the database
        return appDatabase.imageDao().getAllImages();
    }

}
