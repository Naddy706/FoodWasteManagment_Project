package com.creativodevelopers.foodwastagemanagment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class myeventsfragment extends Fragment {


    public static List<String> Title,Description,Location,Time,Date,event_Image,lat,log;

    public static myevent_adapter ad;
    ProgressDialog dialog;
    private DatabaseReference myeventlist;
    public static ListView list;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_my_events, container, false);
        dialog=new ProgressDialog(getActivity());
        dialog.setCancelable(false);
        dialog.setMessage("Loading...");
       // dialog.show();
        list=root.findViewById(R.id.list);
        Title =  new ArrayList<String>();
        Description =  new ArrayList<String>();
        Location =  new ArrayList<String>();
        Time =  new ArrayList<String>();
        lat =  new ArrayList<String>();
        log =  new ArrayList<String>();
        Date =  new ArrayList<String>();
        event_Image =  new ArrayList<String>();

        myeventlist= FirebaseDatabase.getInstance().getReference();
        ad=new myevent_adapter(getActivity(),Title);
        load_clients(root);

        return root;
    }

    public void load_clients(View root){


        myeventlist.child("interestedusers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    dialog.dismiss();
                    if (dsp.exists()) {

                        if(dsp.child("user_key").getValue().toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())) {
                            String event = dsp.child("event_key").getValue().toString();
                            myeventlist.child("Event").child(event).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    if (dataSnapshot.exists()) {

                                        String eventImage = dataSnapshot.child("image").getValue().toString();
                                        String description = dataSnapshot.child("description").getValue().toString();
                                        String title = dataSnapshot.child("title").getValue().toString();
                                        String loc = dataSnapshot.child("location").getValue().toString();
                                        String date = dataSnapshot.child("date").getValue().toString();
                                        String time = dataSnapshot.child("time").getValue().toString();
                                        String latt = dataSnapshot.child("lat").getValue().toString();
                                        String logg = dataSnapshot.child("long").getValue().toString();
                                        Title.add(title);
                                        Description.add(description);
                                        Location.add(loc);
                                        Date.add(date);
                                        Time.add(time);
                                        lat.add(latt);
                                        log.add(logg);
                                        event_Image.add(eventImage);
                                      ad.notifyDataSetChanged();

//                                        Picasso.get().load(eventImage).placeholder(R.drawable.eventimage).into(holder.EventImage);

                                    }


                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }

                    }


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        list.setAdapter(ad);

    }

}