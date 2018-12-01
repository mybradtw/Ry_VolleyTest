package tw.brad.ry_volleytest;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private RequestQueue queue;
    private TextView mesg;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mesg = findViewById(R.id.mesg);
        img = findViewById(R.id.img);

        queue = Volley.newRequestQueue(this);
    }

    public void test1(View view) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                "https://www.bradchao.com",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("brad", response);
                    }
                }, null);

        queue.add(stringRequest);
    }

    public void test2(View view) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                "https://www.bradchao.com/v2/apptest/getTest1.php?account=brad&passwd=1234",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseJSON(response);
                    }
                }, null);

        queue.add(stringRequest);
    }

    public void test3(View view) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                "https://www.bradchao.com/v2/apptest/postTest1.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.v("brad", response);
                        parseJSON(response);
                    }
                }, null){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("account", "brad");
                params.put("passwd", "1234");
                return params;
            }
        };

        queue.add(stringRequest);
    }

    private void parseJSON(String json){
        try {
            JSONObject root = new JSONObject(json);
            String result = root.getString("result");
            if (result.equals("success")){
                String lottery = root.getString("lottery");
                mesg.setText("OK:" + lottery);
            }else{
                mesg.setText("XX");
            }
        }catch (Exception e){
            Log.v("brad", e.toString());
        }
    }


    public void test4(View view) {
        String imgUrl = "http://www.bradchao.com/v2/img/book3.png";

        ImageRequest imageRequest = new ImageRequest(imgUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        img.setImageBitmap(response);
                    }
                },0,0,
                ImageView.ScaleType.CENTER,
                Bitmap.Config.ARGB_8888, null);

        queue.add(imageRequest);

    }

    public void test5(View view) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                "https://www.bradchao.com/v2/apptest/authTest1.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //parseJSON(response);
                        Log.v("brad", response);
                    }
                }, null){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String myAuth = "brad:1234";
                String myAuthBase64 = Base64.encodeToString(
                        myAuth.getBytes(), Base64.NO_WRAP);
                HashMap<String,String> ret = new HashMap<>();
                ret.put("Authorization", "Basic " + myAuthBase64);
                return ret;
            }
        };

        queue.add(stringRequest);

    }
    public void test6(View view) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                "https://www.bradchao.com/v2/apptest/authTest2.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //parseJSON(response);
                        Log.v("brad", response);
                    }
                }, null){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = "123456";
                HashMap<String,String> ret = new HashMap<>();
                ret.put("Authorization", "Bearer " + token);
                return ret;
            }
        };

        queue.add(stringRequest);

    }
}
