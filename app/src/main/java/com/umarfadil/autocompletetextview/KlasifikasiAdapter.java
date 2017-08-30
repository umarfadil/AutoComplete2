package com.umarfadil.autocompletetextview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by umarfadil on 8/30/17.
 */

public class KlasifikasiAdapter extends BaseAdapter implements Filterable {

    private Context mContext;
    private List<Klasifikasi> klasifikasiList;
    private List<Klasifikasi> suggestions = new ArrayList<>();
    private Filter filter = new CustomFilter();

    public KlasifikasiAdapter(Context context, List<Klasifikasi> klasifikasiList) {
        this.mContext = context;
        this.klasifikasiList = klasifikasiList;
    }

    @Override
    public int getCount() {
        return suggestions.size();
    }

    @Override
    public Klasifikasi getItem(int i) {
        return suggestions.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private static class ViewHolder {
        TextView nama_Klasifikasi;
        TextView kode_Klasifikasi;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_klasifikasi, viewGroup, false);
            holder = new ViewHolder();
            holder.nama_Klasifikasi = ((TextView) view.findViewById(R.id.nama_klasifikasi));
            holder.kode_Klasifikasi = ((TextView) view.findViewById(R.id.kode_klasifikasi));
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.nama_Klasifikasi.setText(suggestions.get(i).getName());
        holder.kode_Klasifikasi.setText(suggestions.get(i).getKode());
        return view;
    }


    @Override
    public Filter getFilter() {
        return filter;
    }

    private class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            suggestions.clear();
            // Check if the Original List and Constraint aren't null.
            if (klasifikasiList != null && constraint != null) {
                for (int i = 0; i < klasifikasiList.size(); i++) {
                    // Compare item in original list if it contains constraints.
                    if (klasifikasiList.get(i).getName().toLowerCase().contains(constraint)) {
                        // If TRUE add item in Suggestions.
                        suggestions.add(klasifikasiList.get(i));
                    }
                }
            }
            // Create new Filter Results and return this to publishResults;
            FilterResults results = new FilterResults();
            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}
