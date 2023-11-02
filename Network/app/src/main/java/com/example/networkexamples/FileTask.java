package com.example.networkexamples;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

import androidx.core.content.FileProvider;

import java.io.File;

public class FileTask extends AsyncTask<String, Integer, String> {

    private static final String TAG = "DownloadFileTask";
    private String filename;
    private final Context context;
    long downloadId;

    FileTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        filename = "magazine" + params[0].substring(params[0].lastIndexOf('/')+1);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(params[0]));
        request.setTitle("Download " + filename);
        request.setDescription("Downloading PDF file");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/Magazines/" + filename);
        String env = Environment.DIRECTORY_DOWNLOADS.toString();
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        downloadId = manager.enqueue(request);

        BroadcastReceiver onComplete = new BroadcastReceiver() {
            public void onReceive(Context ctxt, Intent intent) {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/Magazines/" + filename);
                if (!file.exists()) {
                    AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                    alertDialog.setTitle("Ошибка загрузки");
                    alertDialog.setMessage("Журнала "+ filename + " нет");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //
                        }
                    });
                    alertDialog.show();
                    changeStatementsButtons(false, false);
                }
                else
                    changeStatementsButtons(true, true);

                context.unregisterReceiver(this);
            }
        };

        context.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        return null;
    }

    public void openPdfFile(String fileName) {
        File file=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/Magazines/" + fileName);
        Uri uri= FileProvider.getUriForFile(context,"com.example.networkexamples"+".provider",file);
        Intent i=new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(uri,"application/pdf");
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            context.startActivity(i);
        } catch (ActivityNotFoundException e) {
            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Response");
            alertDialog.setMessage("No application available to view PDF");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //
                }
            });
            alertDialog.show();
        }
    }

    public void deletePdffile(String fileName) {
        File file =new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/Magazines/" + fileName);
        file.delete();
        changeStatementsButtons(false, false);
    }

    private void changeStatementsButtons(
                                         boolean isSee,
                                         boolean isDelete) {
        ((SecondActivity)context).btnSee.setEnabled(isSee);
        ((SecondActivity)context).btnDelete.setEnabled(isDelete);
    }
    @Override
    protected void onProgressUpdate(Integer... progress) {
        // Здесь можно обновлять прогресс загрузки файла
    }

    @Override
    protected void onPostExecute(String fileUrl) {
        // Здесь можно выполнить действия после завершения загрузки файла
    }
}