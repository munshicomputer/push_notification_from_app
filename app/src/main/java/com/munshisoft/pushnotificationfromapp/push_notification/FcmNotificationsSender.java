package com.munshisoft.pushnotificationfromapp.push_notification;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessaging;
import com.munshisoft.pushnotificationfromapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FcmNotificationsSender  {

    String userFcmToken;
    String title;
    String body;
    Activity activity;


    private RequestQueue requestQueue;
    private final String postUrl = "https://fcm.googleapis.com/fcm/send";
    private final String fcmServerKey ="SHAMIMMUNSHI_IOSDEVELOPER->AAAAq-AuoTc:APA91bF7lMT9ZuvL8jilqQvXLQX6BBE0qI6tOJLjzIMK7Ggizq7g0T5UaJYvy3Ya75jMIKFwe23mNU0Cpmp-mojU_G09SLAOB0JBjPW7RfHU-YYl2Xl1W6NB6IfWB7Ww-GLL77-Xhqz3";

    public FcmNotificationsSender(String userFcmToken, String title, String body, Activity mActivity) {
        this.userFcmToken = userFcmToken;
        this.title = title;
        this.body = body;
        this.activity = mActivity;
    }

    public void sendNotifications() {
        requestQueue = Volley.newRequestQueue(activity);
        JSONObject mainObj = new JSONObject();
        try {
            mainObj.put("to", userFcmToken);
            JSONObject notiObject = new JSONObject();
            notiObject.put("title", title);
            notiObject.put("body", body);
            notiObject.put("icon", R.drawable.ic_launcher_foreground); // TODO: 20/11/23 Need to Change Small Icon.
            mainObj.put("notification", notiObject);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, postUrl, mainObj, response -> {
                Log.e("Response Form Volley", ""+response.toString());
            }, error -> {
                Log.e("Error Form Volley", ""+error.getLocalizedMessage());
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> header = new HashMap<>();
                    header.put("content-type", "application/json");
                    header.put("authorization", "key=" + fcmServerKey);
                    return header;
                }
            };
            requestQueue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Error", ""+e.getLocalizedMessage());
        }
    }

    public static void subscribeTopic(String topicName, Context context){
        FirebaseMessaging.getInstance().subscribeToTopic(topicName).addOnCompleteListener(task -> {
            String msg = "Subscribed";
            if (!task.isSuccessful()) {
                msg = "Subscribe failed";
            }
            Log.e(TAG, msg);
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            Log.e("Error While subscribe a Topics", ""+e.getLocalizedMessage());
        });
    }

    public static void unSubscribeTopic(String topicName, Context context){
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topicName).addOnCompleteListener(task -> {
            String msg = "Un Subscribed";
            if (!task.isSuccessful()) {
                msg = "Un Subscribe failed";
            }
            Log.e(TAG, msg);
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            Log.e("Error While Unsubscribe a Topics", ""+e.getLocalizedMessage());
        });
    }
}
