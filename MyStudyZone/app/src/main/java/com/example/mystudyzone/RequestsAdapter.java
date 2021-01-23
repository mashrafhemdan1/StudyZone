package com.example.mystudyzone;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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

public class RequestsAdapter extends ArrayAdapter<String> {
    static JSONArray pairs;
    static RequestQueue queue;
    //List<Deadline> deadline;
    Context mContext;
    public RequestsAdapter(@NonNull Context context, JSONArray pairs) {
        super(context, R.layout.upcoming_item);
        this.mContext = context;
        this.pairs = pairs;
    }

    @Override
    public int getCount() {
        return pairs.length();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.request_item, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.pair_name);
            viewHolder.university = (TextView) convertView.findViewById(R.id.pair_university);
            viewHolder.picture = (ImageView) convertView.findViewById(R.id.pair_picture);
            viewHolder.button = (Button) convertView.findViewById(R.id.action_button);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //TODO: adjust the pairs calling here
        queue = Volley.newRequestQueue((MainActivity)mContext);
        try {
            String name = pairs.getJSONObject(position).getString("FName") + " " + pairs.getJSONObject(position).getString("LName");
            viewHolder.name.setText(name);
            viewHolder.university.setText(pairs.getJSONObject(position).getString("university"));
            viewHolder.picture.setImageResource(R.drawable.ic_person);
            viewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    start_acceptRequest (position);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //viewHolder.picture.setImageResource();
        return convertView;
    }

    static class ViewHolder{
        TextView name;
        TextView university;
        ImageView picture;
        Button button;
    }

    public void start_acceptRequest (int position){
        RequestsAdapter.acceptRequestTask task = new RequestsAdapter.acceptRequestTask((MainActivity) mContext); //TODO: see this casting
        task.execute(position);
    }

    private static class acceptRequestTask extends AsyncTask<Integer, Integer, String> {
        private String TAG = "acceptRequestTask";
        private WeakReference<MainActivity> activityWeakReference;
        acceptRequestTask(MainActivity activity){
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
            acceptRequest(integers[0]);
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


        private void acceptRequest(int position) {
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

                String acceptRequest_url = "http://10.0.2.2:3000/acceptRequest";
                Log.d("pair", "In the Background thread now");
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("sender_id", pairs.getJSONObject(position).getString("userid"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ;
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, acceptRequest_url, jsonBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) { //got executed in the UI main thread
                        Log.d("pair", "ArrayRequest Response Success");
                        MainActivity activity = activityWeakReference.get();
                        try {
                            if(response.getString("response").equals("done")){
                                Toast.makeText(activity, "Accepted", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("pair error", error.toString());
                        MainActivity activity = activityWeakReference.get();
                        Toast.makeText(activity, "Error in Accepting Request", Toast.LENGTH_SHORT).show();
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
                queue.add(jsonObjectRequest);
            });
        }

    }
}

