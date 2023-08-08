package algonquin.cst2335.f4;

import android.app.AlertDialog;
import android.content.Intent;
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
import com.google.android.material.snackbar.Snackbar;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private List<ImageEntity> imageList;
    private ImageEntity backupImage;
    private int backupPosition;

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
        /// Check if item is expanded


        Glide.with(holder.imageView.getContext()).load(image.getUrl()).into(holder.imageView);

        // Delete button click listener
        holder.deleteButton.setOnClickListener(v -> {
            // Create and show the confirmation dialog
            new AlertDialog.Builder(v.getContext())
                    .setTitle(R.string.delete_confirmation_title)
                    .setMessage(R.string.delete_confirmation_message)
                    .setPositiveButton(R.string.delete_confirmation_positive_button, (dialog, which) -> {
                        // Backup the image for potential UNDO
                        backupImage = image;
                        backupPosition = position;

                        // Delete from local list
                        imageList.remove(position);
                        notifyItemRemoved(position);

                        // Delete from database
                        new DeleteImageTask(AppDatabase.getDatabase(v.getContext()).imageDao()).execute(image);

                        Snackbar.make(v, "Image deleted", Snackbar.LENGTH_LONG)
                                .setAction("UNDO", view -> {
                                    imageList.add(backupPosition, backupImage);
                                    notifyItemInserted(backupPosition);
                                    // Optionally: Re-insert the image into the database
                                }).show();
                    })
                    .setNegativeButton(R.string.delete_confirmation_negative_button, null)
                    .show();
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ImageDetailsActivity.class);
            intent.putExtra("imageURL", image.getUrl());
            intent.putExtra("imageWidth", image.getWidth());
            intent.putExtra("imageHeight", image.getHeight());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

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
           // textWidth = itemView.findViewById(R.id.textWidth);
          //  textHeight = itemView.findViewById(R.id.textHeight);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }

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
