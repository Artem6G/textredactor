package com.textredactor.textredactor;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

class Send {

    private Context context;

    Send(Context context) {
        this.context = context;
    }

    void send(File file) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, readFile(file));
        sendIntent.setType("text/*");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        context.startActivity(shareIntent);
    }

    private String readFile(File file) {
        StringBuilder finalResult = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(context.openFileInput(file.getName())));

            String str;

            while ((str = br.readLine()) != null) {
                finalResult.append(str).append("\n");
            }

            br.close();
            Log.d("Send", "Файл: " + file.getName() + "  |прочитан успешно");

        } catch (Exception e) {
            Log.d("Send", "Ошибка при прочтении файла");
        }

            return String.valueOf(finalResult);

    }


}
