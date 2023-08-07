package algonquin.cst2335.f4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        // Inflate the list item layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_saved_image, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind data to the views in the ViewHolder
        ImageEntity image = imageList.get(position);
        holder.textWidth.setText("Width: " + image.getWidth());
        holder.textHeight.setText("Height: " + image.getHeight());
        // Load and display the image using Glide or any other image-loading library
        Glide.with(holder.imageView.getContext())
                .load(image.getUrl())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    // ViewHolder class to hold references to views in the list item layout
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textWidth;
        TextView textHeight;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textWidth = itemView.findViewById(R.id.textWidth);
            textHeight = itemView.findViewById(R.id.textHeight);
        }
    }

    // Method to update the adapter with a new list of images
    public void setImageList(List<ImageEntity> newImageList) {
        imageList = newImageList;
        notifyDataSetChanged();
    }
}
