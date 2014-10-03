package intersoul.fashion.controller;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import intersoul.fashion.common.utils.volley.LruBitmapCache;


/**
 * Created by tiger154 on 2014-10-03.
 * Des : For Gloval Context , For Volley
 *      - Future it should be expend to fit MVC parttern
 *       , especially it will be used a Controller to manage all Activity Global Context
 *
 */
public class AppController extends Application {


    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private static AppController mInstance; // For singlton

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    /**
     * Get AppController with Synchronized
     * @return AppController
     */
    public  static  synchronized  AppController getInstance(){
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        if(mRequestQueue == null){

            mRequestQueue = Volley.newRequestQueue(getApplicationContext()); // make RequestQueue , singlton
        }
        return mRequestQueue;
    }

    public ImageLoader getmImageLoader(){
        getRequestQueue();
        if(mImageLoader==null){
            mImageLoader = new ImageLoader(this.mRequestQueue, new LruBitmapCache()); // make ImageLoader , singlton
        }
       return this.mImageLoader;
    }

    /**
     * Add Request to RequestQueue
     * @param req
     * @param tag
     * @param <T>
     */
    public  <T> void addToRequestQueue(Request<T> req, String tag){
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(TAG) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}
