package com.textredactor.textredactor;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

class OpenSaveInFile {

    private EditText editTextFormat;
    private ArraySpinner arraySpinner;
    private EditText nameSaveIn;
    private String saveName;
    private String saveText;
    private MainActivity mainActivity;
    private String resultFile;
    private File fileFromLink;
    private String fileLink;
    private DialogProperties properties;

    OpenSaveInFile(MainActivity mainActivity) {
        this.mainActivity = mainActivity;

        if (ContextCompat.checkSelfPermission(mainActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.d("OpenSaveFile", "Запрос на разрешение для записи файлов на SD");
            ActivityCompat.requestPermissions(mainActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }

    }

    // Установка настроек для выбора директорий
    private void setDirProperties() {
        properties = new DialogProperties();

        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.DIR_SELECT;
        properties.root = new File(DialogConfigs.DEFAULT_DIR);
        properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
        properties.offset = new File(DialogConfigs.DEFAULT_DIR);
        properties.extensions = null;
    }

    // Выбор директории
    void dirPickDialogCreate(final String text, final String name) {
        saveText = text;
        saveName = name;
        setDirProperties();

        FilePickerDialog dialog = new FilePickerDialog(mainActivity, properties);
        dialog.setTitle("Select dir");

        dialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files) {
                if (files[0] != null) {
                    fileLink = files[0];
                    dialogCall(fileLink);
                }
            }
        });

        dialog.show();

    }


    private void dialogCall(String link) {

        File file = new File(link, saveName);

        final Dialog saveIn = new Dialog(mainActivity);
        saveIn.setContentView(R.layout.save_in_dialog);
        saveIn.setCancelable(false);

        Button buttonSaveInDialog = saveIn.findViewById(R.id.buttonSaveIn);
        Button buttonNoSaveInDialog = saveIn.findViewById(R.id.buttonNoSaveIn);
        TextView pathTextView = saveIn.findViewById(R.id.pathTextView);
        editTextFormat = saveIn.findViewById(R.id.textFormatSaveIn);
        nameSaveIn = saveIn.findViewById(R.id.textNameSaveIn);
        Spinner spinner = saveIn.findViewById(R.id.spinnerEncoding);

        pathTextView.setText(link);
        nameSaveIn.setText(file.getName());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mainActivity, R.array.format, android.R.layout.simple_spinner_item);
        arraySpinner = new ArraySpinner(spinner, adapter);

        buttonNoSaveInDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveIn.cancel();
            }
        });

        buttonSaveInDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nameSaveIn.getText().toString().length() > 1 && nameSaveIn.getText().toString().length() < 21) {
                    saveName = nameSaveIn.getText().toString();
                    writeSD(arraySpinner.getResultSelected(), editTextFormat.getText().toString());
                    saveIn.cancel();
                } else {
                    Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.invalid_title_length), Toast.LENGTH_LONG).show();
                }

            }
        });

        saveIn.show();
    }

    // Запись файла во внешнюю память
    private void writeSD(String encoding, String format) {
        File sdFile;

        // Если format != 0, то добавляем формат
        if (format.length() == 0)
            sdFile = new File(fileLink, saveName);
        else
            sdFile = new File(fileLink, saveName + "." + format);

        try {
            try {
                Log.d("OpenSaveFile", "Кодировка: " + encoding);

                // Устанавливаем кодировку для текста
                String finalTextEncoding = new String(saveText.getBytes(), encoding);

                if(sdFile.exists() && sdFile.isFile()) {
                    callFoundFile(sdFile, finalTextEncoding);
                } else {
                    writeFileSaveIn(sdFile, finalTextEncoding);
                }


            } catch (UnsupportedEncodingException e) {
                Toast.makeText(mainActivity, "Error! Due to the selected encoding", Toast.LENGTH_LONG).show();
                Log.d("OpenSaveFile", "UnsupportedEncodingException e");
            }

        } catch (Exception e) {
            Toast.makeText(mainActivity, "Error! Error writing file", Toast.LENGTH_LONG).show();
            Log.d("OpenSaveFile", "Ошибка при записи файла: " + sdFile.getAbsolutePath());
        }

    }

    private void writeFileSaveIn(File file, String text) {
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            // Записываем текст с кодировкой
            fileWriter.write(text);
            // Закрываем поток
            fileWriter.close();
            Log.d("OpenSaveFile", "Файл записан на SD: " + file.getAbsolutePath());
        } catch (Exception e){
            Toast.makeText(mainActivity, "Error! Error writing file", Toast.LENGTH_LONG).show();
            Log.d("OpenSaveFile", "Ошибка при записи файла: " + file.getAbsolutePath());
        }
    }

    // Замена файла
    private void callFoundFile(final File file, final String text) {
        final Dialog dialogFound = new Dialog(mainActivity);
        dialogFound.setContentView(R.layout.file_found);

        TextView animText = dialogFound.findViewById(R.id.textView_foundFile_dialog);
        new Writer(animText, animText.getText().toString());

        Button replace = dialogFound.findViewById(R.id.button_replace_found_file);
        Button cancelReplace = dialogFound.findViewById(R.id.button_cancel_found_file);

        replace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (file.delete())
                    Log.d("OpenSaveFile", "Файл успешно удален");
                else
                    Log.d("OpenSaveFile", "Ошибка при удалении файла");

                writeFileSaveIn(file, text);

                dialogFound.cancel();
            }
        });

        cancelReplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFound.cancel();
            }
        });

        dialogFound.show();
    }

    // Установка настроек для выбора файлов
    private void setFileProperties() {
        properties = new DialogProperties();

        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.root = new File(DialogConfigs.DEFAULT_DIR);
        properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
        properties.offset = new File(DialogConfigs.DEFAULT_DIR);
        properties.extensions = new String[]{"txt", "doc", "text", "html", "", "json", "xml"};
    }

    // Выбор файлов
    void filePickDialogCreate() {
        setFileProperties();

        FilePickerDialog dialog = new FilePickerDialog(mainActivity, properties);
        dialog.setTitle("Select file");

        dialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files) {
                if (files[0] != null) {
                    fileLink = files[0];
                    try {
                        readFile();
                        writeFile();
                        activityScrollView();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        dialog.show();
    }

    // Обновление scrollView
    private void activityScrollView() {
        mainActivity.scrollViewUpd();
    }

    // Проверка файла на совпадения
    private String nameFile() {

        File file_check = new File(mainActivity.getFilesDir(), fileFromLink.getName());
        int i = 1;

        // Цикл для подбора номера
        while (file_check.exists() && file_check.isFile()) {

            file_check = new File(mainActivity.getFilesDir(), fileFromLink.getName() + " #" + i);

            // Если такой файл есть, то мы добавляем к его порядковому номеру 1
            if (file_check.exists() && file_check.isFile()) {
                i++;
            } else
                return fileFromLink.getName() + " #" + i;
        }

        return fileFromLink.getName();

    }


    // Читаем файл
    private void readFile() throws Exception {
        fileFromLink = new File(fileLink);
        BufferedReader br = new BufferedReader(new FileReader(fileFromLink));

        String str;
        StringBuilder finalResult = new StringBuilder();

        while ((str = br.readLine()) != null) {
            finalResult.append(str).append("\n");
        }

        br.close();

        resultFile = String.valueOf(finalResult);
    }

    // Записываем файл
    private void writeFile() throws Exception {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(mainActivity.openFileOutput(nameFile(), Context.MODE_PRIVATE)));
        bw.write(resultFile);
        bw.close();
    }

}