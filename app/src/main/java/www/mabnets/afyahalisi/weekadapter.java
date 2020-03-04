package www.mabnets.afyahalisi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class weekadapter extends RecyclerView.Adapter<weekadapter.weekholder>{
    private ArrayList weeklist;
    private Context context;

    public weekadapter(ArrayList weeklist, Context context) {
        this.weeklist = weeklist;
        this.context = context;
    }

    @NonNull
    @Override
    public weekholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wkinf, parent, false);
        return new weekholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull weekholder holder, int position) {
        week week=(week)weeklist.get(position);
        if(week.status.contains("This week")){
            holder.wkk.setText(week.status);
        }else if(week.status.contains("Last week")){
            holder.wkk.setText(week.status);
        }else{
            holder.wkk.setText(week.status+ "week");
        }
        holder.wweektim.setText("Date:"+week.time);
        holder.wkm.setText(week.message);


    }

    @Override
    public int getItemCount() {
        if (weeklist!=null){
            return weeklist.size();
        }
        return 0;
    }

    public static  class weekholder extends RecyclerView.ViewHolder {
        private CardView cvwk;
        private TextView wkk;
        private TextView wweektim;
        private TextView wkm;
        public weekholder(@NonNull View itemView) {
            super(itemView);
            cvwk=(CardView)itemView.findViewById(R.id.cvwk);
            wkk=(TextView)itemView.findViewById(R.id.Wk);
            wweektim=(TextView)itemView.findViewById(R.id.wktime);
            wkm=(TextView)itemView.findViewById(R.id.weekmsg);
        }
    }
}
