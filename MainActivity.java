package com.example.a1505499.photoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import android.view.*;

import com.android.volley.VolleyError;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ServerRequester requester;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button)this.findViewById(R.id.btnTrending)).setOnClickListener(this);
        ((Button)this.findViewById(R.id.btnTop)).setOnClickListener(this);

        requester = new ServerRequester(this);
    }


    public void onClick(View w)
    {
        int id = w.getId();
        if(id == R.id.btnTrending)
        {
            OnTrendingClicked();
        }
        else if(id == R.id.btnTop)
        {
            OnTopClicked();
        }
        //else if(id == R.id.)

    }

    private void OnTrendingClicked()
    {
        final MainActivity self = this;

        requester.getPhotosCount(null, new IntResponseHandler() {
            @Override
            public void onSuccess(int count) {
                Log.e("test", Integer.toString(count));
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("Response", "That didn't work!");
            }
        });

        requester.getPhotos(null, new PhotosResponseHandler() {
            @Override
            public void onSuccess(List<Photo> photos)
            {
                ImageView myButt = (ImageView)self.findViewById(R.id.testImg);
                new DownloadImageTask((ImageView) findViewById(R.id.testImg)).execute(photos.get(0).url);
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("Response", "That didn't work!");
            }
        });
    }

    private void OnTopClicked()
    {
        final MainActivity self = this;
        final LinearLayout ll = (LinearLayout) findViewById(R.id.linearLayoutTest);

        requester.getPhotos(null, new PhotosResponseHandler() {
            @Override
            public void onSuccess(List<Photo> photos)
            {
                for(int i = 0; i < photos.size(); i++)
                {
                    ImageView myButt = new ImageView(self);
                    new DownloadImageTask((ImageView) findViewById(R.id.testImg)).execute(photos.get(i).url);

                    ll.addView(myButt);
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("Response", "That didn't work!");
            }
        });
    }

    private void OnMenuClicked()
    {

    }

    //https://developer.android.com/training/volley/simple.html
    /*
    public void test()
    {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://ec2-54-194-59-67.eu-west-1.compute.amazonaws.com/photos/count";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        // Display the first 500 characters of the response string.
                        Log.e("Response", "Response is: "+ response.substring(0,500));
                    }
                },
                new Response.ErrorListener()
                {
                     @Override
                     public void onErrorResponse(VolleyError error)
                     {
                        Log.e("Response", "That didn't work!");
                     }
                }
        );

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    */
}
