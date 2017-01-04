package com.view.john.materialdesigndemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import adapter.FruitAdapter;
import bean.FruitBean;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private FloatingActionButton mFloatingActionButton;
    private RecyclerView mRecyclerView;
    private GridLayoutManager gridLayoutManager;
    private FruitAdapter mFruitAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private FruitBean[] mFruitBeens = new FruitBean[]{new FruitBean("Apple", R.drawable.apple),
            new FruitBean("Banana", R.drawable.banana), new FruitBean("Orange", R.drawable.orange),
            new FruitBean("Watermolon", R.drawable.watermelon), new FruitBean("Pear", R.drawable.pear),
            new FruitBean("Grape", R.drawable.grape), new FruitBean("Pineapple", R.drawable.pineapple),
            new FruitBean("StrawBerry", R.drawable.strawberry), new FruitBean("Cherry", R.drawable.cherry),
            new FruitBean("Mango", R.drawable.mango)};
    private List<FruitBean> mFruitList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);//将Toolbar设置为ActionBar
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawable_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        ActionBar actionBar = getSupportActionBar();//获取被Toolbar替换后的AcionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);//显示home菜单
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);//设置Home菜单的图标
        }
        navigationView.setCheckedItem(R.id.nav_call);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "you click FloatingButton", Toast.LENGTH_SHORT).show();
                Snackbar.make(view, "Data deleted", Snackbar.LENGTH_SHORT).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "Data restored", Toast.LENGTH_SHORT).show();
                    }
                }).show();

            }
        });
        mFruitList = new ArrayList<FruitBean>();
        prepareDatas(100);
        gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mFruitAdapter = new FruitAdapter(mFruitList);
        mRecyclerView.setAdapter(mFruitAdapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.mSwipeRefresh);
//      mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.red, R.color.orange, R.color.yellow, R.color.green, R.color.colorPrimary,
                R.color.mediumturquoise, R.color.darkviolet);
//      mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.lightgreen),getResources().getColor(R.color.colorPrimary),getResources().getColor(R.color.red),getResources().getColor(R.color.greenyellow));

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });
    }

    private void refreshFruits() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        prepareDatas(50);
                        mFruitAdapter.notifyDataSetChanged();
                        mSwipeRefreshLayout.setRefreshing(false);

                    }
                });
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.backup:
                Toast.makeText(this, "You clicked Backup", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this, "You clicked delete", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(this, "You clicked setting", Toast.LENGTH_SHORT).show();
//                mDrawerLayout.openDrawer(GravityCompat.END);
                break;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);//打开滑动菜单 打开的方向必须和xml布局中方向一致
                break;
        }
        return true;
    }

    /**
     * prepare datas for RecyclerView
     *
     * @param size
     */
    public void prepareDatas(int size) {
        mFruitList.clear();
        for (int i = 0; i < size; i++) {
            Random mRandom = new Random();
            //produce a num between 0 and mFruitBeens.length,but mFruitBeens.length is not included
            int index = mRandom.nextInt(mFruitBeens.length);
            mFruitList.add(mFruitBeens[index]);
        }

    }

}
