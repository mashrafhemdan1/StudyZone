package com.example.mystudyzone;

import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class MyPairsFragment extends Fragment {
    static ListView pairs_list;
    static RequestQueue queue;
    public MyPairsFragment(){ setHasOptionsMenu(true);}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mypairs, container, false);
        //return super.onCreateView(inflater, container, savedInstanceState);
        pairs_list = (ListView) v.findViewById(R.id.pairsList);
        queue = Volley.newRequestQueue(getActivity());
        startGettingPairs();
        Log.d("pair", "done");
        return v;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.pairs, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_newpairs) {
            getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new AddPairsFragment()).commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void startGettingPairs(){
        getMyPairsTask task = new getMyPairsTask((MainActivity) getActivity()); //TODO: see this casting
        task.execute();
    }

    private static class getMyPairsTask extends AsyncTask<Integer, Integer, String>{
        private WeakReference<MainActivity> activityWeakReference;
        private String TAG = "getMyPairsTask";
        getMyPairsTask(MainActivity activity){
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
            showUserPairs();
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

        private void showUserPairs() {
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

                String getMyPairs_url = "http://10.0.2.2:3000/getMyPairs";
                Log.d("pair", "In the Background thread now");
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest (Request.Method.POST, getMyPairs_url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) { //got executed in the UI main thread
                        Log.d("pair", "ArrayRequest Response Success");
                        MainActivity activity = activityWeakReference.get();
                        PairsAdapter pairsAdapter = new PairsAdapter(activity, response);
                        pairs_list.setAdapter(pairsAdapter);
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

                    /*@Override
                    public byte[] getBody() {
                        LoginActivity.MyRequest r = new LoginActivity.MyRequest();
                        r.fcmToken = token;

                        String json = gson.toJson(r);
                        return json.getBytes();
                    }*/
                };
                queue.add(jsonArrayRequest);
            });
        }

    }
}
