package com.developer.smmmousavi.imageprocessing.util;


import android.graphics.Typeface;

import com.developer.smmmousavi.imageprocessing.application.BaseApplication;


public class FontUtils {

    public static final String IRAN_SANS = "fonts/iran_sans.ttf";
    public static final String IRAN_SANS_BOLD = "fonts/iran_sans_bold.ttf";
    public static final String IRAN_SANS_MOBILE_BOLD = "fonts/iran_sans_mobile_bold.ttf";
    public static final String IRAN_YEKAN_FA_NUM = "fonts/iran_yekan_reqular_mobile_fa_num.ttf";
    public static final String ROBOT_BOLD = "fonts/roboto_bold.ttf";
    public static final String ROBOT_CONDENSED_REGULAR = "fonts/roboto_condensed_regular.ttf";
    public static final String ROBOT_LIGHT = "fonts/roboto_light.ttf";
    public static final String ROBOT_MEDIUM = "fonts/roboto_medium.ttf";
    public static final String ROBOT_REGULAR = "fonts/roboto_regular.ttf";

    public static Typeface getFont(String fontPath) {
        Typeface font = Typeface.createFromAsset(BaseApplication.getContext().getAssets(), fontPath);
        return font;
    }
}
