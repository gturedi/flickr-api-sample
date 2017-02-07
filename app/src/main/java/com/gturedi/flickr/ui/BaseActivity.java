package com.gturedi.flickr.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.gturedi.flickr.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by gturedi on 7.02.2017.
 */
public abstract class BaseActivity
        extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    public abstract int getLayout();

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

    protected void dismissLoadingDialog() {
        if (progressDialog == null) return;
        progressDialog.dismiss();
    }

    protected void showLoadingDialog() {
        // yanlislikla ust uste cagrilabilir
        if (progressDialog != null) {
            progressDialog.show();
            return;
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    protected void showGeneralError() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.errorTitle)
                .setMessage(R.string.errorMessage)
                .setNegativeButton(R.string.close, null)
                .show();
    }

    protected void showConnectionError() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.errorTitle)
                .setMessage(R.string.connectionErrorMessage)
                .setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }

}