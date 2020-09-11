package id.sam.postapibiodatadiri.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import id.sam.postapibiodatadiri.R;
import id.sam.postapibiodatadiri.model.getall.Biodatum;

public class AdapterListSimple extends RecyclerView.Adapter<AdapterListSimple.ViewHolder> {

    List<Biodatum> data;
    Context context;

    public AdapterListSimple(Context context, List<Biodatum> data){
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view ;

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_biodata, parent, false);


        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

//
        holder.txtNama.setText(data.get(position).getNama());
        holder.txtAlamat.setText(data.get(position).getAlamat());
        holder.txtTelepon.setText(data.get(position).getTelepon());
        holder.txtLat.setText(data.get(position).getLat());
        holder.txtLon.setText(data.get(position).getLon());

        String image = data.get(position).getPhoto();
        Picasso.get().load(image).into(holder.imgPhoto);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView txtNama, txtAlamat, txtTelepon, txtLat, txtLon;
        public ImageView imgPhoto;
        public CardView parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            txtNama = itemView.findViewById(R.id.txtNama);
            txtAlamat = itemView.findViewById(R.id.txtAlamat);
            txtTelepon = itemView.findViewById(R.id.txtTelepon);
            txtLat = itemView.findViewById(R.id.txtLat);
            txtLon = itemView.findViewById(R.id.txtLon);

            imgPhoto = itemView.findViewById(R.id.imgPhoto);
            parentLayout =itemView.findViewById(R.id.parentLayout);
        }
    }
}
