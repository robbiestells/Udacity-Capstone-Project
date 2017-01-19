package Utilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.GridView;
import android.widget.ListView;

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

import Adapters.FeedAdapter;
import Adapters.RecyclerAdapter;
import Objects.FeedItem;
import layout.ShowPage;


/**
 * Created by rsteller on 1/11/2017.
 */

public class ReadRss extends AsyncTask<String, Void, Void> {
    Context context;
    //String address = "http://thecommentist.com/feed/rolltohitshow";
    FeedAdapter mAdapter;
    ArrayList<FeedItem> feedItems;
    RecyclerView recyclerView;
    ProgressDialog dialog;
    URL url;
    ShowPage page;

//    public ReadRss(Context context, RecyclerView recyclerView){
//        this.recyclerView = recyclerView;
//        this.context = context;
//        dialog = new ProgressDialog(context);
//        dialog.setMessage("Retrieving feed");
//
//    }

//    public ReadRss(Context context, ListView listView){
public ReadRss(Context context, RecyclerView recyclerView, ShowPage page){
        this.recyclerView = recyclerView;
        this.context = context;
        this.page = page;
       // dialog = new ProgressDialog(context);
       // dialog.setMessage("Retrieving feed");

    }

    @Override
    protected void onPreExecute() {
       // dialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
      //  dialog.dismiss();
        //FeedAdapter feedAdapter = new FeedAdapter(context, feedItems);
        RecyclerAdapter feedAdapter = new RecyclerAdapter(context, feedItems, page);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.getLayoutManager().isSmoothScrolling();
        recyclerView.setAdapter(feedAdapter);

    }

    @Override
    protected Void doInBackground(String... params) {
       String url = params[0];
        ProcessXML(Getdata(url));
        return null;
    }

    private void ProcessXML(Document data) {
        if (data != null) {
           feedItems = new ArrayList<>();
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
                        } else if (current.getNodeName().equalsIgnoreCase("itunes:duration")){
                            //String url = current.getAttributes().item(1).getTextContent();
                            item.setLength(current.getTextContent());
                        }
                    }
                    feedItems.add(item);
                   //Log.d("length", item.getLength());
                }
            }
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
}
