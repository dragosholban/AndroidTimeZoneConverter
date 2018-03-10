package dragosholban.com.timezoneconverter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import java.util.ArrayList;

public class TimeZoneAdapter extends ArrayAdapter<String> {

    private ArrayList<String> original;
    private Filter filter;

    public TimeZoneAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull ArrayList<String> objects) {
        super(context, resource, textViewResourceId, objects);
        original = new ArrayList<>(objects);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new TimeZoneFilter();
    }

    private class TimeZoneFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            ArrayList<String> filtered = new ArrayList<>();
            String search = charSequence.toString().toLowerCase();

            if (search == null || search.length() == 0) {
                filtered = new ArrayList<>(original);
            } else {
                for (int i = 0; i < original.size(); i++) {
                    if (original.get(i).toLowerCase().contains(charSequence)) {
                        filtered.add(original.get(i));
                    }
                }
            }

            results.values = filtered;
            results.count = filtered.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList<String> items = (ArrayList<String>) filterResults.values;
            clear();
            for (int i = 0; i < items.size(); i++) {
                add(items.get(i));
            }

            notifyDataSetChanged();
        }
    }
}
