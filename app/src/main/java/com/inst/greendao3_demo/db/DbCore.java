/*
 ******************************* Copyright (c)*********************************\
 **
 **                 (c) Copyright 2015, 蒋朋, china, qd. sd
 **                          All Rights Reserved
 **
 **                           By(青岛)
 **
 **-----------------------------------版本信息------------------------------------
 ** 版    本: V0.1
 **
 **------------------------------------------------------------------------------
 ********************************End of Head************************************\
 */

package com.inst.greendao3_demo.db;

import android.content.Context;
import android.content.SharedPreferences;


import com.inst.greendao3_demo.app.DatabaseContext;
import com.inst.greendao3_demo.dao.DaoMaster;
import com.inst.greendao3_demo.dao.DaoSession;

import org.greenrobot.greendao.query.QueryBuilder;

import java.io.File;

/**
 * 文 件 名: DbCore
 * 说   明: 核心辅助类，用于获取DaoMaster和DaoSession
 * 参   考：http://blog.inet198.cn/?sbsujjbcy/article/details/48156683
 * 创 建 人: 蒋朋
 * 创建日期: 16-7-19 10:12
 * 邮   箱: jp19891017@gmail.com
 * 博   客: http://jp1017.github.io
 * 修改时间：
 * 修改备注：
 */
public class DbCore {
    private static final String DEFAULT_DB_NAME = "green-dao3.db";
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    private static Context mContext;
    private static String DB_NAME;
    private static SharedPreferences sp;

    public static void init(Context context, SharedPreferences edit) {
        sp = edit;
        init(context, DEFAULT_DB_NAME);
    }

    public static void init(Context context, String dbName) {
        if (context == null) {
            throw new IllegalArgumentException("context can't be null");
        }
        mContext = context.getApplicationContext();
        DB_NAME = dbName;
    }

    public static DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            //此处不可用 DaoMaster.DevOpenHelper, 那是开发辅助类，我们要自定义一个，方便升级
            DatabaseContext context = new DatabaseContext(mContext);
            DaoMaster.OpenHelper helper = new MyOpenHelper(context, DB_NAME);
            if (sp != null && sp.getString("delete", "-1").equals("delete")) {
                if (sp.getString("do", "do").equals("no")) {
                    String pathname = android.os.Environment.getExternalStorageDirectory().getAbsolutePath()
                            + File.separator + "a/databases/";
                    File file = new File(pathname);
                    if (file.isFile()) {
                        file.delete();
                    } else if (file.isDirectory()) {
                        File[] files = file.listFiles();
                        for (int i = 0; i < files.length; i++) {
                            files[i].delete();
                        }
                    }
                    sp.edit().putString("do", "do").commit();
                }
                daoMaster = new DaoMaster(helper.getEncryptedReadableDb("123"));
            } else {
                daoMaster = new DaoMaster(helper.getWritableDatabase());
            }
        }
        return daoMaster;
    }

    public static DaoSession getDaoSession() {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster();
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    public static void enableQueryBuilderLog() {
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }
}
