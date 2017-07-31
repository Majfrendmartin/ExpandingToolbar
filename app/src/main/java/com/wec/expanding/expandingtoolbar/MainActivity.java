package com.wec.expanding.expandingtoolbar;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

public class MainActivity extends AppCompatActivity {

    public static final int DATASET_SIZE = 5;
    private static final String[] STRING_ARRAY_LIST = new String[]{
            "http://people.kzoo.edu/k11kg03/CS107Web/originalLeopard.jpg",
            "http://wfiles.brothersoft.com/e6/android_189017-640x480.jpg",
            "http://www.iceis.pl/640x480/640x480_-_niagarafalls640x480.jpg",
            "http://www.mynetpublish.com/wp-content/uploads/2015/11/green-1397740-640x480.jpg",
            "http://www.dailymobile.net/wp-content/uploads/2009/03/android-wallpapers-640-480-dailymobile030.jpg",
            "http://www.dailymobile.net/wp-content/uploads/wallpapers/android-640x480-wallpapers/android-640x480-wallpaper-70.jpg",
            "http://www.dailymobile.net/wp-content/uploads/2012/06/android-640x480-wallpaper-455.jpg",
            "http://www.dailymobile.net/wp-content/uploads/wallpapers/android-640x480-wallpapers/android-640x480-wallpaper-75.jpg",
            "https://upload.wikimedia.org/wikipedia/commons/1/1d/160604_kew-gardens-waterlily-house_3-640x480.jpg"
    };
    private RecyclerView recyclerView;
    private MyAdapter adapter;

//    {
//        for (int i = 0; i < DATASET_SIZE; i++) {
//            STRING_ARRAY_LIST[i] = "Item " + i;
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);

        final AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        final ImageView header = (ImageView) findViewById(R.id.header);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                final int totalScrollRange = appBarLayout.getTotalScrollRange();
                final float imageAlpha = (float) (totalScrollRange + verticalOffset) / (float) totalScrollRange;
                header.setAlpha(imageAlpha);
            }
        });
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Glide
                .with(MainActivity.this)
                .load("http://www.iceis.pl/640x480/640x480_-_niagarafalls640x480.jpg")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into((ImageView) findViewById(R.id.header));

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
//        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(5), true));

        adapter = new MyAdapter(STRING_ARRAY_LIST);
        recyclerView.setAdapter(adapter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private String[] mDataset;
        private int mExpandedPosition = -1;

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(String[] myDataset) {
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_view_layout, parent, false);
            // set the view's size, margins, paddings and layout parameters
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            final boolean isExpanded = position == mExpandedPosition;
            holder.title.setText("Item " + position);
            holder.setImage(mDataset[position]);

//            holder.details.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
//            holder.itemView.setActivated(isExpanded);
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mExpandedPosition = isExpanded ? -1 : position;
//                    TransitionManager.beginDelayedTransition(recyclerView);
//                    notifyDataSetChanged();
//                }
//            });
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.length;
        }

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder {
            private final View details;
            private final ImageView ivItemImage;
            private final View content;
            public TextView title;
            private String imageUrl;

            public ViewHolder(View v) {
                super(v);
                title = (TextView) itemView.findViewById(R.id.tv_content);
                details = itemView.findViewById(R.id.tv_details);
                content = itemView.findViewById(R.id.cv_content);
                ivItemImage = (ImageView) itemView.findViewById(R.id.iv_item_image);

                content.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final ActivityOptionsCompat activityOptionsCompat =
                                ActivityOptionsCompat.makeSceneTransitionAnimation(
                                        MainActivity.this, ivItemImage, getString(R.string.transition_cover));
                        final Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                        intent.putExtra("url", imageUrl);
                        ActivityCompat.startActivity(MainActivity.this, intent, activityOptionsCompat.toBundle());
                    }
                });

            }

            public void setImage(String url) {
                this.imageUrl = url;
                Glide
                        .with(MainActivity.this)
                        .load(url)
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                ivItemImage.setImageBitmap(resource);
                                Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                                    @Override
                                    public void onGenerated(Palette palette) {
                                        final Palette.Swatch swatch = palette.getLightMutedSwatch();
                                        if (swatch == null) {
                                            return;
                                        }

                                        content.setBackgroundColor(swatch.getRgb());
                                        title.setTextColor(swatch.getTitleTextColor());
                                    }
                                });
                            }
                        });
            }
        }
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
}
