package intersoul.fashion.view;


import android.app.ActionBar.Tab;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar;

import intersoul.fashion.R;
/**
 * Created by tiger154 on 2014-10-02.
 *
 * 탭 리스너..
 *
 */
public class TabListener implements ActionBar.TabListener {
    Fragment mFragment;

    // 생성자
    public TabListener(Fragment aFragment){

        this.mFragment = aFragment;
    }


    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        // 탭이 선택되면 생성자에서 저장해놓은 프레그먼트로 치환한다.
        ft.replace(R.id.container,mFragment);
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        ft.remove(mFragment);
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
      // 재선택 됬을때 모하려나?
    }
}
