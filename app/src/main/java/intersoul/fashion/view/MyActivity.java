package intersoul.fashion.view;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



import intersoul.fashion.R;

import intersoul.fashion.view.NavigationDrawerFragment;
import intersoul.fashion.view.bag.BagFragment;
import intersoul.fashion.view.store.StoreFragment;
import intersoul.fashion.view.timeline.TimeLineFragment;


public class MyActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks
                    , NavigationDrawerFragment.Communicator
                    , ActionBar.TabListener{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer. 주석추가후 커밋
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private PlaceholderFragment mPlaceHolderFragment = new PlaceholderFragment();
    private FrameLayout mFrameLayout;
    private Menu mMenu;
    private int mTabCount = 3;

    Tab mTabTimeLine, mTabStore, mTabBag;
    Fragment mTimeLineFragment = new TimeLineFragment();
    Fragment mStoreFragment = new StoreFragment();
    Fragment mBagFragment = new BagFragment();


    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        Log.d("INFO","OnCreate진입");

        setTab();              // 1. 탭 설정
        setNavigationDrawer(); // 2. 네비게이션 드로워 설정



    }


    /**
     *  액션바 탭을 설정한다.
     */
    protected void setTab(){
        /* *
        *   액션바 + 탭 구성 영역
        * */
        // 액션바 탭 구성하기 (기존) 하나의 프레그먼트에 텍스트 변경 -> 기존 각 탭별 별도의 프레그먼트 치환
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

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
    protected void setNavigationDrawer(){

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer); // 네비게이션 드로워 가져오기
        mTitle = getTitle(); // 제목 가져오기
        // Set up the drawer.
        // OnCreate 시점에서는 프레그먼트 뷰를 잡을수 없다... Start 시점에서 잡게 변경하면 될듯..
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("INFO","onStart 진입");

        setNavigationDrawer(); // 네비게이션 드로워 설정
/*
        TextView lTextView = (TextView)findViewById(R.id.section_label);

        Fragment lFragment = getFragmentManager().findFragmentById(R.id.container);
        int section = lFragment.getArguments().getInt(lFragment.ARG_SECTION_NUMBER);


        String lValue = lTextView.getText().toString();
        String temp = "It's Called from MainActivity's onStart " + section;
        lTextView.setText(temp);
        Log.d("onStart", temp);
*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("INFO","onResume 진입");

    }

    // 네비게이션 프레그먼트의 콜백 클릭 이벤트 실행 부분임... 아주 중요함..
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments

        FragmentManager fragmentManager = getFragmentManager();
        // 예전 코드는 플레이스 홀더 단일 프레그먼트만 적용했지만... 이제는 바꾼다.
        // 1번을 선택하면... 1번이 바뀌게 하고싶다면??
        // 1번이 넘어오면 1번 탭을 활성화, 2번이 넘어오면 2번탭을 활성화

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


        /*
        PlaceholderFragment lPlaceholderF = PlaceholderFragment.newInstance(position + 1);
        fragmentManager.beginTransaction()
                .replace(R.id.container, lPlaceholderF, "MY_FRAGMENT"+(position + 1))
                .commit();
*/
       // lPlaceholderF.changeTextViewData("이게 작동할까?");
       // Log.d("INFO","MY_FRAGMENT"+(position + 1));

        //sendData("이게 작동할까요?");
       // TextView LabelText  = (TextView)findViewById(R.id.section_label);
       // LabelText.setText("Its works i think");
       // 클릭 이벤트가 발생했을경우 내용을 가져와서 별도 처리하는 부분 ....

        Toast.makeText(MyActivity.this, "HelloWorld"+position, Toast.LENGTH_SHORT).show();

    }

    // 네비게이션 드로워 콜백에서, 클릭이벤트 발생시 데이터를
    @Override
    public void sendData(String data){

        mPlaceHolderFragment.changeTextViewData(data);

    }


    // 타임라인 프레그먼트 설정변경
    // Ok 액션 받았음 From DrawerAction To TimeLineFragment
    public void setListViewTimelineFragment(int num){
        ListView lListView = (ListView)findViewById(R.id.timeLineListView);
    }

    public void setTextViewBagFragment(int aNum){
       TextView lText = (TextView)findViewById(R.id.bagText);
       lText.setText("I got param from DrawerFragment, its number : " + aNum);
    }



    // 플레이스 홀더 프레그먼트 내의, 뷰 텍스트를 변경한다...
    public  void setPlaceHoderText(int number){

        TextView lTextView = (TextView)findViewById(R.id.storeText);
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
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // 탭 카운트가 고유 넘버값 과 같다면 생성 제외
        if(actionBar.getTabCount() != mTabCount) {
            /*
            for (int i = 1; i <= mTabCount; i++) {
                Tab tab = actionBar.newTab();
                tab.setText("Tab " + i);
                tab.setTabListener(this);
                actionBar.addTab(tab);
            }*/
            //setTab();
        }
        actionBar.setTitle(R.string.app_name);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            mMenu = menu; // 생성된 메뉴 인스턴스 내부 변수 할당
            getMenuInflater().inflate(R.menu.my, menu);
            //restoreActionBar(); --> 기존 값을 초기화 시켜버리는 문제가 있어 제거 한다.
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    // 액션바의 아이템이 선택되었을때 보여줄 액션
    // 드로워액션 바를 오픈해보겠음...
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        int lSelectedItemID = item.getItemId(); // 액션 메뉴 아이디 확인함.

        if(lSelectedItemID == R.id.action_example){

            Toast.makeText(MyActivity.this, "눌렀나?"+Integer.toString(lSelectedItemID) , Toast.LENGTH_SHORT).show();
        }



        return super.onMenuItemSelected(featureId, item);
    }

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
