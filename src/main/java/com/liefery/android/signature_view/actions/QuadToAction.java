package com.liefery.android.signature_view.actions;

import android.graphics.Path;
import android.os.Parcel;

public class QuadToAction extends Action {

    private float x1, y1, x2, y2;

    public QuadToAction( float x1, float y1, float x2, float y2 ) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    QuadToAction( Parcel in ) {
        x1 = in.readFloat();
        y1 = in.readFloat();
        x2 = in.readFloat();
        y2 = in.readFloat();
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
        parcel.writeFloat( x1 );
        parcel.writeFloat( y1 );
        parcel.writeFloat( x2 );
        parcel.writeFloat( y2 );
    }

    @Override
    public String toString() {
        return "QuadToAction(" + x1 + ", " + y1 + ", " + x2 + ", " + y2 + ")";
    }

    @Override
    public boolean equals( Object obj ) {
        if ( obj instanceof QuadToAction ) {
            QuadToAction q1 = (QuadToAction) obj;
            return x1 == q1.x1 && x2 == q1.x2 && y1 == q1.y1 && y2 == q1.y2;
        }
        return false;
    }
}
