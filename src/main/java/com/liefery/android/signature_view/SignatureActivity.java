package com.liefery.android.signature_view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class SignatureActivity extends AppCompatActivity {

    public static Intent newInstance(
        Activity context,
        String title,
        String subtitle ) {
        Intent intent = new Intent( context, SignatureActivity.class );
        intent.putExtra( "ab_title", title );
        intent.putExtra( "ab_subtitle", subtitle );
        try {
            intent.putExtra(
                "theme",
                context.getPackageManager().getActivityInfo(
                    context.getComponentName(),
                    0 ).theme );
        } catch ( PackageManager.NameNotFoundException exception ) {
            Log.e( "SignatureActivity", "PackageManager failed", exception );
        }

        return intent;
    }

    SignaturePaintView signatureView;

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setThemeIfPassed( getIntent().getExtras() );

        setContentView( R.layout.activity_signature_view );

        signatureView = (SignaturePaintView) findViewById( R.id.signature_view );

        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
            android.support.v7.app.ActionBar ab = getSupportActionBar();
            if ( ab != null ) {
                ab.setTitle( getIntent().getStringExtra( "ab_title" ) );
                ab.setSubtitle( getIntent().getStringExtra( "ab_subtitle" ) );
            }
        }
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
            if ( signatureView.isEmpty() ) {
                setResult( Activity.RESULT_CANCELED );
            } else {
                Intent returnIntent = new Intent();
                returnIntent.putExtra( "result", signatureView.getSource() );
                setResult( Activity.RESULT_OK, returnIntent );
            }
            finish();
            return true;
        }
        return super.onOptionsItemSelected( item );
    }

    private void setThemeIfPassed( Bundle extras ) {
        if ( extras.containsKey( "theme" ) ) {
            int theme = extras.getInt( "theme" );
            setTheme( theme );
        }
    }
}