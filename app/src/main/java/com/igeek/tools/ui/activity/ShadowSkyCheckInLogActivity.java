package com.igeek.tools.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.igeek.tools.R;
import com.igeek.tools.db.DataManager;
import com.igeek.tools.db.entity.ShadowSkyAccount;
import com.igeek.tools.db.entity.ShadowSkyCheckInLogEntity;
import com.igeek.tools.db.entity.ShadowSkyCheckInLogEntity_;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.objectbox.Property;

public class ShadowSkyCheckInLogActivity extends AppCompatActivity {

    @BindView(R.id.shadowsky_check_in_log_recyclerview)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shadow_sky_check_in_log);
        ButterKnife.bind(this);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle("ShadowSky 签到日志");
        }

        Intent intent = getIntent();
        ShadowSkyAccount shadowSkyAccount = (ShadowSkyAccount) intent.getSerializableExtra("shadowSkyAccount");
        if (shadowSkyAccount != null) {
            HashMap<Property, Object> map = new HashMap<>();
            map.put(ShadowSkyCheckInLogEntity_.account, shadowSkyAccount.getAccount());
            List<ShadowSkyCheckInLogEntity> dataList = DataManager.getInstance().getListEqual(ShadowSkyCheckInLogEntity.class, map, ShadowSkyCheckInLogEntity_.checkInTime);
            CheckInLogAdapter checkInLogAdapter = new CheckInLogAdapter(dataList);

            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(checkInLogAdapter);
        }
    }


    public static class CheckInLogAdapter extends RecyclerView.Adapter<ShadowSkyCheckInLogActivity.CheckInLogAdapter.CheckInLogViewHolder> {

        private List<ShadowSkyCheckInLogEntity> dataList = new ArrayList<>();

        public CheckInLogAdapter(List<ShadowSkyCheckInLogEntity> dataList) {
            this.dataList = dataList;
        }

        @NonNull
        @Override
        public ShadowSkyCheckInLogActivity.CheckInLogAdapter.CheckInLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_shadowsky_check_in_log, null);
            return new ShadowSkyCheckInLogActivity.CheckInLogAdapter.CheckInLogViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ShadowSkyCheckInLogActivity.CheckInLogAdapter.CheckInLogViewHolder holder, int position) {
            ShadowSkyCheckInLogEntity shadowSkyCheckInLogEntity = dataList.get(position);
            holder.idView.setText(String.valueOf(shadowSkyCheckInLogEntity.getId()));
            holder.accountView.setText(String.valueOf(shadowSkyCheckInLogEntity.getAccount()));
            holder.checkInTimeView.setText(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    .format(LocalDateTime.ofInstant(shadowSkyCheckInLogEntity.getCheckInTime().toInstant(), ZoneId.systemDefault())));
            holder.checlInResultView.setText(String.valueOf(shadowSkyCheckInLogEntity.getCheckInResult()));
        }

        @Override
        public int getItemCount() {
            return dataList != null ? dataList.size() : 0;
        }

        public static class CheckInLogViewHolder extends RecyclerView.ViewHolder {

            TextView idView;
            TextView accountView;
            TextView checkInTimeView;
            TextView checlInResultView;

            public CheckInLogViewHolder(@NonNull View itemView) {
                super(itemView);
                idView = itemView.findViewById(R.id.id);
                accountView = itemView.findViewById(R.id.account);
                checkInTimeView = itemView.findViewById(R.id.check_in_time);
                checlInResultView = itemView.findViewById(R.id.check_in_result);
            }
        }
    }
}
