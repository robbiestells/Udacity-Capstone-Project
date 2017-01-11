package com.example.studio111.commentist;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by rsteller on 1/11/2017.
 */

public class ReadRss extends AsyncTask<Void, Void, Void> {
    Context context;
    String address = "http://thecommentist.com/feed/rolltohitshow";
    //String address = "http://www.sciencemag.org/rss/news_current.xml";
    ProgressDialog dialog;
    URL url;

    public ReadRss(Context context){
        this.context = context;
        dialog = new ProgressDialog(context);
        dialog.setMessage("Retrieving feed");

    }

    @Override
    protected void onPreExecute() {
        dialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        dialog.dismiss();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        ProcessXML(Getdata());
        return null;
    }

    private void ProcessXML(Document data) {
        if (data != null) {
            ArrayList<FeedItem> feedItems = new ArrayList<>();
            Element root = data.getDocumentElement();
            Node channel = root.getChildNodes().item(1);
            NodeList items = channel.getChildNodes();
            for (int i=0; i<items.getLength(); i++){
                Node currentChild = items.item(i);
                if (currentChild.getNodeName().equalsIgnoreCase("item")){
                    FeedItem item = new FeedItem();
                    NodeList itemchilds = currentChild.getChildNodes();
                    for (int j=0; j<itemchilds.getLength(); j++){
                        Node current = itemchilds.item(j);
                        if (current.getNodeName().equalsIgnoreCase("title")){
                            item.setTitle(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase("description")){
                            item.setDescription(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase("pubDate")){
                            item.setPubDate(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase("link")){
                            item.setLink(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase("enclosure")){
                            String url = current.getAttributes().item(0).getTextContent();
                            item.setAudioUrl(url);
                        }
                    }
                    feedItems.add(item);
                    Log.d("enclosure", item.getPubDate());
                }
            }
        }
    }

    public Document Getdata(){
        try {
            url = new URL(address);
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
}
