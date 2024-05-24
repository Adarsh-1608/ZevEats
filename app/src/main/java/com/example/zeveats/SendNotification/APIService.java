package com.example.zeveats.SendNotification;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

        @Headers(
                {
                        "Content-Type:application/json",
                        "Authorization:key=AAAAZTQaA2Y:APA91bGfCSa3fiK3xnJliNtoW4JlifjoXDpbnE55y5G5mU-CZ8euqXpj0BRbGgGhk-BAnRTIYhYYzRldYg4eSOonNqQhGislaBYA7H_JMHk0gBbEdqz9HwHfKBuVZ7huLrCqqSC5DxHW"
                }
        )

        @POST("fcm/send")
        Call<MyResponse> sendNotification(@Body NotificationSender body);
    }

