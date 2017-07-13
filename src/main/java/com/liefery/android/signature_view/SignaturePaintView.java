package com.liefery.android.signature_view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.*;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

public class SignaturePaintView extends View {

    private Paint paint = new Paint();

    private Path path = new Path();

    private PathDescriptor descriptor = new PathDescriptor();

    private float x, y;

    private float interval;

    public SignaturePaintView(Context context ) {
        super( context );

        TypedArray styles = context
                        .obtainStyledAttributes( R.styleable.SignatureView );
        init( styles );
        styles.recycle();
    }

    public SignaturePaintView(Context context, AttributeSet attrs ) {
        super( context, attrs );

        TypedArray styles = context.obtainStyledAttributes(
            attrs,
            R.styleable.SignatureView );
        init( styles );
        styles.recycle();
    }

    public SignaturePaintView(Context context, AttributeSet attrs, int defStyleAttr ) {
        super( context, attrs, defStyleAttr );

        TypedArray styles = context.obtainStyledAttributes(
            attrs,
            R.styleable.SignatureView,
            defStyleAttr,
            0 );
        init( styles );
        styles.recycle();
    }

    private void init( TypedArray styles ) {

        paint.setAntiAlias( true );
        paint.setColor( Color.BLACK );
        paint.setDither( true );
        paint.setStrokeCap( Paint.Cap.ROUND );
        paint.setStrokeJoin( Paint.Join.ROUND );
        paint.setStrokeWidth( dpToPx( 4 ) );
        paint.setStyle( Paint.Style.STROKE );

        int lineColor = styles.getColor(
            R.styleable.SignatureView_signatureView_linecolor,
            Integer.MIN_VALUE );
        if ( lineColor != Integer.MIN_VALUE )
            setLineColor( lineColor );

        float lineWidth = styles.getDimension(
            R.styleable.SignatureView_signatureView_linewidth,
            Float.MIN_VALUE );
        if ( lineWidth != Float.MIN_VALUE )
            setLineWidth( lineWidth );

        interval = dpToPx( 2 );
    }

    public void setLineColor( int newColor ) {
        paint.setColor( newColor );
    }

    public void setLineWidth( float newWidth ) {
        paint.setStrokeWidth( newWidth );
    }

    @Override
    protected void onDraw( Canvas canvas ) {
        canvas.drawPath( path, paint );
    }

    @Override
    public boolean onTouchEvent( MotionEvent event ) {
        float localX = event.getX();
        float localY = event.getY();

        switch ( event.getAction() ) {
            case MotionEvent.ACTION_DOWN:
                descriptor.moveTo( localX, localY );

                x = localX;
                y = localY;

                reInit();
            break;
            case MotionEvent.ACTION_MOVE:
                if ( Math.abs( localX - x ) >= interval
                    || Math.abs( localY - y ) >= interval ) {
                    descriptor.quadTo(
                        x,
                        y,
                        ( localX + x ) / 2,
                        ( localY + y ) / 2 );

                    x = localX;
                    y = localY;
                }

                reInit();
            break;
            case MotionEvent.ACTION_UP:
                if ( x != localX || y != localY ) {
                    descriptor.lineTo( x, y );
                    reInit();
                }
            break;
        }
        reInit();
        return true;
    }

    public boolean isEmpty() {
        return descriptor.isEmpty();
    }

    public boolean nonEmpty() {
        return descriptor.nonEmpty();
    }

    public void clear() {
        descriptor.reset();
        reInit();
        invalidate();
    }

    public Bitmap export() {
        Bitmap bitmap = Bitmap.createBitmap(
            getWidth(),
            getHeight(),
            Bitmap.Config.ARGB_8888 );
        new Canvas( bitmap ).drawPath( path, paint );
        return bitmap;
    }

    public PathDescriptor getSource() {
        return descriptor;
    }

    public void setSource( PathDescriptor descriptor ) {
        this.descriptor = descriptor;
        reInit();
        invalidate();
    }

    @Override
    protected void onSizeChanged( int w, int h, int oldw, int oldh ) {
        super.onSizeChanged( w, h, oldw, oldh );
        reInit();
        invalidate();
    }

    public void reInit() {
        path = descriptor.create();
        invalidate();
    }

    protected void setPath( Path path ) {
        this.path = path;
        invalidate();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable( "state", super.onSaveInstanceState() );
        bundle.putParcelable( "descriptor", this.descriptor );
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState( Parcelable state ) {
        if ( state instanceof Bundle ) {
            Bundle bundle = (Bundle) state;
            PathDescriptor descriptor = bundle.getParcelable( "descriptor" );
            setSource( descriptor );
            state = bundle.getParcelable( "state" );
        }

        super.onRestoreInstanceState( state );
    }

    private float dpToPx( float dp ) {
        Resources resources = getContext().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp
            * ( (float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT );
    }

}