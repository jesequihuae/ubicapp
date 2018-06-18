package itsa.ubicatec.equihua.ubicatec;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import itsa.ubicatec.equihua.ubicatec.Adapters.DepartmentListAdapter;
import itsa.ubicatec.equihua.ubicatec.Adapters.ScheduleListAdapter;
import itsa.ubicatec.equihua.ubicatec.BD.BuildingsContract;
import itsa.ubicatec.equihua.ubicatec.BD.DepartmentsContract;
import itsa.ubicatec.equihua.ubicatec.BD.FoodbuildingContract;
import itsa.ubicatec.equihua.ubicatec.BD.SchedulesContract;
import itsa.ubicatec.equihua.ubicatec.BD.UBT_DBHelper;
import itsa.ubicatec.equihua.ubicatec.Structures.SchedulePOJO;
import itsa.ubicatec.equihua.ubicatec.Structures.departmentListPOJO;
import itsa.ubicatec.equihua.ubicatec.Structures.markerTag;


public class mapView extends Fragment implements OnMapReadyCallback {

    int MY_LOCATION_REQUEST_CODE = 100;
    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;

    public mapView() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_map_view, container, false);

        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = (MapView) mView.findViewById(R.id.map);
        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        /*googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(19.092642, -102.405371))
                .title("ITSA")
                .snippet("adad")
        );*/

        addMarkersBuildings(googleMap);
        addMarkersFoodbuildings(googleMap);

        CameraPosition ITSA = CameraPosition.builder().target(new LatLng(19.092642, -102.405371))
                .zoom(17)
                .bearing(0).tilt(0).build();

        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(ITSA));

        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.getUiSettings().setIndoorLevelPickerEnabled(true);

        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //if (marker.getTitle().equals("MyHome")) // if marker source is clicked
                //Toast.makeText(getActivity().getApplicationContext(), marker.getTag().toString(), Toast.LENGTH_SHORT).show();// display toast
                openModal(marker.getTitle(), (markerTag) marker.getTag());

                return true;
            }
        });

        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        } else {
            mGoogleMap.setMyLocationEnabled(true);
        }
    }

    public void addMarkersBuildings(GoogleMap googleMap) {
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

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            String coordsString = cursor.getString(cursor.getColumnIndex(BuildingsContract.BuildingsEntry.COORDS));
            String[] coords = coordsString.split(",");
            double lat = Double.parseDouble(coords[0]);
            double lng = Double.parseDouble(coords[1]);

            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lat, lng))
                    .title(cursor.getString(cursor.getColumnIndex(BuildingsContract.BuildingsEntry.NAME)))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_buildingcolor))
            ).setTag(new markerTag(
                    cursor.getInt(cursor.getColumnIndex(BuildingsContract.BuildingsEntry.ID)),
                    true,
                    cursor.getString(cursor.getColumnIndex(BuildingsContract.BuildingsEntry.IMAGE)),
                    cursor.getString(cursor.getColumnIndex(BuildingsContract.BuildingsEntry.INFORMATION))
            ));
        }
        //db.close();
    }

    public void addMarkersFoodbuildings(GoogleMap googleMap) {
        UBT_DBHelper dbHelper = new UBT_DBHelper(getActivity().getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                FoodbuildingContract.FoodBuildingEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            String coordsString = cursor.getString(cursor.getColumnIndex(FoodbuildingContract.FoodBuildingEntry.COORDS));
            String[] coords = coordsString.split(",");
            double lat = Double.parseDouble(coords[0]);
            double lng = Double.parseDouble(coords[1]);

            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lat, lng))
                    .title(cursor.getString(cursor.getColumnIndex(FoodbuildingContract.FoodBuildingEntry.NAME)))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_restaurant))
            ).setTag(new markerTag(
                    cursor.getInt(cursor.getColumnIndex(FoodbuildingContract.FoodBuildingEntry.ID)),
                    false,
                    cursor.getString(cursor.getColumnIndex(FoodbuildingContract.FoodBuildingEntry.IMAGE)),
                    ""
            ));
        }
        //db.close();
    }

    public void openModal(String tituloBuilding, markerTag mTag) {
        String carpeta = "";
        //Toast.makeText(getActivity().getApplicationContext(), mTag.getIDMarker() + "" + mTag.isBuilding(), Toast.LENGTH_SHORT).show();

        AlertDialog.Builder alerta = new AlertDialog.Builder(getContext());
        LayoutInflater inflate = getLayoutInflater();
        View layout_dialog = inflate.inflate(R.layout.dialog_info_building, null);

        TextView titulo = (TextView) layout_dialog.findViewById(R.id.titleBuilding);
        ImageView img = (ImageView) layout_dialog.findViewById(R.id.imageBuilding);
        TextView info = (TextView) layout_dialog.findViewById(R.id.textBuilding);
        RecyclerView buildingListRV = (RecyclerView) layout_dialog.findViewById(R.id.buildingListMap);

        titulo.setText(tituloBuilding);

        if (mTag.isBuilding()) {
            carpeta = "/EDIFICIOS";
            info.setText(mTag.getInformation());
            LinearLayoutManager linearManager;
            //List<BuildingStructurePOJO> buildingsListSQLITE = new ArrayList<>();
            List<departmentListPOJO> departmentsSQLITE = new ArrayList<>();

            linearManager = new LinearLayoutManager(getActivity().getApplicationContext());
            linearManager.setOrientation(LinearLayoutManager.VERTICAL);
            buildingListRV.setLayoutManager(linearManager);

            UBT_DBHelper dbHelper = new UBT_DBHelper(getActivity().getApplicationContext());
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String condicion = DepartmentsContract.DepartmentsEntry.BUILDING_ID + " = ?";
            String[] valoresCondicion = {mTag.getIDMarker() + ""};

            Cursor cursor = db.query(
                    DepartmentsContract.DepartmentsEntry.TABLE_NAME,
                    null,
                    condicion,
                    valoresCondicion,
                    null,
                    null,
                    null
            );

            Bitmap imgResponsable = null;

            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                try {
                    File dir = new File(getActivity().getApplicationContext().getFilesDir(), "/DEPARTAMENTOS");
                    FileInputStream fileInputStream =
                            new FileInputStream(dir + "/" + cursor.getString(cursor.getColumnIndex(DepartmentsContract.DepartmentsEntry.IMAGE)));
                    imgResponsable = BitmapFactory.decodeStream(fileInputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                    imgResponsable = BitmapFactory.decodeResource(getActivity().getApplicationContext().getResources(),
                            R.drawable.departmentdefault);
                }

                departmentsSQLITE.add(new departmentListPOJO(
                        cursor.getInt(cursor.getColumnIndex(DepartmentsContract.DepartmentsEntry.ID)),
                        imgResponsable,
                        cursor.getString(cursor.getColumnIndex(DepartmentsContract.DepartmentsEntry.DEPARTMENT)),
                        cursor.getString(cursor.getColumnIndex(DepartmentsContract.DepartmentsEntry.RESPONSABLE))
                ));
            }

            DepartmentListAdapter adapter = new DepartmentListAdapter(departmentsSQLITE);
            buildingListRV.setAdapter(adapter);
        } else {
            carpeta = "/LONCHERIAS";
            info.setVisibility(View.INVISIBLE);
            LinearLayoutManager linearManager;
            List<SchedulePOJO> horarioList = new ArrayList<>();

            linearManager = new LinearLayoutManager(getActivity().getApplicationContext());
            linearManager.setOrientation(LinearLayoutManager.VERTICAL);
            buildingListRV.setLayoutManager(linearManager);

            UBT_DBHelper dbHelper = new UBT_DBHelper(getActivity().getApplicationContext());
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String condicion = SchedulesContract.SchedulesEntry.BUILDING_ID + " = ?";
            String[] valoresCondicion = {mTag.getIDMarker() + ""};

            Cursor cursor = db.query(
                    SchedulesContract.SchedulesEntry.TABLE_NAME,
                    null,
                    condicion,
                    valoresCondicion,
                    null,
                    null,
                    null
            );

            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                horarioList.add(new SchedulePOJO(
                        cursor.getInt(cursor.getColumnIndex(SchedulesContract.SchedulesEntry.WEEKDAY_ID)),
                        cursor.getString(cursor.getColumnIndex(SchedulesContract.SchedulesEntry.INITIAL_TIME)),
                        cursor.getString(cursor.getColumnIndex(SchedulesContract.SchedulesEntry.END_TIME))
                ));
            }

            ScheduleListAdapter adapter = new ScheduleListAdapter(horarioList);
            buildingListRV.setAdapter(adapter);
        }

        /*******************************************************************/
        Bitmap bitmap = null;
        try {
            File dir = new File(getActivity().getApplicationContext().getFilesDir(), carpeta);
            FileInputStream fileInputStream =
                    new FileInputStream(dir + "/" + mTag.getImg());
            bitmap = BitmapFactory.decodeStream(fileInputStream);
            img.setImageBitmap(bitmap);
        } catch (Exception io) {
            io.printStackTrace();
        }

        alerta.setView(layout_dialog);
        AlertDialog dialog = alerta.create();
        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        if (requestCode == MY_LOCATION_REQUEST_CODE)
        {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {

            } else {
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity().getApplicationContext()).create();
                alertDialog.setTitle("No se aceptaron los permisos");
                alertDialog.setMessage("Alert message to be shown");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        }
    }

}
