package com.example.guideapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;


public class AppGuideRepo {

    public void getFilteredPlaces(ExecutorService srv, Handler uiHandler, String district, String category, String neighborhood) {
        srv.execute(() -> {
            try {
                StringBuilder urlBuilder = new StringBuilder("http://10.0.2.2:8080/places/filter");


                boolean isFirstParameter = true;

                if (district != null && !district.isEmpty()) {
                    urlBuilder.append(isFirstParameter ? "?" : "&");
                    urlBuilder.append("district=").append(encodeURIComponent(district));
                    isFirstParameter = false;
                }
                if (category != null && !category.isEmpty()) {
                    urlBuilder.append(isFirstParameter ? "?" : "&");
                    urlBuilder.append("category=").append(encodeURIComponent(category));
                    isFirstParameter = false;
                }
                if (neighborhood != null && !neighborhood.isEmpty()) {
                    urlBuilder.append(isFirstParameter ? "?" : "&");
                    urlBuilder.append("neighborhood=").append(encodeURIComponent(neighborhood));
                }

                URL url = new URL(urlBuilder.toString());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder buffer = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                JSONArray arr = new JSONArray(buffer.toString());
                List<Place> places = new ArrayList<>();
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject current = arr.getJSONObject(i);
                    Place place = parsePlace(current);
                    places.add(place);
                }

                Message msg = Message.obtain();
                msg.obj = places;
                uiHandler.sendMessage(msg);

            } catch (Exception e) {
                Log.e("PlaceRepository", "Error fetching places", e);
            }
        });
    }

    private String encodeURIComponent(String component) {
        try {
            return URLEncoder.encode(component, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            Log.e("PlaceRepository", "Error encoding URL component", e);
            return component;
        }
    }

    private Place parsePlace(JSONObject json) throws JSONException {
        return new Place(
                json.getString("placeId"),
                json.getString("name"),
                json.getString("description"),
                json.getString("category"),
                json.getDouble("latitude"),
                json.getDouble("longitude"),
                json.getString("neighborhood"),
                json.getString("district"),
                json.getString("text"),
                json.getString("images"),
                json.getString("sources")
        );
    }




    public void getDataById(ExecutorService srv, Handler uiHandler,String id){


        srv.execute(()->{
            try {
                URL url = new URL("http://10.0.2.2:8080/places/filter/" + id);
                HttpURLConnection conn =(HttpURLConnection)url.openConnection();


                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();
                String line = "";

                while((line=reader.readLine())!=null){

                    buffer.append(line);

                }

                JSONObject current = new JSONObject(buffer.toString());
                Place place = new Place(current.getString("placeId"),
                        current.getString("name"),
                        current.getString("description"),
                        current.getString("category"),
                        current.getDouble("latitude"),
                        current.getDouble("longitude"),
                        current.getString("neighborhood"),
                        current.getString("district"),
                        current.getString("text"),
                        current.getString("images"),
                        current.getString("sources"));


                Message msg = new Message();
                msg.obj = place;
                uiHandler.sendMessage(msg);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        });



    }


    public void downloadImage(ExecutorService srv, Handler uiHandler, String path){

            srv.submit(()->{
                try {
                    URL url =
                            new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                  Bitmap bmp = BitmapFactory.decodeStream(conn.getInputStream());

                  Message msg = new Message();
                  msg.obj = bmp;
                  uiHandler.sendMessage(msg);

                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            });

    }

    public void getReviewsForPlace(ExecutorService srv, Handler uiHandler, String placeId) {
        srv.execute(() -> {
            try {
                URL url = new URL("http://10.0.2.2:8080/places/" + placeId + "/reviews");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder buffer = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                JSONArray arr = new JSONArray(buffer.toString());
                List<Review> reviews = new ArrayList<>();
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject current = arr.getJSONObject(i);
                    Review review = parseReview(current);
                    reviews.add(review);
                }

                Message msg = Message.obtain();
                msg.obj = reviews;
                uiHandler.sendMessage(msg);

            } catch (Exception e) {
                Log.e("AppGuideRepo", "Error fetching reviews", e);
            }
        });
    }

    private Review parseReview(JSONObject json) throws JSONException {

        return new Review(
                json.getString("reviewId"),
                json.getString("placeId"),
                json.getString("nickname"),
                (float) json.getDouble("rating"),
                json.getString("text")

        );
    }
    public void postReviewForPlace(ExecutorService srv, Handler uiHandler, String placeId, Review review) {
        srv.execute(() -> {
            HttpURLConnection conn = null;
            try {
                URL url = new URL("http://10.0.2.2:8080/places/" + placeId + "/reviews");
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);


                JSONObject reviewJson = new JSONObject();
                reviewJson.put("nickname", review.getNickname());
                reviewJson.put("rating", review.getRating());
                reviewJson.put("text", review.getText());
                // Add other fields as necessary


                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = reviewJson.toString().getBytes("utf-8");
                    os.write(input, 0, input.length);
                }


                StringBuilder response = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                }


                JSONObject responseJson = new JSONObject(response.toString());
                Review postedReview = parseReview(responseJson);

                Message msg = Message.obtain();
                msg.obj = postedReview;

                uiHandler.sendMessage(msg);
            } catch (Exception e) {
                Log.e("AppGuideRepo", "Error posting review", e);

            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
            int responseCode = 0;
            try {
                responseCode = conn.getResponseCode();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }



    }
