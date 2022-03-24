package com.example.parstagram2;

import static com.example.parstagram2.R.drawable.*;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.parstagram2.fragment.ProfileFragment;
import com.parse.Parse;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.text.ParseException;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;


    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_posts, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        try {
            holder.bind(post);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvUsername;
        private TextView tvDescription;
        private ImageView ivImage;
        private ImageView ivProfileImage;
        private TextView tvUsername2;
        private TextView tvtimeDifference;
        private ImageView ivHeart;
        private ImageView ivComment;
        private ImageView ivShare;
        private ImageView ivSave;

        final FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();


        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvprofileUsername);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvUsername2 = itemView.findViewById(R.id.tvUsername2);
            tvtimeDifference = itemView.findViewById(R.id.tvtimeDifference);
            ivHeart = itemView.findViewById(R.id.ivHeart);
            ivComment = itemView.findViewById(R.id.ivComment);
            ivShare = itemView.findViewById(R.id.ivShare);
            ivSave = itemView.findViewById(R.id.ivSave);

            ivHeart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ivHeart.setImageResource(ic_ufi_heart_active);

                }
            });
        }

        public void bind(Post post) throws ParseException {
            tvDescription.setText(post.getDescription());
            tvUsername.setText(post.getUser().getUsername());
            tvUsername2.setText(post.getUser().getUsername());
            tvtimeDifference.setText(post.getDate());
            ParseFile image = post.getImage();
            if(image != null) {
                Glide.with(context).load(post.getImage().getUrl()).into(ivImage);
            }

            Glide.with(context).load(post.getProfileImage().getUrl()).into(ivProfileImage);

            ivProfileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = new ProfileFragment(post.getUser());
                    fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                }
            });

            tvUsername.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = new ProfileFragment(post.getUser());
                    fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                }
            });
        }

    }
    public void clear(){
        posts.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Post> post_list){
        posts.addAll(post_list);
        notifyDataSetChanged();
    }
}
