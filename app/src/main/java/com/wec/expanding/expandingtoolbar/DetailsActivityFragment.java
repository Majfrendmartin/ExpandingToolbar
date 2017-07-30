package com.wec.expanding.expandingtoolbar;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsActivityFragment extends Fragment {

    private ImageView ivCover;

    public DetailsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ivCover = (ImageView) view.findViewById(R.id.iv_cover);
        Glide
                .with(getActivity().getApplicationContext())
                .load("https://vignette2.wikia.nocookie.net/lotr/images/8/8d/Gandalf-2.jpg/revision/latest?cb=20130209172436")
                .into(ivCover);
    }
}
