package wadosm.brewingclient.currentschedule.recyled;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import wadosm.brewingclient.R;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    private List<ScheduleItem> scheduleItems;

    public ScheduleAdapter(List<ScheduleItem> scheduleItems) {
        this.scheduleItems = scheduleItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.current_schedule_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getContentTextView().setText(scheduleItems.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return scheduleItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView contentTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            contentTextView = itemView.findViewById(R.id.contentTextView);
        }

        public TextView getContentTextView() {
            return contentTextView;
        }
    }

}
