package com.example.gl152.testdemo.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.gl152.testdemo.R;
import com.example.gl152.testdemo.adapter.logGridAdapter;
import com.example.gl152.testdemo.bean.IntentString;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.example.gl152.testdemo.utils.PermissionUtils.PERMISSIONCODE;

public class AfterLoginActivity extends BaseActivity {
    private static final String TAG = "AfterLoginActivity";
    ExecutorService cachedThreadPool;
    ExecutorService fixedThreadPool;
    ScheduledExecutorService scheduledThreadPool;
    ExecutorService singleThreadExecutor;
    Observable observable;
    @BindView(R.id.gridview)
    GridView gridView;
    private List<String> data = new ArrayList<>();
    private String[] stringdata = {"cashebutton", "fixbutton", "schedulebutton", "singlebutton", "toone", "toVideo", "toNextPage", "toSound",
            "toView", "toPermission", "toWeb", "toMenu", "toPhoneContent", "toSD", "toZXing", "toRec", "toScroll","toDB","toHeart","Arg"};
    logGridAdapter logGridAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_after_login;
    }

    @Override
    protected void init() {
        initTitle("AfterLoginActivity");
        cachedThreadPool = Executors.newCachedThreadPool();
        fixedThreadPool = Executors.newFixedThreadPool(3);
        scheduledThreadPool = Executors.newScheduledThreadPool(5);
        singleThreadExecutor = Executors.newSingleThreadExecutor();
        initData();
    }

    private void initData() {
        for (int i = 0; i < stringdata.length; i++) {
            data.add(stringdata[i]);
        }
        logGridAdapter = new logGridAdapter(this);
        logGridAdapter.setData(data);
        gridView.setAdapter(logGridAdapter);



        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "onItemClick: ----------" + position);
                switch (position) {
                    case 0:
                        /**创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。示例代码如下：
                         * 线程池为无限大，当执行第二个任务时第一个任务已经完成，会复用执行第一个任务的线程，而不用每次新建线程。*/

                        for (int i = 0; i < 10; i++) {
                            final int index = i;
                            try {
                                Thread.sleep(index * 1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            cachedThreadPool.execute(new Runnable() {

                                @Override
                                public void run() {
                                    Log.e(TAG, "run:------------- " + index);
                                }
                            });
                        }
                        break;
                    case 1:
                        /**创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。示例代码如下：
                         * 因为线程池大小为3，每个任务输出index后sleep 2秒，所以每两秒打印3个数字。
                         定长线程池的大小最好根据系统资源进行设置。如Runtime.getRuntime().availableProcessors()。可参考PreloadDataCache。*/

                        for (int i = 0; i < 10; i++) {
                            final int index = i;
                            fixedThreadPool.execute(new Runnable() {

                                @Override
                                public void run() {
                                    try {
                                        Log.e(TAG, "fixedThreadPool:------------- " + index + "----系统资源---" + Runtime.getRuntime().availableProcessors());
                                        Thread.sleep(2000);
                                    } catch (InterruptedException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                        break;
                    case 2:
                        /**创建一个定长线程池，支持定时及周期性任务执行。延迟执行示例代码如下：
                         * 表示延迟3秒执行。
                         * 表示延迟1秒后每3秒执行一次。*/

//                scheduledThreadPool.schedule(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        Log.e(TAG, "scheduledThreadPool: delay 3 seconds");
//                    }
//                }, 3, TimeUnit.SECONDS);
                        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {

                            @Override
                            public void run() {
                                Log.e(TAG, "delay 1 seconds, and excute every 3 seconds");
                            }
                        }, 1, 3, TimeUnit.SECONDS);
                        break;
                    case 3:
                        /**创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。示例代码如下：
                         * 结果依次输出，相当于顺序执行各个任务。
                         现行大多数GUI程序都是单线程的。Android中单线程可用于数据库操作，文件操作，应用批量安装，应用批量删除等不适合并发但可能IO阻塞性及影响UI线程响应的操作。*/

                        for (int i = 0; i < 10; i++) {
                            final int index = i;
                            singleThreadExecutor.execute(new Runnable() {

                                @Override
                                public void run() {
                                    try {
                                        Log.e(TAG, "singleThreadExecutor:------------- " + index);
                                        Thread.sleep(2000);
                                    } catch (InterruptedException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                        break;
                    case 4:
                        startActivity(new Intent(AfterLoginActivity.this, OneActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(AfterLoginActivity.this, SearchActivity.class));
                        break;
                    case 6:
                        startActivity(new Intent(AfterLoginActivity.this, NextPageActivity.class));
                        break;
                    case 7:
                        startActivity(new Intent(AfterLoginActivity.this, SoundActivity.class));
                        break;
                    case 8:
                        startActivity(new Intent(AfterLoginActivity.this, DrawViewActivity.class));
                        break;
                    case 9:
                        getPermission();
                        break;
                    case 10:
                        Intent intent = new Intent(AfterLoginActivity.this, WebViewActivity.class);
                        intent.putExtra(IntentString.URL, "https://www.baidu.com/");
                        startActivity(intent);
                        break;
                    case 11:
                        startActivity(new Intent(AfterLoginActivity.this, MenuActivity.class));
                        break;
                    case 12:
                        startActivity(new Intent(AfterLoginActivity.this, PhoneContentActivity.class));
                        break;
                    case 13:
                        startActivity(new Intent(AfterLoginActivity.this, SDCardActivity.class));
                        break;
                    case 14:
                        startActivity(new Intent(AfterLoginActivity.this, ZXingActivity.class));
                        break;
                    case 15:
                        startActivity(new Intent(AfterLoginActivity.this, RecoderVideoActivity.class));
                        break;
                    case 16:
                        startActivity(new Intent(AfterLoginActivity.this, ScrollActivity.class));
                        break;
                    case 17:
                        startActivity(new Intent(AfterLoginActivity.this, DBActivity.class));
                        break;
                    case 18:
                        startActivity(new Intent(AfterLoginActivity.this, HeartActivity.class));
                        break;
                    case 19:
                        startActivity(new Intent(AfterLoginActivity.this, ArgActivity.class));
                        break;
                }
            }
        });
    }

    public void testrxjava() {
        observable.just("1", "2", "3").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                showToast(s);
            }
        });
    }


    public void LogOut() {
        getGlApplication().saveLoginInfo("", "", true);
        finish();
    }

    long clicktime = 0;


    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - clicktime > 2000) {
            showToast("再按一次退出");
            clicktime = System.currentTimeMillis();
        } else {
            LogOut();
        }
    }


    public void getPermission() {
        Log.i(TAG, "getPermission: 检查摄像头权限是否已经有效");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "getPermission: 用户还没有同意权限");
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                Log.i(TAG, "getPermission: 用户拒绝通过权限");
                final AlertDialog.Builder builder = new AlertDialog.Builder(AfterLoginActivity.this);
                builder.setMessage("打劫，Camera权限交出来！");
                builder.setPositiveButton("宁死不交", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        builder.create().dismiss();
                    }
                });

                builder.setNegativeButton("从了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(AfterLoginActivity.this, new String[]{Manifest.permission.CAMERA}, PERMISSIONCODE);
                        builder.create().dismiss();
                    }
                });

                builder.create().show();
            } else {
                //用户拒绝不在询问可以弹出对话框去设置里修改
            }
        } else {
            Log.i(TAG, "getPermission: 摄像权限有效，可以使用摄像头了");
            showToast(" 摄像权限有效，可以使用摄像头了");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONCODE) {
            Log.i(TAG, "onRequestPermissionsResult: 收到摄像头权限申请的结果");
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "onRequestPermissionsResult: 摄像头权限已经申请成功，可以展示摄像预览界面了");
            } else {
                Log.i(TAG, "onRequestPermissionsResult: 摄像头权限申请失败");
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }


}
