package algonquin.cst2335.f4;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

public class DetailFragment extends Fragment {

    private String imageUrl;
    private int imageWidth;
    private int imageHeight;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize your views
        ImageView detailImageView = view.findViewById(R.id.detailImageView);
        TextView detailTextWidth = view.findViewById(R.id.detailTextWidth);
        TextView detailTextHeight = view.findViewById(R.id.detailTextHeight);

        // Retrieve the data passed to the fragment
        if (getArguments() != null) {
            imageUrl = getArguments().getString("imageUrl");
            imageWidth = getArguments().getInt("imageWidth");
            imageHeight = getArguments().getInt("imageHeight");
        }

        // Display details using Glide
        Glide.with(this)
                .load(imageUrl)
                .into(detailImageView);

        detailTextWidth.setText("Width: " + imageWidth);
        detailTextHeight.setText("Height: " + imageHeight);
    }

    public static DetailFragment newInstance(String imageUrl, int width, int height) {
        DetailFragment fragment = new DetailFragment();

        Bundle args = new Bundle();
        args.putString("imageUrl", imageUrl);
        args.putInt("imageWidth", width);
        args.putInt("imageHeight", height);
        fragment.setArguments(args);

        return fragment;
    }
}
