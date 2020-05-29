package com.textredactor.textredactor;

import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

class RenameFile {

    private MainActivity mainActivity;
    private EditText renameDiEditText;
    private Dialog dialog;

    RenameFile(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    private void rename(File file, File newFile) {
        if (file.renameTo(newFile)) {
            Log.d("RenameFile", "Файл переименован успешно!");
        } else {
            Log.d("RenameFile", "Не получилось переименовать файл");
        }
    }

    private void brainRename(String firstUserNameFile) {

        if (renameDiEditText.getText().toString().length() > 1 && renameDiEditText.getText().toString().length() < 16) {

            File oldFile = new File(mainActivity.getFilesDir(), firstUserNameFile);
            File newFile = new File(mainActivity.getFilesDir(), renameDiEditText.getText().toString());

            if (!newFile.exists() && !newFile.isFile()) {
                rename(oldFile, newFile);
                Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.file_rename_successfully), Toast.LENGTH_SHORT).show();
                mainActivity.emptyModUpd();
                dialog.cancel();
                Log.d("RenameFile", "Диалог закрыт");
            } else {
                Log.d("RenameFile", "Файл переименовать не получилось, был найден с таки мже именем");
                Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.file_exists), Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.invalid_title_length), Toast.LENGTH_SHORT).show();
        }

    }


    void callDialog(final String firstUserNameFile) {
        dialog = new Dialog(mainActivity);
        dialog.setContentView(R.layout.rename_dialog);

        Button renameButton = dialog.findViewById(R.id.save_button_name);
        renameDiEditText = dialog.findViewById(R.id.enter_file_name_id);

        renameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("RenameFile", "Нажатие на кнопку");
                brainRename(firstUserNameFile);
            }
        });

        dialog.show();

        Log.d("RenameFile", "Показ диалога");

    }


}
