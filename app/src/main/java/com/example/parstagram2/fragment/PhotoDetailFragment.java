package com.example.parstagram2.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.parstagram2.Post;
import com.example.parstagram2.R;
import com.parse.Parse;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.text.ParseException;

public class PhotoDetailFragment extends Fragment {

    Post clickedUser;
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

    public PhotoDetailFragment() {
        // Required empty public constructor
    }

    public PhotoDetailFragment(Post clickedUser){
        this.clickedUser = clickedUser;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.item_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvUsername = view.findViewById(R.id.tvprofileUsername);
        ivImage = view.findViewById(R.id.ivImage);
        tvDescription = view.findViewById(R.id.tvDescription);
        ivProfileImage = view.findViewById(R.id.ivProfileImage);
        tvUsername2 = view.findViewById(R.id.tvUsername2);
        tvtimeDifference = view.findViewById(R.id.tvtimeDifference);
        ivHeart = view.findViewById(R.id.ivHeart);
        ivComment = view.findViewById(R.id.ivComment);
        ivShare = view.findViewById(R.id.ivShare);
        ivSave = view.findViewById(R.id.ivSave);

        tvDescription.setText(clickedUser.getDescription());
        tvUsername.setText(clickedUser.getUser().getUsername());
        tvUsername2.setText(clickedUser.getUser().getUsername());
        try {
            tvtimeDifference.setText(clickedUser.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ParseFile image = clickedUser.getImage();
        if(image != null) {
            Glide.with(getContext()).load(clickedUser.getImage().getUrl()).into(ivImage);
        }
        Glide.with(getContext()).load(clickedUser.getProfileImage().getUrl()).into(ivProfileImage);
    }
}