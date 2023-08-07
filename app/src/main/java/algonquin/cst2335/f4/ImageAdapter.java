package algonquin.cst2335.f4;

import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private List<ImageEntity> imageList;

    public ImageAdapter(List<ImageEntity> imageList) {
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_saved_image, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageEntity image = imageList.get(position);
        holder.textWidth.setText("Width: " + image.getWidth());
        holder.textHeight.setText("Height: " + image.getHeight());
        Glide.with(holder.imageView.getContext()).load(image.getUrl()).into(holder.imageView);

        // Delete button click listener
        holder.deleteButton.setOnClickListener(v -> {
            // Delete from local list
            imageList.remove(position);
            notifyItemRemoved(position);

            // Delete from database using the async task
            AppDatabase database = AppDatabase.getDatabase(v.getContext());
            new DeleteImageTask(database.imageDao()).execute(image);
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    // Update the list of images and notify the adapter
    public void setImageList(List<ImageEntity> newImageList) {
        imageList = newImageList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textWidth;
        TextView textHeight;
        Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textWidth = itemView.findViewById(R.id.textWidth);
            textHeight = itemView.findViewById(R.id.textHeight);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }

    // Nested static class for async task
    private static class DeleteImageTask extends AsyncTask<ImageEntity, Void, Void> {
        private ImageDao imageDao;

        DeleteImageTask(ImageDao dao) {
            imageDao = dao;
        }

        @Override
        protected Void doInBackground(ImageEntity... images) {
            imageDao.deleteImage(images[0]);
            return null;
        }
    }
}
