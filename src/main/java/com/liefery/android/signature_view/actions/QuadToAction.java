package com.liefery.android.signature_view.actions;

import android.graphics.Path;
import android.os.Parcel;
import android.util.Log;

public class QuadToAction extends Action {

    private float x1, y1, x2, y2;

    public QuadToAction( float x1, float y1, float x2, float y2 ) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    QuadToAction( Parcel in ) {
        float[] data = new float[4];
        in.readFloatArray( data );

        x1 = data[0];
        y1 = data[1];
        x2 = data[2];
        y2 = data[3];
    }

    @Override
    public void replay( Path path ) {
        path.quadTo( x1, y1, x2, y2 );
    }

    @Override
    protected int getIdentifier() {
        return QUAD_TO_ACTION;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel( Parcel parcel, int i ) {
        super.writeToParcel( parcel, i );
        parcel.writeFloatArray( new float[] { x1, y1, x2, y2 } );
    }

    @Override
    public String toString() {
        return "QuadToAction(" + x1 + ", " + y1 + ", " + x2 + ", " + y2 + ")";
    }
}
