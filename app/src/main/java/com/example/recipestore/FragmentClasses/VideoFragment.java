package com.example.recipestore.FragmentClasses;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.recipestore.AllActivities.Constants;
import com.example.recipestore.InterfaceClasses.SelectedProductsInterface;
import com.example.recipestore.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class VideoFragment extends Fragment {
    private YouTubePlayerSupportFragment youTubePlayerSupportFragment;
    private String mId;
    private ImageView back;
    private SelectedProductsInterface selectedProductsInterface;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null){
            mId = bundle.getString("ID");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_video, container, false);
        back = (ImageView) v.findViewById(R.id.imgBackVid);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedProductsInterface.onBackPressed();
            }
        });
        getData();
        return v;
    }
    private String getYouTubeId(String youTubeUrl) {
        String pattern = "https?://(?:[0-9A-Z-]+\\.)?(?:youtu\\.be/|youtube\\.com\\S*[^\\w\\-\\s])([\\w\\-]{11})(?=[^\\w\\-]|$)(?![?=&+%\\w]*(?:['\"][^<>]*>|</a>))[?=&+%\\w]*";

        Pattern compiledPattern = Pattern.compile(pattern,
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = compiledPattern.matcher(youTubeUrl);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private void getData() {
        String convId = getYouTubeId(mId);
        youTubePlayerSupportFragment = new YouTubePlayerSupportFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameVideo, youTubePlayerSupportFragment);
        fragmentTransaction.commit();
        youTubePlayerSupportFragment.initialize(Constants.api_key, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.cueVideo(convId);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        selectedProductsInterface = (SelectedProductsInterface) context;
    }
}
