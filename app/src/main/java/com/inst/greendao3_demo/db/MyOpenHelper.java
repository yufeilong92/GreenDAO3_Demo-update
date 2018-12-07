/*
******************* Copyright (c) ***********************\
**
**         (c) Copyright 2016, 蒋朋, china, sxkj. sd
**                  All Rights Reserved
**
**                 By(青岛世新科技有限公司)
**                    www.qdsxkj.com
**
**                       _oo0oo_
**                      o8888888o
**                      88" . "88
**                      (| -_- |)
**                      0\  =  /0
**                    ___/`---'\___
**                  .' \\|     |// '.
**                 / \\|||  :  |||// \
**                / _||||| -:- |||||- \
**               |   | \\\  -  /// |   |
**               | \_|  ''\---/''  |_/ |
**               \  .-\__  '-'  ___/-. /
**             ___'. .'  /--.--\  `. .'___
**          ."" '<  `.___\_<|>_/___.' >' "".
**         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
**         \  \ `_.   \_ __\ /__ _/   .-` /  /
**     =====`-.____`.___ \_____/___.-`___.-'=====
**                       `=---='
**
**
**     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
**
**               佛祖保佑         永无BUG
**
**
**                   南无本师释迦牟尼佛
**

**----------------------版本信息------------------------
** 版    本: V0.1
**
******************* End of Head **********************\
*/

package com.inst.greendao3_demo.db;

import android.content.Context;

import com.inst.greendao3_demo.dao.DaoMaster;
import com.inst.greendao3_demo.dao.StudentDao;
import com.socks.library.KLog;

import org.greenrobot.greendao.database.Database;

/**
 * 文 件 名: MyOpenHelper
 * 创 建 人: 蒋朋
 * 创建日期: 16-10-11 08:28
 * 邮    箱: jp19891017@gmail.com
 * 博    客: https://jp1017.github.io/
 * 描    述:
 * 修 改 人:
 * 修改时间：
 * 修改备注：
 */

public class MyOpenHelper extends DaoMaster.OpenHelper {
    public MyOpenHelper(Context context, String name) {
        super(context, name);
    }


    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        KLog.w("db version update from " + oldVersion + " to " + newVersion);

        switch (oldVersion) {
            case 1:

                //不能先删除表，否则数据都木了
//                StudentDao.dropTable(db, true);

                StudentDao.createTable(db, true);

                // 加入新字段 score
                db.execSQL("ALTER TABLE 'STUDENT' ADD 'SCORE' TEXT;");

                break;
        }

    }
}
