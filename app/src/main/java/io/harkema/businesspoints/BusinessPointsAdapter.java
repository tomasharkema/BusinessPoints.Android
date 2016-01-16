package io.harkema.businesspoints;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.j256.ormlite.android.AndroidDatabaseResults;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import org.w3c.dom.Text;

import java.util.Objects;

import io.harkema.businesspoints.helpers.OrmliteCursorRecyclerViewAdapter;
import io.harkema.businesspoints.model.BusinessPoint;

/**
 * Created by tomas on 16-1-16.
 */


public class BusinessPointsAdapter extends OrmliteCursorRecyclerViewAdapter<BusinessPoint, BusinessPointsAdapter.ViewHolder> {

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

    private Cursor cursor;
    private OnItemClickListener onItemClickListener;
    private OnItemLongPressListener onItemLongPressListener;

    private BusinessPointsAdapter(Cursor cursor) {
        super(cursor);
    }

    public static BusinessPointsAdapter getForAll() {
        Dao<BusinessPoint, Integer> businessPointDao = App.instance.storageService.getBusinessPointDao();
        Cursor cursor = null;
        PreparedQuery<BusinessPoint> query = null;
        QueryBuilder<BusinessPoint, Integer> queryBuilder = businessPointDao.queryBuilder();
        try {
            query = queryBuilder.where()
                    .isNotNull("title")
                    .prepare();

            CloseableIterator<BusinessPoint> iterator = businessPointDao.iterator(query);
            AndroidDatabaseResults results = (AndroidDatabaseResults) iterator.getRawResults();
            cursor = results.getRawCursor();
        } catch (Exception e) {
            e.printStackTrace();
        }

        BusinessPointsAdapter adapter = new BusinessPointsAdapter(cursor);
        adapter.setHasStableIds(true);
        adapter.cursor = cursor;
        adapter.changeCursor(cursor, query);
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
    public void onBindViewHolder(ViewHolder holder, final BusinessPoint businessPoint) {
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
        if (cursor != null) {
            return cursor.getCount();
        } else {
            return 0;
        }
    }

    @Override
    public long getItemId(int position) {
        if (hasStableIds() && cursor != null) {
            if (cursor.moveToPosition(position)) {
                return cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
            } else {
                return RecyclerView.NO_ID;
            }
        } else {
            return RecyclerView.NO_ID;
        }
    }

    public Object getItem(int position) {
        if (cursor != null) {
            cursor.moveToPosition(position);
            return cursor;
        } else {
            return null;
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongPressListener(OnItemLongPressListener onItemLongPressListener) {
        this.onItemLongPressListener = onItemLongPressListener;
    }
}
