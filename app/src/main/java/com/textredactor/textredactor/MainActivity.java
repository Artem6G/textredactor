package com.textredactor.textredactor;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.lang.reflect.Field;


public class MainActivity extends AppCompatActivity {

    WorkMenu workMenu;
    TextSettingsMenu textSettingsMenu;
    FileManagerMenu fileManagerMenu;
    SettingsMenu settingsMenu;

    TextView textLinkFile;
    Dialog dialogError;
    EditText docEditText;
    TextView nameFileGetTopLevel;
    ScrollFiles scrollFiles;
    ConstraintLayout constraintLayout;
    ImageButton bFileManager;
    ImageButton bSetting;
    ImageButton bTextSetting;
    ImageButton bWorkText;
    View upperView;
    View bottomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ID
        bTextSetting = findViewById(R.id.textSetting);
        bFileManager = findViewById(R.id.fileManager);
        constraintLayout = findViewById(R.id.constraintLayout);
        nameFileGetTopLevel = findViewById(R.id.nameCat);
        docEditText = findViewById(R.id.editText);
        textLinkFile = findViewById(R.id.textLinkFile);
        bSetting = findViewById(R.id.Setting);
        upperView = findViewById(R.id.upperView);
        bottomView = findViewById(R.id.bottomView);
        bWorkText = findViewById(R.id.TextJob);

        // Инициализция диалога, с просьбой дать разрешение
        plsGetPermission();
        DocEditTextWatcher docEditTextWatcher = new DocEditTextWatcher(this);
        docEditTextWatcher.save();

        workMenu = new WorkMenu(this);
        settingsMenu = new SettingsMenu(this);
        textSettingsMenu = new TextSettingsMenu(this);
        fileManagerMenu = new FileManagerMenu(MainActivity.this);
        scrollFiles = new ScrollFiles(this, getFilesDir());

        click();
        new Syntax(docEditText);
    }

    private void plsGetPermission() {
        dialogError = new Dialog(this);
        dialogError.setContentView(R.layout.error_permission_dialog);
        dialogError.setCancelable(false);
    }

    // Данная функция, используется для установки некоторых параметров, которые пользователь может изменить динамически
    private void setParameters() {
        docEditText.setTextColor(SettingsMemory.DocTextColor);
        docEditText.setTextSize(SettingsMemory.textSize);
        constraintLayout.setBackgroundColor(SettingsMemory.BackgroundColor);
        textLinkFile.setTextColor(SettingsMemory.PathFileColor);
        nameFileGetTopLevel.setTextColor(SettingsMemory.FileNameColor);
        upperView.setBackgroundColor(SettingsMemory.UppedStripColor);
        bottomView.setBackgroundColor(SettingsMemory.BottomStripColor);
        setCursorColor(docEditText, SettingsMemory.CursorColor);
        docEditText.setHighlightColor(SettingsMemory.HighlightColor);
        docEditText.setLineSpacing(0, SettingsMemory.LineSpacing);
        docEditText.setLetterSpacing(SettingsMemory.LetterSpacing);
        Font font = new Font(this); font.setFont(this);
    }


    // Данная функция, используется для создания кнопок, которые позволяют пользователю, открыть меню или перейти в какую либо вкладку
    private void click() {
        bSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               settingsMenu.showSettingsMenu(v);
            }
        });

        bFileManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { fileManagerMenu.showFileManagerMenu(v);
            }
        });

        bTextSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textSettingsMenu.showMenu(v);
            }
        });

        bWorkText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workMenu.showMenu(v);
            }
        });
    }

    void emptyModUpd() {
        scrollFiles.emptyMode();
    }

    void scrollViewUpd() {
        scrollFiles.updateScrollFiles();
    }

    // Данная функция используется для установки цвета курсора
    public static void setCursorColor(EditText view, @ColorInt int color) {
        try {
            Field field = TextView.class.getDeclaredField("mCursorDrawableRes");
            field.setAccessible(true);
            int drawableResId = field.getInt(view);
            field = TextView.class.getDeclaredField("mEditor");
            field.setAccessible(true);
            Object editor = field.get(view);
            Drawable drawable = ContextCompat.getDrawable(view.getContext(), drawableResId);
            assert drawable != null;
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            Drawable[] drawables = {drawable, drawable};
            assert editor != null;
            field = editor.getClass().getDeclaredField("mCursorDrawable");
            field.setAccessible(true);
            field.set(editor, drawables);
        } catch (Exception ignored) {
        }
    }

    // Данная функция, используется для проверки того, что пользователь дал разрешение
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 0);

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                Log.d("MainActivity", "Открытие Settings.ACTION_APPLICATION_DETAILS_SETTINGS");

                // Открытие Settings.ACTION_APPLICATION_DETAILS_SETTINGS, чтобы пользователю было удобно дать разрешение
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                startActivityForResult(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(uri), 0);
            }

            Log.d("MainActivity", "Запрос на разрешение для записи и чтения файлов на SD");
            if (!dialogError.isShowing()) {
                Log.d("MainActivity", "Показ dialogError");
                dialogError.show();
            }
        } else {
            if (dialogError.isShowing()) {
                dialogError.cancel();
                Log.d("MainActivity", "Прекращен показ dialogError");
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        setParameters();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}