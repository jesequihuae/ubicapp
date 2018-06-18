package itsa.ubicatec.equihua.ubicatec.Adapters;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import itsa.ubicatec.equihua.ubicatec.R;
import itsa.ubicatec.equihua.ubicatec.Structures.departmentListPOJO;
import itsa.ubicatec.equihua.ubicatec.departmentInformation;

/**
 * Created by Jesus on 05/06/2018.
 */

public class DepartmentListAdapter extends RecyclerView.Adapter<DepartmentListAdapter.DepartmentViewHolder>  {

    private List<departmentListPOJO> DepartmentList;

    public DepartmentListAdapter(List<departmentListPOJO> departmentList) {
        this.DepartmentList = departmentList;
    }

    @Override
    public DepartmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.departament_item_list, parent, false);
        DepartmentViewHolder DVH = new DepartmentViewHolder(item);
        return DVH;
    }

    @Override
    public void onBindViewHolder(DepartmentViewHolder holder, int position) {
        holder.imgDepartment.setImageBitmap(DepartmentList.get(position).getImgDepartemt());
        holder.titleDepartment.setText(DepartmentList.get(position).getTitleDepartment());
        holder.responsableDepartment.setText(DepartmentList.get(position).getResponsableDepartment());
        holder.idDepartment.setText(DepartmentList.get(position).getIdDepartment()+"");
    }

    @Override
    public int getItemCount() {
        return DepartmentList.size();
    }

    public class DepartmentViewHolder extends RecyclerView.ViewHolder
    {
        CardView departmentStructureCV;
        ImageView imgDepartment;
        TextView titleDepartment;
        TextView responsableDepartment;
        TextView idDepartment;

        public DepartmentViewHolder(final View itemView) {
            super(itemView);
            departmentStructureCV = (CardView) itemView.findViewById(R.id.departmentStructureCV);
            idDepartment = (TextView) itemView.findViewById(R.id.idDepart);
            imgDepartment = (ImageView) itemView.findViewById(R.id.imgDepartmentList);
            titleDepartment = (TextView) itemView.findViewById(R.id.departmentName);
            responsableDepartment = (TextView) itemView.findViewById(R.id.departmentDescription);

            departmentStructureCV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(itemView.getContext(), departmentInformation.class);
                    i.putExtra("id", idDepartment.getText().toString());
                    itemView.getContext().startActivity(i);
                }
            });
        }
    }
}
