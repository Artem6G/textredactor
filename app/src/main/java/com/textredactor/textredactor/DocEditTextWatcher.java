package com.textredactor.textredactor;

import android.content.Context;
import android.text.Editable;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.OutputStreamWriter;

class DocEditTextWatcher {
    private MainActivity mainActivity;

    DocEditTextWatcher(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


    public void save() {
        mainActivity.docEditText.addTextChangedListener(new EditableTextWatcher() {
            @Override
            protected void beforeTextChange(CharSequence s, int start, int count, int after) {

            }

            @Override
            protected void onTextChange(CharSequence s, int start, int before, int count) {

            }

            @Override
            protected void afterTextChange(Editable s) {

                if (!mainActivity.nameFileGetTopLevel.getText().toString().equals("")) {
                    File file = new File(mainActivity.getFilesDir(), mainActivity.nameFileGetTopLevel.getText().toString());

                    try {
                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(mainActivity.openFileOutput(file.getName(), Context.MODE_PRIVATE)));
                        bw.write(mainActivity.docEditText.getText().toString());
                        bw.close();
                        Log.d("SaveText", "Файл: " + file.getAbsolutePath() + " перезаписан успешно");

                        new Syntax(mainActivity.docEditText);
                    } catch (Exception e) {
                        Log.d("SaveText", "Не удалось перезаписать файл: " + file.getAbsolutePath());
                    }
                }

            }
        });
    }

}