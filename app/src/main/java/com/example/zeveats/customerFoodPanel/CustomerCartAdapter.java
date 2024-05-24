package com.example.zeveats.customerFoodPanel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zeveats.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.mcdev.quantitizerlibrary.HorizontalQuantitizer;

import java.util.HashMap;
import java.util.List;

public class CustomerCartAdapter extends RecyclerView.Adapter<CustomerCartAdapter.ViewHolder> {

    private Context mcontext;
    private List<Cart> cartModellist;
    static int total = 0;

    public CustomerCartAdapter(Context context, List<Cart> cartModellist) {
        this.cartModellist = cartModellist;
        this.mcontext = context;
        total = 0;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.cart_placeorder, parent, false);
        return new CustomerCartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Cart cart = cartModellist.get(position);
        holder.dishname.setText(cart.getDishName());
        holder.PriceRs.setText("Price: ₹ " + cart.getPrice());
        holder.Qty.setText("× " + cart.getDishQuantity());
        holder.Totalrs.setText("Total: ₹ " + cart.getTotalprice());
        total += Integer.parseInt(cart.getTotalprice());
        int quantity = Integer.parseInt(cart.getDishQuantity());
        holder.elegantNumberButton.setValue(quantity);
        final int dishprice = Integer.parseInt(cart.getPrice());

        holder.elegantNumberButton.findViewById(com.mcdev.quantitizerlibrary.R.id.increase_ib).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int oldValue = holder.elegantNumberButton.getSelectedValue();
                int newValue = oldValue + 1;
                holder.elegantNumberButton.setValue(newValue);
                updateCart(cart, newValue, dishprice);
            }
        });

        holder.elegantNumberButton.findViewById(com.mcdev.quantitizerlibrary.R.id.decrease_ib).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int oldValue = holder.elegantNumberButton.getSelectedValue();
                if (oldValue > 0) {
                    int newValue = oldValue - 1;
                    holder.elegantNumberButton.setValue(newValue);
                    updateCart(cart, newValue, dishprice);
                }
            }
        });

        CustomerCartFragment.grandt.setText("Grand Total: ₹ " + total);
        FirebaseDatabase.getInstance().getReference("Cart").child("GrandTotal").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("GrandTotal").setValue(String.valueOf(total));
    }

    private void updateCart(Cart cart, int quantity, int dishprice) {
        int totalprice = quantity * dishprice;
        if (quantity != 0) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("DishID", cart.getDishID());
            hashMap.put("DishName", cart.getDishName());
            hashMap.put("DishQuantity", String.valueOf(quantity));
            hashMap.put("Price", String.valueOf(dishprice));
            hashMap.put("Totalprice", String.valueOf(totalprice));
            hashMap.put("ChefId", cart.getChefId());

            FirebaseDatabase.getInstance().getReference("Cart").child("CartItems").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(cart.getDishID()).setValue(hashMap);
        } else {
            FirebaseDatabase.getInstance().getReference("Cart").child("CartItems").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(cart.getDishID()).removeValue();
        }
    }

    @Override
    public int getItemCount() {
        return cartModellist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dishname, PriceRs, Qty, Totalrs;
        HorizontalQuantitizer elegantNumberButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dishname = itemView.findViewById(R.id.Dishname);
            PriceRs = itemView.findViewById(R.id.pricers);
            Qty = itemView.findViewById(R.id.qty);
            Totalrs = itemView.findViewById(R.id.totalrs);
            elegantNumberButton = itemView.findViewById(R.id.elegantbtn);
        }
    }
}

