package com.gturedi.flickr.ui;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gturedi.flickr.R;
import com.gturedi.flickr.util.AppUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by gturedi on 7.02.2017.
 */
public abstract class AbstractBaseActivity
        extends AppCompatActivity {

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    protected abstract int getLayout();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Timber.i("onCreate");
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    protected void showInfoDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setIcon(R.drawable.ic_info_outline_24dp)
                .setMessage(message)
                .setNegativeButton(R.string.close, null)
                .show();
    }

    protected void showSnack(@StringRes int msgId) {
        AppUtil.createSnackbar(this, msgId).show();
    }

    protected void showConnectionError() {
        AppUtil.createSnackbar(this, R.string.connectionErrorMessage)
                .setAction(R.string.settings, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                }).show();
    }

}