package info.efficacious.centralmodelschool.Tab;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import info.efficacious.centralmodelschool.R;
import info.efficacious.centralmodelschool.common.ConnectionDetector;
import info.efficacious.centralmodelschool.fragment.Student_LeaveList;
import info.efficacious.centralmodelschool.fragment.Teacher_LeaveList;


public class Admin_LeaveList extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static String page;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    String role_id;
    ConnectionDetector cd;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leavedetail_admin);
        cd = new ConnectionDetector(getApplicationContext());
        settings = getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        role_id = settings.getString("TAG_USERTYPEID", "");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        if (!cd.isConnectingToInternet()) {

            AlertDialog.Builder alert = new AlertDialog.Builder(Admin_LeaveList.this);
            alert.setMessage("No Internet Connection");
            alert.setPositiveButton("OK", null);
            alert.show();

        } else {

            setupViewPager(viewPager);
        }
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        cd = new ConnectionDetector(getApplicationContext());


    }

    private void setupViewPager(ViewPager viewPager) {
        Admin_LeaveList.ViewPagerAdapter adapter = new Admin_LeaveList.ViewPagerAdapter(getSupportFragmentManager());
        try {
            adapter.addFrag(new Teacher_LeaveList(), "Teacher");
            adapter.addFrag(new Student_LeaveList(), "Student");
        } catch (Exception ex) {

        }


        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}