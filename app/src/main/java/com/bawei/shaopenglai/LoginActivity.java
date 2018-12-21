package com.bawei.shaopenglai;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bawei.shaopenglai.adapter.DaoHangAdapter;
import com.bawei.shaopenglai.adapter.PubuAdapter;
import com.bawei.shaopenglai.adapter.ShoppingAdapter;
import com.bawei.shaopenglai.api.Apis;
import com.bawei.shaopenglai.api.Constans;
import com.bawei.shaopenglai.bean.DaoHangBean;
import com.bawei.shaopenglai.bean.ShoppingBean;

import com.bawei.shaopenglai.presenter.IPresenterImpl;
import com.bawei.shaopenglai.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements IView,View.OnClickListener {

    private static final int SPAN_COUNT=2;
    private XRecyclerView myRecyle;
    private IPresenterImpl iPresenter;
    private EditText editText;
    private XRecyclerView xRecyclerView;
    private ShoppingAdapter adapter;
    private ImageView sweep;
    private ImageView iv_change;
    private ImageView iv_search;
    private int mPage=1;
    private boolean b=false;
    private int uid=71;
    private PubuAdapter pubuAdapter;
    private boolean mLinear=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initPresenter();
        initView();
        initRecyclerView();
    }

    private void initRecyclerView() {

        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingMoreEnabled(true);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage=1;
                loadData();
            }

            @Override
            public void onLoadMore() {
                loadData();
            }

        });
        changeRecyclerView();
    }

    private void changeRecyclerView() {
        if (b==false){
            xRecyclerView.setLayoutManager(new LinearLayoutManager(LoginActivity.this,LinearLayoutManager.VERTICAL,false));
            iv_change.setBackgroundResource(R.drawable.ic_action_right);
            b=true;
        }else if (b==true){
            xRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(SPAN_COUNT,StaggeredGridLayoutManager.VERTICAL));
            iv_change.setBackgroundResource(R.drawable.ic_action_shu);
            b=false;
        }
        adapter=new ShoppingAdapter(this,mLinear);
        xRecyclerView.setAdapter(adapter);
        mLinear=!mLinear;
        adapter.setOnLongItemClickListener(new ShoppingAdapter.OnLongItemClickListener() {
            @Override
            public void OnLongItemClick(int position) {

            }
        });

    }

    private void initView() {
        mPage=1;
        myRecyle=findViewById(R.id.MyRecy);
        pubuAdapter=new PubuAdapter(this);
        myRecyle.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));
        myRecyle.setAdapter(pubuAdapter);
        editText=findViewById(R.id.edit_query);
        sweep=findViewById(R.id.zxing);
        iv_change=findViewById(R.id.right);
        iv_search=findViewById(R.id.left);

        xRecyclerView=findViewById(R.id.contents);
        adapter=new ShoppingAdapter(this);
        xRecyclerView.setAdapter(adapter);
        sweep.setOnClickListener(this);
        iv_search.setOnClickListener(this);
        iv_change.setOnClickListener(this);
        pubuAdapter.setOnLongItemClickListener(new ShoppingAdapter.OnLongItemClickListener() {
            @Override
            public void OnLongItemClick(int position) {
                Toast.makeText(LoginActivity.this,pubuAdapter.getmData().get(position).getIcon(),Toast.LENGTH_LONG).show();
            }
        });

    }

    private void initPresenter() {
        iPresenter=new IPresenterImpl(this);
    }

    @Override
    public void getRequest(Object data) {
        if (data instanceof ShoppingBean){
            ShoppingBean bean= (ShoppingBean) data;
            if (bean==null||!bean.isSuccess()){
                Toast.makeText(LoginActivity.this,bean.getMsg(),Toast.LENGTH_LONG).show();
            }else {
                if (mPage==1){
                    adapter.setData(bean.getData());
                }else {
                    adapter.addData(bean.getData());
                }
                mPage++;
                xRecyclerView.refreshComplete();
                xRecyclerView.loadMoreComplete();
            }
        }else if (data instanceof DaoHangBean){
            DaoHangBean bean= (DaoHangBean) data;
            if (bean==null||!bean.isSuccess()){
                Toast.makeText(LoginActivity.this,bean.getMsg(),Toast.LENGTH_LONG).show();
            }else {
                if (mPage==1){
                    pubuAdapter.setmData(bean.getData());
                }else {
                    pubuAdapter.addData(bean.getData());
                }
                mPage++;
                myRecyle.refreshComplete();
                myRecyle.loadMoreComplete();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left:
                loadData();
                break;
            case R.id.right:
                if (b==false){
                    xRecyclerView.setLayoutManager(new LinearLayoutManager(LoginActivity.this,LinearLayoutManager.VERTICAL,false));
                    iv_change.setBackgroundResource(R.drawable.ic_action_right);
                    b=true;
                }else if (b==true){
                    xRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(SPAN_COUNT,StaggeredGridLayoutManager.VERTICAL));
                    iv_change.setBackgroundResource(R.drawable.ic_action_shu);
                    b=false;
                }
                break;
            case R.id.zxing:
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if (ContextCompat.checkSelfPermission(LoginActivity.this,Manifest.permission.CAMERA)!=PackageManager.PERMISSION_DENIED){
                        ActivityCompat.requestPermissions(LoginActivity.this,new String[]{Manifest.permission.CAMERA},100);
                    }else {
                        Intent intent=new Intent(LoginActivity.this,SweepActivity.class);
                        startActivity(intent);
                    }
                    if (ContextCompat.checkSelfPermission(LoginActivity.this,Manifest.permission.INTERNET)!=PackageManager.PERMISSION_DENIED){
                        ActivityCompat.requestPermissions(LoginActivity.this,new String[]{Manifest.permission.INTERNET},200);
                    }else {
                        Intent intent=new Intent(LoginActivity.this,SweepActivity.class);
                        startActivity(intent);
                    }
                }else {
                    Intent intent=new Intent(LoginActivity.this,SweepActivity.class);
                    startActivity(intent);
                }
                break;
                default:
                    break;
        }
    }

    private void loadData() {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constans.MAP_KEY_SEARCH_PRODUCTS_KEYWORDS,editText.getText().toString());
        hashMap.put(Constans.MAP_KEY_SEARCH_PRODUCES_PAGE,String.valueOf(mPage));
        hashMap.put(Constans.MAP_KEY_PRODUCTS_DETAIL_UID,String.valueOf(uid));
        iPresenter.startRequestPost(Apis.URL_LOGIN_POST,hashMap,ShoppingBean.class);
        iPresenter.startRequestGet(String.format(Apis.URL_LOGIN_IMAGE,mPage),null,DaoHangBean.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onDetach();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==100&&grantResults[0]==PackageManager.PERMISSION_DENIED){
            Intent intent=new Intent(LoginActivity.this,SweepActivity.class);
            startActivity(intent);
        }else if (requestCode==200&&grantResults[1]==PackageManager.PERMISSION_DENIED){
            Intent intent=new Intent(LoginActivity.this,SweepActivity.class);
            startActivity(intent);
        }
    }
}
