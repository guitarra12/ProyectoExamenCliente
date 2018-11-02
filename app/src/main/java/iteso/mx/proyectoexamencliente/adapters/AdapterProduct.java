package iteso.mx.proyectoexamencliente.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import iteso.mx.proyectoexamencliente.R;
import iteso.mx.proyectoexamencliente.beans.ItemProduct;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder> {
    private ArrayList<ItemProduct> mDataSet;
    private Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterProduct(Context context, ArrayList<ItemProduct> myDataSet) {
        mDataSet = myDataSet;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AdapterProduct.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_item_product, parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nombre.setText(mDataSet.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nombre;

        public ViewHolder(View v) {
            super(v);
            nombre = v.findViewById(R.id.item_product_name);
        }
    }
}
