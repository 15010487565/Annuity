package com.denong.doluck.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.denong.doluck.R;
import com.denong.doluck.adapter.HomeRcTopAdapter;
import com.denong.doluck.util.RecyclerViewDecoration;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * date:2017/6/7
 */


public class HomeTopVpFragment extends Fragment {
    private View view;
    private static final String KEY = "title";
    private TextView tvContent;
    static HomeRcTopAdapter.OnHomeTopItemClickListener mlistener;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home_rc_top,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.iten_home_rc_top);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        HomeRcTopAdapter adapter = new HomeRcTopAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(mlistener);
        RecyclerViewDecoration recyclerViewDecoration = new RecyclerViewDecoration(
                getActivity(), LinearLayoutManager.HORIZONTAL, 20, getResources().getColor(R.color.black_f7));
        recyclerView.addItemDecoration(recyclerViewDecoration);


        List<String> advs = new ArrayList<>();
        advs.add("http://pic31.nipic.com/20130722/9252150_095713523386_2.jpg");
        advs.add("http://pic27.nipic.com/20130305/9252150_153617685375_2.jpg");
        advs.add("http://img5.imgtn.bdimg.com/it/u=1506310031,2048450892&fm=26&gp=0.jpg");
        adapter.setData(advs);
        return view;
    }

    /**
     * fragment静态传值
     */
    public static HomeTopVpFragment newInstance(String str, HomeRcTopAdapter.OnHomeTopItemClickListener listener){
        mlistener = listener;
        HomeTopVpFragment fragment = new HomeTopVpFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY,str);
        fragment.setArguments(bundle);

        return fragment;
    }
}