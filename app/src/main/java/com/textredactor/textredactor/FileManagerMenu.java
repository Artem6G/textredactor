package com.textredactor.textredactor;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import java.io.File;

class FileManagerMenu {

    private Send send;
    private CreateFile createFile;
    private OpenSaveInFile openSaveInFile;
    private DeleteFile deleteFile;
    private RenameFile renameFile;
    private PopupMenu fileManagerPopupMenu;
    private MainActivity mainActivity;

    FileManagerMenu(MainActivity mainActivity) {
        this.mainActivity = mainActivity;

        createFile = new CreateFile(mainActivity);
        openSaveInFile = new OpenSaveInFile(mainActivity);
        deleteFile = new DeleteFile(mainActivity);
        renameFile = new RenameFile(mainActivity);
        send = new Send(mainActivity);
    }

    // Данная функция, используется для установки действий у меню
    void showFileManagerMenu(View v) {
        fileManagerPopupMenu = new PopupMenu(mainActivity, v);
        fileManagerPopupMenu.inflate(R.menu.file_manager_menu);

        checkConditionFileManagerMenu();

        fileManagerPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.create_file:

                        createFile.createFile();

                        return true;
                    case R.id.duplicate_file:

                        File file_dup = new File(mainActivity.getFilesDir(), mainActivity.nameFileGetTopLevel.getText().toString());
                        DuplicateFile duplicateFile = new DuplicateFile(mainActivity);
                        duplicateFile.duplicate(file_dup);

                        return true;
                    case R.id.open_file:

                        openSaveInFile.filePickDialogCreate();

                        return true;
                    case R.id.send_file:

                        File fileSend = new File(mainActivity.getFilesDir(), mainActivity.nameFileGetTopLevel.getText().toString());
                        send.send(fileSend);

                        return true;
                    case R.id.delete_all_file:

                        deleteFile.callDialogAllDelete();

                        return true;
                    case R.id.save_file:

                        openSaveInFile.dirPickDialogCreate(mainActivity.docEditText.getText().toString(), mainActivity.nameFileGetTopLevel.getText().toString());

                        return true;
                    case R.id.delete_file:

                        File file = new File(mainActivity.getFilesDir(), mainActivity.nameFileGetTopLevel.getText().toString());
                        deleteFile.callDialogDelete(file);

                        return true;
                    case R.id.rename_file:
                        renameFile.callDialog(mainActivity.nameFileGetTopLevel.getText().toString());
                        return true;
                    case R.id.close_file:
                        mainActivity.emptyModUpd();
                        return true;
                    default:
                        return false;
                }
            }
        });

        fileManagerPopupMenu.show();
    }

    // Данная функция смотрит на некоторые условия и решает, давать ли пользователь доступ к item или нет, отвечает за FileManagerMenu
    private void checkConditionFileManagerMenu() {
        SpannableString textSend = new SpannableString("Send");
        SpannableString textRename = new SpannableString(mainActivity.getResources().getString(R.string.rename));
        SpannableString textDelete = new SpannableString(mainActivity.getResources().getString(R.string.delete));
        SpannableString textSaveTo = new SpannableString(mainActivity.getResources().getString(R.string.save_to));
        SpannableString textClose = new SpannableString(mainActivity.getResources().getString(R.string.close));
        SpannableString textDuplicate = new SpannableString(mainActivity.getResources().getString(R.string.duplicate));

        // Проверка на некоторые условия, в случае если они не выполняются, пользователь не сможет нажать на пункт
        if (mainActivity.nameFileGetTopLevel != null && !mainActivity.nameFileGetTopLevel.getText().toString().equals("")) {
            textSend.setSpan(new ForegroundColorSpan(Color.argb(255, 255, 136, 0)), 0, textSend.length(), 0);

            fileManagerPopupMenu.getMenu().findItem(R.id.send_file).setTitle(textSend);
            fileManagerPopupMenu.getMenu().findItem(R.id.send_file).setEnabled(true);
        } else {
            textSend.setSpan(new ForegroundColorSpan(Color.GRAY), 0, textSend.length(), 0);

            fileManagerPopupMenu.getMenu().findItem(R.id.send_file).setTitle(textSend);
            fileManagerPopupMenu.getMenu().findItem(R.id.send_file).setEnabled(false);
        }

        // Проверка на некоторые условия, в случае если они не выполняются, пользователь не сможет нажать на пункт
        if (mainActivity.nameFileGetTopLevel != null && !mainActivity.nameFileGetTopLevel.getText().toString().equals("")) {
            textDuplicate.setSpan(new ForegroundColorSpan(Color.argb(255, 255, 136, 0)), 0, textDuplicate.length(), 0);

            fileManagerPopupMenu.getMenu().findItem(R.id.duplicate_file).setTitle(textDuplicate);
            fileManagerPopupMenu.getMenu().findItem(R.id.duplicate_file).setEnabled(true);
        } else {
            textDuplicate.setSpan(new ForegroundColorSpan(Color.GRAY), 0, textDuplicate.length(), 0);

            fileManagerPopupMenu.getMenu().findItem(R.id.duplicate_file).setTitle(textDuplicate);
            fileManagerPopupMenu.getMenu().findItem(R.id.duplicate_file).setEnabled(false);
        }


        if (mainActivity.nameFileGetTopLevel != null && !mainActivity.nameFileGetTopLevel.getText().toString().equals("")) {
            textClose.setSpan(new ForegroundColorSpan(Color.argb(255, 255, 136, 0)), 0, textClose.length(), 0);

            fileManagerPopupMenu.getMenu().findItem(R.id.close_file).setTitle(textClose);
            fileManagerPopupMenu.getMenu().findItem(R.id.close_file).setEnabled(true);
        } else {
            textClose.setSpan(new ForegroundColorSpan(Color.GRAY), 0, textClose.length(), 0);

            fileManagerPopupMenu.getMenu().findItem(R.id.close_file).setTitle(textClose);
            fileManagerPopupMenu.getMenu().findItem(R.id.close_file).setEnabled(false);
        }


        if (mainActivity.nameFileGetTopLevel != null && !mainActivity.nameFileGetTopLevel.getText().toString().equals("")) {
            textSaveTo.setSpan(new ForegroundColorSpan(Color.argb(255, 255, 136, 0)), 0, textSaveTo.length(), 0);

            fileManagerPopupMenu.getMenu().findItem(R.id.save_file).setTitle(textSaveTo);
            fileManagerPopupMenu.getMenu().findItem(R.id.save_file).setEnabled(true);
        } else {
            textSaveTo.setSpan(new ForegroundColorSpan(Color.GRAY), 0, textSaveTo.length(), 0);

            fileManagerPopupMenu.getMenu().findItem(R.id.save_file).setTitle(textSaveTo);
            fileManagerPopupMenu.getMenu().findItem(R.id.save_file).setEnabled(false);
        }


        if (mainActivity.nameFileGetTopLevel != null && !mainActivity.nameFileGetTopLevel.getText().toString().equals("")) {
            textDelete.setSpan(new ForegroundColorSpan(Color.argb(255, 255, 136, 0)), 0, textDelete.length(), 0);

            fileManagerPopupMenu.getMenu().findItem(R.id.delete_file).setTitle(textDelete);
            fileManagerPopupMenu.getMenu().findItem(R.id.delete_file).setEnabled(true);
        } else {
            textDelete.setSpan(new ForegroundColorSpan(Color.GRAY), 0, textDelete.length(), 0);

            fileManagerPopupMenu.getMenu().findItem(R.id.delete_file).setTitle(textDelete);
            fileManagerPopupMenu.getMenu().findItem(R.id.delete_file).setEnabled(false);
        }


        if (mainActivity.nameFileGetTopLevel.getText() == "") {
            textRename.setSpan(new ForegroundColorSpan(Color.GRAY), 0, textRename.length(), 0);

            fileManagerPopupMenu.getMenu().findItem(R.id.rename_file).setTitle(textRename);
            fileManagerPopupMenu.getMenu().findItem(R.id.rename_file).setEnabled(false);
        } else {
            textRename.setSpan(new ForegroundColorSpan(Color.argb(255, 255, 136, 0)), 0, textRename.length(), 0);

            fileManagerPopupMenu.getMenu().findItem(R.id.rename_file).setTitle(textRename);
            fileManagerPopupMenu.getMenu().findItem(R.id.rename_file).setEnabled(true);
        }

    }

}