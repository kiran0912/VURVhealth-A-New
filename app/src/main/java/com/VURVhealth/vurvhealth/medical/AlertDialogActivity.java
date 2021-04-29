package com.VURVhealth.vurvhealth.medical;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.VURVhealth.vurvhealth.R;

import java.util.ArrayList;

public class AlertDialogActivity extends AppCompatActivity {
    private ArrayList<String> list_data;

    public class AdapterViewCustom extends BaseAdapter {
        private Activity context_1;
        private ArrayList<String> pairs;

        public class ViewHolder {
            public TextView txt;
        }

        public AdapterViewCustom(Activity context, ArrayList<String> pairs) {
            this.context_1 = context;
            this.pairs = pairs;
        }

        public int getCount() {
            return this.pairs.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(this.context_1).inflate(R.layout.picker_item, null);
                viewHolder = new ViewHolder();
                viewHolder.txt = (TextView) convertView.findViewById(R.id.tvpicker);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.txt.setText((CharSequence) this.pairs.get(position));
            final ViewHolder finalViewHolder = viewHolder;
            viewHolder.txt.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (position == 2) {
                        finalViewHolder.txt.setTextColor(ContextCompat.getColor(AlertDialogActivity.this, R.color.blue));
                    }
                }
            });
            return convertView;
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picker_screen);
        ListView customlistView = (ListView) findViewById(R.id.spinnerlist);
        this.list_data = new ArrayList();
        this.list_data.add("Surgery");
        this.list_data.add("Primary Care");
        this.list_data.add("Behavioral Health");
        this.list_data.add("All Specialties");
        this.list_data.add("Surgery");
        this.list_data.add("Primary Care");
        customlistView.setAdapter(new AdapterViewCustom(this, this.list_data));
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
