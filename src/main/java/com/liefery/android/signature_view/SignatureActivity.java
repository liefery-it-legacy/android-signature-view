package com.liefery.android.signature_view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class SignatureActivity extends Activity {

    SignaturePaintView signatureView;

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_signature_view );

        signatureView = (SignaturePaintView) findViewById( R.id.signature_view );
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.menu, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        if ( item.getItemId() == R.id.clear_canvas ) {
            signatureView.clear();
            return true;
        } else if ( item.getItemId() == R.id.confirm_signature ) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra( "result", signatureView.getSource() );
            setResult( Activity.RESULT_OK, returnIntent );
            finish();
            return true;
        }
        return super.onOptionsItemSelected( item );
    }
}