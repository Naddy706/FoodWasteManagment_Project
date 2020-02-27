package com.creativodevelopers.foodwastagemanagment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;


public class ShowEventFragment extends Fragment {


    private View showEvent;
    private RecyclerView showEventList;

    private DatabaseReference eventRef,interestedref,foodRef,myRef;
    private FirebaseAuth mAuth;
    ArrayList<String> mylist = new ArrayList<String>();
    ArrayList<String> key = new ArrayList<String>();
    ArrayList<String> votes = new ArrayList<String>();
    ArrayList<String> foodidd = new ArrayList<String>();
    String[] simpleArray,simpleArray2,simpleArray3,foodid;
    String TotalPeople ="";
    ProgressDialog dialog2;
    String a,b,c,d;
    int aa=0;
    Date date,date2;


    public ShowEventFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        showEvent=inflater.inflate(R.layout.fragment_show_event, container, false);
        eventRef= FirebaseDatabase.getInstance().getReference().child("Event");
        interestedref=FirebaseDatabase.getInstance().getReference().child("Interested");
        foodRef=FirebaseDatabase.getInstance().getReference().child("Food");
        mAuth=FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("interestedusers");
        dialog2=new ProgressDialog(getActivity());
        dialog2.setMessage("Loading...");
        dialog2.setCancelable(false);
        dialog2.show();
        showEventList =  showEvent.findViewById(R.id.event_list);
        showEventList.setLayoutManager(new LinearLayoutManager(getActivity()));


        return showEvent;
    }


    @Override
    public void onStart() {
        super.onStart();


        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Event>()
                .setQuery(eventRef, Event.class)
                .build();



        FirebaseRecyclerAdapter<Event,EventViewHolder> adapter= new FirebaseRecyclerAdapter<Event, EventViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final EventViewHolder holder, int position, @NonNull Event model) {

                final String eventId=getRef(position).getKey();

                eventRef.child(eventId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        dialog2.dismiss();

                        if(dataSnapshot.exists()){

                            if(dataSnapshot.hasChild("image")){

                                final String eventImage=dataSnapshot.child("image").getValue().toString();
                                final String tit=dataSnapshot.child("title").getValue().toString();
                                final String des=dataSnapshot.child("description").getValue().toString();
                                final String da=dataSnapshot.child("date").getValue().toString();
                                final String ti=dataSnapshot.child("time").getValue().toString();
                                final String l=dataSnapshot.child("location").getValue().toString();
                                d=dataSnapshot.getKey();

                                String ab=Calendar.getInstance().get(Calendar.YEAR)+"";
                                String ab2=Calendar.getInstance().get(Calendar.DATE)+"";
                                String ab3=Calendar.getInstance().get(Calendar.MONTH)+"";
                                String ab4=ab2+"-"+ab3+"-"+ab;
                                String dtStart = da;
                                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                                try {
                                     date = format.parse(dtStart);

                                    if (date.before(new Date())) {
                                       holder.itemView.setVisibility(View.INVISIBLE);
                                    }
                                    else {
                                        holder.itemView.setVisibility(View.VISIBLE);
                                        holder.Title.setText(tit);
                                        holder.Description.setText(des);
                                        holder.Location.setText(l);
                                        holder.Date.setText(da);
                                        holder.Time.setText(ti);
                                        holder.Id.setText(d);



                                        myRef.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot2) {
                                                for (DataSnapshot dsp : dataSnapshot2.getChildren()) {

                                                    a=dsp.child("event_key").getValue().toString();
                                                    b=dsp.child("food_key").getValue().toString();
                                                    c=dsp.child("user_key").getValue().toString();
                                                      // Toast.makeText(getActivity(), "key "+d, Toast.LENGTH_SHORT).show();
                                                    if(holder.Id.getText().equals(a) && c.equals(FirebaseAuth.getInstance().getUid().toString()))
                                                    {
                                                        holder.Interested.setText("Registered");
                                                        holder.Interested.setEnabled(false);
                                                        a="";
                                                        b="";
                                                        c="";
                                                        d="";
                                                    }
                                                    else{
                                                        Toast.makeText(getActivity(), "key "+d, Toast.LENGTH_SHORT).show();
                                                        holder.Interested.setText("Interested");
                                                        holder.Interested.setEnabled(true);
                                                        a="";
                                                        b="";
                                                        c="";
                                                        d="";
                                                    }

                                                }

                                            }

                                            @Override
                                            public void onCancelled(DatabaseError error) {

                                            }
                                        });

                                        Picasso.get().load(eventImage).placeholder(R.drawable.eventimage).into(holder.EventImage);


                                        holder.Interested.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                mylist.clear();
                                                key.clear();
                                                votes.clear();
                                                foodidd.clear();

                                                interestedUser(eventId);

                                            }
                                        });
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }




                            }
                            else {

                                String tit=dataSnapshot.child("title").getValue().toString();
                                String des=dataSnapshot.child("description").getValue().toString();
                                String da=dataSnapshot.child("date").getValue().toString();
                                String ti=dataSnapshot.child("time").getValue().toString();
                                String l=dataSnapshot.child("location").getValue().toString();

                                holder.Title.setText(tit);
                                holder.Description.setText(des);
                                holder.Location.setText(l);
                                holder.Date.setText(da);
                                holder.Time.setText(ti);
                            }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



            }

            @NonNull
            @Override
            public EventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.show_events_list,viewGroup,false);
                EventViewHolder viewHolder= new EventViewHolder(view);

                return viewHolder;

            }
        };

        showEventList.setAdapter(adapter);
        adapter.startListening();
    }

    private void interestedUser(final String eventId) {


                                foodRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {


                                        for(DataSnapshot data:dataSnapshot.getChildren()){


                                            String id=data.child("eventid").getValue().toString();
                                            if(id.equals(eventId))
                                            {
                                                foodidd.add(data.getKey());
                                                mylist.add(data.child("foodname").getValue().toString());
                                                key.add(data.child("eventid").getValue().toString());
                                                votes.add(data.child("Votes").getValue().toString());

                                            }
                                        }


                                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                        builder.setTitle("Choose food");


                                            foodid = new String[foodidd.size()];
                                            simpleArray = new String[ mylist.size() ];
                                            simpleArray2 = new String[key.size()];
                                            simpleArray3 = new String[votes.size()];

                                             foodidd.toArray( foodid );
                                             mylist.toArray( simpleArray );
                                             key.toArray(simpleArray2);
                                             votes.toArray(simpleArray3);

                                        builder.setItems(simpleArray, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                for(int i = 0; i < simpleArray.length; i++){
                                                    if(which == i){
                                                        interesteduser(simpleArray2[i],foodid[i],FirebaseAuth.getInstance().getCurrentUser().getUid());
                                                        Vote(simpleArray[i],simpleArray2[i],simpleArray3[i],i);
                                                    }

                                                }


                                                foodid=null;
                                                simpleArray=null;
                                                simpleArray2=null;
                                                simpleArray3=null;
                                                mylist.clear();
                                                key.clear();
                                                votes.clear();
                                                foodidd.clear();

                                            }
                                        });

                                        AlertDialog dialog = builder.create();
                                        dialog.show();


                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });


    }



    public void interesteduser(String event_key,String food_key,String userkey){

        DatabaseReference abc=FirebaseDatabase.getInstance().getReference().child("interestedusers");
        Map<String,String> map=new HashMap<>();

        map.put("event_key",event_key);
        map.put("food_key",food_key);
        map.put("user_key",userkey);

        abc.push().setValue(map);
    }


    private void Vote(String s, final String s1, String s2, final int index) {

        int result = Integer.parseInt(s2);
        result=result+1;
        String res = Integer.toString(result);

        HashMap<String,String> map = new HashMap<>();
        map.put("Votes",res);
        map.put("eventid",s1);
        map.put("foodname",s);

        foodRef.child(foodidd.get(index)).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {
                    Toast.makeText(getActivity(), " Voted Sucessfully", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), "failed to Vote food", Toast.LENGTH_SHORT).show();
                }
            }
        });

        foodidd.clear();



    }


    public static class EventViewHolder extends RecyclerView.ViewHolder {

        TextView Title, Description,Date , Time , Location, Id;
        Button Interested;
        ImageView EventImage;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            Title = itemView.findViewById(R.id.title);
            Description = itemView.findViewById(R.id.description);
            Date= itemView.findViewById(R.id.date);
            Time=itemView.findViewById(R.id.time);
            Location=itemView.findViewById(R.id.location);
            EventImage= itemView.findViewById(R.id.Eventimage);
            Interested=itemView.findViewById(R.id.interested);
            Id=itemView.findViewById(R.id.id);

        }
    }

}