package com.sunloto.drawing.lotterydrawresult;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.sunloto.drawing.lotterydrawresult.adapter.StickyListAdapter;
import com.sunloto.drawing.lotterydrawresult.bean.HotGame;
import com.sunloto.drawing.lotterydrawresult.bean.Result;
import com.sunloto.drawing.lotterydrawresult.common.WebDefine;
import com.sunloto.drawing.lotterydrawresult.net.WoZhongLaApi;
import com.sunloto.drawing.lotterydrawresult.widget.DragLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.RestAdapter;
import se.emilsjolander.stickylistheaders.ExpandableStickyListHeadersListView;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


public class MainActivity extends FragmentActivity implements AdapterView.OnItemClickListener{

    @InjectView(R.id.dragLayout)
    DragLayout mDragLayout;
    @InjectView(R.id.expandListView)
    ExpandableStickyListHeadersListView mExpandListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initViews();

    }

    private void initViews() {
        mDragLayout.setDragListener(new DragLayout.DragListener() {
            @Override
            public void onOpen() {

            }

            @Override
            public void onClose() {

            }

            @Override
            public void onDrag(float percent) {
//                ViewHelper.setAlpha();
            }
        });
        mExpandListView.setAdapter(new StickyListAdapter(this));
        mExpandListView.setOnHeaderClickListener(new StickyListHeadersListView.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(StickyListHeadersListView stickyListHeadersListView, View view, int itemPosition, long headerId, boolean currentlySticky) {
                if (mExpandListView.isHeaderCollapsed(headerId)) {
                    mExpandListView.expand(headerId);
                } else {
                    mExpandListView.collapse(headerId);
                }
            }
        });
        mExpandListView.setOnItemClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("youzh", "位置： " + position);
        mDragLayout.close();

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(WebDefine.BASE_URL).build();
        WoZhongLaApi woZhongLaApi = restAdapter.create(WoZhongLaApi.class);
        for (HotGame hotGame : woZhongLaApi.getLotteryHotList()) {
            hotGame.getHomeTeamName();
            hotGame.getAwayTeamName();
            Log.d("youzh", "主队：" + hotGame.getHomeTeamName() + "  客队： " + hotGame.getAwayTeamName());
        }
    }
}
