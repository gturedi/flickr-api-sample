package com.gturedi.flickr.ui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.gturedi.flickr.BuildConfig;
import com.gturedi.flickr.R;
import com.gturedi.flickr.adapter.PhotoAdapter;
import com.gturedi.flickr.model.PhotoModel;
import com.gturedi.flickr.model.event.SearchEvent;
import com.gturedi.flickr.service.FlickrService;
import com.gturedi.flickr.util.AppUtil;
import com.gturedi.flickr.util.RowClickListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import timber.log.Timber;

public class MainActivity
        extends BaseActivity
        implements RowClickListener<PhotoModel>,
        SwipeRefreshLayout.OnRefreshListener, NavigationView.OnNavigationItemSelectedListener {

    private FlickrService flickrService = FlickrService.INSTANCE;
    private PhotoAdapter adapter;
    private boolean isLoading;
    private int page = 1;

    @BindView(R.id.drawer) protected DrawerLayout drawer;
    @BindView(R.id.navigation) protected NavigationView navigation;
    @BindView(R.id.toolbar) protected Toolbar toolbar;
    @BindView(R.id.swipe) protected SwipeRefreshLayout swipe;
    @BindView(R.id.recycler) protected RecyclerView recycler;

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActionBar() != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_24dp);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        navigation.setNavigationItemSelectedListener(this);
        swipe.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAccent));
        swipe.setOnRefreshListener(this);

        adapter = new PhotoAdapter();
        adapter.setRowClickListener(this);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        setScrollListener();

        navigation.getMenu().findItem(R.id.mnVersion).setTitle(BuildConfig.VERSION_NAME + " (" + BuildConfig.VERSION_CODE + ")");

        sendRequest();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRowClicked(int row, PhotoModel item) {
        startActivity(DetailActivity.createIntent(this, row, adapter.getAll()));
    }

    @Override
    public void onRefresh() {
        page = 1;
        sendRequest();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawer.closeDrawer(GravityCompat.START);
        if (item.getItemId() == R.id.mnFeedback) {
            startActivity(AppUtil.createMailIntent(AppUtil.FEEDBACK_MAIL, getString(R.string.feedbackSubject)));
        } else if (item.getItemId() == R.id.mnAbout) {
            String title = getString(R.string.about);
            String message = getString(R.string.aboutText);
            showInfoDialog(title, message);
        }
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SearchEvent event) {
        //dismissLoadingDialog();
        isLoading = false;
        if (swipe.isRefreshing()) {
            swipe.setRefreshing(false);
            adapter.clear();
        }

        if (event.exception == null) {
            //remove progress item
            adapter.remove(adapter.getItemCount() - 1);
            adapter.addAll(event.item);
        } else {
            showGeneralError();
        }
    }

    private void sendRequest() {
        Timber.i("sendRequest: " + page);
        if (AppUtil.isConnected()) {
            //showLoadingDialog();
            // add null , so the adapter will check view_type and show progress bar at bottom
            adapter.addAll(null);
            isLoading = true;
            flickrService.searchAsync(page++);
        } else {
            showConnectionError();
        }
    }

    private void setScrollListener() {
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                if (!isLoading) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= FlickrService.PAGE_SIZE) {
                        sendRequest();
                    }
                }
            }
        });
    }

}