package com.example.tiger154.myapplication;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class MyActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, NavigationDrawerFragment.Communicator {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer. 주석추가후 커밋
     * 집에서 주석 추가후에 추가 커밋
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private PlaceholderFragment mPlaceHolderFragment = new PlaceholderFragment();
    private FrameLayout mFrameLayout;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();


        Log.d("INFO","OnCreate진입");
        //RelativeLayout some = (RelativeLayout)findViewById(R.layout.fragment_my);

         //TextView LabelText  = (TextView)findViewById(R.id.section_label);
         //LabelText.setText("Its works i think");


        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("INFO","onStart 진입");


        TextView lTextView = (TextView)findViewById(R.id.section_label);

        PlaceholderFragment PlaceholderF= (PlaceholderFragment)getFragmentManager().findFragmentById(R.id.container);
        int section = PlaceholderF.getArguments().getInt(PlaceholderFragment.ARG_SECTION_NUMBER);


        String lValue = lTextView.getText().toString();
        String temp = "It's Called from MainActivity's onStart " + section;
        lTextView.setText(temp);
        Log.d("onStart", temp);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("INFO","onResume 진입");
        PlaceholderFragment PlaceholderF= (PlaceholderFragment)getFragmentManager().findFragmentByTag("MY_FRAGMENT");
    }

    // 네비게이션 프레그먼트의 콜백 클릭 이벤트 실행 부분임... 아주 중요함..
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments

        FragmentManager fragmentManager = getFragmentManager();
        PlaceholderFragment lPlaceholderF = PlaceholderFragment.newInstance(position + 1);
        fragmentManager.beginTransaction()
                .replace(R.id.container, lPlaceholderF, "MY_FRAGMENT"+(position + 1))
                .commit();

        lPlaceholderF.changeTextViewData("이게 작동할까?");
        Log.d("INFO","MY_FRAGMENT"+(position + 1));

        sendData("이게 작동할까요?");
       // TextView LabelText  = (TextView)findViewById(R.id.section_label);
       // LabelText.setText("Its works i think");
       // 클릭 이벤트가 발생했을경우 내용을 가져와서 별도 처리하는 부분 ....

        Toast.makeText(MyActivity.this, "HelloWorld", Toast.LENGTH_SHORT).show();

    }

    // 네비게이션 드로워 콜백에서, 클릭이벤트 발생시 데이터를
    @Override
    public void sendData(String data){

        mPlaceHolderFragment.changeTextViewData(data);

    }

    // 플레이스 홀더 프레그먼트 내의, 뷰 텍스트를 변경한다...
    public  void setPlaceHoderText(int number){

        TextView lTextView = (TextView)findViewById(R.id.section_label);
        String lValue = lTextView.getText().toString();
        String temp = "It's Called from MainActivity 텍스트셋 " + number;
        lTextView.setText(temp);
        Log.d("onStart", "엑티비티, 뷰텍스트변경");

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.my, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment  {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private MyActivity mMyActivity;




        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {

            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
           // fragment.mMyActivity = aMyActivity; // 부모 엑티비티를 가지고있게...한다.
            return fragment;
        }

        public void changeTextViewData(String aText){


            // 콜 시점에서 mRootView 가존재하지 않는다...
           // TextView lTextView = (TextView)mRootView.findViewById(R.id.section_label);

           //  String lValue = lTextView.getText().toString();
           // String temp = aText;
           // lTextView.setText(temp);

        }


        public PlaceholderFragment() {
        }

        // 각종 뷰들과 연관된 작업을 진행한다.
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_my, container, false);


           // TextView lTextView = (TextView)rootView.findViewById(R.id.section_label);

            int section = getArguments().getInt(ARG_SECTION_NUMBER);

           // String lValue = lTextView.getText().toString();
           // String temp = "Got it section = " + section;
           // lTextView.setText(temp);
           // Log.d("section", "section = " + section.toString());

            return rootView;
        }


        /**
         *  To communicate with other Fragment
         * */
        public interface CommunicatorB{
            public void sendData(String data);
        }


        @Override
        public void onStart() {
            super.onStart();
            int section = getArguments().getInt(ARG_SECTION_NUMBER);
            mMyActivity.setPlaceHoderText(section);
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            mMyActivity = ((MyActivity) activity);


            ((MyActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }


    }

}
