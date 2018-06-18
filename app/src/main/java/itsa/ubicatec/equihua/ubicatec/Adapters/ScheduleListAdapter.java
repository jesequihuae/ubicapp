package itsa.ubicatec.equihua.ubicatec.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import itsa.ubicatec.equihua.ubicatec.R;
import itsa.ubicatec.equihua.ubicatec.Structures.SchedulePOJO;

/**
 * Created by Jesus on 05/06/2018.
 */

public class ScheduleListAdapter extends RecyclerView.Adapter<ScheduleListAdapter.ScheduleViewHolder> {

    private List<SchedulePOJO> horariosList;

    public ScheduleListAdapter(List<SchedulePOJO> horariosList) {
        this.horariosList = horariosList;
    }

    @Override
    public ScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.weekdays_item_list, parent, false);
        ScheduleViewHolder SVH = new ScheduleViewHolder(item);
        return SVH;
    }

    @Override
    public void onBindViewHolder(ScheduleViewHolder holder, int position) {
        holder.scheduleDay.setText(horariosList.get(position).getDia());
        holder.scheduleTime.setText(horariosList.get(position).getHoraInicio() + " - " + horariosList.get(position).getHoraFinal());
    }

    @Override
    public int getItemCount() {
        return horariosList.size();
    }

    public class ScheduleViewHolder extends RecyclerView.ViewHolder {

        TextView scheduleDay;
        TextView scheduleTime;

        public ScheduleViewHolder(View itemView) {
            super(itemView);

            scheduleDay = (TextView) itemView.findViewById(R.id.scheduleDay);
            scheduleTime = (TextView) itemView.findViewById(R.id.scheduleTime);
        }
    }
}
