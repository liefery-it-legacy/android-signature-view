package com.liefery.android.signature_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

/**
 * Created by gerogerke on 13.07.17.
 */
public class SignaturePreviewWidget extends FrameLayout {

    Context context;

    SignaturePathView pathView;

    public SignaturePreviewWidget( Context context ) {
        super( context );
        init( context );
    }

    public SignaturePreviewWidget( Context context, AttributeSet attrs ) {
        super( context, attrs );
        init( context );
    }

    public SignaturePreviewWidget(
        Context context,
        AttributeSet attrs,
        int defStyleAttr ) {
        super( context, attrs, defStyleAttr );
        init( context );
    }

    private void init( Context context ) {
        this.context = context;

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

        setPadding( 50, 50, 50, 50 );
        setBackgroundResource( R.color.grey );
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
