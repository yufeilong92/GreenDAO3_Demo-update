package com.inst.greendao3_demo.app;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.db.DbHelp
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/6/5 10:21
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class DatabaseContext extends ContextWrapper {

    public DatabaseContext(Context base) {
        super(base);
    }

    /**
     * 获得数据库路径，如果不存在，则自动创建
     */
    @Override
    public File getDatabasePath(String name) {
        //判断是否存在sd卡
        boolean sdExist = android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState());
        if (!sdExist) {//如果不存在,
            Log.e("SD卡管理：", "SD卡不存在，请加载SD卡");
            return super.getDatabasePath(name);
        }

        //如果存在
        //获取sd卡路径
        //可移除的存储介质（例如 SD 卡），需要写入特定目录/storage/sdcard1/Android/data/包名/。
        String externalSDCardPath =  android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
        if (!TextUtils.isEmpty(externalSDCardPath)) {
            StringBuilder dirPath = new StringBuilder();

            dirPath.append(externalSDCardPath).append(File.separator).append("a/databases/");

            String path = dirPath.toString().trim();
            File saveDir = new File(path);
            if (!saveDir.exists()) {
                getExternalFilesDir(null); // 生成包名目录
                saveDir.mkdirs();//创建下载目录
            }
            //数据库文件是否创建成功
            boolean isFileCreateSuccess = false;
            //设置下载存储目录
            //数据库文件是否创建成功
            //判断文件是否存在，不存在则创建该文件
            StringBuilder append = dirPath.append(name);
            File dbFile = new File(append.toString().trim());
            if (!dbFile.exists()) {
                try {
                    isFileCreateSuccess = dbFile.createNewFile();//创建文件
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                isFileCreateSuccess = true;
            }
            //返回数据库文件对象
            if (isFileCreateSuccess)
                return dbFile;
            else{
                return super.getDatabasePath(name);
            }
        }
        //如果没有可移除的存储介质（例如 SD 卡），那么一定有内部（不可移除）存储介质可用，都不可用的情况在前面判断过了。
        String pathname =  android.os.Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "a/databases/";
        File saveDir = new File(pathname);
        //数据库文件是否创建成功
        boolean isFileCreateSuccess = false;
        if (!saveDir.exists()) {
            saveDir.mkdirs();//创建下载目录
        }
        //数据库文件是否创建成功
        //判断文件是否存在，不存在则创建该文件
        String pathname1 =  android.os.Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "a/databases/" + name;
        File dbFile = new File(pathname1);
        if (!dbFile.exists()) {
            try {
                isFileCreateSuccess = dbFile.createNewFile();//创建文件
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            isFileCreateSuccess = true;
        }
        //返回数据库文件对象
        if (isFileCreateSuccess)
            return dbFile;
        else{
            return super.getDatabasePath(name);
        }
    /*    String dbDir = android.os.Environment.getExternalStorageDirectory().toString();
        dbDir += "/xuechuan";
        dbDir += "/databases";//数据库所在目录
        String dbPath = dbDir + "/" + name;//数据库路径
        //判断目录是否存在，不存在则创建该目录
        File dirFile = new File(dbDir);
        if (!dirFile.exists())
            dirFile.mkdirs();
        //数据库文件是否创建成功
        //判断文件是否存在，不存在则创建该文件
        File dbFile = new File(dbPath);
        //数据库文件是否创建成功
        boolean isFileCreateSuccess = false;
        if (!dbFile.exists()) {
            try {
                isFileCreateSuccess = dbFile.createNewFile();//创建文件
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            isFileCreateSuccess = true;
        }
        //返回数据库文件对象
        if (isFileCreateSuccess)
            return dbFile;
        else{
            return super.getDatabasePath(name);
        }*/

    }


    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode,
                                               SQLiteDatabase.CursorFactory factory) {
        SQLiteDatabase result = SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), factory);
        return result;
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory,
                                               DatabaseErrorHandler errorHandler) {
        SQLiteDatabase result = SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), factory);
        return result;
    }
}