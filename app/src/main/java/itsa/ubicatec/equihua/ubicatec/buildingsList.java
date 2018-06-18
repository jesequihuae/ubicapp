package itsa.ubicatec.equihua.ubicatec;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import itsa.ubicatec.equihua.ubicatec.Adapters.AllBuildingsListAdapter;
import itsa.ubicatec.equihua.ubicatec.BD.BuildingsContract;
import itsa.ubicatec.equihua.ubicatec.BD.FoodbuildingContract;
import itsa.ubicatec.equihua.ubicatec.BD.UBT_DBHelper;
import itsa.ubicatec.equihua.ubicatec.Structures.AllBuildingsListPOJO;


public class buildingsList extends Fragment {

    private RecyclerView buildingListRV;
    private LinearLayoutManager linearManager;
    //private List<BuildingStructurePOJO> buildingsListSQLITE = new ArrayList<>();
    private List<AllBuildingsListPOJO> allBuildingsListSQLITE = new ArrayList<>();

    public buildingsList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_buildings_list, container, false);

        final FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        buildingListRV = (RecyclerView) rootView.findViewById(R.id.buildingList);
        linearManager = new LinearLayoutManager(getActivity());
        linearManager.setOrientation(LinearLayoutManager.VERTICAL);
        buildingListRV.setLayoutManager(linearManager);

        fab.hide();

        /*buildingListRV.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy > 0 ||dy<0 && fab.isShown())
                    fab.hide();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    fab.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });*/

        /******************************************************************************/
        UBT_DBHelper dbHelper = new UBT_DBHelper(getActivity().getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                BuildingsContract.BuildingsEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            allBuildingsListSQLITE.add(new AllBuildingsListPOJO(
                     cursor.getInt(cursor.getColumnIndex(BuildingsContract.BuildingsEntry.ID)),
                    true,
                    cursor.getString(cursor.getColumnIndex(BuildingsContract.BuildingsEntry.NAME)),
                    cursor.getString(cursor.getColumnIndex(BuildingsContract.BuildingsEntry.INFORMATION))
            ));
        }

        Cursor cursor2 = db.query(
                FoodbuildingContract.FoodBuildingEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        for(cursor2.moveToFirst(); !cursor2.isAfterLast(); cursor2.moveToNext()){
            allBuildingsListSQLITE.add(new AllBuildingsListPOJO(
                    cursor2.getInt(cursor2.getColumnIndex(FoodbuildingContract.FoodBuildingEntry.ID)),
                    false,
                    cursor2.getString(cursor2.getColumnIndex(FoodbuildingContract.FoodBuildingEntry.NAME)),
                    ""
            ));
        }

        AllBuildingsListAdapter adapter = new AllBuildingsListAdapter(allBuildingsListSQLITE);
        buildingListRV.setAdapter(adapter);

        return rootView;
    }

}
