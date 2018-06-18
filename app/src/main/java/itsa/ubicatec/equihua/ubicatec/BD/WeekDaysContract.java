package itsa.ubicatec.equihua.ubicatec.BD;

import android.provider.BaseColumns;

/**
 * Created by Jesus on 14/03/2018.
 * THIS CLASS IS A TABLE REPRESENTATION FOR tbldiasemana TABLE IN THE APP
 */

public class WeekDaysContract {

    private WeekDaysContract(){}

    /* TABLE VARIABLES */
    public class WeekDaysEntry implements BaseColumns {
        public static final String TABLE_NAME   = "tblDiaSemana";
        public static final String TABLE_ID     = "idTabla";
        public static final String ID           = "idDiaSemana";
        public static final String DAY          = "vDia";
    }

    /* CREATE TABLE STRING */
    public static final String SQL_CREATE_WEEKDAYS =
            "CREATE TABLE " + WeekDaysEntry.TABLE_NAME + " (" +
                    WeekDaysEntry.TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL, " +
                    WeekDaysEntry.ID + " INTEGER NOT NULL," +
                    WeekDaysEntry.DAY + " TEXT NOT NULL);";

    /* DELETE TABLE STRING */
    public static final String SQL_DELETE_WEEKDAYS =
            "DROP TABLE IF EXISTS " + WeekDaysEntry.TABLE_NAME;

}
