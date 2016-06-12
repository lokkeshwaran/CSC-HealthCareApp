package andyfolders.com.csc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Recordsfrag extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_records, container,
                false);


       Button button9=(Button)rootView.findViewById(R.id.button9);
        button9.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                MainActivity activity = (MainActivity) getActivity();
                String pid = activity.getMyData();
                Intent i = new Intent(getActivity(), list_appoinments.class);
                i.putExtra("pid", pid );
                startActivity(i);
            }
        });

        Button button8=(Button)rootView.findViewById(R.id.button8);
        button8.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                MainActivity activity = (MainActivity) getActivity();
                String pid = activity.getMyData();
                Intent i = new Intent(getActivity(), list_mrecord.class);
                i.putExtra("pid", pid );
                startActivity(i);
            }
        });
/*
        Button button3=(Button)rootView.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getActivity(),M2SMainActivity.class);
                startActivity(i);
            }
        });

        Button button4=(Button)rootView.findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getActivity(), Results.class);
                startActivity(i);
                getActivity().finish();

            }
        });

        Button button7=(Button)rootView.findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getActivity(), annasite.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        Button button5=(Button)rootView.findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getActivity(), Internals.class);
                startActivity(i);
            }
        });

        Button button6=(Button)rootView.findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getActivity(), tnea.class);
                startActivity(i);
                getActivity().finish();
            }
        });
*/
        return rootView;


    }

}
