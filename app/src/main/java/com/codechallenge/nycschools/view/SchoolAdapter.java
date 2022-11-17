package com.codechallenge.nycschools.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codechallenge.nycschools.R;
import com.codechallenge.nycschools.model.pojo.NYCSchoolResponse;

import java.util.List;

public class SchoolAdapter extends RecyclerView.Adapter<SchoolAdapter.SchoolViewHolder> {

    private List<NYCSchoolResponse> dataSet;
    private Listener.ListClickEvent clickEvent;

    public SchoolAdapter(List<NYCSchoolResponse> dataSet,
                         Listener.ListClickEvent clickEvent){
        this.dataSet = dataSet;
        this.clickEvent = clickEvent;
    }

    @NonNull
    @Override
    public SchoolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SchoolViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(
                                R.layout.school_layout_item,
                                parent,
                                false
                        )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SchoolViewHolder holder, int position) {
        holder.bind(
                dataSet.get(position),
                clickEvent
        );
    }

    @Override
    public int getItemCount() {
        return dataSet != null ? dataSet.size() : 0;
    }

    public class SchoolViewHolder extends RecyclerView.ViewHolder {

        private TextView schoolName;

        public SchoolViewHolder(@NonNull View itemView) {
            super(itemView);
            schoolName = itemView.findViewById(R.id.school_item_school_name);
        }

        public void bind(NYCSchoolResponse dataItem, Listener.ListClickEvent clickEvent) {
            schoolName.setText(
                    dataItem.getSchool_name()
            );
            itemView.setOnClickListener(view->
                    clickEvent.clickDetails(dataItem.getDbn(), dataItem.getSchool_name(), dataItem.getLocation(), dataItem.getSchool_email(), dataItem.getPhone_number())
            );
        }
    }
}

