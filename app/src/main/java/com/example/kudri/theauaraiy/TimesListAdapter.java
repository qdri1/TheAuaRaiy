package com.example.kudri.theauaraiy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Kudri on 04.06.2018.
 */

public class TimesListAdapter extends RecyclerView.Adapter<VH2> {

    private List<Weather> wList;
    private Context context;
    private String iconUrl = "http://openweathermap.org/img/w/";

    public TimesListAdapter(Context context, List<Weather> wList) {
        this.context = context;
        this.wList = wList;
    }

    @Override
    public VH2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new VH2(v);
    }

    @Override
    public void onBindViewHolder(final VH2 holder, final int position) {
        TextView dateView = holder.dateView;
        TextView tempTV = holder.tempTV;

        if (wList.get(position).isTitle()) {
            dateView.setText(wList.get(position).getDateTxt());
            tempTV.setText("");

            Glide.with(context)
                    .load(R.drawable.index_background_circle)
                    .into(holder.iconView);

            holder.backgroundLayout.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        } else {
            dateView.setText(wList.get(position).getDateTxt());
            tempTV.setText(String.valueOf(wList.get(position).getTemp()) + context.getString(R.string.label_temp_item));

            Glide.with(context)
                    .load(iconUrl + wList.get(position).getIcon() + ".png")
                    .into(holder.iconView);

            holder.backgroundLayout.setBackgroundColor(context.getResources().getColor(R.color.color_white));
        }
    }

    @Override
    public int getItemCount() {
        return wList.size();
    }
}

class VH2 extends RecyclerView.ViewHolder {
    TextView dateView, tempTV;
    ImageView iconView;
    LinearLayout backgroundLayout;

    VH2(View itemView) {
        super(itemView);
        dateView = itemView.findViewById(R.id.date_tv);
        tempTV = itemView.findViewById(R.id.temperature_tv);
        iconView = itemView.findViewById(R.id.icon_view);
        backgroundLayout = itemView.findViewById(R.id.background_layout);
    }
}
