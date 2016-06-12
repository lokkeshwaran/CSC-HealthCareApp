package andyfolders.com.csc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Servicesfrag extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_servicesfrag, container,
                false);


       Button button1=(Button)rootView.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getActivity(),FindNearHospital.class);
                startActivity(i);
                //getActivity().finish();
            }
        });

        Button button2=(Button)rootView.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getActivity(),FindnearbyPharmacy.class);
                startActivity(i);
            }
        });

        Button button3=(Button)rootView.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                MainActivity activity = (MainActivity) getActivity();
                String pid = activity.getMyData();
                Intent i = new Intent(getActivity(), book_appoinment.class);
                i.putExtra("pid", pid );
                startActivity(i);

            }
        });

        Button button4=(Button)rootView.findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                MainActivity activity = (MainActivity) getActivity();
                String pid = activity.getMyData();
                Intent i = new Intent(getActivity(), orderrefills.class);
                i.putExtra("pid", pid );
                startActivity(i);

            }
        });

        Button button7=(Button)rootView.findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:9444*****66"));
                startActivity(callIntent);
            }
        });

        Button button5=(Button)rootView.findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                String pid = activity.getMyData();
                Intent i = new Intent(getActivity(), symptoms.class);
                i.putExtra("pid", pid );
                startActivity(i);
            }
        });

        Button button6=(Button)rootView.findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                MainActivity activity = (MainActivity) getActivity();
                String pid = activity.getMyData();
                Intent i = new Intent(getActivity(), feedback.class);
                i.putExtra("pid", pid );
                startActivity(i);
            }
        });

        return rootView;


    }


}
