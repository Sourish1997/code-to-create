package acm.event.codetocreate17.View.Fragments;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.ArrayList;
import java.util.Arrays;

import acm.event.codetocreate17.Model.Data.DataGenerator;
import acm.event.codetocreate17.R;
import acm.event.codetocreate17.Utility.Adapters.CouponsAdapter;
import acm.event.codetocreate17.Utility.Miscellaneous.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import yalantis.com.sidemenu.interfaces.ScreenShotable;

import static android.content.Context.MODE_PRIVATE;

public class CouponsFragment extends Fragment implements ScreenShotable {
    @BindView(R.id.coupons_root_view)
    ConstraintLayout couponsContainer;
    @BindView(R.id.coupons_recycler_view)
    RecyclerView couponsRecyclerView;
    @BindView(R.id.coupons_guest_card)
    CardView couponsGuestCard;

    CouponsAdapter couponsAdapter;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    String[] couponTitles;
    String userId;
    int[] couponPrimaryImages;

    private ArrayList<Bitmap> qrCodesList;
    private ArrayList<String> couponTitlesList;
    private ArrayList<Integer> couponPrimaryImagesList;
    private Bitmap[] qrCodes;
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

        if(Constants.isGuest) {
            couponsRecyclerView.setVisibility(View.INVISIBLE);
            couponsGuestCard.setVisibility(View.VISIBLE);
        } else {
            couponTitles = new DataGenerator().getCouponTitles();
            couponTitlesList = new ArrayList<>(Arrays.asList(couponTitles));

            couponPrimaryImages = new DataGenerator().getCouponPrimaryImages();
            couponPrimaryImagesList = new ArrayList<>();
            for (int i = 0; i < couponPrimaryImages.length; i++)
                couponPrimaryImagesList.add(couponPrimaryImages[i]);

            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Initializing QR Codes...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            Thread qrCodesInitializeThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    sharedPreferences = getActivity().getSharedPreferences(Constants.sharedPreferenceName, MODE_PRIVATE);
                    userId = sharedPreferences.getString("userid", "");
                    qrCodes = new Bitmap[couponTitles.length];
                    qrCodesList = new ArrayList<>();
                    for (int i = 0; i < qrCodes.length; i++) {
                        try {
                            qrCodes[i] = TextToImageEncode(userId + " " + couponTitles[i]);
                            qrCodesList.add(qrCodes[i]);
                        } catch (WriterException e) {
                        }
                    }

                    couponsAdapter = new CouponsAdapter(couponTitlesList, couponPrimaryImagesList, qrCodesList);
                    getActivity().runOnUiThread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(couponsAdapter);
                                    alphaAdapter.setDuration(1000);
                                    couponsRecyclerView.setAdapter(alphaAdapter);
                                    couponsRecyclerView.setLayoutManager(new LinearLayoutManager(couponsRecyclerView.getContext()));
                                    progressDialog.dismiss();
                                }
                            });
                }
            });
            qrCodesInitializeThread.start();
        }
        return rootView;
    }

    private Bitmap TextToImageEncode(String value) throws WriterException {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        BitMatrix bitMatrix = multiFormatWriter.encode(value, BarcodeFormat.QR_CODE, 200, 200);
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.black):getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels, 0, 200, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
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
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(couponsContainer.getWidth(),
                        couponsContainer.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                couponsContainer.draw(canvas);
                CouponsFragment.this.bitmap = bitmap;
            }
        });
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}