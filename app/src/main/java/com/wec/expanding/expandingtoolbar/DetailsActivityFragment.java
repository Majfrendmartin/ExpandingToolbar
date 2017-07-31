package com.wec.expanding.expandingtoolbar;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

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
        final View view = inflater.inflate(R.layout.fragment_details, container, false);
        ivCover = (ImageView) view.findViewById(R.id.iv_cover);
        final String url = getActivity().getIntent().getStringExtra("url");
        Glide
                .with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivCover);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }
}
