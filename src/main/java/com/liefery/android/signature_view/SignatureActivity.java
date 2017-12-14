package com.liefery.android.signature_view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public abstract class SignatureActivity extends AppCompatActivity {

    private SignaturePaintView signatureView;

    public SignaturePaintView getSignatureView() {
        return signatureView;
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.signature_view_signature_activity );
        setSupportActionBar( (Toolbar) findViewById( R.id.toolbar ) );
        signatureView = (SignaturePaintView) findViewById( R.id.signature );
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void finish() {
        if ( signatureView.isEmpty() ) {
            setResult( Activity.RESULT_CANCELED );
        } else {
            Intent intent = new Intent();
            intent.putExtra( "result", signatureView.getSource() );
            setResult( Activity.RESULT_OK, intent );
        }

        super.finish();
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater().inflate( R.menu.menu, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        int id = item.getItemId();

        if ( id == R.id.clear_canvas ) {
            signatureView.clear();
            return true;
        } else if ( id == R.id.confirm_signature ) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected( item );
    }
}