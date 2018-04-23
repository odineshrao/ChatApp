package com.example.dineshrao.chatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainPage extends AppCompatActivity {

    EditText enterText;
    TextView showText;
    Button sendBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        enterText=findViewById(R.id.enterText);
        showText=findViewById(R.id.showText);
        sendBT=findViewById(R.id.sendBT);

        sendBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("", enterText.getText().toString());
                    sentToServer(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void sentToServer(JSONObject jsonObject) {
    String Api="http://www.safehoms.in/api/v1/property/search?";
        String REQUEST_TAG = "volley_key";
        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(Request.Method.GET, Api, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                           getData(response);
                            Toast.makeText(getApplication(), response.toString(), Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplication(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            String getToken="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMzEwIiwiaXNzIjoiaHR0cDpcL1wvd3d3LnNhZmVob21zLmluXC9hcGlcL3YxXC9hdXRoXC9sb2dpbiIsImlhdCI6MTUyNDA0NTAyNywiZXhwIjoxNTI0MDQ4NjI3LCJuYmYiOjE1MjQwNDUwMjcsImp0aSI6IkpXUDVCVGwxdkRTT2trVTUifQ.3eiipqZXpWAEfuZpOyWTSPgtQmxfP0YZUt_yD67v83k";
            //This is for Headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer" + getToken);
                return params;
            }

         /*   @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null)
                    volleyError = new VolleyError(new String(volleyError.networkResponse.data));
                return volleyError;
            }*/
        };

        jsonObjectReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Add the request to the RequestQueue.
        VolleySingletonClass.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectReq, REQUEST_TAG);
    }

    public void getData(JSONObject resp) throws JSONException {
       // get/set data**

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
         getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        //setting**
         if (id == R.id.action_settings) {
             Intent intent = new Intent(getApplicationContext(), SettingPage.class);
             startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
