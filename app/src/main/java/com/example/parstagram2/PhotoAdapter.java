package com.example.parstagram2;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.parstagram2.fragment.PhotoDetailFragment;
import com.example.parstagram2.fragment.ProfileFragment;
import com.parse.Parse;
import com.parse.ParseFile;

import java.text.ParseException;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private Context context;
    private List<Post> photos;

    public PhotoAdapter(Context context, List<Post> photos) {
        this.context = context;
        this.photos = photos;
    }

    @NonNull
    @Override
    public PhotoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.profile_grid_photos, parent, false);
        return new PhotoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoAdapter.ViewHolder holder, int position) {
        Post post = photos.get(position);
        try {
            holder.bind(post);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivpostsPhoto;
        final FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivpostsPhoto = itemView.findViewById(R.id.ivpostsPhoto);
        }

        public void bind(Post post) throws ParseException{
            ParseFile image = post.getImage();
            if(image != null){
                Glide.with(context).load(post.getImage().getUrl()).into(ivpostsPhoto);
            }

            ivpostsPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = new PhotoDetailFragment(post);
                    fragmentManager.beginTransaction().add(R.id.flContainer, fragment).addToBackStack(null).commit();
                }
            });
        }
    }
    public void clear(){
        photos.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Post> photo_list){
        photos.addAll(photo_list);
        notifyDataSetChanged();
    }
}
