package com.liefery.android.signature_view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class SignaturePreviewView extends View {

    private Paint paint = new Paint();

    private PathDescriptor pathDescriptor = new PathDescriptor();

    private Path scaledPath = new Path();

    public SignaturePreviewView( Context context ) {
        super( context );

        initialize();
    }

    public SignaturePreviewView( Context context, AttributeSet attrs ) {
        super( context, attrs );

        initialize();
    }

    public SignaturePreviewView(
        Context context,
        AttributeSet attrs,
        int defStyleAttr ) {
        super( context, attrs, defStyleAttr );

        initialize();
    }

    private void initialize() {
        setBackgroundResource( R.color.signature_view_signature_preview_background );

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
        canvas.drawPath( scaledPath, paint );
    }

    public PathDescriptor getSignature() {
        return pathDescriptor;
    }

    public void setSignature( PathDescriptor source ) {
        this.pathDescriptor = source;
        rescale();
    }

    public void clear() {
        setSignature( new PathDescriptor() );
        this.scaledPath = new Path();
        invalidate();
    }

    private void setScaledDrawingPath( Path path ) {
        this.scaledPath = path;
        invalidate();
    }

    private void rescale() {
        if ( pathDescriptor.isEmpty() )
            return;

        int viewWidth = getWidth();
        int viewHeight = getHeight();

        if ( viewWidth == 0 || viewHeight == 0 )
            return;

        Path path = getSignature().create();

        RectF bounds = new RectF();
        path.computeBounds( bounds, false );

        // Normalize to 0,0
        path.offset( -bounds.left, -bounds.top );

        // Scale to fill the view
        float width = bounds.right - bounds.left;
        float height = bounds.bottom - bounds.top;
        float ratio = Math.min( viewWidth / width, viewHeight / height ) * 0.9f;

        Matrix matrix = new Matrix();
        matrix.setScale( ratio, ratio );
        path.transform( matrix );

        // Center
        path.computeBounds( bounds, false );
        width = bounds.right;
        height = bounds.bottom;

        path.offset( ( viewWidth - width ) / 2, ( viewHeight - height ) / 2 );

        setScaledDrawingPath( path );
    }

    @Override
    protected void onSizeChanged(
        int width,
        int height,
        int oldWidth,
        int oldHeight ) {
        super.onSizeChanged( width, height, oldWidth, oldHeight );
        rescale();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable( "state", super.onSaveInstanceState() );
        bundle.putParcelable( "descriptor", this.pathDescriptor );
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState( Parcelable state ) {
        if ( state instanceof Bundle ) {
            Bundle bundle = (Bundle) state;
            super.onRestoreInstanceState( bundle.getParcelable( "state" ) );

            PathDescriptor descriptor = bundle.getParcelable( "descriptor" );
            setSignature( descriptor );
        } else {
            super.onRestoreInstanceState( state );
        }
    }

    private float dpToPx( float dp ) {
        Resources resources = getContext().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp
            * ( (float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT );
    }
}
