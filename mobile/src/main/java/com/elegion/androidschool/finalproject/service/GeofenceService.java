package com.elegion.androidschool.finalproject.service;


import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.TextUtils;
import android.util.Log;

import com.elegion.androidschool.finalproject.MainActivity;
import com.elegion.androidschool.finalproject.R;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleksandra on 25.10.15.
 */
public class GeofenceService extends IntentService {
    public static final String TITLE = "QQ";
    public static final String NAME = "GeofenceIntentService";

    public GeofenceService(){super(NAME);}

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("qq", "service was started");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.v("qq", "in onTransitionHandleIntent");
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            Log.v("qq" , "event has error");
            return;
        }

        int transition = geofencingEvent.getGeofenceTransition();
        if (transition != Geofence.GEOFENCE_TRANSITION_ENTER) {
            return;
        }

        List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
        String details = getGeofenceTransitionDetails(triggeringGeofences);
        sendNotification(details);
    }

    private String getGeofenceTransitionDetails(List<Geofence> triggeringGeofences) {
        ArrayList<String> triggeringGeofencesIdsList = new ArrayList<>();
        for (Geofence geofence : triggeringGeofences) {
            triggeringGeofencesIdsList.add(geofence.getRequestId());
        }
        return TextUtils.join(", ", triggeringGeofencesIdsList);
    }

    private void sendNotification(String notificationDetails) {

        Intent intent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_local_grocery_store_white_18dp)
                .setContentTitle(TITLE)
                .setContentText(notificationDetails)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }
}
