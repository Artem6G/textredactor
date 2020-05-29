package com.textredactor.textredactor;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

class Encoding {


    private ArraySpinner arraySpinner;
    private ArraySpinner arraySpinnerFrom;
    private MainActivity mainActivity;

    Encoding(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    void writeFile(File file) throws Exception{
        String finalTextEncoding;
        try {
            finalTextEncoding = new String(readFile(file).getBytes(arraySpinnerFrom.getResultSelected()), arraySpinner.getResultSelected());
        } catch (Exception e) {
            finalTextEncoding = readFile(file);
            Log.d("Encoding", "Не получилось установить выбранную кодировку");
        }

        Log.d("Encoding", "name file: " + file.getName() + " encoding: " + arraySpinner.getResultSelected());

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(mainActivity.openFileOutput(file.getName(), Context.MODE_PRIVATE)));
        bw.write(finalTextEncoding);
        bw.close();

        Log.d("Encoding", "Кодировка успешно заменена");
    }

    private String readFile(File file) throws Exception {
        Log.d("Encoding", "Начало чтения файла");
        BufferedReader br = new BufferedReader(new InputStreamReader(mainActivity.openFileInput(file.getName())));

        String str;
        StringBuilder finalResult = new StringBuilder();

        while ((str = br.readLine()) != null) {
            finalResult.append(str).append("\n");
        }

        br.close();
        Log.d("Encoding", "Файл успешно прочитан");

        return String.valueOf(finalResult);
    }

    void callDialog(final File file) {

        final Dialog dialog = new Dialog(mainActivity);
        dialog.setContentView(R.layout.encoding_dialog);
        dialog.setCancelable(false);

        TextView animText = dialog.findViewById(R.id.textViewEncoding_Dialog);
        new Writer(animText, animText.getText().toString());

        Button save = dialog.findViewById(R.id.button_save_encoding_dialog);
        Button noSave = dialog.findViewById(R.id.button_cancel_encoding_dialog);
        Spinner spinner = dialog.findViewById(R.id.spinnerEncoding_in_EncodingDialog);
        Spinner fromSpinner = dialog.findViewById(R.id.from_spinnerEncoding_in_EncodingDialog);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mainActivity, R.array.format, android.R.layout.simple_spinner_item);
        arraySpinner = new ArraySpinner(spinner, adapter);
        arraySpinnerFrom = new ArraySpinner(fromSpinner, adapter);


        noSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    writeFile(file);
                    mainActivity.emptyModUpd();
                    dialog.cancel();
                } catch (Exception e) {
                    Log.d("Encoding", "Ошибка! При вызове функции записи файла");
                }

            }

        });

        dialog.show();

    }
}