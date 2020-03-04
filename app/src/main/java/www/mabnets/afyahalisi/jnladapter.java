package www.mabnets.afyahalisi;

import android.content.Context;
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

public class jnladapter extends  RecyclerView.Adapter<jnladapter.jnlholder> {
    public ArrayList jnlist;
    public Context context;

    public jnladapter(ArrayList jnlist, Context context) {
        this.jnlist = jnlist;
        this.context = context;
    }

    @NonNull
    @Override
    public jnlholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jnlinf, parent, false);
        return new jnlholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull jnlholder holder, int position) {
        final jounalz jounalz=(jounalz)jnlist.get(position);
        holder.jnlst.setText(jounalz.title);
        holder.jnlscv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager=((AppCompatActivity)context).getSupportFragmentManager();
                Bundle bundle=new Bundle();
                bundle.putString("url",jounalz.url);
                Fragment fragmentc=new getjournals();
                fragmentc.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.framelayout,fragmentc).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(jnlist!=null){
            return  jnlist.size();
        }
        return 0;
    }

    public static  class jnlholder extends RecyclerView.ViewHolder {
        private CardView jnlscv;
        private TextView jnlst;
        public jnlholder(@NonNull View itemView) {
            super(itemView);
            jnlscv=(CardView)itemView.findViewById(R.id.cvjnl);
            jnlst=(TextView)itemView.findViewById(R.id.jnlt);
        }
    }
}
