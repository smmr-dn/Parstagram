package com.example.parstagram2.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.parstagram2.PhotoAdapter;
import com.example.parstagram2.Post;
import com.example.parstagram2.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";
    private ImageView ivProfile;
    private TextView tvprofileUsername;
    private View divider;
    public ParseUser currentUser;
    private RecyclerView rvuserPosts;
    private PhotoAdapter adapter;
    private List<Post> allPhotos;
    private TextView tvBio;
    private TextView tvPosts;
    private TextView tvNum;
    private Button btnEditProfile;
    public ProfileFragment() {
        // Required empty public constructor
    }

    public ProfileFragment(ParseUser currentUser){
        this.currentUser = currentUser;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivProfile = view.findViewById(R.id.ivProfile);
        tvprofileUsername = view.findViewById(R.id.tvprofileUsername);
        divider = view.findViewById(R.id.divider);
        rvuserPosts = view.findViewById(R.id.rvuserPosts);
        tvBio = view.findViewById(R.id.tvBio);
        tvPosts = view.findViewById(R.id.tvPosts);
        tvNum = view.findViewById(R.id.tvNum);
        btnEditProfile = view.findViewById(R.id.btnEditProfile);

        if(ParseUser.getCurrentUser() != currentUser){
            btnEditProfile.setVisibility(View.GONE);
        }
        //Create activity launcher to access gallery
        ActivityResultLauncher<String> imageUri = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                //TODO: create a class to convert from Uri to Bitmap
                //Bitmap bitmap = BitmapFactory.decodeFile(result.toString());
                Bitmap bitmap = null;
                ivProfile.setImageURI(result);
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                byte[] bitmapData = bos.toByteArray();

                ParseFile newPhoto = new ParseFile("profile.jpeg", bitmapData);
                currentUser.put("profilePicture", newPhoto);
                currentUser.saveInBackground();
            }
        });
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageUri.launch("image/*");
            }
        });

        allPhotos = new ArrayList<>();
        adapter = new PhotoAdapter(getContext(), allPhotos);

        rvuserPosts.setAdapter(adapter);

        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        // Attach the layout manager to the recycler view
        rvuserPosts.setLayoutManager(gridLayoutManager);

        tvBio.setText(currentUser.getString("userBio"));

        //currentUser = ParseUser.getCurrentUser();
        tvprofileUsername.setText(currentUser.getUsername());
        Glide.with(getContext()).load(currentUser.getParseFile("profilePicture").getUrl()).into(ivProfile);

        queryPosts();
    }


    protected void queryPosts() {
        // Specify which class to query
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, currentUser);
        //Set limit to 20 posts
        query.setLimit(20);

        query.addDescendingOrder(Post.KEY_CREATED_KEY);

        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                for(Post post : posts){
                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                }
                adapter.clear();
                adapter.addAll(posts);
                Integer num = adapter.getItemCount();
                tvNum.setText(num.toString());
            }
        });
    }
}
