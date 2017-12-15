package com.liefery.android.signature_view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;

public class Util {
    static float dpToPx( Resources resources, float dp ) {
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float ratio = (float) metrics.densityDpi
            / DisplayMetrics.DENSITY_DEFAULT;
        return dp * ratio;
    }

    static Paint lineStyle( Resources resources ) {
        Paint paint = new Paint();
        paint.setAntiAlias( true );
        paint.setColor( Color.BLACK );
        paint.setDither( true );
        paint.setStrokeCap( Paint.Cap.ROUND );
        paint.setStrokeJoin( Paint.Join.ROUND );
        paint.setStrokeWidth( Util.dpToPx( resources, 4 ) );
        paint.setStyle( Paint.Style.STROKE );
        return paint;
    }
}
