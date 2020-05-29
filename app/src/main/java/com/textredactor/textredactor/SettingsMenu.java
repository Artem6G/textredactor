package com.textredactor.textredactor;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

class SettingsMenu {

    private MainActivity mainActivity;
    private PopupMenu settingsPopupMenu;

    SettingsMenu(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    void showSettingsMenu(View v) {
        settingsPopupMenu = new PopupMenu(mainActivity, v);
        settingsPopupMenu.inflate(R.menu.settings_menu);

        settingsPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.open_settings_settings_menu) {
                    Intent intent = new Intent(mainActivity, SettingActivity.class);
                    mainActivity.startActivity(intent);
                    return true;
                }
                return true;
            }
        });

        settingsPopupMenu.show();
    }
}
