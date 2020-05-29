package com.textredactor.textredactor;

import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.util.Objects;

class DeleteFile {

    private MainActivity mainActivity;

    DeleteFile(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    private void deleteFile(File file) {
        if (file.delete()) {
            Log.d("DeleteFile", "Файл удален");
        } else {
            Log.d("DeleteFile", "Ошибка удаления");
        }
    }

    void callDialogDelete(final File file) {
        final Dialog dialog = new Dialog(mainActivity);
        dialog.setContentView(R.layout.delete_dialog);

        TextView animText = dialog.findViewById(R.id.deleteThisFileTextView);
        new Writer(animText, animText.getText().toString());

        TextView textView = dialog.findViewById(R.id.linkFileDelete);
        Button yep = dialog.findViewById(R.id.buttonDeleteImSure);
        Button no = dialog.findViewById(R.id.buttonCancelNoSure);

        textView.setText(file.getAbsolutePath());

        yep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFile(file);
                mainActivity.emptyModUpd();
                dialog.cancel();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();

    }

    void callDialogAllDelete() {
        final Dialog dialog = new Dialog(mainActivity);
        dialog.setContentView(R.layout.delete_all_dialog);

        TextView animText = dialog.findViewById(R.id.deleteAllTextView);
        new Writer(animText, animText.getText().toString());

        Button deleteAll = dialog.findViewById(R.id.button_delete_all_files);
        Button cancelDeleteAll = dialog.findViewById(R.id.button_cancel_delete_all_files);

        cancelDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAllFiles();
                dialog.cancel();
            }
        });

        dialog.show();
    }

    private void deleteAllFiles() {
        // Удаляет все файлы из корневой папки
        for (File myFile : Objects.requireNonNull(new File(mainActivity.getFilesDir().toString()).listFiles()))
            if (myFile.isFile()) if (myFile.delete()) Log.d("DeleteFile", "Файлы удалены");
            else Log.d("DeleteFile", "Не получилось удалить файлы");

        mainActivity.emptyModUpd();
    }

}
