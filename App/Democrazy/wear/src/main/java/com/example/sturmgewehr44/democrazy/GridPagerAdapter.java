package com.example.sturmgewehr44.democrazy;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.hardware.camera2.params.Face;
import android.support.v4.content.ContextCompat;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.FragmentGridPagerAdapter;
//import android.support.wearable.view.ImageReference;
import android.view.Gravity;
import android.graphics.drawable.Drawable;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import java.util.ArrayList;


/**
 * Created by Sturmgewehr44 on 2/27/16.
 */

public class GridPagerAdapter extends FragmentGridPagerAdapter {

    private final Context mContext;
//    private List mRows;

    public GridPagerAdapter(Context ctx, FragmentManager fm) {
        super(fm);
        mContext = ctx;
    }

    static final int[] BG_IMAGES = new int[] {
            R.drawable.hitler,
            R.drawable.stalin,
            R.drawable.churchill,
            R.drawable.wendell,
            R.drawable.fdr
    };

    /** A simple container for static data in each page */
    private static class Page {
        int titleRes;
        int textRes;
        int iconRes;
        int cardGravity = Gravity.BOTTOM;
        boolean expansionEnabled = true;
        float expansionFactor = 1.0f;
        int expansionDirection = CardFragment.EXPAND_DOWN;
        String senator = "sendd";
        String Partei = "partie";
        int value;

        public Page(int titleRes, int textRes, boolean expansion) {
            this(titleRes, textRes, 0);
            this.expansionEnabled = expansion;
        }

        public Page(int titleRes, int textRes, boolean expansion, float expansionFactor) {
            this(titleRes, textRes, 0);
            this.expansionEnabled = expansion;
            this.expansionFactor = expansionFactor;
        }

        public Page(int titleRes, int textRes, int iconRes) {
            this.titleRes = titleRes;
            this.textRes = textRes;
            this.iconRes = iconRes;
        }

        public Page(int titleRes, int textRes, int iconRes, int gravity) {
            this.titleRes = titleRes;
            this.textRes = textRes;
            this.iconRes = iconRes;
            this.cardGravity = gravity;
        }

        public Page(String sen, String par, String val) {
            this(0, 0, 0);
            this.senator = sen;
            this.Partei = par;
            this.value = Integer.parseInt(val);
        }
    }

    private Page[][] PAGES = {
            {
                    new Page("Hitler", "National Socialist Party", "0"),
            },};
//
//            {
//                    new Page(10, 11, true, 2),
//                    new Page(12, 12, true, 4),
//            },
//            {
//                    new Page(13, 13, true, 3),
//                    new Page(12, 13, true, 1)
//            },
//            {
//                    new Page(11, 11, R.drawable.hitler,
//                            Gravity.CENTER_VERTICAL),
//            },
//
//    };

    public void overridePages(int cases, ArrayList<String> info) {
        Page[][] Present = new Page[cases][2];
        for (int i = 0; i < cases; i++) {
            Present[i][0] = new Page(info.get(i * 3), info.get(i * 3 + 1), info.get(i * 3 + 2));
            Present[i][1] = new Page(info.get(i * 3), info.get(i * 3 + 1), info.get(i * 3 + 2));
        }
        PAGES = Present;
    }

    @Override
    public Fragment getFragment(int row, int col) {
        Page page = PAGES[row][col];
//        String title = "litera" + Integer.toString(row); //page.titleRes != 0 ? mContext.getString(page.titleRes) : null;
//        String text = "hitler" + Integer.toString(col); //page.textRes != 0 ? mContext.getString(page.textRes) : null;
        System.out.println(row);
        System.out.println(col);
        String sen = page.senator;
        String par = page.Partei;
//        CardFragment fragment = CardFragment.create(title, text, page.iconRes);
        Fragment fragment;
        if (page.value == 0) {
            fragment = FaceFragment.newInstance(sen, par, getBackgroundInt(par), 0);
            return fragment;
        }
        if (col == 0) {
            fragment = FaceFragment.newInstance(sen, par, getBackgroundInt(par), row + 1);
        } else {
            fragment = VoteFragment.newInstance("CA", "Barbarossa County", "Obama: 101% of votes", "Romney: -1% of votes");
        }
        // Advanced settings
//        fragment.setCardGravity(page.cardGravity);
//        fragment.setExpansionEnabled(page.expansionEnabled);
//        fragment.setExpansionDirection(page.expansionDirection);
//        fragment.setExpansionFactor(page.expansionFactor);
        return fragment;
    }

//    @Override
//    public ImageReference getBackground(int row, int column) {
//        return ImageReference.forDrawable(BG_IMAGES[row % BG_IMAGES.length]);
//    }
    @Override
    public Drawable getBackgroundForPage(int row, int column) {
        return new ColorDrawable(0xFF3c3b6e);
    }

    public int getBackgroundInt(String party) {
        switch (party) {
            case "Democratic Party": return 0xFFB0CEFF;
            case "Communist Party": return 0xFF970018;
            case "Republican Party": return 0xFFFFB6B6;
            case "National Socialist Party": return 0xFF000000;
            case "Conservative Party": return 0xFF0087DC;
        }
        return 0xFFFFFF;
    }

    @Override
    public int getRowCount() {
        return PAGES.length;
    }

    @Override
    public int getColumnCount(int rowNum) {
        return PAGES[rowNum].length;
    }
}