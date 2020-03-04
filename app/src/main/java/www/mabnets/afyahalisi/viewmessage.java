package www.mabnets.afyahalisi;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class viewmessage extends Fragment {
    private TextView mcategory;
    private TextView  msg;
    private String cate;
    private String msgg;


    public viewmessage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vmesssage= inflater.inflate(R.layout.fragment_viewmessage, container, false);

        mcategory=(TextView)vmesssage.findViewById(R.id.c);
        msg=(TextView)vmesssage.findViewById(R.id.m);

        Bundle bundle = getArguments();
        if (bundle != null) {
            cate = bundle.getString("category");
            msgg= bundle.getString("message");

        }
        mcategory.setText(cate);
        msg.setText(msgg);
        return vmesssage;
    }

}
