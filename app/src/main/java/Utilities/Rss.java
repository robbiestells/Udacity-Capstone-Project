package Utilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import Adapters.FeedAdapter;
import Adapters.RecyclerAdapter;
import Objects.FeedItem;

/**
 * Created by rsteller on 1/18/2017.
 */

public class Rss extends AsyncTask<String, Void, ArrayList<FeedItem>> {
    Context context;
    //String address = "http://thecommentist.com/feed/rolltohitshow";
    FeedAdapter mAdapter;
    ArrayList<FeedItem> feedItems;
    RecyclerView listView;
    //ListView listView;
    ProgressDialog dialog;
    URL url;

    public Rss(Context context) {
        //this.listView = listView;
        this.context = context;
        // dialog = new ProgressDialog(context);
        // dialog.setMessage("Retrieving feed");

    }

    @Override
    protected void onPreExecute() {
        // dialog.show();
        super.onPreExecute();
    }

    @Override
    protected ArrayList<FeedItem> doInBackground(String... params) {
        String url = params[0];
        ArrayList<FeedItem> feedItems = ProcessXML(Getdata(url));
        return feedItems;
    }

    @Override
    protected void onPostExecute(ArrayList<FeedItem> feedItems) {
        super.onPostExecute(feedItems);

        //  dialog.dismiss();
        //FeedAdapter feedAdapter = new FeedAdapter(context, feedItems);
//            RecyclerAdapter feedAdapter = new RecyclerAdapter(context, feedItems);
//            listView.setLayoutManager(new LinearLayoutManager(context));
//            listView.getLayoutManager().isSmoothScrolling();
//            listView.setAdapter(feedAdapter);

    }

    private ArrayList<FeedItem> ProcessXML(Document data) {
        if (data != null) {
            feedItems = new ArrayList<>();
            Element root = data.getDocumentElement();
            Node channel = root.getChildNodes().item(1);
            NodeList items = channel.getChildNodes();
            for (int i = 0; i < items.getLength(); i++) {
                Node currentChild = items.item(i);
                if (currentChild.getNodeName().equalsIgnoreCase("item")) {
                    FeedItem item = new FeedItem();
                    NodeList itemchilds = currentChild.getChildNodes();
                    for (int j = 0; j < itemchilds.getLength(); j++) {
                        Node current = itemchilds.item(j);
                        if (current.getNodeName().equalsIgnoreCase("title")) {
                            item.setTitle(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase("description")) {
                            item.setDescription(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase("pubDate")) {
                            item.setPubDate(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase("link")) {
                            item.setLink(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase("enclosure")) {
                            String url = current.getAttributes().item(0).getTextContent();
                            item.setAudioUrl(url);
                        } else if (current.getNodeName().equalsIgnoreCase("itunes:duration")) {
                            //String url = current.getAttributes().item(1).getTextContent();
                            item.setLength(current.getTextContent());
                        }
                    }
                    feedItems.add(item);
                    //Log.d("length", item.getLength());
                }
            }
        }
        return feedItems;
    }

    public Document Getdata(String feed) {
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
}

