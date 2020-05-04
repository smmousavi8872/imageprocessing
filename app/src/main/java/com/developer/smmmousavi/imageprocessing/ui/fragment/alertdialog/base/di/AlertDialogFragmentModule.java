package com.developer.smmmousavi.imageprocessing.ui.fragment.alertdialog.base.di;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.developer.smmmousavi.imageprocessing.R;

import dagger.Module;
import dagger.Provides;

@Module
public class AlertDialogFragmentModule {

    @Provides
    public View provideAlertDialogView(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.fragment_alert_dialog, null, false);
    }

    @Provides
    public Dialog provideAlertDialog(Context context, View view) {
        return new AlertDialog.Builder(context)
            .setView(view)
            .create();

    }
}
