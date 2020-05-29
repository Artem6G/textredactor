package com.textredactor.textredactor;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.OutputStreamWriter;

class DuplicateFile {

    private MainActivity mainActivity;

    DuplicateFile(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    void duplicate(File file) {

        if (file.getName().length() < 22) {
            try {
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(mainActivity.openFileOutput(newName(file.getName()), Context.MODE_PRIVATE)));
                bw.write(readFile(file));
                bw.close();

                mainActivity.emptyModUpd();
                Log.d("DuplicateFile", "Файл успешно продрублирован");
            } catch (Exception e) {
                Log.d("DuplicateFile", "Не получилось продублировать файл");
            }
        } else {
            Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.invalid_title_length), Toast.LENGTH_SHORT).show();
        }

    }

    private String readFile(File file) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(file));

        String str;
        StringBuilder finalResult = new StringBuilder();

        while ((str = br.readLine()) != null) {
            finalResult.append(str).append("\n");
        }

        br.close();
        Log.d("DuplicateFile", "Файл успешно прочитан");
        return String.valueOf(finalResult);
    }

    private String newName(String name) {

        File file_check = new File(mainActivity.getFilesDir(), name);
        int i = 1;

        // Цикл для подбора номера
        while (file_check.exists() && file_check.isFile()) {

            file_check = new File(mainActivity.getFilesDir(), name + " #" + i);

            // Если такой файл есть, то мы добавляем к его порядковому номеру 1
            if (file_check.exists() && file_check.isFile()) {
                i++;
            } else
                return name + " #" + i;
        }

        return name;
    }
}
