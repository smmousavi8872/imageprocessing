package com.developer.smmmousavi.imageprocessing.ui.fragment.alertdialog.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import com.developer.smmmousavi.imageprocessing.R;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.DialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseAlertDialogFragment extends DialogFragment
    implements OnDialogButtonsListener {

    @BindView(R.id.txtDilaogTitle)
    AppCompatTextView mTxtDialogTitle;
    @BindView(R.id.txtDilaogMessage)
    AppCompatTextView mTxtDialogMessage;
    @BindView(R.id.txtPositiveButton)
    AppCompatTextView mBtnPositive;
    @BindView(R.id.txtNegetiveButton)
    AppCompatTextView mBtnNegetive;
    @BindView(R.id.flDialogContentContainer)
    LinearLayout mDialogContentContaienr;

    protected View mMainView;

    public abstract View getDialogContentView();

    public abstract String getMessageTitle();

    public abstract String getMessageText();

    public abstract String getPositiveButtonText();

    public abstract String getNegitiveButtonText();

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mMainView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_alert_dialog, null, false);
        ButterKnife.bind(this, mMainView);
        setViews();

        return new AlertDialog.Builder(getActivity())
            .setView(mMainView)
            .create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        hideDefualtBackground();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void setViews() {
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/roboto_medium.ttf");
        LinearLayout.LayoutParams flp =
            new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mDialogContentContaienr.addView(getDialogContentView(), flp);
        mTxtDialogTitle.setText(getMessageTitle());
        mTxtDialogMessage.setText(getMessageText());
        mBtnPositive.setText(getPositiveButtonText());
        mBtnNegetive.setText(getNegitiveButtonText());
        mTxtDialogTitle.setTypeface(tf);

        mBtnPositive.setOnClickListener(this::onPositiveButtonClick);

        mBtnNegetive.setOnClickListener(this::onNegetiveButtonClick);
    }

    private void hideDefualtBackground() {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
    }
}
