package com.liefery.android.signature_view.actions;

import android.graphics.Path;
import android.os.Parcel;

public class MoveToAction extends Action {

    private float x, y;

    public MoveToAction( float x, float y ) {
        this.x = x;
        this.y = y;
    }

    MoveToAction( Parcel in ) {
        this.x = in.readFloat();
        this.y = in.readFloat();
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
        parcel.writeFloat( x );
        parcel.writeFloat( y );
    }

    @Override
    public String toString() {
        return "MoveToAction(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals( Object obj ) {
        if ( obj instanceof MoveToAction ) {
            MoveToAction m1 = (MoveToAction) obj;
            return x == m1.x && y == m1.y;
        }
        return false;
    }
}
