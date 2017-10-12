package com.example.gl152.testdemo.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by gl152 on 2017/3/28.
 */

public class PicDownloadAsyncTask extends AsyncTask<String, Integer, byte[]> {
    private ProgressDialog progressDialog;
    private CompleteDownlaod completeDownlaod;
    private Context context;

    public void setCompleteDownlaodListener(CompleteDownlaod completeDownlaod) {
        this.completeDownlaod = completeDownlaod;
    }

    public PicDownloadAsyncTask(Context context) {
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("正在下载");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();
    }

    @Override
    protected byte[] doInBackground(String... params) {
        ByteArrayOutputStream baos = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            baos = new ByteArrayOutputStream();
            int dataLength = 0;
            if (connection.getResponseCode() == 200) {
                inputStream = connection.getInputStream();
                dataLength = connection.getContentLength();
                byte[] bytes = new byte[1024];
                int len = 0;
                int currentLength = 0;
                int progress = 0;
                while ((len = inputStream.read(bytes)) != -1) {
                    baos.write(bytes, 0, len);
                    currentLength += len;
                    progress = (int) (currentLength / (double) dataLength * 100);
                    //添加进度条
                    publishProgress(progress);
                }

                return baos.toByteArray();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new byte[0];
    }

    // TODO 接受publishProgress（）方法传递过来的进度
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressDialog.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(byte[] bytes) {
        super.onPostExecute(bytes);
        if (bytes != null && completeDownlaod != null) {
            completeDownlaod.completeDownload(bytes);
            progressDialog.dismiss();
        } else {
            Toast.makeText(context, "网络异常，下载失败", Toast.LENGTH_SHORT).show();
        }
    }

    public interface CompleteDownlaod {
        void completeDownload(byte[] bytes);
    }

}
