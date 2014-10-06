package intersoul.fashion.view;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import com.actionbarsherlock.internal.widget.ScrollingTabContainerView; // added 2014.10.06
import com.actionbarsherlock.view.Window;
import android.util.TypedValue;                                         // added 2014.10.06
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;



import intersoul.fashion.R;

import intersoul.fashion.view.NavigationDrawerFragment;
import intersoul.fashion.view.bag.BagFragment;
import intersoul.fashion.view.store.StoreFragment;
import intersoul.fashion.view.timeline.TimeLineFragment;


public class MyActivity extends Activity
        implements  NavigationDrawerFragment.NavigationDrawerCallbacks
                    , ActionBar.TabListener
                    , ProfileDrawerFragment.ProfileDrawerCallbacks{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer. 주석추가후 커밋
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;  // NavigationDrawer
    private ProfileDrawerFragment mProfileDrawerFragment;        // ProfileDrawer

    private int mTabCount = 3;
    private Tab mTabTimeLine, mTabStore, mTabBag; // Tab Header Define
    private Fragment mTimeLineFragment = new TimeLineFragment();
    private Fragment mStoreFragment = new StoreFragment();
    private Fragment mBagFragment = new BagFragment();
    private ScrollingTabContainerView mTabsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        Log.d("INFO","OnCreate진입");

        initActionbarTab();              // 1. 탭 설정
        initNavigationDrawer(); // 2. 네비게이션 드로워 설정
    }





    /**
     *  액션바 탭을 설정한다.
     */
    protected void initActionbarTab(){
        // 액션바 탭 구성하기 (기존) 하나의 프레그먼트에 텍스트 변경 -> 기존 각 탭별 별도의 프레그먼트 치환

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        /*
        final TabHost tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        // Tab1 Setting
        TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("Tab1");
        tabSpec1.setIndicator("Timeline"); // Tab Subject
        tabSpec1.setContent(R.id.tab1); // Tab Content
        tabHost.addTab(tabSpec1);

        // Tab2 Setting
        TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("Tab2");
        tabSpec2.setIndicator("Bag"); // Tab Subject
        tabSpec2.setContent(R.id.tab2); // Tab Content
        tabHost.addTab(tabSpec2);

        // Tab3 Setting
        TabHost.TabSpec tabSpec3 = tabHost.newTabSpec("Tab3");
        tabSpec3.setIndicator("Store"); // Tab Subject
        tabSpec3.setContent(R.id.tab3); // Tab Content

        tabHost.addTab(tabSpec3);

        // show First Tab Content
        tabHost.setCurrentTab(0);


        tabHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tabHost.getCurrentTab() == 0) {
                    Toast.makeText(getApplicationContext(), "토스트메시지입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        */








        // 탭 인스턴스 생성..
        mTabTimeLine  = actionBar.newTab().setText("Timeline");
        mTabBag  = actionBar.newTab().setText("Bag");
        mTabStore = actionBar.newTab().setText("Store");

        // 탭 리스너 설정
        mTabTimeLine.setTabListener(new TabListener(mTimeLineFragment));
        mTabBag.setTabListener(new TabListener(mBagFragment));
        mTabStore.setTabListener(new TabListener(mStoreFragment));

        // 액션바에 탭 추가
        actionBar.addTab(mTabTimeLine);
        actionBar.addTab(mTabBag);
        actionBar.addTab(mTabStore);

        actionBar.setTitle(R.string.app_name);
    }

    /**
     * 네비게이션 드로워를 셋팅한다... onCreate 에서 -> onStart 로 위치 변경함
     */
    protected void initNavigationDrawer(){

        //  왼쪽 메뉴 네비게이션 정보 셋팅
        mNavigationDrawerFragment = (NavigationDrawerFragment)getFragmentManager().findFragmentById(R.id.navigation_drawer); // 네비게이션 드로워 가져오기
        mProfileDrawerFragment = (ProfileDrawerFragment)getFragmentManager().findFragmentById(R.id.profile_drawer); // 프로필 드로워 가져오기


        // Set up the Navigation drawer(Left).
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        // Set up the Navigation drawer(Right)
        mProfileDrawerFragment.setUp(
                R.id.profile_drawer
                , (DrawerLayout) findViewById(R.id.drawer_layout));

    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("INFO","onStart 진입");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("INFO","onResume 진입");
    }


    @Override
    public void onProfileDrawerItemSelected(int position) {

        FragmentManager fragmentManager = getFragmentManager();


        ActionBar actionBar = getActionBar();
        actionBar.setSelectedNavigationItem(position);

        if(position ==0) {
            // If its TimeLine replace FrameLayout to New BagFragment instance .
            TimeLineFragment lTimelinefragment = TimeLineFragment.newInstance(position + 1);
            fragmentManager.beginTransaction()
                    .replace(R.id.container, lTimelinefragment, "TimelineF" + (position + 1))
                    .commit();
        }else if(position==1){
            // If its Bag replace FrameLayout to New BagFragment instance
            BagFragment lBagFragment = BagFragment.newInstance(position + 1);
            fragmentManager.beginTransaction()
                    .replace(R.id.container, lBagFragment, "BagF" + (position + 1))
                    .commit();
        }else if(position==2){
            StoreFragment lStoreFragment = StoreFragment.newInstance(position + 1);
            fragmentManager.beginTransaction()
                    .replace(R.id.container, lStoreFragment , "StoreF" + (position + 1))
                    .commit();

        }

    }


    // Execute from TimelineFragment
    public void setListViewTimelineFragment(int num){
        ListView lListView = (ListView)findViewById(R.id.timeLineListView);
    }

    // Execute from BagFragment
    public void setTextViewBagFragment(int aNum){
        TextView lText = (TextView)findViewById(R.id.bagText);
        lText.setText("I got param from DrawerFragment, its number : " + aNum);
    }

    // 네비게이션 프레그먼트의 콜백 클릭 이벤트 실행 부분임... 아주 중요함..
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments

        FragmentManager fragmentManager = getFragmentManager();

        ActionBar actionBar = getActionBar();
        actionBar.setSelectedNavigationItem(position);

        if(position ==0) {
            // If its TimeLine replace FrameLayout to New BagFragment instance .
            TimeLineFragment lTimelinefragment = TimeLineFragment.newInstance(position + 1);
            fragmentManager.beginTransaction()
                    .replace(R.id.container, lTimelinefragment, "TimelineF" + (position + 1))
                    .commit();
        }else if(position==1){
            // If its Bag replace FrameLayout to New BagFragment instance
            BagFragment lBagFragment = BagFragment.newInstance(position + 1);
            fragmentManager.beginTransaction()
                    .replace(R.id.container, lBagFragment, "BagF" + (position + 1))
                    .commit();
        }else if(position==2){
            StoreFragment lStoreFragment = StoreFragment.newInstance(position + 1);
            fragmentManager.beginTransaction()
                    .replace(R.id.container, lStoreFragment , "StoreF" + (position + 1))
                    .commit();

        }

    }

    /**
     * 액션바 메뉴 생성 결정 부위
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.

            getMenuInflater().inflate(R.menu.my, menu);

            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }



   // generic callback, don't need to use this usually
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        int lSelectedItemID = item.getItemId(); // 액션 메뉴 아이디 확인함.
        if(lSelectedItemID == R.id.action_example){
           // To-do something
        }
        return super.onMenuItemSelected(featureId, item);
    }

    /**
     * Handle Action bar item clicks,  the callback of the options menu and onContextItemSelected
     * -> more useful :  the callback of the options menu and onContextItemSelected
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        // 주석처리함..
       // if (id == R.id.action_settings) {
       //     return true;
      //  }

        return super.onOptionsItemSelected(item);
    }

    /**
     *    탭리스너 콜백 액션 총 3종 구현 부위 2014.09.31
     * */
    @Override
    public void onTabSelected(Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction fragmentTransaction) {

    }


}
