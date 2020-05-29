package com.textredactor.textredactor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

class ScrollFiles {

    private DeleteFile df;
    private File fileDir;
    private StringBuilder finalResult;
    private TextView[] textViews;
    private File[] files;
    private LinearLayout linearLayout;
    private MainActivity mainActivity;

    ScrollFiles(MainActivity mainActivity, File dirLink) {
        LinearLayout linearLayout = ((Activity) mainActivity).findViewById(R.id.linerLayoutHorizontalScroll);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        this.mainActivity = mainActivity;
        this.linearLayout = linearLayout;
        this.fileDir = dirLink;

        _clearLinerLayout();

        _fileInit();

        _addTextView();

        emptyMode();

        _openFirstFile();

    }

    // Инициализация файлов
    private void _fileInit() {
        files = fileDir.listFiles();

        assert files != null;
        if (files.length == 0)
            try {
                _createFirstFile();
                Log.d("ScrollFiles", "Создан первый файл для пользователя");
            } catch (Exception ignored) {
                Log.d("ScrollFiles", "Ошибка при создании первого файла для пользователя");
            }

        files = fileDir.listFiles();
    }

    // Очистка LinerLayout
    private void _clearLinerLayout() {
        if (linearLayout != null)
            linearLayout.removeAllViews();
    }

    // Добавление TextView в ScrollView
    private void _addTextView() {
        assert files != null;
        textViews = new TextView[files.length];
        try {
            for (int i = 0; i < files.length; i++) {

                textViews[i] = new TextView(mainActivity);

                // Установка некоторых значений для textView
                textViews[i].setText(files[i].getName());
                textViews[i].setId(i);
                textViews[i].setTextColor(SettingsMemory.NotOpenFileColor);
                textViews[i].setPadding(25, 0, 25, 0);

                // Установка обработчика нажатий, для TextView
                _touch(i);

                linearLayout.addView(textViews[i]);
                Log.d("ScrollFiles", "TextView" + "(" + i + ")" + " был успешно добавлен");
            }
        } catch (Exception ignored) {
            Log.d("ScrollFiles", "Ошибка при создании textView");
        }
    }

    // Установка имени, доступа, пути, цвета и текста открытого файла
    private void _set_name_text_color(int i) {

        mainActivity.nameFileGetTopLevel.setText(files[i].getName());

        mainActivity.nameFileGetTopLevel.setTextColor(SettingsMemory.FileNameColor);

        mainActivity.textLinkFile.setText(files[i].getAbsolutePath());

        mainActivity.docEditText.setText(_readFile(i));

        mainActivity.docEditText.setEnabled(true);

        for (TextView tx : textViews) {
            tx.setTextColor(SettingsMemory.NotOpenFileColor);
        }

        textViews[i].setTextColor(SettingsMemory.OpenFileColor);
    }


    // Прочитывание файла
    private String _readFile(int i) {

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(mainActivity.openFileInput(files[i].getName())));

            String str;

            finalResult = new StringBuilder();

            while ((str = br.readLine()) != null) {
                finalResult.append(str).append("\n");
            }

            br.close();
            Log.d("ScrollFiles", "Файл: " + files[i].getName() + "  |прочитан успешно");

        } catch (Exception e) {
            Log.d("ScrollFiles", "Ошибка при прочтении файла");
        }

        if (finalResult != null)
            return String.valueOf(finalResult);

        else
            return "";
    }


    // Открытие последнего файла
    private void _openLastFile() {

        if (files.length > 0) {
            mainActivity.nameFileGetTopLevel.setText(files[files.length - 1].getName());

            mainActivity.nameFileGetTopLevel.setTextColor(SettingsMemory.FileNameColor);

            mainActivity.textLinkFile.setText(files[files.length - 1].getAbsolutePath());

            mainActivity.docEditText.setText(_readFile(files.length - 1));

            mainActivity.docEditText.setEnabled(true);

            for (TextView tx : textViews) {
                tx.setTextColor(SettingsMemory.NotOpenFileColor);
            }

            textViews[files.length - 1].setTextColor(SettingsMemory.OpenFileColor);

            Log.d("ScrollFiles", "Открыт последний файл");
        }

    }

    // Обработчик нажатий
    private long startTime;

    @SuppressLint("ClickableViewAccessibility")
    private void _touch(final int i) {
        textViews[i].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP && event.getEventTime() - event.getDownTime() < 225) {
                    _set_name_text_color(i);
                }

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        startTime = System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        long totalTime = System.currentTimeMillis() - startTime;
                        Log.d("ScrollFiles", "Прошло: " + totalTime + " Millis с момента зажатия");
                        if (totalTime >= 1250) {
                            Log.d("ScrollFiles", "Меню для управления файлом, открыто успешно");
                            showMenuFile(v, i);
                        }
                        break;
                }
                return true;
            }
        });
    }

    // Функция для вызова меню с действиями
    private void showMenuFile(View v, final int i) {

        df = new DeleteFile(mainActivity);
        final OpenSaveInFile openSaveInFile = new OpenSaveInFile(mainActivity);

        PopupMenu popupMenu = new PopupMenu(mainActivity, v);
        popupMenu.inflate(R.menu.file_open_menu);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.delete_file_m) {

                    df.callDialogDelete(files[i]);

                    return true;
                } else if (item.getItemId() == R.id.rename_file_m) {

                    RenameFile renameFile = new RenameFile(mainActivity);
                    renameFile.callDialog(files[i].getName());

                    return true;
                } else if (item.getItemId() == R.id.duplicate_file_m) {

                    DuplicateFile duplicateFile = new DuplicateFile(mainActivity);
                    duplicateFile.duplicate(files[i]);

                    return true;

                } else if (item.getItemId() == R.id.send_m) {

                    Send send = new Send(mainActivity);
                    send.send(files[i]);

                    return true;

                }  else if (item.getItemId() == R.id.encoding_file_m) {

                    Encoding encoding = new Encoding(mainActivity);
                    encoding.callDialog(files[i]);

                    return true;

                } else if (item.getItemId() == R.id.open_file_m) {

                    _set_name_text_color(i);

                    return true;
                } else if (item.getItemId() == R.id.save_to_m) {

                    try {
                        BufferedReader br = new BufferedReader(new FileReader(files[i].getAbsoluteFile()));

                        String str;
                        StringBuilder finalResult = new StringBuilder();

                        while ((str = br.readLine()) != null) {
                            finalResult.append(str).append("\n");
                        }

                        br.close();
                        Log.d("ScrollFiles", "Файл прочитан успешно! Для saveTo");

                        openSaveInFile.dirPickDialogCreate(String.valueOf(finalResult), files[i].getName());
                    } catch (Exception e) {
                        Log.d("ScrollFiles", "Ошибка при чтении файла, в saveTo");
                    }


                    return true;
                }

                return false;
            }
        });

        popupMenu.show();
    }

    private void _createFirstFile() throws Exception {

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(mainActivity.openFileOutput(mainActivity.getResources().getString(R.string.new_file), Context.MODE_PRIVATE)));
        bw.write("");
        bw.close();

    }

    private void _openFirstFile() {
        if (files.length == 1 && files[0].getName().equals(mainActivity.getResources().getString(R.string.new_file))) {
            _openLastFile();
        }
    }

    // Redact
    void emptyMode() {
        _clearLinerLayout();

        _fileInit();

        _addTextView();

        mainActivity.nameFileGetTopLevel.setText("");

        for (TextView tx : textViews) {
            tx.setTextColor(SettingsMemory.NotOpenFileColor);
        }

        mainActivity.docEditText.setText("");

        mainActivity.textLinkFile.setText("");

        mainActivity.docEditText.setEnabled(false);

    }

    void updateScrollFiles() {
        _clearLinerLayout();

        _fileInit();

        _addTextView();

        _openLastFile();

        Log.d("ScrollFiles", "Обновлен ScrollFiles");
    }

}