package id.sam.postapibiodatadiri.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import id.sam.postapibiodatadiri.MainActivity;
import id.sam.postapibiodatadiri.R;
import id.sam.postapibiodatadiri.model.getall.Biodatum;

public class AdapterListSimple extends RecyclerView.Adapter<AdapterListSimple.ViewHolder> {

    List<Biodatum> data;
    Context context;
    private OnItemClickListener mOnItemClickListener;

    public AdapterListSimple(Context context, List<Biodatum> data){
        this.data = data;
        this.context = context;
    }

    public  interface  OnItemClickListener{
        void onItemClick(@NonNull View view, Biodatum obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;

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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

//
        holder.txtNama.setText(data.get(position).getNama());
        holder.txtAlamat.setText(data.get(position).getAlamat());
        holder.txtTelepon.setText(data.get(position).getTelepon());
        holder.txtLat.setText(data.get(position).getLat());
        holder.txtLon.setText(data.get(position).getLon());

        String image = data.get(position).getPhoto();
        Picasso.get().load(image).resize(500, 500).centerCrop().into(holder.imgPhoto);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                CharSequence [] menupilih = {"Edit", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
                dialog.setTitle("Pilih Aksi");
                dialog.setItems(menupilih, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent myIntent = new Intent(context, MainActivity.class);
                                myIntent.putExtra("flag", 110);
                                myIntent.putExtra("id", data.get(holder.getAdapterPosition()).getId());
                                myIntent.putExtra("nama", data.get(holder.getAdapterPosition()).getNama());
                                myIntent.putExtra("alamat", data.get(holder.getAdapterPosition()).getAlamat());
                                myIntent.putExtra("telepon", data.get(holder.getAdapterPosition()).getTelepon());
                                myIntent.putExtra("lat", data.get(holder.getAdapterPosition()).getLat());
                                myIntent.putExtra("lon", data.get(holder.getAdapterPosition()).getLon());
                                myIntent.putExtra("photo", data.get(holder.getAdapterPosition()).getPhoto());
                                context.startActivity(myIntent);
                                break;

                            case 1:
                                if (mOnItemClickListener != null) {
                                    mOnItemClickListener.onItemClick(view, data.get(position), position);
                                }
                                data.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeRemoved(position, data.size());
                                break;
                        }
                    }
                });
                dialog.create();
                dialog.show();
            }
        });
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
