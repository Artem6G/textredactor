package com.textredactor.textredactor;

import android.app.Dialog;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Spinner;

import com.madrapps.pikolo.ColorPicker;
import com.madrapps.pikolo.listeners.SimpleColorSelectionListener;

import java.io.File;

class TextSettingsMenu {

    private int newColor;
    private ArraySpinner arraySpinnerSyntax;
    private PopupMenu popupMenu;
    private MainActivity mainActivity;
    private Encoding encoding;

    TextSettingsMenu(MainActivity mainActivity) {
        this.mainActivity = mainActivity;

        encoding = new Encoding(mainActivity);
    }

    void showMenu(View v) {

        popupMenu = new PopupMenu(mainActivity, v);
        popupMenu.inflate(R.menu.text_setting_menu);

        checkConditionMenu();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.encoding_text_setting_menu:

                        File file = new File(mainActivity.getFilesDir(), mainActivity.nameFileGetTopLevel.getText().toString());
                        encoding.callDialog(file);

                        return true;

                    case R.id.syntax_text_setting_menu:

                       syntax();

                        return true;
                    default:
                        return false;
                }
            }
        });

        popupMenu.show();
    }

    // Данная функция смотрит на некоторые условия и решает, давать ли пользователь доступ к item или нет
    private void checkConditionMenu() {
        SpannableString textEncoding = new SpannableString(mainActivity.getResources().getString(R.string.encoding_converter));

        if (mainActivity.nameFileGetTopLevel != null && !mainActivity.nameFileGetTopLevel.getText().toString().equals("")) {
            textEncoding.setSpan(new ForegroundColorSpan(Color.argb(255, 255, 136, 0)), 0, textEncoding.length(), 0);

            popupMenu.getMenu().findItem(R.id.encoding_text_setting_menu).setTitle(textEncoding);
            popupMenu.getMenu().findItem(R.id.encoding_text_setting_menu).setEnabled(true);
        } else {
            textEncoding.setSpan(new ForegroundColorSpan(Color.GRAY), 0, textEncoding.length(), 0);

            popupMenu.getMenu().findItem(R.id.encoding_text_setting_menu).setTitle(textEncoding);
            popupMenu.getMenu().findItem(R.id.encoding_text_setting_menu).setEnabled(false);
        }
    }

    private void syntax() {
        final Dialog dialogSyntax = new Dialog(mainActivity);

        dialogSyntax.setContentView(R.layout.syntax_dialog);

        Spinner spinnerSyntax = dialogSyntax.findViewById(R.id.spinner_syntax);
        Button saveSyntax = dialogSyntax.findViewById(R.id.save_button_syntax_dialog);
        final View viewColorSyntax = dialogSyntax.findViewById(R.id.viewSyntaxColor);

        ArrayAdapter<CharSequence> adapterSyntax = ArrayAdapter.createFromResource(mainActivity, R.array.syntax, android.R.layout.simple_spinner_item);
        arraySpinnerSyntax = new ArraySpinner(spinnerSyntax,adapterSyntax);
        arraySpinnerSyntax.selectSpinnerValue(String.valueOf(SettingsMemory.Syntax));

        saveSyntax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsMemory.Syntax = arraySpinnerSyntax.getResultSelected();
                SettingsMemory.save_other_settings(mainActivity);
                new Syntax().fullReplace(mainActivity.docEditText);
                dialogSyntax.cancel();
            }
        });

        viewColorSyntax.setBackgroundColor(SettingsMemory.SyntaxColor);

        viewColorSyntax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialogColorPick = new Dialog(mainActivity);

                newColor = SettingsMemory.SyntaxColor;

                dialogColorPick.setContentView(R.layout.color_pick_dialog);
                dialogColorPick.setCancelable(false);

                ColorPicker colorPicker = dialogColorPick.findViewById(R.id.colorPicker);
                colorPicker.setColor(SettingsMemory.SyntaxColor);

                Button insertButtonColorPickDialog = dialogColorPick.findViewById(R.id.buttonSave);
                insertButtonColorPickDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SettingsMemory.SyntaxColor = newColor;
                        viewColorSyntax.setBackgroundColor(SettingsMemory.SyntaxColor);
                        SettingsMemory.save_color_settings(mainActivity);
                        dialogColorPick.cancel();
                    }
                });

                colorPicker.setColorSelectionListener(new SimpleColorSelectionListener() {
                    @Override
                    public void onColorSelected(int color) {
                        newColor = color;
                    }
                });

                Button cancelButton = dialogColorPick.findViewById(R.id.buttonCancel);
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogColorPick.cancel();
                    }
                });

                dialogColorPick.show();
            }
        });

        dialogSyntax.show();
    }
}