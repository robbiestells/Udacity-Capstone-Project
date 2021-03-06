package com.prime.perspective.commentist.Utilities;

/**
 * Created by rsteller on 1/27/2017.
 */

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.prime.perspective.commentist.Data.FeedContract;
import com.prime.perspective.commentist.Data.FeedDbHelper;
import com.prime.perspective.commentist.Objects.FeedItem;
import com.prime.perspective.commentist.Objects.Show;
import com.prime.perspective.commentist.R;


/**
 * Created by rsteller on 1/11/2017.
 */

public class Rss extends AsyncTask<ArrayList<Show>, Void, Void> {
    Context context;
    //String address = "http://thecommentist.com/feed/rolltohitshow";
    ArrayList<FeedItem> feedItems;
    URL url;
    ProgressDialog progressDialog;

    public Rss(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        //TODO check for internet connection

        //display loading screen
        progressDialog = new ProgressDialog(context);
        //Set the progress dialog to display spinner
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //Set the dialog title to 'Looking...'
        progressDialog.setTitle(R.string.get_episodes);
        //This dialog can't be canceled by pressing the back key
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        //dismiss loading dialog
        progressDialog.dismiss();
    }

    @Override
    protected Void doInBackground(ArrayList<Show>... params) {
        ArrayList<Show> shows = new ArrayList<>();
        for (int i = 0; i < params[0].size(); i++){
            Show show = params[0].get(i);
            ProcessXML(Getdata(show.getFeed()));
        }
        return null;
    }

    private void ProcessXML(Document data) {
        if (data != null) {
            feedItems = new ArrayList<>();
            String showTitle = new String();
            Element root = data.getDocumentElement();
            Node channel = root.getChildNodes().item(1);
            NodeList items = channel.getChildNodes();
            for (int i=0; i<items.getLength(); i++){
                Node currentChild = items.item(i);
                if (currentChild.getNodeName().equalsIgnoreCase("title")){
                    NodeList itemchilds = currentChild.getChildNodes();
                    Node current = itemchilds.item(0);
                    showTitle = current.getTextContent();
                }
                if (currentChild.getNodeName().equalsIgnoreCase("item")){
                    FeedItem item = new FeedItem();
                    NodeList itemchilds = currentChild.getChildNodes();
                    for (int j=0; j<itemchilds.getLength(); j++){
                        Node current = itemchilds.item(j);

                        //set show title
                        item.setShow(showTitle);
                        if (current.getNodeName().equalsIgnoreCase("title")){

                            //set episode title
                            item.setTitle(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase("itunes:summary")){

                            //set description
                            item.setDescription(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase("pubDate")){

                            //set date
                            item.setPubDate(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase("link")){

                            //set url
                            item.setLink(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase("enclosure")){

                            //set audio link
                            String url = current.getAttributes().item(0).getTextContent();
                            item.setAudioUrl(url);
                        } else if (current.getNodeName().equalsIgnoreCase("itunes:duration")){

                            //get length
                            item.setLength(current.getTextContent());
                        }
                    }
                    //add to list
                    feedItems.add(item);
                }
            }
            //save list
            saveFeedItems(feedItems);
        }
    }

    public Document Getdata(String feed){
        try {
            url = new URL(feed);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);
            return document;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveFeedItems(ArrayList<FeedItem> feedItems){
        FeedDbHelper mDbHelper = new FeedDbHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String query = "SELECT * FROM " + FeedContract.FeedEntry.TABLE_NAME + " WHERE " + FeedContract.FeedEntry.COLUMN_EPIOSDE_AUDIO
                + " =?";

        for (FeedItem item:feedItems) {

            Cursor cursor = db.rawQuery(query, new String[] {item.getAudioUrl()}) ;

            if (cursor.getCount() <= 0){
                //get values
                ContentValues values = new ContentValues();
                values.put(FeedContract.FeedEntry.COLUMN_SHOW_NAME, item.getShow());
                values.put(FeedContract.FeedEntry.COLUMN_EPISODE_TITLE, item.getTitle());
                values.put(FeedContract.FeedEntry.COLUMN_EPIOSDE_LINK, item.getLink());
                values.put(FeedContract.FeedEntry.COLUMN_EPISODE_DESCRIPTION, item.getDescription());
                values.put(FeedContract.FeedEntry.COLUMN_EPISODE_DATE, item.getPubDate());
                values.put(FeedContract.FeedEntry.COLUMN_EPIOSDE_LENGTH, item.getLength());
                values.put(FeedContract.FeedEntry.COLUMN_EPIOSDE_AUDIO, item.getAudioUrl());

                //insert a new entry with the data above
                long newRowId = db.insert(FeedContract.FeedEntry.TABLE_NAME, null, values);
                Log.v("Insert Feed item", "New row ID: " + newRowId);
            }
            cursor.close();
        }

        db.close();
    }
}
