package com.liefery.android.signature_view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class SignaturePreviewView extends View {
    private Paint paint = Util.lineStyle( getResources() );

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
    }

    @Override
    protected void onDraw( Canvas canvas ) {
        canvas.drawPath( scaledPath, paint );
    }

    public PathDescriptor getSignature() {
        return pathDescriptor;
    }

    public void setSignature( PathDescriptor signature ) {
        this.pathDescriptor = signature;
        setScaledDrawingPath( signature.create( getWidth(), getHeight() ) );
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

    @Override
    protected void onSizeChanged(
        int width,
        int height,
        int oldWidth,
        int oldHeight ) {
        super.onSizeChanged( width, height, oldWidth, oldHeight );
        setScaledDrawingPath( pathDescriptor.create( width, height ) );
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
}
