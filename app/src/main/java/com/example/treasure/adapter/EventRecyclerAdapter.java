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
    private static OnItemClickListener listener;  // Aggiungi un listener

    // Crea un'interfaccia per il listener
    public interface OnItemClickListener {
        void onItemClick(Event event); // Metodo per gestire il clic sull'elemento
    }

    // Aggiungi un metodo per settare il listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewStartDateEvent;
        private final TextView textViewTitleEvent;

        public ViewHolder(View view) {
            super(view);
            textViewStartDateEvent = view.findViewById(R.id.textview_startdate_event);
            textViewTitleEvent = view.findViewById(R.id.textview_title_event);

            // Aggiungi un listener di clic
            view.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick((Event) v.getTag()); // Usa getTag per ottenere l'oggetto dell'evento
                }
            });
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

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(layout, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Event event = eventList.get(position);
        viewHolder.getTextViewStartDateEvent().setText(event.getTime());
        viewHolder.getTextViewTitleEvent().setText(event.getTitle());

        // Imposta l'oggetto Event come tag per ogni vista
        viewHolder.itemView.setTag(event);
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}