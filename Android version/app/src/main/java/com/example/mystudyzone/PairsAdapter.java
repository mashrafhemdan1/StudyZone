package com.example.mystudyzone;

import android.content.Context;
import android.content.Intent;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.util.List;

public class PairsAdapter extends ArrayAdapter<String> {
    JSONArray pairs;
    //List<Deadline> deadline;
    Context mContext;
    public PairsAdapter(@NonNull Context context, JSONArray pairs) {
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
            convertView = inflater.inflate(R.layout.pairs_item, parent, false);
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

        try {
            String name = pairs.getJSONObject(position).getString("FName") + " " + pairs.getJSONObject(position).getString("LName");
            viewHolder.name.setText(name);
            viewHolder.university.setText(pairs.getJSONObject(position).getString("university"));
            viewHolder.picture.setImageResource(R.drawable.ic_person);
            viewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                    //startActivity(intent);
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
}

