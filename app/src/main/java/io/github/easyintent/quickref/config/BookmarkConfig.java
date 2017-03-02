package io.github.easyintent.quickref.config;

import android.content.Context;
import android.content.SharedPreferences;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class BookmarkConfig {

    private static final Logger logger = LoggerFactory.getLogger(BookmarkConfig.class);

    private static final String NAME = "bookmark";

    private Context context;

    public BookmarkConfig(Context context) {
        this.context = context;
    }

    /** Add a bookmark.
     *
     * @param id
     */
    public void add(String id) {
        getSetting()
                .edit()
                .putLong(id, System.currentTimeMillis())
                .apply();
    }

    public void delete(String id) {
        getSetting()
                .edit()
                .remove(id)
                .apply();
    }

    /** Get list of bookmark item ids, sorted.
     *
     * @return
     */
    public List<String> list() {
        Map<String, ?> all = getSetting().getAll();
        List<? extends Map.Entry<String, ?>> entry = new LinkedList<>(all.entrySet());
        Collections.sort(entry, new Comparator<Map.Entry<String, ?>>() {
            @Override
            public int compare(Map.Entry<String, ?> e1, Map.Entry<String, ?> e2) {
                Long t1 = (Long) e1.getValue();
                Long t2 = (Long) e2.getValue();
                return t1.compareTo(t2);
            }
        });
        int n = entry.size();
        String[] array = new String[n];
        for (int i=0; i<n; i++) {
            array[i] = entry.get(i).getKey();
        }
        return Arrays.asList(array);
    }

    private SharedPreferences getSetting() {
        return context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }
}
