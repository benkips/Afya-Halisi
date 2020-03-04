package www.mabnets.afyahalisi;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import maes.tech.intentanim.CustomIntent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class main extends Fragment {

    private ListView index_menulv;
    public main() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View mn=inflater.inflate(R.layout.fragment_main, container, false);
        index_menulv=(ListView)mn.findViewById(R.id.lvindex);
        String [] titlee=getResources().getStringArray(R.array.indexmenuone);
        String [] Description=getResources().getStringArray(R.array.indexmenutwo);
        SimpleAdapter adapter=new SimpleAdapter(getContext(),titlee,Description);
        index_menulv.setAdapter(adapter);
        index_menulv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle=new Bundle();

                switch(position){
                    case 0:{
                        Fragment fragmentmain=new materials();
                        getFragmentManager().beginTransaction().replace(R.id.framelayout,fragmentmain).addToBackStack(null).commit();
                        break;
                    }
                    case  1:{
                        Fragment fragmentmain=new weeklyupdates();
                        getFragmentManager().beginTransaction().replace(R.id.framelayout,fragmentmain).addToBackStack(null).commit();
                        break;

                    }
                    case 2:{
                        Fragment fragmentmain=new getnews();
                        getFragmentManager().beginTransaction().replace(R.id.framelayout,fragmentmain).addToBackStack(null).commit();
                        break;
                    }
                    case 3:{
                        Fragment fragmentmain=new journal();
                        getFragmentManager().beginTransaction().replace(R.id.framelayout,fragmentmain).addToBackStack(null).commit();
                        break;
                    }
                    case 4:{
                        Fragment fragmentmain=new public_portal();
                        getFragmentManager().beginTransaction().replace(R.id.framelayout,fragmentmain).addToBackStack(null).commit();
                        break;
                    }

                }
            }
        });
        return mn;
    }
    public class SimpleAdapter extends BaseAdapter {
        private Context mcontext;
        private LayoutInflater layoutInflater;
        private TextView title,description;
        private String [] titleArray;
        private String [] Description;
        private ImageView imv;

        public SimpleAdapter(Context context,String [] title ,String [] description){
            mcontext=context;
            titleArray=title;
            Description=description;
            layoutInflater=LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return titleArray.length;

        }

        @Override
        public Object getItem(int i) {
            return titleArray[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(view==null){
                view=layoutInflater.inflate(R.layout.maininf,null);

            }
            title=(TextView)view.findViewById(R.id.tvtitleindex_menu);
            description=(TextView)view.findViewById(R.id.tvdescindex_menu);
            imv=(ImageView)view.findViewById(R.id.ivindex_menu);
            title.setText(titleArray[i]);
            description.setText(Description[i]);
            if(titleArray[i].equalsIgnoreCase("Training materials")){
                imv.setImageResource(R.drawable.training);
            }else if(titleArray[i].equalsIgnoreCase("Weekly medical updates")){
                imv.setImageResource(R.drawable.calendar);
            }else  if(titleArray[i].equalsIgnoreCase("Medical news")){
                imv.setImageResource(R.drawable.newsicon);
            }else  if(titleArray[i].equalsIgnoreCase("Library and journals")){
                imv.setImageResource(R.drawable.libraryicon);
            }else {
                imv.setImageResource(R.drawable.portalicon);
            }
            return view;

        }
    }

}
