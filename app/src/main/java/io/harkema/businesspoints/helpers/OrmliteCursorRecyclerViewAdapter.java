//package io.harkema.businesspoints.helpers;
//
//import android.content.Context;
//import android.database.Cursor;
//import android.support.v7.widget.RecyclerView;
//
//import java.sql.SQLException;
//
///**
// * Created by tomas on 16-1-16.
// */
//public abstract class OrmliteCursorRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder> extends CursorRecyclerViewAdapter<VH> {
////    protected PreparedQuery<T> preparedQuery;
//
//    public OrmliteCursorRecyclerViewAdapter(Cursor cursor){
//        super(cursor);
//    }
//
//    public abstract void onBindViewHolder(VH holder, T t);
//
//    public final void onBindViewHolder(VH viewHolder, Cursor cursor){
//        try {
//            onBindViewHolder(viewHolder, this.cursorToObject(cursor));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public final void changeCursor(Cursor cursor) {
//        throw new UnsupportedOperationException("Please use OrmLiteCursorAdapter.changeCursor(Cursor,PreparedQuery) instead");
//    }
//
//    public void changeCursor(Cursor cursor, PreparedQuery<T> preparedQuery) {
//        this.setPreparedQuery(preparedQuery);
//        super.changeCursor(cursor);
//    }
//
//    public void setPreparedQuery(PreparedQuery<T> preparedQuery) {
//        this.preparedQuery = preparedQuery;
//    }
//
//    public T getTypedItem(int position) {
//        try {
//            return this.cursorToObject((Cursor)getItem(position));
//        } catch (SQLException var3) {
//            throw new RuntimeException(var3);
//        }
//    }
//
//    protected T cursorToObject(Cursor cursor) throws SQLException {
//        return this.preparedQuery.mapRow(new AndroidDatabaseResults(cursor, null));
//    }
//}