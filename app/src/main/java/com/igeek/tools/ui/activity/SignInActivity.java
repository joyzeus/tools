package com.igeek.tools.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.igeek.tools.R;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        Button shadowSkyButton = findViewById(R.id.shadowsky_sin_in_button);
        shadowSkyButton.setOnClickListener(this);
//        RecyclerView recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setAdapter();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("签到功能");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shadowsky_sin_in_button:
                Intent shadowSkyIntent = new Intent(SignInActivity.this, ShadowSkyActivity.class);
                startActivity(shadowSkyIntent);
                break;
            default:
                break;
        }
    }

//    public static class SignInAdapter extends RecyclerView.Adapter<SignInAdapter.SignInViewHolder> {
//
//        @NonNull
//        @Override
//        public SignInViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            return null;
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull SignInViewHolder holder, int position) {
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return 1;
//        }
//
//        public static class SignInViewHolder extends RecyclerView.ViewHolder {
//            public SignInViewHolder(@NonNull View itemView) {
//                super(itemView);
//            }
//        }
}
