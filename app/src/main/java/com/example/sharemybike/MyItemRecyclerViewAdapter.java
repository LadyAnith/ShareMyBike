package com.example.sharemybike;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sharemybike.bikes.BikesContent;
import com.example.sharemybike.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.sharemybike.databinding.FragmentItemBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private Context context;

    public MyItemRecyclerViewAdapter(Context context){
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FragmentItemBinding view = FragmentItemBinding.bind(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item,null,false));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //Relleno los valores de los widget para cada una de las filas
        //holder.photo.setImageBitmap(BikesContent.ITEMS.get(position).getPhoto());
        holder.city.setText(BikesContent.ITEMS.get(position).getCity());
        holder.location.setText(BikesContent.ITEMS.get(position).getLocation());
        holder.owner.setText(BikesContent.ITEMS.get(position).getOwner());
        holder.description.setText(BikesContent.ITEMS.get(position).getDescription());
        holder.item = BikesContent.ITEMS.get(position);
        holder.mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectorIntent = new Intent(Intent.ACTION_SENDTO);
                selectorIntent.setData(Uri.parse("mailto:"));
                //Recojo la fecha seleccionada en el calendario
                String fecha = (String) BikeActivity.fecha.getText();

                final Intent emailIntent = new Intent(Intent.ACTION_SEND);
                //Destinatario
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{BikesContent.ITEMS.get(position).getEmail()});
                //Cabecera
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "I´d like to book your bike");
                //Cuerpo del e-mail
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Dear Mr/Mrs " + holder.owner.getText() +":\n" +
                        "I'd like to use your bike at " + holder.location.getText() + " (" + holder.city.getText()+ ")\n" +
                        "for the following date: " + fecha + "\n" +
                        "Can you confirm its availability?\n" +
                        "Kindest regards");
                emailIntent.setSelector( selectorIntent );

                context.startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });
    }

    @Override
    public int getItemCount() {
        return BikesContent.ITEMS.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //vinculo cada componente de cada fila del RecyclerView a una variable
        public BikesContent.Bike item;

        public final ImageView photo;
        public final TextView city;
        public final TextView location;
        public final TextView owner;
        public final TextView description;
        public final ImageButton mail;


        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            photo = binding.imgPhoto;
            city = binding.txtCity;
            location = binding.txtLocation;
            owner = binding.txtOwner;
            description = binding.txtDescription;
            mail = binding.btnMail;

        }

        @Override
        public String toString() {
            return super.toString() + " '" + BikesContent.ITEMS.toString() + "'";
        }
    }
}