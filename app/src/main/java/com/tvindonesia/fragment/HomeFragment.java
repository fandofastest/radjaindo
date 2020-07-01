package com.tvindonesia.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.tvindonesia.adapter.CategoryAdapter;
import com.tvindonesia.adapter.ChannelAdapter;
import com.tvindonesia.adapter.SliderAdapter;
import com.tvindonesia.tvonline.MainActivity;
import com.tvindonesia.tvonline.R;
import com.tvindonesia.item.ItemCategory;
import com.tvindonesia.item.ItemChannel;
import com.tvindonesia.util.Constant;
import com.tvindonesia.util.ItemOffsetDecoration;
import com.tvindonesia.util.NetworkUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import me.relex.circleindicator.CircleIndicator;

import static com.tvindonesia.tvonline.SplashActivity.defaultimage;
import static com.tvindonesia.tvonline.SplashActivity.statususer;



public class HomeFragment extends Fragment {

    ArrayList<ItemChannel> mSliderList, mLatestList,mlistindo,mlistsport,mlistfav;
    ArrayList<ItemCategory> mCategoryList;
    SliderAdapter sliderAdapter;
    ScrollView mScrollView;
    ProgressBar mProgressBar;
    ViewPager mViewPager;
    CircleIndicator circleIndicator;
    RecyclerView rvLatest, rvfav,rvindo,rvsport;
    Button btnMoreLatest, btnMorefav,btnmoreindo,btnmoresport;
    ChannelAdapter  channelAdapter,indoAdapter, channelAdapterfav,channelAdaptersport;
    CategoryAdapter categoryAdapter;
    LinearLayout lyt_not_found;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        mSliderList = new ArrayList<>();
        mLatestList = new ArrayList<>();
        mCategoryList = new ArrayList<>();
        mlistindo= new ArrayList<>();
        mlistfav= new ArrayList<>();
        mlistsport= new ArrayList<>();
        lyt_not_found = rootView.findViewById(R.id.lyt_not_found);
        mScrollView = rootView.findViewById(R.id.scrollView);
        mProgressBar = rootView.findViewById(R.id.progressBar);
        mViewPager = rootView.findViewById(R.id.viewPager);
        circleIndicator = rootView.findViewById(R.id.indicator_unselected_background);
        rvLatest = rootView.findViewById(R.id.rv_latest);
        rvfav=rootView.findViewById(R.id.rv_fav);

        rvindo=rootView.findViewById(R.id.rv_indonesia);
        rvsport=rootView.findViewById(R.id.rv_korea);

//        rvCategory = rootView.findViewById(R.id.rv_category);
        btnMoreLatest = rootView.findViewById(R.id.btn_latest);
        btnMorefav=rootView.findViewById(R.id.btn_homefav);
        btnmoreindo=rootView.findViewById(R.id.btn_homeindo);

        btnmoresport=rootView.findViewById(R.id.btn_homekorea);
//        btnMoreCategory = rootView.findViewById(R.id.btn_category);

        rvLatest.setHasFixedSize(true);
        rvLatest.setNestedScrollingEnabled(false);
        rvLatest.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));


        rvfav.setHasFixedSize(true);
        rvfav.setNestedScrollingEnabled(false);
        rvfav.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        rvindo.setHasFixedSize(true);
        rvindo.setNestedScrollingEnabled(false);
        rvindo.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvsport.setHasFixedSize(true);
        rvsport.setNestedScrollingEnabled(false);
        rvsport.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(requireActivity(), R.dimen.item_offset);
        rvLatest.addItemDecoration(itemDecoration);
        rvindo.addItemDecoration(itemDecoration);
        rvsport.addItemDecoration(itemDecoration);
        rvfav.addItemDecoration(itemDecoration);

        btnMoreLatest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) requireActivity()).hideShowBottomView(false);
                ((MainActivity) requireActivity()).navigationItemSelected(1);
                String categoryName = getString(R.string.International);
                FragmentManager fm = getFragmentManager();
                LatestFragment f1 = new LatestFragment();
                Bundle args = new Bundle();
                args.putString("info", "int");
                f1.setArguments(args);
                assert fm != null;
                FragmentTransaction ft = fm.beginTransaction();
                ft.add(R.id.Container, f1, categoryName);
                ft.addToBackStack(null);

                ft.commit();
                ((MainActivity) requireActivity()).setToolbarTitle(categoryName);
            }
        });

        btnMorefav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) requireActivity()).hideShowBottomView(false);
                ((MainActivity) requireActivity()).navigationItemSelected(1);
                String categoryName = "Favorite";
                FragmentManager fm = getFragmentManager();
                LatestFragment f1 = new LatestFragment();
                Bundle args = new Bundle();
                args.putString("info", "fav");
                f1.setArguments(args);
                assert fm != null;
                FragmentTransaction ft = fm.beginTransaction();
                ft.add(R.id.Container, f1, categoryName);
                ft.addToBackStack(null);

                ft.commit();
                ((MainActivity) requireActivity()).setToolbarTitle(categoryName);
            }
        });


        btnmoreindo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) requireActivity()).hideShowBottomView(false);
                ((MainActivity) requireActivity()).navigationItemSelected(1);
                String categoryName = getString(R.string.indonesia);
                FragmentManager fm = getFragmentManager();
                LatestFragment f1 = new LatestFragment();
                Bundle args = new Bundle();
                args.putString("info", "id");
                f1.setArguments(args);
                assert fm != null;
                FragmentTransaction ft = fm.beginTransaction();
                ft.add(R.id.Container, f1, categoryName);
                ft.addToBackStack(null);
                ft.commit();
                ((MainActivity) requireActivity()).setToolbarTitle(categoryName);
            }
        });
        btnmoresport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) requireActivity()).hideShowBottomView(false);
                ((MainActivity) requireActivity()).navigationItemSelected(1);
                String categoryName = "Sport TV";
                FragmentManager fm = getFragmentManager();
                LatestFragment f1 = new LatestFragment();
                Bundle args = new Bundle();
                args.putString("info", "sport");
                f1.setArguments(args);
                assert fm != null;
                FragmentTransaction ft = fm.beginTransaction();
                ft.add(R.id.Container, f1, categoryName);
                ft.addToBackStack(null);

                ft.commit();
                ((MainActivity) requireActivity()).setToolbarTitle(categoryName);
            }
        });



        if (NetworkUtils.isConnected(getActivity())) {
            getchannelint();
            getchanneliid();
            getchannelfav();
            getchannelisport();
        } else {
            Toast.makeText(getActivity(), getString(R.string.conne_msg1), Toast.LENGTH_SHORT).show();
        }
        return rootView;
    }

    private void getchanneliid() {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Constant.BASEURLCHANNEL+"id.m3u", new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                mProgressBar.setVisibility(View.VISIBLE);
                mScrollView.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                mProgressBar.setVisibility(View.GONE);
                mScrollView.setVisibility(View.VISIBLE);

                String result = new String(responseBody);
                try {
                    JSONObject mainJson = new JSONObject(result);
                    JSONObject objJson;
                                 JSONArray jsonLatest = mainJson.getJSONArray(Constant.ARRAY_NAME);
                    for (int i = 0; i < jsonLatest.length(); i++) {
                        objJson = jsonLatest.getJSONObject(i);
                        ItemChannel objItem = new ItemChannel();
                        objItem.setId(String.valueOf(i));
                        objItem.setChannelName(objJson.getString("title"));
                        objItem.setChannelCategory("Indonesia");
                        objItem.setImage(objJson.getString("thumb_square"));
                        if (objJson.getString("thumb_square").equals("")){
                            objItem.setImage(defaultimage);
                        }

                        if (statususer.equals("aman")){
                            objItem.setChannelUrl(objJson.getString("url"));
                        }else{
                            objItem.setChannelUrl(null);
                            objItem.setImage(defaultimage);
                        }


                        objItem.setChannelAvgRate("5");
                        mlistindo.add(objItem);
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
                displayData();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                mProgressBar.setVisibility(View.GONE);
                mScrollView.setVisibility(View.GONE);
                lyt_not_found.setVisibility(View.VISIBLE);
            }
        });
    }

    private void getchannelfav() {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://fando.xyz/gettvfav.php", new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                mProgressBar.setVisibility(View.VISIBLE);
                mScrollView.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                mProgressBar.setVisibility(View.GONE);
                mScrollView.setVisibility(View.VISIBLE);

                String result = new String(responseBody);
                try {
                    JSONObject mainJson = new JSONObject(result);
                    JSONObject objJson;
                    JSONArray jsonLatest = mainJson.getJSONArray(Constant.ARRAY_NAME);
                    for (int i = 0; i < jsonLatest.length(); i++) {
                        objJson = jsonLatest.getJSONObject(i);
                        ItemChannel objItem = new ItemChannel();
                        objItem.setId(String.valueOf(i));
                        objItem.setChannelName(objJson.getString("title"));
                        objItem.setChannelCategory("Favorite");
                        objItem.setImage(objJson.getString("thumb_square"));
                        if (objJson.getString("thumb_square").equals("")){
                            objItem.setImage(defaultimage);
                        }

                        if (statususer.equals("aman")){
                            objItem.setChannelUrl(objJson.getString("url"));
                        }else{
                            objItem.setChannelUrl(null);
                            objItem.setImage(defaultimage);
                        }


                        objItem.setChannelAvgRate("5");
                        mlistfav.add(objItem);
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
                displayData();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                mProgressBar.setVisibility(View.GONE);
                mScrollView.setVisibility(View.GONE);
                lyt_not_found.setVisibility(View.VISIBLE);
            }
        });
    }


    private void getchannelisport() {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Constant.URLSPORT, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                mProgressBar.setVisibility(View.VISIBLE);
                mScrollView.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                mProgressBar.setVisibility(View.GONE);
                mScrollView.setVisibility(View.VISIBLE);

                String result = new String(responseBody);
                try {
                    JSONObject mainJson = new JSONObject(result);
                    JSONObject objJson;
                    JSONArray jsonLatest = mainJson.getJSONArray(Constant.ARRAY_NAME);
                    for (int i = 0; i < jsonLatest.length(); i++) {
                        objJson = jsonLatest.getJSONObject(i);
                        ItemChannel objItem = new ItemChannel();
                        objItem.setId(String.valueOf(i));
                        objItem.setChannelName(objJson.getString("title"));
                        objItem.setChannelCategory("Sport TV");
                        objItem.setImage(objJson.getString("thumb_square"));
                        if (objJson.getString("thumb_square").equals("")){
                            objItem.setImage(defaultimage);
                        }

                        if (statususer.equals("aman")){
                            objItem.setChannelUrl(objJson.getString("url"));
                        }else{
                            objItem.setChannelUrl(null);
                            objItem.setImage(defaultimage);
                        }


                        objItem.setChannelAvgRate("5");
                        mlistsport.add(objItem);
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
                displayData();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                mProgressBar.setVisibility(View.GONE);
                mScrollView.setVisibility(View.GONE);
                lyt_not_found.setVisibility(View.VISIBLE);
            }
        });
    }


    private void getchannelint() {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Constant.BASEURLCHANNEL+"id.m3u", new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                mProgressBar.setVisibility(View.VISIBLE);
                mScrollView.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                mProgressBar.setVisibility(View.GONE);
                mScrollView.setVisibility(View.VISIBLE);

                String result = new String(responseBody);
                try {
                    JSONObject mainJson = new JSONObject(result);
                    JSONArray jsonSlider = mainJson.getJSONArray(Constant.ARRAY_NAME);

                    JSONObject objJson;
                    for (int i = 0; i < jsonSlider.length(); i++) {
                        objJson = jsonSlider.getJSONObject(i);
                        ItemChannel objItem = new ItemChannel();
                        objItem.setId(String.valueOf(i));
                        objItem.setChannelName(objJson.getString("title"));
                        objItem.setChannelCategory("TV Indonesia");
                        objItem.setImage(objJson.getString("thumb_square"));
                        if (objJson.getString("thumb_square").equals("")){
                            objItem.setImage(defaultimage);
                        }

                        if (statususer.equals("aman")){
                            objItem.setChannelUrl(objJson.getString("url"));
                        }else{
                            objItem.setChannelUrl(null);
                            objItem.setImage(defaultimage);
                        }

                        objItem.setChannelAvgRate("5");
                        mSliderList.add(objItem);
                    }

                    JSONArray jsonLatest = mainJson.getJSONArray(Constant.ARRAY_NAME);
                    for (int i = 0; i < jsonLatest.length(); i++) {
                        objJson = jsonLatest.getJSONObject(i);
                        ItemChannel objItem = new ItemChannel();
                        objItem.setId(String.valueOf(i));
                        objItem.setChannelName(objJson.getString("title"));
                        objItem.setChannelCategory("International");
                        objItem.setImage(objJson.getString("thumb_square"));
                        if (objJson.getString("thumb_square").equals("")){
                            objItem.setImage(defaultimage);
                        }

                        if (statususer.equals("aman")){
                            objItem.setChannelUrl(objJson.getString("url"));
                        }else{
                            objItem.setChannelUrl(null);
                            objItem.setImage(defaultimage);
                        }
                        objItem.setChannelAvgRate("5");
                        mLatestList.add(objItem);
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
                displayData();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                mProgressBar.setVisibility(View.GONE);
                mScrollView.setVisibility(View.GONE);
                lyt_not_found.setVisibility(View.VISIBLE);
            }
        });
    }


    private void displayData() {
        sliderAdapter = new SliderAdapter(requireActivity(), mSliderList);
        mViewPager.setAdapter(sliderAdapter);
        circleIndicator.setViewPager(mViewPager);

        channelAdapter = new ChannelAdapter(getActivity(), mLatestList, R.layout.row_home_channel_item);
        rvLatest.setAdapter(channelAdapter);
        channelAdapterfav = new ChannelAdapter(getActivity(), mlistfav, R.layout.row_home_channel_item);
        rvfav.setAdapter(channelAdapterfav);
        channelAdaptersport = new ChannelAdapter(getActivity(), mlistsport, R.layout.row_home_channel_item);
        rvsport.setAdapter(channelAdaptersport);
//
//        categoryAdapter = new CategoryAdapter(getActivity(), mCategoryList, R.layout.row_home_category_item);
//        rvCategory.setAdapter(categoryAdapter);
    }

}
