package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.view.john.materialdesigndemo.FruitActivity;
import com.view.john.materialdesigndemo.R;

import java.util.List;

import bean.FruitBean;


/**
 * Created by John on 2017/1/1.
 */

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {

    List<FruitBean> mList;
    Context mContext;
    public FruitAdapter(List<FruitBean> mList){
        if (this.mList!=null &&this.mList.size()>0)
            this.mList.clear();
        this.mList = mList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();

        }

        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final FruitBean fruitBean = mList.get(position);
        holder.mTextView.setText(fruitBean.getName());
        Glide.with(mContext).load(fruitBean.getDrawableId()).into(holder.mImageView);
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, FruitActivity.class);
                intent.putExtra(FruitActivity.FRUIT_NAME,fruitBean.getName());
                intent.putExtra(FruitActivity.FRUIT_IAMGE_ID,fruitBean.getDrawableId());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        TextView mTextView;
        CardView mCardView;

        public ViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView;
            mImageView= (ImageView) itemView.findViewById(R.id.fruit_image);
            mTextView  = (TextView) itemView.findViewById(R.id.fruit_name);
        }
    }
}
