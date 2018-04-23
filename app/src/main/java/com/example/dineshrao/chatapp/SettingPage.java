package com.example.dineshrao.chatapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class SettingPage extends AppCompatActivity {

    EditText settingEdtText;
    Button settingBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_page);

        settingEdtText=findViewById(R.id.settingEdtText);
        settingBT=findViewById(R.id.settingBT);
        settingBT.setOnClickListener(new View.OnClickListener() {

            JSONObject jsonObject=new JSONObject();
            @Override
            public void onClick(View v) {
                try {
                    jsonObject.put("", settingEdtText.getText().toString());
                    sentToServer(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void sentToServer(JSONObject jsonObject) {
        String Api="";
        String REQUEST_TAG = "volley_key";
        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(Request.Method.GET, Api, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplication(), response.toString(), Toast.LENGTH_LONG).show();

                     /*   try {
                            //getData(response);
                            Toast.makeText(getApplication(), response.toString(), Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplication(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null)
                    volleyError = new VolleyError(new String(volleyError.networkResponse.data));
                return volleyError;
            }
        };

        jsonObjectReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Add the request to the RequestQueue.
        VolleySingletonClass.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectReq, REQUEST_TAG);
    }
}
