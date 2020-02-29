package com.creativodevelopers.foodwastagemanagment;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class myevent_adapter extends ArrayAdapter {

    private final Activity context;

    private final List<String> subtitle;


    public myevent_adapter(Activity context, List<String> subtitle) {
        super(context, R.layout.show_events_list, subtitle);
        // TODO Auto-generated constructor stub

        this.context=context;

        this.subtitle=subtitle;


    }
    TextView Title, Description,Date , Time , Location, Id;
    Button Interested;
    ImageView EventImage;

    public View getView(final int position, final View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View itemView=inflater.inflate(R.layout.show_events_list, null,true);

        Title = itemView.findViewById(R.id.title);
        Description = itemView.findViewById(R.id.description);
        Date= itemView.findViewById(R.id.date);
        Time=itemView.findViewById(R.id.time);
        Location=itemView.findViewById(R.id.location);
        EventImage= itemView.findViewById(R.id.Eventimage);
        Interested=itemView.findViewById(R.id.interested);
        Id=itemView.findViewById(R.id.id);

        Title.setText(myeventsfragment.Title.get(position));
        Description.setText(myeventsfragment.Description.get(position));
        Date.setText(myeventsfragment.Date.get(position));
        Time.setText(myeventsfragment.Time.get(position));
        Location.setText(myeventsfragment.Location.get(position));
        Picasso.get().load(myeventsfragment.event_Image.get(position)).placeholder(R.drawable.eventimage).into(EventImage);

        Interested.setText("View Location");
        Interested.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i=new Intent(context,WaypointNavigationActivity.class);
        i.putExtra("lat", myeventsfragment.lat.get(position));
        i.putExtra("long", myeventsfragment.log.get(position));
        context.startActivity(i);


    }
    });
        return itemView;

    };
}