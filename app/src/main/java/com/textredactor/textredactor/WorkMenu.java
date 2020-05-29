package com.textredactor.textredactor;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;

import com.madrapps.pikolo.ColorPicker;
import com.madrapps.pikolo.listeners.SimpleColorSelectionListener;

import java.util.Calendar;
import java.util.Date;

import static android.content.Context.CLIPBOARD_SERVICE;

class WorkMenu {
    private ArraySpinner arraySpinnerNumRepeat;
    private EditText textRepeat;
    private String resultRepeat = "";
    private ClipboardManager clipboardManager;
    private PopupMenu popupMenu;
    private int colorDefault = -1;
    private MainActivity mainActivity;

    WorkMenu(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        clipboardManager= (ClipboardManager) mainActivity.getSystemService(CLIPBOARD_SERVICE);
    }

    void showMenu(View v) {

        popupMenu = new PopupMenu(mainActivity, v);
        popupMenu.inflate(R.menu.text_work_menu);

        access();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.insert_date_text_work_menu:

                        insertDate(mainActivity.docEditText);

                        return true;
                    case R.id.select_all_work_menu:

                        mainActivity.docEditText.selectAll();

                        return true;

                    case R.id.insert_text_work_menu:

                        insert(mainActivity.docEditText);

                        return true;
                    case R.id.repeat_work_menu:

                        repeat();

                        return true;
                    case R.id.insert_color_work_menu:

                        insertColor();

                        return true;
                    case R.id.add_indent_work_menu:

                        addIndent(mainActivity.docEditText);

                        return true;
                    default:
                        return false;
                }
            }
        });

        popupMenu.show();
    }

    private void access() {
        SpannableString selectAll = new SpannableString(mainActivity.getResources().getString(R.string.select_all));
        SpannableString textDate = new SpannableString(mainActivity.getResources().getString(R.string.insert_date));
        SpannableString textInsert = new SpannableString(mainActivity.getResources().getString(R.string.insert));
        SpannableString textRepeat = new SpannableString(mainActivity.getResources().getString(R.string.repeat));
        SpannableString textAddIndent = new SpannableString(mainActivity.getResources().getString(R.string.add_indent));
        SpannableString textInsertColor = new SpannableString(mainActivity.getResources().getString(R.string.insert_color));

        if (mainActivity.nameFileGetTopLevel != null && !mainActivity.nameFileGetTopLevel.getText().toString().equals("") && clipboardManager.getPrimaryClip() != null) {
            textInsert.setSpan(new ForegroundColorSpan(Color.argb(255, 255, 136, 0)), 0,textInsert.length(), 0);

            popupMenu.getMenu().findItem(R.id.insert_text_work_menu).setTitle(textInsert);
            popupMenu.getMenu().findItem(R.id.insert_text_work_menu).setEnabled(true);
        } else {
            textInsert.setSpan(new ForegroundColorSpan(Color.GRAY), 0,  textInsert.length(), 0);

            popupMenu.getMenu().findItem(R.id.insert_text_work_menu).setTitle(textInsert);
            popupMenu.getMenu().findItem(R.id.insert_text_work_menu).setEnabled(false);
        }

        if (mainActivity.nameFileGetTopLevel != null && !mainActivity.nameFileGetTopLevel.getText().toString().equals("")) {
            selectAll .setSpan(new ForegroundColorSpan(Color.argb(255, 255, 136, 0)), 0,selectAll.length(), 0);

            popupMenu.getMenu().findItem(R.id.select_all_work_menu).setTitle(selectAll);
            popupMenu.getMenu().findItem(R.id.select_all_work_menu).setEnabled(true);
        } else {
            selectAll .setSpan(new ForegroundColorSpan(Color.GRAY), 0,  selectAll.length(), 0);

            popupMenu.getMenu().findItem(R.id.select_all_work_menu).setTitle(selectAll);
            popupMenu.getMenu().findItem(R.id.select_all_work_menu).setEnabled(false);
        }

        if (mainActivity.nameFileGetTopLevel != null && !mainActivity.nameFileGetTopLevel.getText().toString().equals("")) {
            textDate.setSpan(new ForegroundColorSpan(Color.argb(255, 255, 136, 0)), 0,textDate.length(), 0);

            popupMenu.getMenu().findItem(R.id.insert_date_text_work_menu).setTitle(textDate);
            popupMenu.getMenu().findItem(R.id.insert_date_text_work_menu).setEnabled(true);

            textRepeat.setSpan(new ForegroundColorSpan(Color.argb(255, 255, 136, 0)), 0,textRepeat.length(), 0);

            popupMenu.getMenu().findItem(R.id.repeat_work_menu).setTitle(textRepeat);
            popupMenu.getMenu().findItem(R.id.repeat_work_menu).setEnabled(true);

            textAddIndent.setSpan(new ForegroundColorSpan(Color.argb(255, 255, 136, 0)), 0,textAddIndent.length(), 0);

            popupMenu.getMenu().findItem(R.id.add_indent_work_menu).setTitle(textAddIndent);
            popupMenu.getMenu().findItem(R.id.add_indent_work_menu).setEnabled(true);

            textInsertColor.setSpan(new ForegroundColorSpan(Color.argb(255, 255, 136, 0)), 0,textInsertColor.length(), 0);

            popupMenu.getMenu().findItem(R.id.insert_color_work_menu).setTitle(textInsertColor);
            popupMenu.getMenu().findItem(R.id.insert_color_work_menu).setEnabled(true);

        } else {
            textDate.setSpan(new ForegroundColorSpan(Color.GRAY), 0, textDate.length(), 0);

            popupMenu.getMenu().findItem(R.id.insert_date_text_work_menu).setTitle(textDate);
            popupMenu.getMenu().findItem(R.id.insert_date_text_work_menu).setEnabled(false);

            textRepeat.setSpan(new ForegroundColorSpan(Color.GRAY), 0, textRepeat.length(), 0);

            popupMenu.getMenu().findItem(R.id.repeat_work_menu).setTitle(textRepeat);
            popupMenu.getMenu().findItem(R.id.repeat_work_menu).setEnabled(false);

            textAddIndent.setSpan(new ForegroundColorSpan(Color.GRAY), 0,textAddIndent.length(), 0);

            popupMenu.getMenu().findItem(R.id.add_indent_work_menu).setTitle(textAddIndent);
            popupMenu.getMenu().findItem(R.id.add_indent_work_menu).setEnabled(false);

            textInsertColor.setSpan(new ForegroundColorSpan(Color.GRAY), 0,textInsertColor.length(), 0);

            popupMenu.getMenu().findItem(R.id.insert_color_work_menu).setTitle(textInsertColor);
            popupMenu.getMenu().findItem(R.id.insert_color_work_menu).setEnabled(false);
        }
    }

    private void repeat() {
        final Dialog dialog = new Dialog(mainActivity);
        dialog.setContentView(R.layout.repeatedly_dialog);

        resultRepeat = "";

        TextView textViewNumberOfTimes = dialog.findViewById(R.id.textViewNumberOfTimes);
        new Writer(textViewNumberOfTimes, textViewNumberOfTimes.getText().toString());

        textRepeat = dialog.findViewById(R.id.text_repeatedly);
        Button bRepeat = dialog.findViewById(R.id.save_repeatedly_text);
        Spinner numRepeat = dialog.findViewById(R.id.spinnerNumRepeat);

        ArrayAdapter<CharSequence> adapterNumRepeat = ArrayAdapter.createFromResource(mainActivity, R.array.repeatArray, android.R.layout.simple_spinner_item);
        arraySpinnerNumRepeat = new ArraySpinner(numRepeat, adapterNumRepeat);

        bRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        for (int i = Integer.parseInt(arraySpinnerNumRepeat.getResultSelected()); i > 0; i--) {
                            resultRepeat += textRepeat.getText().toString();
                        }

                            mainActivity.docEditText.getText().insert(mainActivity.docEditText.getSelectionStart(), resultRepeat);
                            dialog.cancel();

            }

        });

        dialog.show();
    }

    private void addIndent(EditText editText) {
        editText.getText().insert(startLine(editText.getText().toString(), editText.getSelectionStart()), "  ");
    }

    private int startLine(String str, int index) {
        for(int i = index; i >= 0; i--)
            if(i < str.length())
            if(str.charAt(i) == '\n' && i != index)
                return i + 1;
        return 0;
    }

    private void insertColor() {
        final Dialog dialogColorPick = new Dialog(mainActivity);

        dialogColorPick.setContentView(R.layout.color_pick_dialog);
        dialogColorPick.setCancelable(false);

        ColorPicker colorPicker = dialogColorPick.findViewById(R.id.colorPicker);
        colorPicker.setColor(colorDefault);

        Button insertButtonColorPickDialog = dialogColorPick.findViewById(R.id.buttonSave);
        insertButtonColorPickDialog.setText("Insert");

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
                colorDefault = color;
            }
        });

        insertButtonColorPickDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.docEditText.getText().insert(mainActivity.docEditText.getSelectionStart(), (toHex(colorDefault)));
                dialogColorPick.cancel();
            }
        });

        dialogColorPick.show();
    }

    String toHex(int color) {
        return String.format("#%06X", (0xFFFFFF & color));
    }

    private void insertDate(EditText editText) {
        Date currentTime = Calendar.getInstance().getTime();
        editText.getText().insert(editText.getSelectionStart(), currentTime.toString());
    }

    private void insert(EditText editText) {
        ClipData data = clipboardManager.getPrimaryClip();
        assert data != null;
        ClipData.Item item = data.getItemAt(0);
        String insetText = item.getText().toString();
        editText.getText().insert(editText.getSelectionStart(), insetText);
    }

}
