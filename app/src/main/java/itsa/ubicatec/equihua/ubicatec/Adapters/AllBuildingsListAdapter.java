package itsa.ubicatec.equihua.ubicatec.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import itsa.ubicatec.equihua.ubicatec.R;
import itsa.ubicatec.equihua.ubicatec.Structures.AllBuildingsListPOJO;
import itsa.ubicatec.equihua.ubicatec.buildingInformation;
import itsa.ubicatec.equihua.ubicatec.foodBuildingInformation;

/**
 * Created by Jesus on 05/06/2018.
 */

public class AllBuildingsListAdapter extends RecyclerView.Adapter<AllBuildingsListAdapter.AllBuildingsViewHolder>  {

    private Context context;
    private List<AllBuildingsListPOJO> allBuildingsList;

    public AllBuildingsListAdapter(List<AllBuildingsListPOJO> allBuildingsList) {
        this.allBuildingsList = allBuildingsList;
    }

    @Override
    public AllBuildingsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.building_structure, parent, false);
        AllBuildingsViewHolder AVH = new AllBuildingsViewHolder(item);
        return AVH;
    }

    @Override
    public void onBindViewHolder(AllBuildingsViewHolder holder, int position) {
        holder.buildingName.setText(allBuildingsList.get(position).getNameBuilding());
        holder.buildingDescription.setText(allBuildingsList.get(position).getInformationBuilding());
        holder.isBuilding.setText(allBuildingsList.get(position).isBuilding() == true ? "1" : "0");
        holder.idBuilding.setText(allBuildingsList.get(position).getIdBuilding()+"");
        if(allBuildingsList.get(position).isBuilding())
            holder.imgBuilding.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_buildingcolor));
        else
            holder.imgBuilding.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_restaurant));
    }

    @Override
    public int getItemCount() {
        return allBuildingsList.size();
    }

    public class AllBuildingsViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imgBuilding;
        CardView cardView;
        TextView buildingName;
        TextView  buildingDescription;
        TextView isBuilding;
        TextView idBuilding;

        public AllBuildingsViewHolder(final View itemView) {
            super(itemView);

            context = itemView.getContext();
            cardView = (CardView) itemView.findViewById(R.id.buildingStructure);
            buildingName = (TextView) itemView.findViewById(R.id.buildingName);
            buildingDescription = (TextView) itemView.findViewById(R.id.buildingDescription);
            imgBuilding = (ImageView) itemView.findViewById(R.id.imgBuilding);
            isBuilding = (TextView) itemView.findViewById(R.id.isBuilding);
            idBuilding = (TextView) itemView.findViewById(R.id.idBuilding);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isBuilding.getText().toString().equals("1")) {
                        Intent i = new Intent(itemView.getContext(), buildingInformation.class);
                        i.putExtra("id", idBuilding.getText().toString());
                        itemView.getContext().startActivity(i);
                    } else {
                        Intent i = new Intent(itemView.getContext(), foodBuildingInformation.class);
                        i.putExtra("id", idBuilding.getText().toString());
                        itemView.getContext().startActivity(i);
                    }
                }
            });
        }
    }
}
