package intersoul.fashion.view.bag;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;

import intersoul.fashion.R;
import intersoul.fashion.view.MyActivity;

/**
 * Created by tiger154 on 2014-10-02.
 */
public class BagFragment extends  Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private MyActivity mMyActivity;


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static BagFragment newInstance(int sectionNumber) {

        BagFragment fragment = new BagFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Get fragment_bag Layout
        View lrootView = inflater.inflate(R.layout.fragment_bag, container, false);
        return lrootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(getArguments() != null){
            int section = getArguments().getInt(ARG_SECTION_NUMBER);
            mMyActivity.setTextViewBagFragment(section);
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mMyActivity = ((MyActivity)activity);


    }
}
