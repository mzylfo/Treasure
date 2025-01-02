package com.example.treasure.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.treasure.model.Event;
import com.example.treasure.R;

import java.util.List;

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.ViewHolder> {
    private int layout;
    private List<Event> eventList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewStartDateEvent;
        private final TextView textViewTitleEvent;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            textViewStartDateEvent = (TextView) view.findViewById(R.id.textview_startdate_event);
            textViewTitleEvent = (TextView) view.findViewById(R.id.textview_title_event);

        }

        public TextView getTextViewStartDateEvent() {
            return textViewStartDateEvent;
        }

        public TextView getTextViewTitleEvent() {
            return textViewTitleEvent;
        }
    }

    public EventRecyclerAdapter(int layout, List<Event> eventList) {
        this.layout = layout;
        this.eventList = eventList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(layout, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getTextViewStartDateEvent().setText(eventList.get(position).getTime());
        viewHolder.getTextViewTitleEvent().setText(eventList.get(position).getTitle());
    }

    // Return the size of your dataset (invoked by the layout manager)
  @Override
  public int getItemCount() {
        return eventList.size();
   }
}