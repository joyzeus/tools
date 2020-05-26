package com.igeek.tools.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.igeek.tools.R;
import com.igeek.tools.api.ApiManager;
import com.igeek.tools.api.service.ShadowSkyService;
import com.igeek.tools.db.DataManager;
import com.igeek.tools.db.entity.ShadowSkyAccount;
import com.igeek.tools.db.entity.ShadowSkyAccount_;
import com.igeek.tools.db.entity.ShadowSkyCheckInLogEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.objectbox.Property;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.igeek.tools.R.layout.activity_shadow_sky;

public class ShadowSkyActivity extends AppCompatActivity {

    @BindView(R.id.add_account_button)
    Button addAccountButton;
    @BindView(R.id.account_list_recyclerview)
    RecyclerView accountListRecyclerview;
    @BindView(R.id.shadowsky_check_in_button)
    Button shadowskyCheckInButton;
    private AccountAdapter accountAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_shadow_sky);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle("ShadowSKy 账号管理");
        }

        ButterKnife.bind(this);
        List<ShadowSkyAccount> dataList = DataManager.getInstance().getBox(ShadowSkyAccount.class).getAll();
        accountAdapter = new AccountAdapter(dataList);
        accountAdapter.setOnItemClickListener(new AccountAdapter.OnItemClickListener() {
            @Override
            public void onCheckInClick(View v, int position) {
                ShadowSkyAccount shadowSkyAccount = dataList.get(position);
                doCheckIn(shadowSkyAccount);
            }

            @Override
            public void onCheckInLogClick(View v, int position) {
                ShadowSkyAccount shadowSkyAccount = dataList.get(position);
                showCheckInLog(shadowSkyAccount);
            }
        });
        accountListRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        accountListRecyclerview.setAdapter(accountAdapter);
    }

    public static class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder> implements View.OnClickListener {

        private List<ShadowSkyAccount> dataList = new ArrayList<>();

        private OnItemClickListener onItemClickListener;

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        public AccountAdapter(List<ShadowSkyAccount> dataList) {
            this.dataList = dataList;
        }

        @NonNull
        @Override
        public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_shadowsky_account, null);
            return new AccountViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
            ShadowSkyAccount shadowSkyAccount = dataList.get(position);
            holder.idView.setText(String.valueOf(shadowSkyAccount.getId()));
            holder.accountView.setText(String.valueOf(shadowSkyAccount.getAccount()));

            holder.checkInButton.setTag(position);
            holder.checlInLogButton.setTag(position);
            holder.checkInButton.setOnClickListener(AccountAdapter.this);
            holder.checlInLogButton.setOnClickListener(AccountAdapter.this);
        }

        @Override
        public int getItemCount() {
            return dataList != null ? dataList.size() : 0;
        }

        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            switch (v.getId()) {
                case R.id.check_in_button:
                    if (onItemClickListener != null) {
                        onItemClickListener.onCheckInClick(v, position);
                    }
                    break;
                case R.id.check_in_log_button:
                    if (onItemClickListener != null) {
                        onItemClickListener.onCheckInLogClick(v, position);
                    }
                    break;
            }
        }

        public void setDataList(List<ShadowSkyAccount> all) {
            dataList = all;
        }

        public static class AccountViewHolder extends RecyclerView.ViewHolder {

            TextView idView;
            TextView accountView;
            TextView checkInButton;
            TextView checlInLogButton;

            public AccountViewHolder(@NonNull View itemView) {
                super(itemView);
                idView = itemView.findViewById(R.id.account_id);
                accountView = itemView.findViewById(R.id.account);
                checkInButton = itemView.findViewById(R.id.check_in_button);
                checlInLogButton = itemView.findViewById(R.id.check_in_log_button);
            }
        }

        public interface OnItemClickListener {
            void onCheckInClick(View v, int position);

            void onCheckInLogClick(View v, int position);
        }
    }

    @OnClick({R.id.add_account_button, R.id.shadowsky_check_in_button})
    public void onClick(View v) {
        Log.d(ShadowSkyActivity.class.getName(), "click");
        switch (v.getId()) {
            case R.id.add_account_button:
                View view = LayoutInflater.from(ShadowSkyActivity.this).inflate(R.layout.dialog_add_shadowsky_account, null);
                AlertDialog alertDialog = new AlertDialog.Builder(ShadowSkyActivity.this)
                        .setTitle("请添加ShadowSky账号")
                        .setView(view)
                        .setPositiveButton("登录", (dialog, which) -> {
                            EditText accountEditText = view.findViewById(R.id.account_edittext);
                            EditText passwordEditText = view.findViewById(R.id.password_edittext);

                            String account = accountEditText.getText().toString().trim();
                            String password = passwordEditText.getText().toString().trim();

                            HashMap<String, Object> map = new HashMap<>();
                            map.put("email", account);
                            map.put("passwd", password);
                            map.put("remember_me", "week");

                            ShadowSkyAccount shadowSkyAccount = new ShadowSkyAccount(account, password, null);
                            HashMap<Property, Object> hashMap = new HashMap<>();
                            hashMap.put(ShadowSkyAccount_.account, account);
                            List<ShadowSkyAccount> list = DataManager.getInstance().getListEqual(ShadowSkyAccount.class, hashMap);
                            if (list != null && list.size() > 0) {
                                for (ShadowSkyAccount skyAccount : list) {
                                    shadowSkyAccount.setId(skyAccount.getId());
                                    DataManager.getInstance().put(ShadowSkyAccount.class, shadowSkyAccount);
                                }
                            } else {
                                DataManager.getInstance().put(ShadowSkyAccount.class, shadowSkyAccount);
                            }

                            reloadData();

//                            ApiManager.getInstance()
//                                    .getApiService(ShadowSkyService.class)
//                                    .login(map)
//                                    .observeOn(AndroidSchedulers.mainThread())
//                                    .subscribeOn(Schedulers.io())
//                                    .unsubscribeOn(Schedulers.io())
//                                    .subscribe(result -> Log.d(ShadowSkyActivity.class.getName(), result.toString()));
                        })
                        .setNegativeButton("取消", (dialog, which) -> {
                            dialog.dismiss();
                        })
                        .create();
                alertDialog.show();
                break;
            case R.id.shadowsky_check_in_button:
                ApiManager.getInstance()
                        .getApiService(ShadowSkyService.class)
                        .checkIn()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .subscribe(result -> Log.d(ShadowSkyActivity.class.getName(), result.toString()));
                break;
        }
    }

    /**
     * shadowsky 签到
     *
     * @param shadowSkyAccount
     */
    public void doCheckIn(final ShadowSkyAccount shadowSkyAccount) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("email", shadowSkyAccount.getAccount());
        map.put("passwd", shadowSkyAccount.getPassword());
        map.put("remember_me", "week");
        ApiManager.getInstance()
                .getApiService(ShadowSkyService.class)
                .login(map)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribe(result -> {
                    Log.e(ShadowSkyActivity.class.getName(), result.toString());

                    ApiManager.getInstance()
                            .getApiService(ShadowSkyService.class)
                            .checkIn()
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .unsubscribeOn(Schedulers.io())
                            .subscribe(r -> {
                                ShadowSkyCheckInLogEntity entity = new ShadowSkyCheckInLogEntity(shadowSkyAccount.getAccount(), new Date(), r.toString());
                                DataManager.getInstance().put(ShadowSkyCheckInLogEntity.class, entity);
                            });
                });
    }

    /**
     * 查看签到记录
     *
     * @param shadowSkyAccount
     */
    public void showCheckInLog(ShadowSkyAccount shadowSkyAccount) {
        Intent intent = new Intent(ShadowSkyActivity.this, ShadowSkyCheckInLogActivity.class);
        intent.putExtra("shadowSkyAccount", shadowSkyAccount);
        startActivity(intent);
    }


    public void reloadData() {
        if (accountAdapter != null) {
            accountAdapter.setDataList(DataManager.getInstance().getBox(ShadowSkyAccount.class).getAll());
            accountAdapter.notifyDataSetChanged();
        }
    }
}
