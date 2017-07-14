package com.liefery.android.signature_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

/**
 * Created by gerogerke on 13.07.17.
 */
public class SignaturePreviewWidget extends FrameLayout {

    SignaturePathView pathView;

    public SignaturePreviewWidget( Context context ) {
        super( context );

        TypedArray styles = context
                        .obtainStyledAttributes( R.styleable.SignaturePreviewWidget );
        init( context, styles );
        styles.recycle();
    }

    public SignaturePreviewWidget( Context context, AttributeSet attrs ) {
        super( context, attrs );

        TypedArray styles = context
                        .obtainStyledAttributes( R.styleable.SignaturePreviewWidget );
        init( context, styles );
        styles.recycle();
    }

    public SignaturePreviewWidget(
        Context context,
        AttributeSet attrs,
        int defStyleAttr ) {
        super( context, attrs, defStyleAttr );

        TypedArray styles = context
                        .obtainStyledAttributes( R.styleable.SignaturePreviewWidget );
        init( context, styles );
        styles.recycle();
    }

    private void init( Context context, TypedArray styles ) {

        ( (LayoutInflater) context
                        .getSystemService( Context.LAYOUT_INFLATER_SERVICE ) )
                        .inflate(
                            R.layout.widget_transaction_signature_preview,
                            this );

        pathView = (SignaturePathView) findViewById( R.id.layout_widget_transaction_signature_preview );

        setClickable( true );

        TypedArray array = context
                        .obtainStyledAttributes( new int[] { R.attr.selectableItemBackground } );
        setForeground( array.getDrawable( 0 ) );
        array.recycle();

        int widgetColor = styles.getColor(
            R.styleable.SignaturePreviewWidget_signaturePreview_color,
            Integer.MIN_VALUE );
        setBackgroundColor( widgetColor == Integer.MIN_VALUE ? ContextCompat
                        .getColor( context, R.color.grey ) : widgetColor );

        int borderColor = styles.getColor(
            R.styleable.SignaturePreviewWidget_signaturePreview_borderColor,
            Integer.MIN_VALUE );
        pathView.setBackgroundColor( widgetColor == Integer.MIN_VALUE ? ContextCompat
                        .getColor( context, R.color.grey_light ) : borderColor );

        setPadding( 50, 50, 50, 50 );
    }

    public void set( PathDescriptor path ) {
        pathView.setSource( path );
    }

    public PathDescriptor getSource() {
        return pathView.getSource();
    }

    public void clear() {
        pathView.clear();
    }

    public Bitmap export() {
        return pathView.export();
    }

    public boolean isEmpty() {
        return pathView.isEmpty();
    }
}
