package com.textredactor.textredactor;


import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.madrapps.pikolo.ColorPicker;
import com.madrapps.pikolo.listeners.SimpleColorSelectionListener;

public class SettingActivity extends AppCompatActivity {

    CheckBox checkBoxAnimate;
    ArraySpinner arraySpinnerAnimateTime;
    ArraySpinner arraySpinnerTextSize;
    ArraySpinner arraySpinnerLineSpacing;
    ArraySpinner arraySpinnerLetterSpacing;
    ColorPicker colorPicker;
    Dialog dialogColorPick;
    Button saveButtonColorPickDialog;
    TextView textTestSize;

    private int newColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setViewColor();
        clickS();

    }

    private void setViewColor() {
        View viewTextColor = findViewById(R.id.viewTextColor);
        View viewNameFileColor = findViewById(R.id.viewFileNameColor);
        View viewNoOpenFileColor = findViewById(R.id.viewNoOpenFileColor);
        View viewOpenFileColor = findViewById(R.id.viewOpenFileColor);
        View viewBackgroundColor = findViewById(R.id.viewBackgroundColor);
        View viewPathFileColor = findViewById(R.id.viewFilePathColor);
        View viewUppedStripColor = findViewById(R.id.viewUppedStripColor);
        View viewBottomStripColor = findViewById(R.id.viewBottomStripColor);
        View viewCursorColor = findViewById(R.id.viewCursorColor);
        View viewHighlightColor = findViewById(R.id.viewHighlightColor);

        viewTextColor.setBackgroundColor(SettingsMemory.DocTextColor);
        viewNameFileColor.setBackgroundColor(SettingsMemory.FileNameColor);
        viewNoOpenFileColor.setBackgroundColor(SettingsMemory.NotOpenFileColor);
        viewOpenFileColor.setBackgroundColor(SettingsMemory.OpenFileColor);
        viewBackgroundColor.setBackgroundColor(SettingsMemory.BackgroundColor);
        viewPathFileColor.setBackgroundColor(SettingsMemory.PathFileColor);
        viewUppedStripColor.setBackgroundColor(SettingsMemory.UppedStripColor);
        viewBottomStripColor.setBackgroundColor(SettingsMemory.BottomStripColor);
        viewCursorColor.setBackgroundColor(SettingsMemory.CursorColor);
        viewHighlightColor.setBackgroundColor(SettingsMemory.HighlightColor);
    }

    void clickS() {
        buttonColor();
        checkBoxAnimate();
        animateTime();
        text_settings(true);
    }

    // Функция для вызова функций для работы с текстом
    private void text_settings(boolean anim) {
        textSize(anim);
        lineSpacing();
        letterSpacing();
        fontSetting();
        default_text();
    }

    // Функция для возращения первоначальных настроек текса
    private void default_text() {
        Button defaultButtonText = findViewById(R.id.button_reset_text_settings);
        final Dialog dialogDefaultText = new Dialog(this);

        defaultButtonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDefaultText.setContentView(R.layout.original_text_settings);

                Button cancel = dialogDefaultText.findViewById(R.id.button_cancel_text_settings_reset);
                Button reset = dialogDefaultText.findViewById(R.id.button_reset_text_settings_inDialog);

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogDefaultText.cancel();
                    }
                });

                reset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SettingsMemory.default_text();
                        text_settings(false);
                        dialogDefaultText.cancel();
                    }
                });

                dialogDefaultText.show();
            }
        });
    }

    // Установка и сброс шрифта
    private void fontSetting() {
        Button fontButton = findViewById(R.id.button_font_text);
        fontButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Font fo = new Font(SettingActivity.this);
               fo.filePickDialogCreate();
            }
        });

        Button resetFontButton = findViewById(R.id.button_reset_font_text);
        resetFontButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsMemory.FontLink = null;
            }
        });
    }

    // Функция для настройки флажка у анимации
    private void checkBoxAnimate() {
        checkBoxAnimate = findViewById(R.id.checkBoxTextAnimation);

        checkBoxAnimate.setChecked(SettingsMemory.Animate);

        checkBoxAnimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsMemory.Animate = checkBoxAnimate.isChecked();
            }
        });
    }

    // Функция для настройки времени анимации
    private void animateTime() {
        Spinner spinnerAnimateTimeSpinner = findViewById(R.id.spinnerAnimationTime);
        Button saveTimeAnimate = findViewById(R.id.button_save_animation_time);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.textAnimation, android.R.layout.simple_spinner_item);
        arraySpinnerAnimateTime = new ArraySpinner(spinnerAnimateTimeSpinner, adapter);
        arraySpinnerAnimateTime.selectSpinnerValue(String.valueOf(SettingsMemory.TimeAnimate));

        saveTimeAnimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsMemory.TimeAnimate = Integer.parseInt(arraySpinnerAnimateTime.getResultSelected());
                Toast.makeText(SettingActivity.this, "Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Функция для настройки отступов символов
    private void letterSpacing() {
        Spinner spinnerLetterSpinner = findViewById(R.id.spinnerLetterSpacing);
        Button saveLetterSpacing = findViewById(R.id.button_save_letter_spacing);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.letterSpacing, android.R.layout.simple_spinner_item);
        arraySpinnerLetterSpacing = new ArraySpinner(spinnerLetterSpinner, adapter);
        arraySpinnerLetterSpacing.selectSpinnerValue(String.valueOf(SettingsMemory.LetterSpacing));

        saveLetterSpacing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsMemory.LetterSpacing = Integer.parseInt(arraySpinnerLetterSpacing.getResultSelected());
                Toast.makeText(SettingActivity.this, "Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Функция для настройки отступов линии
    private void lineSpacing() {
        Spinner spinnerLineSpacing = findViewById(R.id.spinnerLineSpacing);
        Button saveLineSpacing = findViewById(R.id.button_save_line_spacing);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.lineSpacing, android.R.layout.simple_spinner_item);
        arraySpinnerLineSpacing = new ArraySpinner(spinnerLineSpacing, adapter);
        arraySpinnerLineSpacing.selectSpinnerValue(String.valueOf(SettingsMemory.LineSpacing));

        saveLineSpacing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsMemory.LineSpacing = Integer.parseInt(arraySpinnerLineSpacing.getResultSelected());
                Toast.makeText(SettingActivity.this, "Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Функция для настройки размера текста
    private void textSize(boolean anim) {
        Spinner spinnerTextSize = findViewById(R.id.spinnerTextSize);
        Button saveTextSize = findViewById(R.id.button_save_text_size);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.textSize, android.R.layout.simple_spinner_item);
        arraySpinnerTextSize = new ArraySpinner(spinnerTextSize, adapter);
        arraySpinnerTextSize.selectSpinnerValue(String.valueOf(SettingsMemory.textSize));

        textTestSize = findViewById(R.id.textTestTextSize);
        textTestSize.setTextSize(SettingsMemory.textSize);

        if(anim)
        new Writer(textTestSize, textTestSize.getText().toString());

        saveTextSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsMemory.textSize = Integer.parseInt(arraySpinnerTextSize.getResultSelected());
                textTestSize.setTextSize(SettingsMemory.textSize);
                Toast.makeText(SettingActivity.this, "Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Функция для возращения первоначальных настроек у цвета
    private void default_colors() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.original_colors_dialog);

        Button cancel = dialog.findViewById(R.id.button_cancel_originals_color_reset);
        Button reset = dialog.findViewById(R.id.button_reset_originals_color);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsMemory.default_colors();
                setViewColor();
                dialog.cancel();
            }
        });

        dialog.show();
    }

    // Функция для изменения цвета подсветки
    private void highlightColor() {
        // Устанавливаем цвет подсветки
        newColor = SettingsMemory.HighlightColor;

        // Вызываем ColorPicker
        colorPickDialogCall();

        // Устанавливаем цвет подсветки по стандарту в ColorPicker
        colorPicker.setColor(SettingsMemory.HighlightColor);

        // Установка цвета подсветки в случае сохранения
        saveButtonColorPickDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsMemory.HighlightColor = newColor;
                setViewColor();
                dialogColorPick.cancel();
            }
        });

    }

    // Функция для изменения цвета курсора
    private void cursorColor() {
        // Устанавливаем цвет курсора
        newColor = SettingsMemory.CursorColor;

        // Вызываем ColorPicker
        colorPickDialogCall();

        // Устанавливаем цвет курсора по стандарту в ColorPicker
        colorPicker.setColor(SettingsMemory.CursorColor);

        // Установка цвета курсора в случае сохранения
        saveButtonColorPickDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsMemory.CursorColor = newColor;
                setViewColor();
                dialogColorPick.cancel();
            }
        });

    }

    // Функция для изменения цвета нижней линии
    private void bottomStripColor() {
        // Устанавливаем цвет нижней линии
        newColor = SettingsMemory.BottomStripColor;

        // Вызываем ColorPicker
        colorPickDialogCall();

        // Устанавливаем цвет нижней линии по стандарту в ColorPicker
        colorPicker.setColor(SettingsMemory.BottomStripColor);

        // Установка цвета нижней линии в случае сохранения
        saveButtonColorPickDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsMemory.BottomStripColor = newColor;
                setViewColor();
                dialogColorPick.cancel();
            }
        });

    }

    // Функция для изменения цвета верхней линии
    private void uppedStripColor() {
        // Устанавливаем цвет верхней линии
        newColor = SettingsMemory.UppedStripColor;

        // Вызываем ColorPicker
        colorPickDialogCall();

        // Устанавливаем цвет верхней линии по стандарту в ColorPicker
        colorPicker.setColor(SettingsMemory.UppedStripColor);

        // Установка цвета верхней линии в случае сохранения
        saveButtonColorPickDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsMemory.UppedStripColor = newColor;
                setViewColor();
                dialogColorPick.cancel();
            }
        });

    }

    // Функция для изменения цвета не открытого файла
    private void noOpenFileColor() {
        // Устанавливаем цвет не открытого файла
        newColor = SettingsMemory.NotOpenFileColor;

        // Вызываем ColorPicker
        colorPickDialogCall();

        // Устанавливаем цвет не открытого файла по стандарту в ColorPicker
        colorPicker.setColor(SettingsMemory.NotOpenFileColor);

        // Установка цвета не открытого файла в случае сохранения
        saveButtonColorPickDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsMemory.NotOpenFileColor = newColor;
                setViewColor();
                dialogColorPick.cancel();
            }
        });

    }

    // Функция для изменения цвета открытого файла
    private void openFileColor() {
        // Устанавливаем цвет открытого файла
        newColor = SettingsMemory.OpenFileColor;

        // Вызываем ColorPicker
        colorPickDialogCall();

        // Устанавливаем цвет открытого файла по стандарту в ColorPicker
        colorPicker.setColor(SettingsMemory.OpenFileColor);

        // Установка цвета открытого файла в случае сохранения
        saveButtonColorPickDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsMemory.OpenFileColor = newColor;
                setViewColor();
                dialogColorPick.cancel();
            }
        });

    }

    // Функция для изменения цвета имени файла
    private void nameFileColor() {
        // Устанавливаем цвет имени файла
        newColor = SettingsMemory.FileNameColor;

        // Вызываем ColorPicker
        colorPickDialogCall();

        // Устанавливаем цвет имени файла по стандарту в ColorPicker
        colorPicker.setColor(SettingsMemory.FileNameColor);
        // Установка цвета имени файла в случае сохранения
        saveButtonColorPickDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsMemory.FileNameColor = newColor;
                setViewColor();
                dialogColorPick.cancel();
            }
        });

    }

    // Функция для изменения цвета пути файла
    private void docPathFileColor() {
        // Устанавливаем цвет пути файла
        newColor = SettingsMemory.PathFileColor;

        // Вызываем ColorPicker
        colorPickDialogCall();

        // Устанавливаем цвет пути файла по стандарту в ColorPicker
        colorPicker.setColor(SettingsMemory.PathFileColor);

        // Установка цвета пути файла в случае сохранения
        saveButtonColorPickDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsMemory.PathFileColor = newColor;
                setViewColor();
                dialogColorPick.cancel();
            }
        });

    }

    // Функция для изменения цвета текста
    private void docTextColor() {

        // Устанавливаем цвет нынешнего текста
        newColor = SettingsMemory.DocTextColor;

        // Вызываем ColorPicker
        colorPickDialogCall();

        // Устанавливаем цвет нынешнего текста по стандарту в ColorPicker
        colorPicker.setColor(SettingsMemory.DocTextColor);

        // Установка цвета текста в случае сохранения
        saveButtonColorPickDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsMemory.DocTextColor = newColor;
                setViewColor();
                dialogColorPick.cancel();
            }
        });

    }

    // Функция для изменения цвета фона
    private void docBackgroundColor() {

        // Устанавливаем цвет нынешнего фона
        newColor = SettingsMemory.BackgroundColor;

        // Вызываем ColorPicker
        colorPickDialogCall();

        // Устанавливаем цвет нынешнего фона по стандарту в ColorPicker
        colorPicker.setColor(SettingsMemory.BackgroundColor);

        // Установка цвета фона в случае сохранения
        saveButtonColorPickDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsMemory.BackgroundColor = newColor;
                setViewColor();
                dialogColorPick.cancel();
            }
        });

    }


    // Функция для вызова ColorPicker
    private void colorPickDialogCall() {
        dialogColorPick = new Dialog(this);

        dialogColorPick.setContentView(R.layout.color_pick_dialog);
        dialogColorPick.setCancelable(false);

        colorPicker = dialogColorPick.findViewById(R.id.colorPicker);
        saveButtonColorPickDialog = dialogColorPick.findViewById(R.id.buttonSave);
        Button cancelButton = dialogColorPick.findViewById(R.id.buttonCancel);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogColorPick.cancel();
            }
        });

        colorPicker.setColorSelectionListener(new SimpleColorSelectionListener() {
            @Override
            public void onColorSelected(int color) {
                newColor = color;
            }
        });

        dialogColorPick.show();
    }

    // Кнопки для работы с цветами
    private void buttonColor() {
        Button bDocTextColor = findViewById(R.id.buttonTextColor);
        bDocTextColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                docTextColor();
            }
        });

        Button backgroundColor = findViewById(R.id.buttonBackgroundColor);
        backgroundColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                docBackgroundColor();
            }
        });

        Button docPathFileColor = findViewById(R.id.buttonFilePatchColor);
        docPathFileColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                docPathFileColor();
            }
        });

        Button nameFileColor = findViewById(R.id.buttonFileNameTop);
        nameFileColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameFileColor();
            }
        });

        Button openFileColor = findViewById(R.id.buttonOpenFileColor);
        openFileColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileColor();
            }
        });

        Button noOpenFileColor = findViewById(R.id.buttonNoOpenFile);
        noOpenFileColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noOpenFileColor();
            }
        });

        Button uppedStripColor = findViewById(R.id.buttonUppedStripColor);
        uppedStripColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uppedStripColor();
            }
        });

        Button bottomStripColor = findViewById(R.id.buttonBottomStripColor);
        bottomStripColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomStripColor();
            }
        });

        Button resetColor = findViewById(R.id.button_reset_color);
        resetColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                default_colors();
            }
        });

        Button cursorColor = findViewById(R.id.buttonCursorColor);
        cursorColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cursorColor();
            }
        });

        Button highlightColor = findViewById(R.id.buttonHighlightColor);
        highlightColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightColor();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        SettingsMemory.save_color_settings(this);
        SettingsMemory.save_text_settings(this);
        SettingsMemory.save_other_settings(this);
    }

}