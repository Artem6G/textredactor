package com.textredactor.textredactor;

import android.os.Handler;
import android.widget.TextView;

class Writer {

    private String sText = "";
    private int index;

    private TextView textView;

    Writer(TextView tView, String sText) {
        if(SettingsMemory.Animate) {
            textView = tView;
            this.sText = sText;
            animateText();
        }
    }

    private void animateText() {
        index = 0;

        textView.setText("");

        new Handler().removeCallbacks(characterAdder);
        new Handler().postDelayed(characterAdder, SettingsMemory.TimeAnimate);
    }

    private Runnable characterAdder = new Runnable() {
        @Override
        public void run() {
            textView.setText(sText.subSequence(0, index++));

            if (index <= sText.length()) {
                new Handler().postDelayed(characterAdder, SettingsMemory.TimeAnimate);
            }
        }
    };
}