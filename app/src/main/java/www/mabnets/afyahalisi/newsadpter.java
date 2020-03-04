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

public class newsadpter extends RecyclerView.Adapter<newsadpter.newsholder> {
    private ArrayList newslist;
    private Context context;

    public newsadpter(ArrayList newslist, Context context) {
        this.newslist = newslist;
        this.context = context;
    }

    @NonNull
    @Override
    public newsholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newsinf, parent, false);
        return new newsholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull newsholder holder, int position) {
        news news=(news)newslist.get(position);

        holder.tnws.setText(news.tittle);
        holder.tfrm.setText("From: "+news.sender);
        holder.tm.setText(news.message);
    }

    @Override
    public int getItemCount() {
        if(newslist!=null){
            return newslist.size();
        }
        return 0;
    }

    public static class newsholder extends RecyclerView.ViewHolder {
        private CardView cvns;
        private TextView tnws;
        private TextView tfrm;
        private TextView tm;
        public newsholder(@NonNull View itemView) {
            super(itemView);
            cvns=(CardView)itemView.findViewById(R.id.cvnews);
            tnws=(TextView)itemView.findViewById(R.id.tnews);
            tfrm=(TextView)itemView.findViewById(R.id.tfrom);
            tm=(TextView)itemView.findViewById(R.id.tmsg);
        }
    }
}
