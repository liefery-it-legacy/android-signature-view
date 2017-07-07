package com.liefery.android.signature_view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.*;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import com.liefery.android.stop_badge.R;

public class SignatureView extends View {

    private Paint paint = new Paint();

    private Path path = new Path();

    private PathDescriptor descriptor = new PathDescriptor();

    private float x, y;

    private float interval;

    Display display;

    public SignatureView( Context context ) {
        super( context );

        TypedArray styles = context
                        .obtainStyledAttributes( R.styleable.SignatureView );
        init( styles );
        styles.recycle();
    }

    public SignatureView( Context context, AttributeSet attrs ) {
        super( context, attrs );

        TypedArray styles = context.obtainStyledAttributes(
            attrs,
            R.styleable.SignatureView );
        init( styles );
        styles.recycle();
    }

    public SignatureView( Context context, AttributeSet attrs, int defStyleAttr ) {
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

        display = ( (WindowManager) getContext().getSystemService(
            Context.WINDOW_SERVICE ) ).getDefaultDisplay();
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

    private void reInit() {
        path = descriptor.create();

        Matrix matrix = new Matrix();
        matrix.postRotate(
            getRotationAngle(),
            getPivotPointX(),
            getPivotPointY() );

        matrix.setScale( 0.3f, 0.3f );
        path.transform( matrix );

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

    private int getRotationAngle() {
        switch ( ( (WindowManager) getContext().getSystemService(
            Context.WINDOW_SERVICE ) ).getDefaultDisplay().getRotation() ) {
            case 1:
                return 0;
            case 0:
                return 45;
            case 3:
                return 0;
            default:
                return 0;
        }
    }

    private float getPivotPointX() {
        switch ( ( (WindowManager) getContext().getSystemService(
            Context.WINDOW_SERVICE ) ).getDefaultDisplay().getRotation() ) {
            case 1:
                return ( (WindowManager) getContext().getSystemService(
                    Context.WINDOW_SERVICE ) ).getDefaultDisplay().getHeight();
            case 0:
                return display.getHeight();
            case 3:
                return ( (WindowManager) getContext().getSystemService(
                    Context.WINDOW_SERVICE ) ).getDefaultDisplay().getHeight();
            default:
                return 0;
        }
    }

    private float getPivotPointY() {
        switch ( ( (WindowManager) getContext().getSystemService(
            Context.WINDOW_SERVICE ) ).getDefaultDisplay().getRotation() ) {
            case 1:
                return 0;
            case 0:
                return 0;
            case 3:
                return 0;
            default:
                return 0;
        }
    }

}