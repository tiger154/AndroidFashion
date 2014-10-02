package intersoul.fashion.view.bag;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;

import intersoul.fashion.R;

/**
 * Created by tiger154 on 2014-10-02.
 */
public class BagFragment extends  Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View lrootView = inflater.inflate(R.layout.fragment_bag, container, false);
        return lrootView;
    }
}
