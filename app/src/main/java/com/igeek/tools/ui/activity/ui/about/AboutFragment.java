package com.igeek.tools.ui.activity.ui.about;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.igeek.tools.BuildConfig;
import com.igeek.tools.R;
import com.igeek.tools.utils.ActionUtil;

public class AboutFragment extends Fragment implements View.OnClickListener {

    private AboutViewModel aboutViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        aboutViewModel = ViewModelProviders.of(this).get(AboutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_about, container, false);

        Context context = container.getContext();

        final TextView textView = root.findViewById(R.id.text_version);
        textView.setText(BuildConfig.VERSION_NAME);

        root.findViewById(R.id.text_qq).setOnClickListener(this);
        root.findViewById(R.id.text_email).setOnClickListener(this);
        root.findViewById(R.id.text_github_link).setOnClickListener(this);
        root.findViewById(R.id.text_link).setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.text_qq:
                ActionUtil.sendQQMessage(getContext(), getResources().getString(R.string.qq_info));
                break;
            case R.id.text_email:
                ActionUtil.sendEmail(getContext(), "", "", getResources().getString(R.string.email_info));
                break;
            case R.id.text_github_link:
                ActionUtil.openBrowser(getContext(), getResources().getString(R.string.github_link_info));
                break;
            case R.id.text_link:
                ActionUtil.openBrowser(getContext(), getResources().getString(R.string.link_info));
                break;
        }
    }
}
