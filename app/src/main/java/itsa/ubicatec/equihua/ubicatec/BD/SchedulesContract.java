package itsa.ubicatec.equihua.ubicatec.BD;

import android.provider.BaseColumns;

/**
 * Created by Jesus on 14/03/2018.
 *
 * THIS CLASS IS A TABLE REPRESENTATION FOR tblHorarios TABLE IN THE APP
 */

public class SchedulesContract {

    private SchedulesContract(){}

    /* TABLE VARIABLES */
    public class SchedulesEntry implements BaseColumns {
        public static final String TABLE_NAME       = "tblHorarios";
        public static final String TABLE_ID         = "idTable";
        public static final String ID               = "idHorario";
        public static final String INITIAL_TIME     = "tHoraInicial";
        public static final String END_TIME         = "tHoraFinal";
        public static final String BUILDING_ID      = "idEdificio";
        public static final String WEEKDAY_ID       = "idDiaSemana";
    }

    /* CREATE TABLE STRING */
    public static final String SQL_CREATE_SCHEDULES =
            "CREATE TABLE " + SchedulesEntry.TABLE_NAME + " (" +
                    SchedulesEntry.TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL , " +
                    SchedulesEntry.ID + " INTEGER NOT NULL, " +
                    SchedulesEntry.INITIAL_TIME + " TEXT NOT NULL, " +
                    SchedulesEntry.END_TIME + " TEXT NOT NULL, " +
                    SchedulesEntry.BUILDING_ID + " INTEGER NOT NULL, " +
                    SchedulesEntry.WEEKDAY_ID + " INTEGER NOT NULL, " +
                    "FOREIGN KEY (" + SchedulesEntry.BUILDING_ID + ") REFERENCES " + BuildingsContract.BuildingsEntry.TABLE_NAME + " (" + BuildingsContract.BuildingsEntry.ID + "), " +
                    "FOREIGN KEY (" + SchedulesEntry.WEEKDAY_ID + ") REFERENCES " + WeekDaysContract.WeekDaysEntry.TABLE_NAME + " (" + WeekDaysContract.WeekDaysEntry.TABLE_ID + "));";

    /* DELETE TABLE STRING */
    public static final String SQL_DELETE_SCHEDULES =
            "DROP TABLE IF EXISTS " + SchedulesEntry.TABLE_NAME;

}
