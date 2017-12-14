package com.liefery.android.signature_view;

import android.graphics.Path;
import android.os.Parcel;
import android.os.Parcelable;
import com.liefery.android.signature_view.actions.Action;
import com.liefery.android.signature_view.actions.LineToAction;
import com.liefery.android.signature_view.actions.MoveToAction;
import com.liefery.android.signature_view.actions.QuadToAction;

import java.util.LinkedList;

public class PathDescriptor implements Parcelable {

    LinkedList<Action> actions = new LinkedList<>();

    public PathDescriptor() {

    }

    public PathDescriptor( Parcel in ) {
        in.readTypedList( actions, Action.CREATOR );
    }

    public void moveTo( float x, float y ) {
        Action moveTo = new MoveToAction( x, y );
        actions.add( moveTo );
    }

    public void lineTo( float x, float y ) {
        Action lineTo = new LineToAction( x, y );
        actions.add( lineTo );
    }

    public void quadTo( float x1, float y1, float x2, float y2 ) {
        Action quadTo = new QuadToAction( x1, y1, x2, y2 );
        actions.add( quadTo );
    }

    public void reset() {
        actions.clear();
    }

    public boolean isEmpty() {
        return !nonEmpty();
    }

    public boolean nonEmpty() {
        for ( Action action : actions ) {
            if ( action instanceof LineToAction
                || action instanceof QuadToAction ) {
                return true;
            }
        }
        return false;
    }

    public Path create() {
        Path path = new Path();
        for ( Action action : actions ) {
            action.replay( path );
        }
        return path;
    }

    @Override
    public String toString() {
        return "PathDescriptor(" + actions.toString() + ")";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel( Parcel parcel, int i ) {
        parcel.writeTypedList( actions );
    }

    public static final Creator<PathDescriptor> CREATOR = new Parcelable.Creator<PathDescriptor>() {
        public PathDescriptor createFromParcel( Parcel in ) {
            return new PathDescriptor( in );
        }

        public PathDescriptor[] newArray( int size ) {
            return new PathDescriptor[size];
        }
    };

    @Override
    public boolean equals( Object obj ) {
        if ( obj instanceof PathDescriptor ) {
            PathDescriptor path = (PathDescriptor) obj;
            return path.actions.equals( path.actions );
        }

        return false;
    }
}
