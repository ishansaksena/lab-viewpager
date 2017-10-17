package edu.uw.viewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements MovieListFragment.OnMovieSelectedListener, SearchFragment.OnSearchListener {

    private static final String TAG = "MainActivity";
    public static final String MOVIE_LIST_FRAGMENT_TAG = "MoviesListFragment";
    public static final String MOVIE_DETAIL_FRAGMENT_TAG = "DetailFragment";

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    private SearchFragment searchFragment;
    private MovieListFragment listFragment;
    private DetailFragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchFragment = SearchFragment.newInstance();

        viewPager = (ViewPager)findViewById(R.id.pager);
        pagerAdapter = new MoviePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    public void onSearchSubmitted(String searchTerm) {
        Log.v(TAG, "Search submitted");
        listFragment = MovieListFragment.newInstance(searchTerm);
        pagerAdapter.notifyDataSetChanged();
        viewPager.setCurrentItem(1);
    }

    @Override
    public void onMovieSelected(Movie movie) {
        detailFragment = DetailFragment.newInstance(movie);
        pagerAdapter.notifyDataSetChanged();
        viewPager.setCurrentItem(2);
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    private class MoviePagerAdapter extends FragmentStatePagerAdapter {

        public MoviePagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0) return searchFragment;
            if(position == 1) return listFragment;
            if(position == 2) return detailFragment;
            return null;
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            if(listFragment == null){
                return 1;
            } else if (detailFragment == null) {
                return 2;
            } else {
                return 3;
            }
        }
    }
}
