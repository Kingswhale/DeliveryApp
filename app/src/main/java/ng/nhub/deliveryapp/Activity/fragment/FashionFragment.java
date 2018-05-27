package ng.nhub.deliveryapp.Activity.fragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;

import ng.nhub.deliveryapp.Activity.adapter.FashionAdapter;
import ng.nhub.deliveryapp.Activity.model.FashionModel;
import ng.nhub.deliveryapp.Activity.activity.ProfileUpgrade;
import ng.nhub.deliveryapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FashionFragment extends Fragment {

    private RecyclerView recyclerView, recyclerView2;
    private FashionAdapter fashionAdapter;
    private FashionAdapter fashionAdapter2;
    private List<FashionModel> fashionModelList;
    private List<FashionModel> fashionModelList2;
    private Context context;
    private CardView cardviewProfile;
    private ImageView close;
    private Button upgradeUser;
    CarouselView carouselView;
    int[] sampleImages = {
            R.drawable.image_1,
            R.drawable.image_2,
            R.drawable.image_3,
            R.drawable.image_4,
            R.drawable.image_5,
            R.drawable.image_6,
            R.drawable.image_7,
            R.drawable.image_8,
            R.drawable.image_9,
            R.drawable.image_10,
            R.drawable.image_11};

    public FashionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fashion, container, false);
        carouselView = view.findViewById(R.id.carouselView);
        cardviewProfile = view.findViewById(R.id.cardview_profile);
        close = view.findViewById(R.id.profile_close);
        upgradeUser = view.findViewById(R.id.profile_upgrade);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

        recyclerView = view.findViewById(R.id.recyler_view);
        recyclerView2 = view.findViewById(R.id.recycler_view2);

        fashionModelList = new ArrayList<>();
        fashionModelList2 = new ArrayList<>();
        fashionAdapter = new FashionAdapter(getActivity(), fashionModelList);
        fashionAdapter2 = new FashionAdapter(getActivity(), fashionModelList2);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(fashionAdapter);

        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(layoutManager1);
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        recyclerView2.setAdapter(fashionAdapter2);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cardviewProfile.animate()
                        .translationX(view.getWidth())
                        .alpha(0.0f)
                        .setDuration(500)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                cardviewProfile.setVisibility(View.GONE);
                            }
                        });

            }
        });

        upgradeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ProfileUpgrade.class));
            }
        });

        loadFashion();

        loadFashion2();

        return view;
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
//            Glide.with(context).load(sampleImages[position]).into(imageView);
        }

    };

    /**
     * Adding few albums for testing
     */
    private void loadFashion() {
        int[] covers = new int[]{
                R.drawable.cheeseburger,
                R.drawable.chromebook,
                R.drawable.coffee,
                R.drawable.cranberry_cocktail_beverage,
                R.drawable.imac,
                R.drawable.iphone_x,
                R.drawable.macbook_pro,
                R.drawable.pineapples,
                R.drawable.pizza,
                R.drawable.raspberry_blueberry,
                R.drawable.sandwich,
                R.drawable.strawberry,
                R.drawable.strawberry_smoothie,
                R.drawable.vegetable};

        FashionModel model = new FashionModel("Cheeseburger", getString(R.string.naira_symbol).concat("3,000"),
                covers[0]);
        fashionModelList.add(model);

        model = new FashionModel("Chromebook", getString(R.string.naira_symbol).concat("222,000"), covers[1]);
        fashionModelList.add(model);

        model = new FashionModel("Coffee", getString(R.string.naira_symbol).concat("1,000"), covers[2]);
        fashionModelList.add(model);

        model = new FashionModel("Cranberry Cocktail beverage", getString(R.string.naira_symbol).concat("12,980"), covers[3]);
        fashionModelList.add(model);

        model = new FashionModel("iMac", getString(R.string.naira_symbol).concat("893,000"), covers[4]);
        fashionModelList.add(model);

        model = new FashionModel("iPhone X", getString(R.string.naira_symbol).concat("312,000"), covers[5]);
        fashionModelList.add(model);

        model = new FashionModel("Macbook Pro", getString(R.string.naira_symbol).concat("650,000"), covers[6]);
        fashionModelList.add(model);

        model = new FashionModel("Pineapple Juice", getString(R.string.naira_symbol).concat("680"), covers[7]);
        fashionModelList.add(model);

        model = new FashionModel("Pizza", getString(R.string.naira_symbol).concat("4,500"), covers[8]);
        fashionModelList.add(model);

        model = new FashionModel("Raspberry Blueberry", getString(R.string.naira_symbol).concat("3,000"), covers[9]);
        fashionModelList.add(model);

        model = new FashionModel("Sandwich", getString(R.string.naira_symbol).concat("5,500"), covers[10]);
        fashionModelList.add(model);

        model = new FashionModel("Strawberry", getString(R.string.naira_symbol).concat("1,980"), covers[11]);
        fashionModelList.add(model);

        model = new FashionModel("Strawberry Smoothie", getString(R.string.naira_symbol).concat("980"), covers[12]);
        fashionModelList.add(model);

        model = new FashionModel("Carrot", getString(R.string.naira_symbol).concat("950"), covers[13]);
        fashionModelList.add(model);

    }

    private void loadFashion2() {
        int[] covers = new int[]{
                R.drawable.album1,
                R.drawable.album2,
                R.drawable.album3,
                R.drawable.album4,
                R.drawable.album5,
                R.drawable.album6,
                R.drawable.album7,
                R.drawable.album9};

        FashionModel model = new FashionModel("TAG Heuer", getString(R.string.naira_symbol).concat("73,000"), covers[0]);
        fashionModelList2.add(model);

        model = new FashionModel("Movado", getString(R.string.naira_symbol).concat("63,000"), covers[1]);
        fashionModelList2.add(model);

        model = new FashionModel("Rynback", getString(R.string.naira_symbol).concat("56,000"), covers[2]);
        fashionModelList2.add(model);

        model = new FashionModel("Adidas", getString(R.string.naira_symbol).concat("92,980"), covers[3]);
        fashionModelList2.add(model);

        model = new FashionModel("Movado", getString(R.string.naira_symbol).concat("63,000"), covers[4]);
        fashionModelList2.add(model);

        model = new FashionModel("Rynback", getString(R.string.naira_symbol).concat("56,000"), covers[5]);
        fashionModelList2.add(model);

        model = new FashionModel("Adidas", getString(R.string.naira_symbol).concat("92,980"), covers[6]);
        fashionModelList2.add(model);

        model = new FashionModel("Aliassa", getString(R.string.naira_symbol).concat("22,980"), covers[7]);
        fashionModelList2.add(model);

    }

}
