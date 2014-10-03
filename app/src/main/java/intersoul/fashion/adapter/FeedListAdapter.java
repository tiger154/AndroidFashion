package intersoul.fashion.adapter;

import intersoul.fashion.common.helper.view.FeedImageView;
import intersoul.fashion.R;
import intersoul.fashion.controller.AppController;
import intersoul.fashion.model.FeedItem;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.w3c.dom.Text;

/**
 * Created by tiger154 on 2014-10-03.
 */
public class FeedListAdapter extends BaseAdapter {
    private Activity mActivity;
    private LayoutInflater mInflater;
    private List<FeedItem> mFeedItems;
    ImageLoader mImageLoader = AppController.getInstance().getmImageLoader();

    // Constructor
    public FeedListAdapter(Activity aActivity, List<FeedItem> aFeedItems){
        this.mActivity = aActivity;
        this.mFeedItems = aFeedItems;
    }

    @Override
    public int getCount() {
        return mFeedItems.size();
    }

    @Override
    public Object getItem(int aLocation) {
        return mFeedItems.get(aLocation);
    }

    @Override
    public long getItemId(int aLocation) {
        return aLocation;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // init core service
        if(mInflater == null){  mInflater = (LayoutInflater)mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE); }
        if(convertView == null){  convertView =  mInflater.inflate(R.layout.feed_item, null);}     // View which will be handled
        if(mImageLoader == null){  mImageLoader = AppController.getInstance().getmImageLoader();};

        // Get views by id
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView timestamp = (TextView) convertView.findViewById(R.id.timestamp);
        TextView statusMsg = (TextView) convertView.findViewById(R.id.txtStatusMsg);
        TextView url = (TextView) convertView.findViewById(R.id.txtUrl);

        NetworkImageView profilePic = (NetworkImageView) convertView.findViewById(R.id.profilePic); // it will be to Bag Image
        FeedImageView feedImageView = (FeedImageView) convertView.findViewById(R.id.feedImage1);

        // Start Model Binding
        FeedItem item = mFeedItems.get(position);

        // Adjust View data by Model
        name.setText(item.getName());

        // Converting timestamp into x ago format
        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(Long.parseLong(item.getTimeStamp()), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        timestamp.setText(timeAgo);

        // Check for empty status message
        if(!TextUtils.isEmpty(item.getStatus())){
            statusMsg.setText(item.getStatus());
            statusMsg.setVisibility(View.VISIBLE);
        }else{
            statusMsg.setVisibility(View.GONE);
        }

        // Checking for null feed url
        if(item.getUrl() != null){
            url.setText(Html.fromHtml("<a href=\"" + item.getUrl() + "\">"
                    + item.getUrl() + "</a> "));
            url.setMovementMethod(LinkMovementMethod.getInstance());
            url.setVisibility(View.VISIBLE);
        }else{
            // url is null, remove from the view
            url.setVisibility(View.GONE);
        }

        // user profile pic
        profilePic.setImageUrl(item.getProfilePic(), mImageLoader);

        // Feed image
        if(item.getImge() != null){
            feedImageView.setImageUrl(item.getImge(), mImageLoader);
            feedImageView.setVisibility(View.VISIBLE);
            feedImageView
                    .setResponseObserver(new FeedImageView.ResponseObserver(){
                        @Override
                        public void onError() {

                        }

                        @Override
                        public void onSuccess() {
                            // To-do something after success
                        }
                    });

        }else{

            feedImageView.setVisibility(View.GONE);
        }

        return convertView;
    }
}
