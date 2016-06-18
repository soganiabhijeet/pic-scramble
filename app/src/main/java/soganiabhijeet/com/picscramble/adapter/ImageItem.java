package soganiabhijeet.com.picscramble.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import soganiabhijeet.com.picscramble.R;

/**
 * Created by abhijeetsogani on 6/18/16.
 */
public class ImageItem extends RecyclerView.ViewHolder {
    public ImageView image;
    public ImageItem(View itemView) {
        super(itemView);
        image = (ImageView) itemView.findViewById(R.id.image_holder);
    }
}
