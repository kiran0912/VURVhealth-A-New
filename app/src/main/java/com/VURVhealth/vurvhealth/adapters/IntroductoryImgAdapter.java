package com.VURVhealth.vurvhealth.adapters;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.VURVhealth.vurvhealth.R;

/**
 * Created by yqlabs on 01-08-2016.
 */
public class IntroductoryImgAdapter extends PagerAdapter {
    private int[] IMAGES;
    private Context context;
    private String[] headAry;
    private String[] textAry;
    private String[] paraAry;

    private LayoutInflater inflater;

    public IntroductoryImgAdapter(Context context,int[] IMAGES, String[] headAry, String[] textAry, String[] paraAry) {
        this.context = context;
        this.IMAGES = IMAGES;
        this.headAry = headAry;
        this.textAry = textAry;
        this.paraAry = paraAry;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return IMAGES.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {

        View imageLayout = inflater.inflate(R.layout.intro_viewpager_inflator,null);

//        assert imageLayout != null;
        Button gradient = (Button) imageLayout.findViewById(R.id.gradient);
        ImageView imageView = (ImageView) imageLayout.findViewById(R.id.step_image);
        TextView introHeadId = (TextView) imageLayout.findViewById(R.id.steps_num);
        TextView introTextId = (TextView) imageLayout.findViewById(R.id.step_txt);
        TextView introParaId = (TextView) imageLayout.findViewById(R.id.steps_para);


        imageView.setBackgroundResource(IMAGES[position]);
        introHeadId.setText(Html.fromHtml(headAry[position]));
        introTextId.setText(Html.fromHtml(textAry[position]));
        introParaId.setText(Html.fromHtml(paraAry[position]));


        if(position==0)
        {
            gradient.setVisibility(View.VISIBLE);
            introHeadId.setGravity(Gravity.CENTER);
            introTextId.setGravity(Gravity.CENTER);
            introHeadId.setTextSize(18);

        }else if(position==1)
        {
            gradient.setVisibility(View.INVISIBLE);
            introHeadId.setGravity(Gravity.LEFT);

        }else if(position==2) {
            gradient.setVisibility(View.INVISIBLE);
            introHeadId.setGravity(Gravity.LEFT);
            imageView.getLayoutParams().width = 400;
//            imageView.getLayoutParams().height = 350;

        }else if(position==3) {
            gradient.setVisibility(View.INVISIBLE);
            introHeadId.setGravity(Gravity.LEFT);

        }else{
            gradient.setVisibility(View.INVISIBLE);
            introHeadId.setGravity(Gravity.LEFT);

        }

        //((ViewPager) view).addView(imageLayout);

       view.addView(imageLayout,0);

        return imageLayout;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }
}
