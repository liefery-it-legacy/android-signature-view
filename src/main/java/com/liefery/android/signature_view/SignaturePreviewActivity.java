package com.liefery.android.signature_view;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by gerogerke on 20.07.17.
 */
public abstract class SignaturePreviewActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SIGNATURE = 11;

    @NonNull
    public abstract String getSignatureActivityTitle();

    @Nullable
    public abstract String getSignatureActivitySubtitle();

    @NonNull
    public abstract SignaturePreviewView getSignaturePreviewWidget();

    @Override
    public void onPostCreate( Bundle savedInstanceState ) {
        super.onPostCreate( savedInstanceState );

        getSignaturePreviewWidget().setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick( View view ) {
                    Intent i = new Intent(
                        getApplicationContext(),
                        SignatureActivity.class );
                    i.putExtra( "ab_title", getSignatureActivityTitle() );
                    i.putExtra( "ab_subtitle", getSignatureActivitySubtitle() );
                    try {
                        i.putExtra(
                            "theme",
                            getPackageManager().getActivityInfo(
                                getComponentName(),
                                0 ).theme );
                    } catch ( PackageManager.NameNotFoundException e ) {
                        e.printStackTrace();
                    }
                    startActivityForResult( i, REQUEST_CODE_SIGNATURE );
                }
            } );
    }

    @Override
    protected void onActivityResult(
        int requestCode,
        int resultCode,
        Intent data ) {
        if ( requestCode == REQUEST_CODE_SIGNATURE ) {
            if ( resultCode == Activity.RESULT_OK ) {
                PathDescriptor result = data.getParcelableExtra( "result" );
                getSignaturePreviewWidget().set( result );
                onSignatureReceived( result );
            } else {
                onSignatureEmpty();
            }
        }
    }

    public void onSignatureEmpty() {
        //Method Stub
    }

    public void onSignatureReceived( PathDescriptor signature ) {
        //Method Stub
    }
}
