package com.liefery.android.signature_view.actions;

import android.graphics.Path;
import android.os.Parcel;
import android.util.Log;

/**
 * Created by gerogerke on 05.07.17.
 */
public class MoveToAction extends Action {

    float x, y;

    public MoveToAction( float x, float y ) {
        this.x = x;
        this.y = y;
    }

    public MoveToAction( Parcel in ) {
        float[] data = new float[2];
        in.readFloatArray( data );

        this.x = data[0];
        this.y = data[1];
    }

    @Override
    public void replay( Path path ) {
        path.moveTo( x, y );
    }

    @Override
    protected int getIdentifier() {
        return MOVE_TO_ACTION;
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
        return "MoveToAction(" + x + ", " + y + ")";
    }
}
