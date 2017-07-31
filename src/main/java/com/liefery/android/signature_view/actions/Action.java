package com.liefery.android.signature_view.actions;

import android.graphics.Path;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gerogerke on 05.07.17.
 */
public abstract class Action implements Parcelable {
    abstract public void replay( Path path );

    abstract protected int getIdentifier();

    static final int LINE_TO_ACTION = 0;

    static final int MOVE_TO_ACTION = 1;

    static final int QUAD_TO_ACTION = 2;

    @Override
    public void writeToParcel( Parcel parcel, int i ) {
        parcel.writeInt( getIdentifier() );
    }

    public static final Creator<Action> CREATOR = new Parcelable.Creator<Action>() {
        public Action createFromParcel( Parcel in ) {
            int actionIndex = in.readInt();

            switch ( actionIndex ) {
                case LINE_TO_ACTION:
                    return new LineToAction( in );
                case MOVE_TO_ACTION:
                    return new MoveToAction( in );
                case QUAD_TO_ACTION:
                    return new QuadToAction( in );
                default:
                    return null;
            }
        }

        public Action[] newArray( int size ) {
            return new Action[size];
        }
    };
}