package com.example.treasure.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.treasure.model.Event;
import com.example.treasure.R;
import com.example.treasure.model.Feeling;
import com.example.treasure.util.FeelingUtils;

import org.w3c.dom.Text;

import java.util.List;

public class FeelingRecyclerAdapter extends RecyclerView.Adapter<FeelingRecyclerAdapter.ViewHolder> {
    private int layout;
    private List<Feeling> feelingList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewEntryTime;
        private final TextView textViewEntryFace;
        private final TextView textViewEntryText;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            textViewEntryTime = (TextView) view.findViewById(R.id.time_entry);
            textViewEntryFace = (TextView) view.findViewById(R.id.entry_face);
            textViewEntryText = (TextView) view.findViewById(R.id.entry_text);

        }

        public TextView getTextViewEntryTime() {
            return textViewEntryTime;
        }

        public TextView getTextViewEntryFace() {
            return textViewEntryFace;
        }

        public TextView getTextViewEntryText() {
            return textViewEntryText;
        }
    }

    public FeelingRecyclerAdapter(int layout, List<Feeling> feelingList) {
        this.layout = layout;
        this.feelingList = feelingList;
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        Feeling feeling = feelingList.get(position);
        holder.getTextViewEntryText().setText(feeling.getText());
        holder.getTextViewEntryFace().setText(FeelingUtils.getFeelingString(feeling.getFace())); // Usa il metodo di utilità
        holder.getTextViewEntryTime().setText(feeling.getTime()+" - "+feeling.getCondition());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return feelingList.size();
    }
}