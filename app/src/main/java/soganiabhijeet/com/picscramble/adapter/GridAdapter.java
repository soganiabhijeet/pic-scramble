package soganiabhijeet.com.picscramble.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import soganiabhijeet.com.picscramble.R;
import soganiabhijeet.com.picscramble.retrofit.Items;
import soganiabhijeet.com.picscramble.utils.Constants;

/**
 * Created by abhijeetsogani on 6/18/16.
 */
public class GridAdapter extends RecyclerView.Adapter<ImageItem> {
    private Context mContext;
    private LayoutInflater layoutInflater;
    private ArrayList<Items> imageList;
    private final OnItemClickListener listener;
    private int gridState;

    public GridAdapter(Context c, OnItemClickListener listener) {
        mContext = c;
        imageList = new ArrayList<Items>();
        this.listener = listener;
        this.gridState = Constants.GridStates.TIMER_RUNNING;
        this.layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItems(ArrayList<Items> imageList) {
        this.imageList = imageList;
        notifyDataSetChanged();
    }


    @Override
    public ImageItem onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ImageItem(layoutInflater.inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ImageItem holder, int position) {
        switch (gridState) {
            case Constants.GridStates.TIMER_RUNNING:
            case Constants.GridStates.USER_WON:
                Glide.with(mContext).load(imageList.get(position).media.getM()).into(holder.image);
                holder.image.setOnClickListener(null);
                break;
            case Constants.GridStates.USER_PLAYING:
                if (imageList.get(position).getIsPositionIdentified()) {
                    Glide.with(mContext).load(imageList.get(position).media.getM()).into(holder.image);
                } else {
                    Glide.with(mContext).load(R.color.darker_gray).into(holder.image);
                }
                holder.image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClick(holder.getAdapterPosition());
                    }
                });
                break;
        }


    }

    public void updateGameState(int state) {
        this.gridState = state;
        notifyDataSetChanged();
    }

    public void itemIdentified(int position) {
        this.imageList.get(position).setIsPositionIdentified(true);
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }


}