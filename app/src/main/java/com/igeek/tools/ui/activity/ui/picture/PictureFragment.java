package com.igeek.tools.ui.activity.ui.picture;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.igeek.tools.R;

import java.util.ArrayList;
import java.util.List;

public class PictureFragment extends Fragment {

    private PictureViewModel pictureViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        pictureViewModel = ViewModelProviders.of(this).get(PictureViewModel.class);
        View root = inflater.inflate(R.layout.fragment_picture, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.recyclerview_picture);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(new PictureAdapter());
        return root;
    }

    public static class PictureAdapter extends RecyclerView.Adapter<PictureHolder> {

        private List<String> dataList = new ArrayList<>();

        @NonNull
        @Override
        public PictureHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull PictureHolder holder, int position) {
            holder.textView.setText(dataList.get(position));
        }

        @Override
        public int getItemCount() {
            return dataList == null ? 0 : dataList.size();
        }
    }

    public static class PictureHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public PictureHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
