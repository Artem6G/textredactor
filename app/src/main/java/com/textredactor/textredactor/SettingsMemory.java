package com.textredactor.textredactor;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;

class SettingsMemory {

    private static SharedPreferences preferences;

    // Color
    static int DocTextColor = Color.WHITE;
    static int BackgroundColor = -14671840;
    static int PathFileColor = -10058614;
    static int FileNameColor = -3488572;
    static int NotOpenFileColor = Color.LTGRAY;
    static int OpenFileColor = -23296;
    static int BottomStripColor = -15132391;
    static int UppedStripColor = -14342875;
    static int CursorColor = -3488572;
    static int HighlightColor = -23296;
    static int SyntaxColor = -23296;

    // Text
    static int textSize = 16;
    static int LineSpacing = 1;
    static int LetterSpacing = 0;
    static String FontLink = null;

    // Other
    static boolean Animate = true;
    static int TimeAnimate = 70;
    static String Syntax = "No syntax";

    static void default_colors() {
        DocTextColor = Color.WHITE;
        BackgroundColor = -14671840;
        PathFileColor = -10058614;
        FileNameColor = -3488572;
        NotOpenFileColor = Color.LTGRAY;
        OpenFileColor = -23296;
        BottomStripColor = -15132391;
        UppedStripColor = -14342875;
        CursorColor = -3488572;
        HighlightColor = -23296;
        SyntaxColor = -23296;
    }

    static void default_text() {
        textSize = 16;
        LineSpacing = 1;
        LetterSpacing = 0;
        FontLink = null;
    }

    static void save_color_settings(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt("DocTextColor", DocTextColor);
        editor.putInt("BackgroundColor", BackgroundColor);
        editor.putInt("PathFileColor", PathFileColor);
        editor.putInt("FileNameColor", FileNameColor);
        editor.putInt("NotOpenFileColor", NotOpenFileColor);
        editor.putInt("OpenFileColor", OpenFileColor);
        editor.putInt("BottomStripColor", BottomStripColor);
        editor.putInt("UppedStripColor", UppedStripColor);
        editor.putInt("CursorColor", CursorColor);
        editor.putInt("HighlightColor", HighlightColor);
        editor.putInt("SyntaxColor", SyntaxColor);

        editor.apply();
    }

    static void save_text_settings(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt("TextSize", textSize);
        editor.putInt("LineSpacing", LineSpacing);
        editor.putInt("LetterSpacing", LetterSpacing);
        editor.putString("FontLink", FontLink);

        editor.apply();
    }

    static void save_other_settings(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean("Animate", Animate);
        editor.putInt("TimeAnimate", TimeAnimate);
        editor.putString("Syntax", Syntax);

        editor.apply();
    }

    static void read_settings(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        DocTextColor = preferences.getInt("DocTextColor", DocTextColor);
        BackgroundColor = preferences.getInt("BackgroundColor", BackgroundColor);
        PathFileColor = preferences.getInt("PathFileColor", PathFileColor);
        FileNameColor = preferences.getInt("FileNameColor", FileNameColor);
        NotOpenFileColor = preferences.getInt("NotOpenFileColor", NotOpenFileColor);
        OpenFileColor = preferences.getInt("OpenFileColor", OpenFileColor);
        BottomStripColor = preferences.getInt("BottomStripColor", BottomStripColor);
        UppedStripColor = preferences.getInt("UppedStripColor", UppedStripColor);
        textSize = preferences.getInt("TextSize", textSize);
        CursorColor = preferences.getInt("CursorColor", CursorColor);
        HighlightColor = preferences.getInt("HighlightColor", HighlightColor);
        LineSpacing = preferences.getInt("LineSpacing", LineSpacing);
        LetterSpacing = preferences.getInt("LetterSpacing", LetterSpacing);
        Animate = preferences.getBoolean("Animate", Animate);
        TimeAnimate = preferences.getInt("TimeAnimate", TimeAnimate);
        FontLink = preferences.getString("FontLink", FontLink);
        Syntax = preferences.getString("Syntax", Syntax);
        SyntaxColor = preferences.getInt("SyntaxColor", SyntaxColor);

    }

}
