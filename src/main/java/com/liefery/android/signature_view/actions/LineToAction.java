package com.liefery.android.signature_view.actions;

import android.graphics.Path;
import android.os.Parcel;

public class LineToAction extends Action {

    private float x, y;

    public LineToAction( float x, float y ) {
        this.x = x;
        this.y = y;
    }

    LineToAction( Parcel in ) {
        x = in.readFloat();
        y = in.readFloat();
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
        parcel.writeFloat( x );
        parcel.writeFloat( y );
    }

    @Override
    public String toString() {
        return "LineToAction(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals( Object obj ) {
        if ( obj instanceof LineToAction ) {
            LineToAction l1 = (LineToAction) obj;
            return x == l1.x && y == l1.y;
        }
        return false;
    }
}
