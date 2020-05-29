package com.textredactor.textredactor;

import android.content.Context;
import android.graphics.Typeface;

import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;

import java.io.File;

class Font {

    private Context context;

    Font(Context context) {
        this.context = context;
    }

    void filePickDialogCreate() {
        DialogProperties properties = new DialogProperties();

        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.root = new File(DialogConfigs.DEFAULT_DIR);
        properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
        properties.offset = new File(DialogConfigs.DEFAULT_DIR);
        properties.extensions = new String[]{"ttf", "otf"};

        FilePickerDialog dialog = new FilePickerDialog(context, properties);
        dialog.setTitle("Select file");

        dialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files) {
                if (files[0] != null) {
                    SettingsMemory.FontLink = files[0];
                }
            }
        });

        dialog.show();

    }

    void setFont(MainActivity mainActivity) {
        if(SettingsMemory.FontLink != null) {
            try {
                mainActivity.docEditText.setTypeface(Typeface.createFromFile(SettingsMemory.FontLink));
            } catch (Exception e) {
                SettingsMemory.FontLink = null;
            }
        } else {
            mainActivity.docEditText.setTypeface(Typeface.DEFAULT);
        }
    }

}
