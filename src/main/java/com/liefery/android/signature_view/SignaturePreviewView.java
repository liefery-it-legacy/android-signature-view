package com.liefery.android.signature_view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class SignaturePreviewView extends View {

    Path signature = new Path();

    Paint paint = new Paint();

    PathDescriptor source;

    public SignaturePreviewView( Context context ) {
        super( context );

        init();
    }

    public SignaturePreviewView( Context context, AttributeSet attrs ) {
        super( context, attrs );

        init();
    }

    public SignaturePreviewView(
        Context context,
        AttributeSet attrs,
        int defStyleAttr ) {
        super( context, attrs, defStyleAttr );

        init();
    }

    private void init() {
        setBackgroundColor( ContextCompat.getColor(
            getContext(),
            R.color.signature_view_signature_preview_background ) );

        paint.setAntiAlias( true );
        paint.setColor( Color.BLACK );
        paint.setDither( true );
        paint.setStrokeCap( Paint.Cap.ROUND );
        paint.setStrokeJoin( Paint.Join.ROUND );
        paint.setStrokeWidth( dpToPx( 4 ) );
        paint.setStyle( Paint.Style.STROKE );
    }

    @Override
    protected void onDraw( Canvas canvas ) {
        canvas.drawPath( signature, paint );
    }

    public PathDescriptor getSignature() {
        return source;
    }

    public void setSignature( PathDescriptor source ) {
        this.source = source;
        rescale();
    }

    public void setPath( Path path ) {
        this.signature = path;
        invalidate();
    }

    private void rescale() {
        if ( !getSignature().isEmpty() ) {
            Path path = getSignature().create();

            RectF bounds = new RectF();
            path.computeBounds( bounds, false );

            // Normalize to 0,0
            path.offset( -bounds.left, -bounds.top );

            // Scale to fill the view
            float width = bounds.right - bounds.left;
            float height = bounds.bottom - bounds.top;
            float ratio = Math.min( getWidth() / width, getHeight() / height ) * 0.9f;

            Matrix matrix = new Matrix();
            matrix.setScale( ratio, ratio );
            path.transform( matrix );

            // Center
            path.computeBounds( bounds, false );
            width = bounds.right;
            height = bounds.bottom;

            path.offset(
                ( getWidth() - width ) / 2,
                ( getHeight() - height ) / 2 );

            setPath( path );
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable( "state", super.onSaveInstanceState() );
        bundle.putParcelable( "descriptor", this.source );
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

    private float dpToPx( float dp ) {
        Resources resources = getContext().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp
            * ( (float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT );
    }
}
