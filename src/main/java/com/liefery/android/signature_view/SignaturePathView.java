package com.liefery.android.signature_view;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class SignaturePathView extends SignatureView {
    public SignaturePathView( Context context ) {
        super( context );
    }

    public SignaturePathView( Context context, AttributeSet attrs ) {
        super( context, attrs );
    }

    public SignaturePathView(
        Context context,
        AttributeSet attrs,
        int defStyleAttr ) {
        super( context, attrs, defStyleAttr );
    }

    @Override
    public boolean onTouchEvent( MotionEvent event ) {
        return false;
    }

    @Override
    public void reInit() {
        rescale();
    }

    private void rescale() {
        if ( !getSource().isEmpty() ) {
            Path path = getSource().create();

            RectF bounds = new RectF();
            path.computeBounds( bounds, false );

            // Normalize to 0,0
            path.offset( -bounds.left, -bounds.top );

            {
                // Scale to fill the view
                float width = bounds.right - bounds.left;
                float height = bounds.bottom - bounds.top;
                float ratio = Math
                                .min( getWidth() / width, getWidth() / height ) * 0.9f;

                Matrix matrix = new Matrix();
                matrix.setScale( ratio, ratio );
                path.transform( matrix );
            }

            {
                // Center
                path.computeBounds( bounds, false );
                float width = bounds.right;
                float height = bounds.bottom;

                path.offset(
                    ( getWidth() - width ) / 2,
                    ( getHeight() - height ) / 2 );
            }

            setPath( path );
        }
    }
}
