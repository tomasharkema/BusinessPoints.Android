package io.harkema.businesspoints;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Objects;

//import io.harkema.businesspoints.helpers.OrmliteCursorRecyclerViewAdapter;
import io.harkema.businesspoints.model.BusinessPoint;

/**
 * Created by tomas on 16-1-16.
 */


public class BusinessPointsAdapter extends RecyclerView.Adapter<BusinessPointsAdapter.ViewHolder> { //extends OrmliteCursorRecyclerViewAdapter<BusinessPoint, BusinessPointsAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public ViewHolder(View view) {
            super(view);
            this.view = view;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(BusinessPoint businessPoint);
    }

    public interface OnItemLongPressListener {
        boolean onItemLongPress(BusinessPoint businessPoint);
    }

    private List<BusinessPoint> businessPoints;
    private OnItemClickListener onItemClickListener;
    private OnItemLongPressListener onItemLongPressListener;

    public static BusinessPointsAdapter getForAll() {
        BusinessPointsAdapter adapter = new BusinessPointsAdapter();
        adapter.setHasStableIds(true);
        adapter.businessPoints = App.instance.businessPointsDbHelper.getAllBusinessPoints();
        return adapter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View cellView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.business_points_cell, parent, false);
        final ViewHolder cellViewHolder = new ViewHolder(cellView);
        return cellViewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final BusinessPoint businessPoint = businessPoints.get(position);

        final TextView titleView = (TextView)holder.view.findViewById(R.id.titleView);
        titleView.setText(businessPoint.title);

        final TextView subtitleView = (TextView)holder.view.findViewById(R.id.subtitleView);
        subtitleView.setText(businessPoint.teacher);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(businessPoint);
                }
            }
        });
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongPressListener != null) {
                    return onItemLongPressListener.onItemLongPress(businessPoint);
                }

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        if (businessPoints != null) {
            return businessPoints.size();
        } else {
            return 0;
        }
    }

    @Override
    public long getItemId(int position) {
        return businessPoints.get(position).id;
    }

    public Object getItem(int position) {
        return businessPoints.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongPressListener(OnItemLongPressListener onItemLongPressListener) {
        this.onItemLongPressListener = onItemLongPressListener;
    }
}
