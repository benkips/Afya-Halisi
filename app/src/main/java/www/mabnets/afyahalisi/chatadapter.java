package www.mabnets.afyahalisi;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class chatadapter extends RecyclerView.Adapter<chatadapter.chatholder> {
    private ArrayList chatlist;
    private Context context;

    public chatadapter(ArrayList chatlist, Context context) {
        this.chatlist = chatlist;
        this.context = context;
    }

    @NonNull
    @Override
    public chatholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatinf, parent, false);
        return new chatholder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull chatholder holder, int position) {
        final chatz chatz=(chatz)chatlist.get(position);
        holder.chatsendr.setText(chatz.sender);
        holder.chatxt.setText(chatz.message);
        holder.repliez.setText(chatz.replies+" replies");
        holder.xchatr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          /*      if(chatz.replies.equals("0")){
                    FragmentManager fragmentManager=((AppCompatActivity)context).getSupportFragmentManager();
                    Bundle bundle=new Bundle();
                    bundle.putString("sid",chatz.id);
                    Fragment fragmentc=new replychats();
                    fragmentc.setArguments(bundle);
                    fragmentManager.beginTransaction().replace(R.id.framelayout,fragmentc).addToBackStack(null).commit();
                }else{*/
                    FragmentManager fragmentManager=((AppCompatActivity)context).getSupportFragmentManager();
                    Bundle bundle=new Bundle();
                    bundle.putString("sid",chatz.id);
                    Fragment fragmentc=new replychats();
                    fragmentc.setArguments(bundle);
                    fragmentManager.beginTransaction().replace(R.id.framelayout,fragmentc).addToBackStack(null).commit();
                /*}*/
            }
        });


    }

    @Override
    public int getItemCount() {
        if(chatlist!=null){
            return  chatlist.size();
        }
        return 0;
    }

    public static class chatholder extends RecyclerView.ViewHolder {
        private CardView xchatr;
        private TextView chatxt;
        private TextView repliez;
        private TextView chatsendr;
        public chatholder(@NonNull View itemView) {
            super(itemView);
            chatxt=(TextView)itemView.findViewById(R.id.chtmsg);
            repliez=(TextView)itemView.findViewById(R.id.rplys);
            xchatr=(CardView)itemView.findViewById(R.id.cvxchats);
            chatsendr=(TextView)itemView.findViewById(R.id.chtsender);
        }
    }
}
