package intersoul.fashion.view.timeline;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;

import intersoul.fashion.R;
import intersoul.fashion.view.MyActivity;

import intersoul.fashion.adapter.FeedListAdapter;
import intersoul.fashion.controller.AppController;
import intersoul.fashion.model.FeedItem;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;


/**
 * Created by tiger154 on 2014-10-02.
 */
public  class TimeLineFragment extends Fragment {
    private static final String mTAG = MyActivity.class.getSimpleName();
    private ListView mListView;
    private FeedListAdapter mListadapter;
    private List<FeedItem> mFeedItems;
    private String URL_Feed = "http://api.androidhive.info/feed/feed.json";
    private static final String ARG_SECTION_NUMBER = "section_number";
    private MyActivity mMyActivity;


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TimeLineFragment newInstance(int sectionNumber) {

        TimeLineFragment fragment = new TimeLineFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View lrootView = inflater.inflate(R.layout.fragment_timeline, container, false);

        return lrootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        initControls(); //
           if(getArguments() != null){
               int section = getArguments().getInt(ARG_SECTION_NUMBER);
               mMyActivity.setListViewTimelineFragment(section);
           }

    }

    @SuppressLint("NewApi")
    public void initControls(){

        mListView = (ListView) getActivity().findViewById(R.id.timeLineListView);
        mFeedItems = new ArrayList<FeedItem>();
        mListadapter = new FeedListAdapter(getActivity(), mFeedItems);
        mListView.setAdapter(mListadapter);

        // These two lines not needed,
        // just to get the look of facebook (changing background color & hiding the icon)
        //getActivity().getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3b5998")));
        //getActivity().getActionBar().setIcon(
          //      new ColorDrawable(getResources().getColor(android.R.color.transparent)));

        // We first check for cached request
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Entry entry = cache.get(URL_Feed);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    parseJsonFeed(new JSONObject(data)); // 2014. DEBUG

                   JSONObject lJsonO =  new JSONObject(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {
            // making fresh volley request and getting json
            JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
                    URL_Feed, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(mTAG, "Response: " + response.toString());
                    if (response != null) {
                        parseJsonFeed(response);
                        // 2014. DEBUG
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(URL_Feed, "Error: " + error.getMessage());
                }
            });

            // Adding request to volley request queue
            AppController.getInstance().addToRequestQueue(jsonReq);
        }

    }


    /**
     * Parsing json reponse and passing the data to feed view list adapter
     * */
    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("feed");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                FeedItem item = new FeedItem();
                item.setId(feedObj.getInt("id"));
                item.setName(feedObj.getString("name"));

                // Image might be null sometimes
                String image = feedObj.isNull("image") ? null : feedObj
                        .getString("image");
                item.setImge(image);
                item.setStatus(feedObj.getString("status"));
                item.setProfilePic(feedObj.getString("profilePic"));
                item.setTimeStamp(feedObj.getString("timeStamp"));

                // url might be null sometimes
                String feedUrl = feedObj.isNull("url") ? null : feedObj
                        .getString("url");
                item.setUrl(feedUrl);

                mFeedItems.add(item);
            }

            // notify data changes to list adapater
            mListadapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mMyActivity = ((MyActivity)activity);


    }
}


