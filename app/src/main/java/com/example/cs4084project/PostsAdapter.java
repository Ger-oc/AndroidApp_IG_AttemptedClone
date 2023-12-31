package com.example.cs4084project;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    public Context prevContext;
    private ProgressDialog progressDialog;

    // Sets base components of each item in the recyclerview
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView captionText;
        public ImageView imageView;
        public Button locationButton;
        public TextView userText;

        public ViewHolder(View itemView){
            super(itemView);
            captionText = itemView.findViewById(R.id.post_caption);
            userText = itemView.findViewById(R.id.post_user);
            imageView = itemView.findViewById(R.id.post_image);
            locationButton = itemView.findViewById(R.id.location_button);
        }
    }

    private ArrayList<Post> allPosts;

    // Initializes PostsAdapter
    public PostsAdapter(ArrayList<Post> allPosts, Context prevContext){
        this.allPosts = allPosts;
        this.prevContext = prevContext;
    }

    // Instantiates XML contents for each item in the recyclerview
    @Override
    public PostsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View postView = inflater.inflate(R.layout.post_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;
    }

    // Defines the interaction for each position in the recyclerview
    @Override
    public void onBindViewHolder(PostsAdapter.ViewHolder holder, int position){
        Post post = allPosts.get(position);
        TextView captionTextView = holder.captionText;
        TextView userTextView = holder.userText;
        ImageView imageView = holder.imageView;
        Button locationBtn = holder.locationButton;
        imageView.setImageBitmap(post.getImage());
        captionTextView.setText(post.getCaption());
        userTextView.setText(post.getUserNickname());

        // Removes the Location Button
        // if the post doesn't have a location
        if(!post.hasLocation()){
            locationBtn.setVisibility(View.GONE);
        } else {
            locationBtn.setVisibility(View.VISIBLE);
        }

        // OnClick a loading bar appears while
        // displaying the location in a Google Maps fragment
        locationBtn.setOnClickListener(view -> {
            progressDialog = new ProgressDialog(view.getContext());
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading Location...");
            progressDialog.show();
            showMapsFragment(view, post.getLatitude(), post.getLongitude());
        });
    }

    @Override
    public int getItemCount(){
        return allPosts.size();
    }

    // Switches to the Google Maps Fragment to display location
    public void showMapsFragment(View view, double latitude, double longitude){
        MapsFragment fragment5 = new MapsFragment(progressDialog, latitude, longitude);
        FragmentTransaction fragmentTransaction = ((AppCompatActivity)prevContext).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment5, "");
        fragmentTransaction.addToBackStack("Return to Home Screen");
        fragmentTransaction.commit();
    }
}
