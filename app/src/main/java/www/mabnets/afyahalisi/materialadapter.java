package www.mabnets.afyahalisi;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class materialadapter extends RecyclerView.Adapter<materialadapter.materialholder> {
    private ArrayList materiallst;
    private Context context;

    public materialadapter(ArrayList materiallst, Context context) {
        this.materiallst = materiallst;
        this.context = context;
    }

    @NonNull
    @Override
    public materialholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.materialinf, parent, false);
        return new materialholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull materialholder holder, int position) {
        final materialz materialz=(materialz)materiallst.get(position);
        holder.category.setText(materialz.category);
        holder.Title.setText(materialz.title);
        if(materialz.document.equals("")){
            holder.Document.setText("Instructions");

            holder.cvm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FragmentManager fragmentManager=((AppCompatActivity)context).getSupportFragmentManager();
                    Bundle bundle=new Bundle();
                    bundle.putString("message",materialz.mesage);
                    bundle.putString("category",materialz.category);
                    Fragment fragmentc=new viewmessage();
                    fragmentc.setArguments(bundle);
                    fragmentManager.beginTransaction().replace(R.id.framelayout,fragmentc).addToBackStack(null).commit();

                }
            });
        }else {
            holder.Document.setText(materialz.document);
            holder.cvm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fragmentManager=((AppCompatActivity)context).getSupportFragmentManager();
                    Bundle bundle=new Bundle();
                    bundle.putString("doc",materialz.document);
                    Fragment fragmentc=new getmaterials();
                    fragmentc.setArguments(bundle);
                    fragmentManager.beginTransaction().replace(R.id.framelayout,fragmentc).addToBackStack(null).commit();
                }
            });


        }


    }

    @Override
    public int getItemCount() {
        if(materiallst!=null){
            return materiallst.size();
        }
        return 0;
    }

    public  static  class  materialholder extends RecyclerView.ViewHolder {
        private CardView cvm;
        private TextView category;
        private TextView Title;
        private TextView Document;
        public materialholder(@NonNull View itemView) {
            super(itemView);
            cvm=(CardView)itemView.findViewById(R.id.cvmat);
            category=(TextView)itemView.findViewById(R.id.category);
            Title=(TextView)itemView.findViewById(R.id.tittle);
            Document=(TextView)itemView.findViewById(R.id.document);
        }
    }
}
