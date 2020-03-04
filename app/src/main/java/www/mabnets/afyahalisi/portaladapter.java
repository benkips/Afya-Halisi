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

public class portaladapter  extends  RecyclerView.Adapter<portaladapter.portalholder>{
    private ArrayList portallist;
    private Context context;

    public portaladapter(ArrayList portallist, Context context) {
        this.portallist = portallist;
        this.context = context;
    }

    @NonNull
    @Override
    public portalholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.portalinf, parent, false);
        return new portalholder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull portalholder holder, int position) {
        final portal portal= (portal)portallist.get(position);
        holder.cvp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager=((AppCompatActivity)context).getSupportFragmentManager();
                Bundle bundle=new Bundle();
                bundle.putString("category",portal.category);
                Fragment fragmentc=new chats();
                fragmentc.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.framelayout,fragmentc).addToBackStack(null).commit();
            }
        });
        holder.txtcat.setText(portal.category+"\n\n"+portal.total+" conversations");

    }

    @Override
    public int getItemCount() {
        if(portallist!=null){
            return  portallist.size();
        }
        return 0;
    }

    public static class portalholder extends RecyclerView.ViewHolder {
        private TextView txtcat ;
        private CardView cvp;
        public portalholder(@NonNull View itemView) {
            super(itemView);
            cvp=(CardView)itemView.findViewById(R.id.cvportal);
            txtcat=(TextView) itemView.findViewById(R.id.catep);
        }
    }
}
