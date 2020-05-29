package com.textredactor.textredactor;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.OutputStreamWriter;

class CreateFile {

    private MainActivity mainActivity;
    private EditText fileName;
    private Dialog dialog;

    CreateFile(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    // Создание файла
    private void brainFile(EditText editText) {

        String text_get = editText.getText().toString();

        if (text_get.length() > 1 && text_get.length() < 14) {

            try {
                File file = new File(mainActivity.getFilesDir() + "/" + text_get);
                if (file.exists() && file.isFile()) {
                    int i = 1;
                    while (true) {

                        File checkFile = new File(mainActivity.getFilesDir() + "/" + text_get + " #" + i);

                        if (checkFile.exists() && checkFile.isFile()) {
                            i++;
                        } else {
                            text_get = checkFile.getName();
                            break;
                        }
                    }
                }

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(mainActivity.openFileOutput(text_get, Context.MODE_PRIVATE)));
                bw.write("");
                bw.close();

                activityScrollView();

                dialog.cancel();

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(mainActivity, "Не получилось создать файл!", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }

        } else {
            Toast.makeText(mainActivity, "Неправильная длина названия!", Toast.LENGTH_SHORT).show();
        }

    }

    // Обновление ScrollView
    private void activityScrollView() {
        mainActivity.scrollViewUpd();
    }

    // Создание диалога
    private void dialogFile() {

        dialog = new Dialog(mainActivity);
        dialog.setContentView(R.layout.create_file_dialog);

        TextView textView = dialog.findViewById(R.id.createFileTextView);
        new Writer(textView, textView.getText().toString());

        Button saveButton = dialog.findViewById(R.id.save_button);
        fileName = dialog.findViewById(R.id.fileName);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                brainFile(fileName);

            }
        });

        dialog.show();


    }

    void createFile() {
        dialogFile();
    }

}
