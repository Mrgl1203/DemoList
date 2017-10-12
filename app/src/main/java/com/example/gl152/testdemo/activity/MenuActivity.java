package com.example.gl152.testdemo.activity;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gl152.testdemo.R;
import com.example.gl152.testdemo.bean.JSONBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MenuActivity extends BaseActivity {
    private static final String TAG = "MenuActivity";
    @BindView(R.id.to1)
    TextView to1;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.addprogress)
    Button addprogress;
    int progress;
    @BindView(R.id.but_dialog)
    Button but_dialog;
    Dialog dialog;
    @BindView(R.id.async)
    Button async;
    @BindView(R.id.spinner)
    Spinner spinner;
    private static final String PATH = "http://khd.qingdaonews.com/shoujikehuduan/mdi_newslist180.php?&type=bd&num=20";
    @BindView(R.id.but_pop)
    Button butPop;
    @BindView(R.id.but_toast)
    Button butToast;
    @BindView(R.id.but_pwin)
    Button butPwin;
    @BindView(R.id.activity_menu)
    LinearLayout activityMenu;
    Toast toast;
    @BindView(R.id.but_file)
    Button butFile;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.but_getfile)
    Button butGetfile;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_menu;
    }

    @Override
    protected void init() {

        addprogress.setOnClickListener(addprogresslistener);
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        if (savedInstanceState != null) {
            progress = savedInstanceState.getInt("progress");
            progressbar.setProgress(progress);
            addprogress.setText(String.format("进度：%1$d", progress));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                showToast("点击了设置");
                break;
            case R.id.delete:
                showToast("点击了删除");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    View.OnClickListener addprogresslistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            progress += 5;
            if (progress >= 100) {
                progress = 100;
            }
            progressbar.setProgress(progress);
            addprogress.setText(String.format("进度：%1$d", progress));
        }
    };

    @OnClick({R.id.to1, R.id.but_dialog, R.id.async, R.id.but_pop, R.id.but_toast, R.id.but_pwin, R.id.but_file, R.id.but_getfile})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.to1:
                Intent intent1 = new Intent();
                intent1.setAction(Intent.ACTION_DIAL);
                intent1.setData(Uri.parse("tel://10086"));
                startActivity(intent1);
                break;
            case R.id.but_dialog:
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle("gl");
//                String[] colors = new String[]{"red", "green", "blue"};
//                builder.setItems(colors, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which) {
//                            case 0:
//                                but_dialog.setTextColor(Color.RED);
//                                break;
//                            case 1:
//                                but_dialog.setTextColor(Color.GREEN);
//                                break;
//                            case 2:
//                                but_dialog.setTextColor(Color.BLUE);
//                                break;
//                        }
//                    }
//                });
//                builder.show();
                if (dialog == null) {
                    dialog = new Dialog(this, R.style.colordialog);
                    View view1 = LayoutInflater.from(this).inflate(R.layout.dialog_1, null, false);
                    TextView red = (TextView) view1.findViewById(R.id.tvred);
                    TextView green = (TextView) view1.findViewById(R.id.tvgreen);
                    TextView blue = (TextView) view1.findViewById(R.id.tvblue);
                    red.setOnClickListener(coloronclicklistener);
                    green.setOnClickListener(coloronclicklistener);
                    blue.setOnClickListener(coloronclicklistener);
                    dialog.setContentView(view1);
                    dialog.setCancelable(false);
                    Window window = dialog.getWindow();
                    WindowManager.LayoutParams lp = window.getAttributes();
                    lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
                    lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    window.setAttributes(lp);
                    window.setGravity(Gravity.BOTTOM);
                }
                dialog.show();
                break;
            case R.id.async:
                MyAsync myAsync = new MyAsync(this, spinner);
                myAsync.execute(JSONBean.json);
                break;
            case R.id.but_pop:
                // 创建PopupMenu的步骤：
                // 1、new一个PopUpMenu：PopupMenu(context,
                // anchor)anchor:从哪个控件弹出popupmenu
                final PopupMenu popupMenu = new PopupMenu(MenuActivity.this, butPop);
                // 2、为popupMenu填充菜单布局
                popupMenu.inflate(R.menu.menu_list);
                // 4、为popupMenu添加点击事件
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_settings:
                                showToast("点击了设置");
                                break;
                            case R.id.delete:
                                showToast("点击了删除");
                                break;
                        }
                        return false;
                    }
                });
                // 3、显示弹出菜单

                popupMenu.show();
                break;
            case R.id.but_toast:
                if (toast == null) {
                    toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
                    View view1 = LayoutInflater.from(this).inflate(R.layout.toasr_view, null, false);
                    textView = (TextView) view1.findViewById(R.id.tvToast);
                    textView.setText("+" + i);
                    toast.setView(view1);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                } else {
                    textView.setText("+" + i);
                    i++;
                }
                toast.show();
                break;
            case R.id.but_pwin:
                View view2 = LayoutInflater.from(this).inflate(R.layout.toasr_view, null, false);
                PopupWindow popupWindow = new PopupWindow(view2, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setAnimationStyle(R.style.PopAnimation);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.RED));
                popupWindow.setFocusable(false);
                popupWindow.showAsDropDown(butPwin, 0, 0);
                break;
            case R.id.but_file:
                FileOutputStream fileOutputStream;
                String filecontent = etContent.getText().toString().trim();
                try {
                    fileOutputStream = openFileOutput("testdemofile", MODE_PRIVATE);
                    byte[] bytes = filecontent.getBytes();
                    fileOutputStream.write(bytes, 0, bytes.length);
                    fileOutputStream.flush();
                    etContent.setText("");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.but_getfile:
                /**把数据通过io流存在内存中（文件读取）*/

                FileInputStream fileInputStream;
                try {
                    fileInputStream = openFileInput("testdemofile");
                    int len =0;
                    byte[] buff = new byte[1024];
                    StringBuffer stringBuffer = new StringBuffer();
                    while ((len = fileInputStream.read(buff)) != -1) {
                        stringBuffer.append(new String(buff, 0, len));
                    }
                    etContent.setText(stringBuffer);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    TextView textView;
    int i;
    View.OnClickListener coloronclicklistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tvred:
                    but_dialog.setTextColor(Color.RED);
                    dialog.dismiss();
                    break;
                case R.id.tvgreen:
                    but_dialog.setTextColor(Color.GREEN);
                    dialog.dismiss();
                    break;
                case R.id.tvblue:
                    but_dialog.setTextColor(Color.BLUE);
                    dialog.dismiss();
                    break;

            }
        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putInt("progress", progress);
        super.onSaveInstanceState(outState, outPersistentState);
    }


    class MyAsync extends AsyncTask<String, Void, List<String>> {
        ProgressDialog progressDialog;
        Context context;
        Spinner spineer;

        public MyAsync(Context context, Spinner spineer) {
            this.context = context;
            this.spineer = spineer;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("提示");
            progressDialog.setMessage("正在加载");
            progressDialog.show();
        }

        @Override
        protected List<String> doInBackground(String... params) {

            List<String> list = new ArrayList<>();
            try {
                JSONObject jso = new JSONObject(params[0]);
                JSONArray jsa = (JSONArray) jso.get("data");
                for (int i = 0; i < jsa.length(); i++) {
                    JSONObject jso1 = (JSONObject) jsa.get(i);
                    String data = jso1.getString("subject");
                    list.add(data);

                }
                return list;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            if (strings != null && strings.size() > 0) {

                ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, strings);
                spineer.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }
    }

}
