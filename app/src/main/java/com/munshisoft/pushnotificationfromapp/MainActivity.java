package com.munshisoft.pushnotificationfromapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.Ringtone;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseMessaging.getInstance().subscribeToTopic("all");
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button button = findViewById(R.id.send_notification_button);
        button.setOnClickListener(v -> {
            FcmNotificationsSender fcmNotificationsSender = new FcmNotificationsSender("/topics/all", "Test", "This is test notification from app", MainActivity.this );
            fcmNotificationsSender.sendNotifications();
        });
    }

    void sendNotification(){

    }
}