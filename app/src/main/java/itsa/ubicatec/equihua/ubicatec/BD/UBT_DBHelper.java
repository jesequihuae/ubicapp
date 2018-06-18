package itsa.ubicatec.equihua.ubicatec.BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jesus on 14/03/2018.
 *
 * THIS CLASS ALLOWS TO CREATE AND DELETE THE DATABASE FOR THIS APP
 */

public class UBT_DBHelper extends SQLiteOpenHelper {

    /* CURRENT DATABASE VERSION - INCREASE BY 1 EACH UPDATE */
    public static final int VERSION = 10;
    /* DATABASE NAME */
    public static final String BD_NAME = "ubtc.bd";

    public UBT_DBHelper(Context context) {
        super(context, BD_NAME, null, VERSION);
    }

    /* CREATING TABLES USING CREATION STRINGS FROM CONTRACTS */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(WeekDaysContract.SQL_CREATE_WEEKDAYS);
        sqLiteDatabase.execSQL(BuildingsContract.SQL_CREATE_BUILDINGS);
        sqLiteDatabase.execSQL(FoodbuildingContract.SQL_CREATE_FOODBUILDINGS);
        sqLiteDatabase.execSQL(DepartmentsContract.SQL_CREATE_DEPARTMENTS);
        sqLiteDatabase.execSQL(SchedulesContract.SQL_CREATE_SCHEDULES);
    }

    /* DELETING TABLES WHEN AN UPGRATING IS FIRED - USING DELETE STRINGS FROM CONTRACTS */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SchedulesContract.SQL_DELETE_SCHEDULES);
        sqLiteDatabase.execSQL(DepartmentsContract.SQL_DELETE_DEPARTMENTS);
        sqLiteDatabase.execSQL(FoodbuildingContract.SQL_DELETE_FOODBUILDINGS);
        sqLiteDatabase.execSQL(BuildingsContract.SQL_DELETE_BUILDINGS);
        sqLiteDatabase.execSQL(WeekDaysContract.SQL_DELETE_WEEKDAYS);
        onCreate(sqLiteDatabase);
    }
}
