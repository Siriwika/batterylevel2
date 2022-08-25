package com.example.batterylevel;

import androidx.annotation.NonNull;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel;

import java.util.HashMap;
import java.util.Map;


import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import android.util.Log;
//import android.os.Bundle;


public class MainActivity extends FlutterActivity {
    private static final String CHANNEL = "samples.flutter.dev/battery";
    private static final String TAG = "MainActivity";
    // Access a Firestore instance from your Activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL)
                .setMethodCallHandler(
                        (call, result) -> {
                           db.collection("User")
                                    .get().addOnCompleteListener(task -> {
                                         if(task.isSuccessful()){

                                             if (task.getResult().isEmpty()) {
                                                 System.out.println("tasks is empty");
                                             }
                                             for (QueryDocumentSnapshot doc : task.getResult()){
                                                 Log.d(TAG, "configureFlutterEngine: "+doc);
                                                 Log.d(TAG, doc.getId()+ " => " +doc.getData());
                                             }
                                         }else{
                                             Log.w(TAG, "Error getting documents.", task.getException());
                                         }
                                    });
                            // This method is invoked on the main thread.
                            if (call.method.equals("getBatteryLevel")) {
//                                List<String> dataList = new ArrayList<String>();
                                Map<String, Object> user = new HashMap<>();
                                user.put("name","Cake");
                                user.put("age","22");
                                result.success(user);
                        }
                        }
                );
    }
}