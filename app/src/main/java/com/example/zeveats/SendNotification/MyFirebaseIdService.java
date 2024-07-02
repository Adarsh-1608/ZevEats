package com.example.zeveats.SendNotification;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFirebaseIdService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful()) {
                    String token = task.getResult();
                    Log.d("FCMToken", "Token: " + token);
                    if (firebaseUser != null) {
                        updateToken(token);
                    }
                } else {
                    Log.w("FCMToken", "Fetching FCM registration token failed", task.getException());
                }
            }
        });
    }

    private void updateToken(String refreshToken) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            Token token1 = new Token(refreshToken);
            FirebaseDatabase.getInstance().getReference("Tokens")
                    .child(firebaseUser.getUid()).setValue(token1)
                    .addOnSuccessListener(aVoid -> Log.d("FCMToken", "Token saved successfully"))
                    .addOnFailureListener(e -> Log.w("FCMToken", "Error saving token", e));
        }
    }

}
