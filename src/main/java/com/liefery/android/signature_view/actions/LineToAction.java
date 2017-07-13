package com.liefery.android.signature_view.actions;

import android.graphics.Path;
import android.os.Parcel;
import android.util.Log;

public class LineToAction extends Action {

    private float x, y;

    public LineToAction( float x, float y ) {
        this.x = x;
        this.y = y;
    }

    LineToAction( Parcel in ) {
        float[] data = new float[2];
        in.readFloatArray( data );

        x = data[0];
        y = data[1];
    }

    @Override
    public void replay( Path path ) {
        path.lineTo( x, y );
    }

    @Override
    protected int getIdentifier() {
        return LINE_TO_ACTION;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel( Parcel parcel, int i ) {
        super.writeToParcel( parcel, i );
        parcel.writeFloatArray( new float[] { x, y } );
    }

    @Override
    public String toString() {
        return "LineToAction(" + x + ", " + y + ")";
    }
}
