package com.liefery.android.signature_view.sample;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.liefery.android.signature_view.SignatureView;

public class Activity extends android.app.Activity {

    SignatureView signatureView;

    @Override
    public void onCreate( Bundle state ) {
        super.onCreate( state );

        setContentView( R.layout.main );

        signatureView = (SignatureView) findViewById( R.id.signature_view );
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.menu, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        switch ( item.getItemId() ) {
            case R.id.clear_canvas:
                signatureView.clear();
                return true;
            default:
                return super.onOptionsItemSelected( item );
        }
    }

}