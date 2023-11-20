package com.munshisoft.pushnotificationfromapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.messaging.FirebaseMessaging;
import com.munshisoft.pushnotificationfromapp.push_notification.FcmNotificationsSender;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseMessaging.getInstance().subscribeToTopic("all");
        FcmNotificationsSender.subscribeTopic("students", getApplicationContext());
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button button = findViewById(R.id.send_notification_button);
        button.setOnClickListener(v -> {
            FcmNotificationsSender fcmNotificationsSender = new FcmNotificationsSender("/topics/students", "Test", "This is test notification from app", MainActivity.this );
            fcmNotificationsSender.sendNotifications();
        });
    }


}