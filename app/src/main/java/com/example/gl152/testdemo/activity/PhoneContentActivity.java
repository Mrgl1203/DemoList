package com.example.gl152.testdemo.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.gl152.testdemo.R;
import com.example.gl152.testdemo.adapter.PhoneAdapter;
import com.example.gl152.testdemo.bean.PhoneBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class PhoneContentActivity extends BaseActivity {
    private static final String TAG = "PhoneContentActivity";
    public static final int READ_CONTACTS_CODE = 1;
    @BindView(R.id.listview)
    ListView listview;
    private List<PhoneBean> phoneBeanList;
    PhoneAdapter phoneAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_phone_content;
    }

    @Override
    protected void init() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, READ_CONTACTS_CODE);
            Log.e(TAG, "init: -------------------没有权限");
        } else {
            Log.e(TAG, "init:--------------有权限");
            initAdapter();
        }
    }

    public void initAdapter() {
        phoneBeanList = getPhoneContent(PhoneContentActivity.this);
        phoneAdapter = new PhoneAdapter(this);
        phoneAdapter.setData(phoneBeanList);
        listview.setAdapter(phoneAdapter);
    }

    /**
     * 获取手机联系人
     * 先查询contacts
     */
    public List<PhoneBean> getPhoneContent(Context context) {
        List<PhoneBean> list = new ArrayList<>();
        ContentResolver cr = context.getContentResolver();
        //Android.provider.ContactsContract.Contacts.CONTENT_URI 来获取联系人的ID和NAME
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            int displayNameColumn = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

            do {
                // 查看联系人有多少个号码，如果没有号码，返回0
                int phoneCount = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                if (phoneCount > 0) {
                    // 有手机号码才加入列表
                    PhoneBean bean = new PhoneBean();
                    bean.setDisplayname(cursor.getString(displayNameColumn));// 获得联系人姓名
                    String contactId = cursor.getString(idColumn);// 获得联系人的ID
                    Log.e(TAG, "getPhoneContent: cursor-------");
                    //android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI 获取联系人的电话号码
                    // 获得联系人的电话号码列表
                    Cursor phoneCursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);
                    if (phoneCursor != null && phoneCursor.moveToFirst()) {
                        do {
                            //遍历所有的联系人下面所有的电话号码
                            String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            bean.setPhoneNumber(phoneNumber);
                            Log.e(TAG, "getPhoneContent: phoneCursor-------");
                        } while (phoneCursor.moveToNext());
                    }
                    list.add(bean);
                }
            } while (cursor.moveToNext());
        } else {
            Log.e(TAG, "getPhoneContent: cursor有问题");
        }


        return list;
    }

    private Uri rawUri = ContactsContract.RawContacts.CONTENT_URI;
    private Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    private Uri emailUri = ContactsContract.CommonDataKinds.Email.CONTENT_URI;


    // 此方法通过查询安卓系统的手机联系人得到联系人手机，姓名等数据
    private List<Map<String, Object>> getContacts() {
        List<Map<String, Object>> contacts = new ArrayList<Map<String, Object>>();
        ContentResolver resolver = getContentResolver();
        // 到raw_contacts表中读取_id、display_name两个字段
        Cursor rawCursor = resolver.query(rawUri, new String[]{"_id", "display_name"}, null, null, null);
        if (rawCursor != null) {
            while (rawCursor.moveToNext()) {
                Map<String, Object> contact = new HashMap<String, Object>();
                int id = rawCursor.getInt(rawCursor.getColumnIndex("_id"));
                String display_name = rawCursor.getString(rawCursor.getColumnIndex("display_name"));
                contact.put("id", id);
                contact.put("display_name", display_name);

                // 根据联系人的_id即data表中的raw_contact_id,查询data表中的该联系人的phone信息
                Cursor phoneCursor = resolver.query(phoneUri, new String[]{"data1"}, "raw_contact_id=?",
                        new String[]{id + ""}, null);
                while (phoneCursor.moveToNext()) {
                    String phone = phoneCursor.getString(phoneCursor.getColumnIndex("data1"));
                    contact.put("phone", phone);
                }

                // 根据联系人id,查询data表中的该联系人的email信息
                Cursor emailCursor = resolver.query(emailUri, new String[]{"data1"}, "raw_contact_id=?",
                        new String[]{id + ""}, null);
                while (emailCursor.moveToNext()) {
                    String email = emailCursor.getString(emailCursor.getColumnIndex("data1"));
                    contact.put("email", email);
                }
                contacts.add(contact);
            }
        } else {
            Toast.makeText(this, "联系人不存在", Toast.LENGTH_SHORT).show();
        }
        return contacts;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case READ_CONTACTS_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initAdapter();
                    Log.e(TAG, "onRequestPermissionsResult: ------------同意了");
                } else {
                    Log.e(TAG, "onRequestPermissionsResult: -------------不同意");
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
