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

    private Paint paint = Util.lineStyle( getResources() );

    private Path path = new Path();

    private PathDescriptor descriptor = new PathDescriptor();

    private float x, y;

    private float interval;

    public SignaturePaintView( Context context ) {
        super( context );

        TypedArray styles = context
                        .obtainStyledAttributes( R.styleable.SignaturePaintView );
        init( styles );
        styles.recycle();
    }

    public SignaturePaintView( Context context, AttributeSet attrs ) {
        super( context, attrs );

        TypedArray styles = context.obtainStyledAttributes(
            attrs,
            R.styleable.SignaturePaintView );
        init( styles );
        styles.recycle();
    }

    public SignaturePaintView(
        Context context,
        AttributeSet attrs,
        int defStyleAttr ) {
        super( context, attrs, defStyleAttr );

        TypedArray styles = context.obtainStyledAttributes(
            attrs,
            R.styleable.SignaturePaintView,
            defStyleAttr,
            0 );
        init( styles );
        styles.recycle();
    }

    private void init( TypedArray styles ) {
        int lineColor = styles.getColor(
            R.styleable.SignaturePaintView_signatureView_linecolor,
            Integer.MIN_VALUE );
        if ( lineColor != Integer.MIN_VALUE )
            setLineColor( lineColor );

        float lineWidth = styles.getDimension(
            R.styleable.SignaturePaintView_signatureView_linewidth,
            Float.MIN_VALUE );
        if ( lineWidth != Float.MIN_VALUE )
            setLineWidth( lineWidth );

        setBackgroundColor( Color.WHITE );

        interval = Util.dpToPx( getResources(), 2 );
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

    public PathDescriptor getSignature() {
        return descriptor;
    }

    public void setSignature( PathDescriptor signature ) {
        this.descriptor = signature;
        reInit();
        invalidate();
    }

    @Override
    protected void onSizeChanged( int w, int h, int oldw, int oldh ) {
        super.onSizeChanged( w, h, oldw, oldh );
        reInit();
        invalidate();
    }

    private void reInit() {
        path = descriptor.create();
        invalidate();
    }

    private void setPath( Path path ) {
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
            setSignature( descriptor );
            state = bundle.getParcelable( "state" );
        }

        super.onRestoreInstanceState( state );
    }
}