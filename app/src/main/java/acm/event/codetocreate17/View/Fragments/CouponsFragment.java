package acm.event.codetocreate17.View.Fragments;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;

import acm.event.codetocreate17.Model.Data.DataGenerator;
import acm.event.codetocreate17.Model.RealmModels.User;
import acm.event.codetocreate17.R;
import acm.event.codetocreate17.Utility.Adapters.CouponsAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import yalantis.com.sidemenu.interfaces.ScreenShotable;

public class CouponsFragment extends Fragment implements ScreenShotable {
    @BindView(R.id.coupons_root_view)
    ConstraintLayout couponsContainer;
    @BindView(R.id.coupons_recycler_view)
    RecyclerView couponsRecyclerView;

    CouponsAdapter couponsAdapter;
    Realm realm;
    User user;
    String[] couponTitles;
    int[] couponPrimaryImages;

    private Bitmap bitmap;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_coupons, container, false);
        ButterKnife.bind(this, rootView);

        Realm.init(this.getActivity());
        realm = Realm.getDefaultInstance();
        user = realm.where(User.class).findFirst();

        couponTitles = new DataGenerator().getCouponTitles();
        ArrayList<String> cTitles = new ArrayList<>(Arrays.asList(couponTitles));

        couponPrimaryImages = new DataGenerator().getCouponPrimaryImages();
        ArrayList<Integer> cPrimaryImages = new ArrayList<>();
        for(int i = 0; i < couponPrimaryImages.length; i++)
            cPrimaryImages.add(couponPrimaryImages[i]);

        couponsAdapter = new CouponsAdapter(cTitles, cPrimaryImages);
        couponsRecyclerView.setAdapter(couponsAdapter);
        couponsRecyclerView.setLayoutManager(new LinearLayoutManager(couponsRecyclerView.getContext()));
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void takeScreenShot() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(couponsContainer.getWidth(),
                        couponsContainer.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                couponsContainer.draw(canvas);
                CouponsFragment.this.bitmap = bitmap;
            }
        };
        thread.start();
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}