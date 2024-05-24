package com.example.zeveats.customerFoodPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zeveats.Customer;
import com.example.zeveats.CustomerFoodPanel_BottomNavigation;
import com.example.zeveats.R;
import com.example.zeveats.Chef;
import com.example.zeveats.chefFoodPanel.UpdateDishModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mcdev.quantitizerlibrary.HorizontalQuantitizer;

import java.util.HashMap;

public class OrderDish extends AppCompatActivity {
    String RandomId, ChefID;
    ImageView imageView;
    HorizontalQuantitizer additem;
    TextView Foodname, ChefName, ChefLoaction, FoodQuantity, FoodPrice, FoodDescription;
    DatabaseReference databaseReference, dataaa, chefdata, reference, data, dataref;
    String State, City, Sub, dishname;
    int dishprice;
    String custID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_dish);
        Foodname = findViewById(R.id.food_name);
        ChefName = findViewById(R.id.chef_name);
        ChefLoaction = findViewById(R.id.chef_location);
        FoodQuantity = findViewById(R.id.food_quantity);
        FoodPrice = findViewById(R.id.food_price);
        FoodDescription = findViewById(R.id.food_description);
        imageView = findViewById(R.id.image);
        additem = findViewById(R.id.number_btn);

        final String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dataaa = FirebaseDatabase.getInstance().getReference("Customer").child(userid);
        dataaa.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Customer cust = dataSnapshot.getValue(Customer.class);
                custID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                RandomId = getIntent().getStringExtra("FoodMenu");
                ChefID = getIntent().getStringExtra("ChefId");

                chefdata = FirebaseDatabase.getInstance().getReference("Chef").child(ChefID);
                chefdata.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Chef chef = dataSnapshot.getValue(Chef.class);
                        if (chef != null) {
                            State = chef.getState();
                            City = chef.getCity();
                            Sub = chef.getArea();

                            databaseReference = FirebaseDatabase.getInstance().getReference("FoodDetails").child(State).child(City).child(Sub).child(ChefID).child(RandomId);
                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    UpdateDishModel updateDishModel = dataSnapshot.getValue(UpdateDishModel.class);
                                    if (updateDishModel != null) {
                                        Foodname.setText(updateDishModel.getDishes());
                                        String qua = "<b>" + "Quantity: " + "</b>" + updateDishModel.getQuantity();
                                        FoodQuantity.setText(Html.fromHtml(qua));
                                        String ss = "<b>" + "Description: " + "</b>" + updateDishModel.getDescription();
                                        FoodDescription.setText(Html.fromHtml(ss));
                                        String pri = "<b>" + "Price: ₹ " + "</b>" + updateDishModel.getPrice();
                                        FoodPrice.setText(Html.fromHtml(pri));
                                        Glide.with(OrderDish.this).load(updateDishModel.getImageURL()).into(imageView);
                                    } else {
                                        Toast.makeText(OrderDish.this, "Dish details not found", Toast.LENGTH_SHORT).show();
                                    }

                                    String name = "<b>" + "Chef Name: " + "</b>" + chef.getFirstName() + " " + chef.getLastName();
                                    ChefName.setText(Html.fromHtml(name));
                                    String loc = "<b>" + "Location: " + "</b>" + chef.getArea();
                                    ChefLoaction.setText(Html.fromHtml(loc));
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(OrderDish.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(OrderDish.this, "Chef details not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(OrderDish.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                additem.findViewById(com.mcdev.quantitizerlibrary.R.id.increase_ib).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int oldValue = additem.getSelectedValue();
                        int newValue = oldValue + 1;
                        additem.setValue(newValue);
                        handleQuantityChange(1);
                    }
                });

                additem.findViewById(com.mcdev.quantitizerlibrary.R.id.decrease_ib).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int oldValue = additem.getSelectedValue();
                        if (oldValue > 0) {
                            int newValue = oldValue - 1;
                            additem.setValue(newValue);
                        }
                        handleQuantityChange(-1);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(OrderDish.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleQuantityChange(int change) {
        dataref = FirebaseDatabase.getInstance().getReference("Cart").child("CartItems").child(custID);
        dataref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Cart cart1 = null;
                if (dataSnapshot.exists()) {
                    int totalcount = 0;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        totalcount++;
                    }
                    int i = 0;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        i++;
                        if (i == totalcount) {
                            cart1 = snapshot.getValue(Cart.class);
                        }
                    }

                    if (ChefID.equals(cart1.getChefId())) {
                        data = FirebaseDatabase.getInstance().getReference("FoodDetails").child(State).child(City).child(Sub).child(ChefID).child(RandomId);
                        data.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                UpdateDishModel update = dataSnapshot.getValue(UpdateDishModel.class);
                                if (update != null) {
                                    dishname = update.getDishes();
                                    dishprice = Integer.parseInt(update.getPrice());

                                    int num = additem.getSelectedValue() + change;
                                    int totalprice = num * dishprice;
                                    if (num > 0) {
                                        HashMap<String, String> hashMap = new HashMap<>();
                                        hashMap.put("DishName", dishname);
                                        hashMap.put("DishID", RandomId);
                                        hashMap.put("DishQuantity", String.valueOf(num));
                                        hashMap.put("Price", String.valueOf(dishprice));
                                        hashMap.put("Totalprice", String.valueOf(totalprice));
                                        hashMap.put("ChefId", ChefID);
                                        reference = FirebaseDatabase.getInstance().getReference("Cart").child("CartItems").child(custID).child(RandomId);
                                        reference.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(OrderDish.this, "Added to cart", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } else {
                                        FirebaseDatabase.getInstance().getReference("Cart").child("CartItems").child(custID).child(RandomId).removeValue();
                                    }
                                } else {
                                    Toast.makeText(OrderDish.this, "Dish details not found", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(OrderDish.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        showAlertForMultipleChefs();
                    }
                } else {
                    addNewItemToCart(change);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(OrderDish.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAlertForMultipleChefs() {
        AlertDialog.Builder builder = new AlertDialog.Builder(OrderDish.this);
        builder.setMessage("You can't add food items of multiple chefs at a time. Try to add items of the same chef.");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void addNewItemToCart(int change) {
        data = FirebaseDatabase.getInstance().getReference("FoodDetails").child(State).child(City).child(Sub).child(ChefID).child(RandomId);
        data.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UpdateDishModel update = dataSnapshot.getValue(UpdateDishModel.class);
                if (update != null) {
                    dishname = update.getDishes();
                    dishprice = Integer.parseInt(update.getPrice());

                    int num = additem.getSelectedValue() + change;
                    int totalprice = num * dishprice;
                    if (num > 0) {
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("DishName", dishname);
                        hashMap.put("DishID", RandomId);
                        hashMap.put("DishQuantity", String.valueOf(num));
                        hashMap.put("Price", String.valueOf(dishprice));
                        hashMap.put("Totalprice", String.valueOf(totalprice));
                        hashMap.put("ChefId", ChefID);
                        reference = FirebaseDatabase.getInstance().getReference("Cart").child("CartItems").child(custID).child(RandomId);
                        reference.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(OrderDish.this, "Added to cart", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        FirebaseDatabase.getInstance().getReference("Cart").child("CartItems").child(custID).child(RandomId).removeValue();
                    }
                } else {
                    Toast.makeText(OrderDish.this, "Dish details not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(OrderDish.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}