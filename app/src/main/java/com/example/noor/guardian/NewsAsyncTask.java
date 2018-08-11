package com.example.noor.guardian;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class NewsAsyncTask extends AsyncTaskLoader<ArrayList<News>> {
    private String string_url;
    private ArrayList<News> newsArticleArrayList = new ArrayList<>();

     NewsAsyncTask(@NonNull Context context, String string_url) {
        super(context);
        this.string_url=string_url;

    }

    @Nullable
    @Override
    public ArrayList<News> loadInBackground() {
        StringBuilder stringBuilder = new StringBuilder();
        URL url;
        try {
            url = new URL(string_url);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.setConnectTimeout(10000);


            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
            JSONObject root = new JSONObject(stringBuilder.toString());
            JSONObject response = root.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");


            String title = "";
            String date = "";
            String Url = "";
            String author = "";
            String section = "";
            String image = "";


            for (int i = 0; i < results.length(); i++) {
                JSONObject element = results.getJSONObject(i);
                if (element.has("webTitle")) {
                    title = element.getString("webTitle");
                }
                if (element.has("webPublicationDate")) {
                    date = element.getString("webPublicationDate");
                }
                if (element.has("sectionName")) {
                    section = element.getString("sectionName");
                }
                if (element.has("webUrl")) {
                    Url = element.getString("webUrl");
                }
                if (element.has("fields")) {
                    JSONObject fields = element.getJSONObject("fields");
                    if (fields.has("thumbnail")) {
                        image = fields.getString("thumbnail");
                    }
                    if (fields.has("byline")) {
                        author = fields.getString("byline");
                    }
                }


                newsArticleArrayList.add(new News(title, date, Url, author, section, image));


            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return newsArticleArrayList;

    }


}