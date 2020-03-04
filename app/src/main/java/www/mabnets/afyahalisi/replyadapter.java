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

public class replyadapter extends RecyclerView.Adapter<replyadapter.replyholder> {
    private ArrayList replist;
    private Context context;

    public replyadapter(ArrayList replist, Context context) {
        this.replist = replist;
        this.context = context;
    }

    @NonNull
    @Override
    public replyholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.replyinf, parent, false);
        return new replyholder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull replyholder holder, int position) {
        final replies replies=(replies) replist.get(position);
        holder.chatsendr.setText(replies.sender);
        holder.chatxt.setText(replies.chat);
    }

    @Override
    public int getItemCount() {
        if (replist != null) {
        return replist.size();
        }
        return 0;
    }

    public static class  replyholder extends RecyclerView.ViewHolder {
        private TextView chatxt;
        private TextView chatsendr;
        public replyholder(@NonNull View itemView) {
            super(itemView);
            chatxt=(TextView)itemView.findViewById(R.id.chtmsgr);
            chatsendr=(TextView)itemView.findViewById(R.id.chtsenderr);
        }
    }
}
