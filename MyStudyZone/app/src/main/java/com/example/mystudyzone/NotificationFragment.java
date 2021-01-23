package com.example.mystudyzone;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class NotificationFragment extends Fragment {
    static ListView pairs_list;
    static RequestQueue queue;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        //return super.onCreateView(inflater, container, savedInstanceState);
        ((MainActivity)getActivity()).toolbar.setTitle("New Pair Requests");
        pairs_list = (ListView) view.findViewById(R.id.notificationList);
        queue = Volley.newRequestQueue(getActivity());
        startGettingRequests();
        Log.d("pair", "done");
        return view;
    }
    public void startGettingRequests(){
        getRequestsTask task = new getRequestsTask((MainActivity) getActivity()); //TODO: see this casting
        task.execute();
    }

    private static class getRequestsTask extends AsyncTask<Integer, Integer, String> {
        private WeakReference<MainActivity> activityWeakReference;
        private String TAG = "getRequestsTask";
        getRequestsTask(MainActivity activity){
            activityWeakReference = new WeakReference<MainActivity>(activity);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            MainActivity activity = activityWeakReference.get();
            if(activity == null || activity.isFinishing()) {
                return;
            }
            //do something here
        }

        @Override
        protected String doInBackground(Integer... integers) { //go outside the main thread
            getRequests();
            return "Done";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            MainActivity activity = activityWeakReference.get();
            if(activity == null || activity.isFinishing()) {
                return;
            }
            //do something
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            MainActivity activity = activityWeakReference.get();
            if(activity == null || activity.isFinishing()) {
                return;
            }
            //Toast.makeText(activity, "Finished!", Toast.LENGTH_SHORT).show();
        }

        private void getRequests() {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            Gson gson = new Gson();

            if (user == null) {
                Log.d(TAG, "No user logged in.");
                MainActivity activity = activityWeakReference.get();
                Toast.makeText(activity, "Error No user is logged in", Toast.LENGTH_SHORT).show();
                return;
            }

            // Get the *account authentication* token: The API is going to require authentication.
            // Without authentication, how will we know which user is matched to this device?
            user.getIdToken(true).addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Log.e(TAG, "Could not get authentication token.");
                    MainActivity activity = activityWeakReference.get();
                    Toast.makeText(activity, "Couldn't get Authentication token", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.d(TAG, "Got authentication token.");

                String idToken = task.getResult().getToken();

                String getRequests_url = "http://10.0.2.2:3000/getRequests";
                Log.d("pair", "In the Background thread now");
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest (Request.Method.POST, getRequests_url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) { //got executed in the UI main thread
                        Log.d("pair", "ArrayRequest Response Success");
                        MainActivity activity = activityWeakReference.get();
                        RequestsAdapter requestsAdapter = new RequestsAdapter(activity, response);
                        pairs_list.setAdapter(requestsAdapter);
                        Toast.makeText(activity, "Request Served", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("pair error", error.toString());
                        MainActivity activity = activityWeakReference.get();
                        Toast.makeText(activity, "Error in Request Response", Toast.LENGTH_SHORT).show();
                    }
                })
                {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Authorization", "Token " + idToken);
                        params.put("Content-Type", "application/json");
                        return params;
                    }
                };
                queue.add(jsonArrayRequest);
            });
        }

    }
}




