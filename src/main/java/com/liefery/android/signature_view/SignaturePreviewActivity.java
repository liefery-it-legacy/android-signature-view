package com.liefery.android.signature_view;

import android.content.Intent;
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
                    Intent intent = SignatureActivity.newInstance(
                        SignaturePreviewActivity.this,
                        getSignatureActivityTitle(),
                        getSignatureActivitySubtitle() );
                    startActivityForResult( intent, REQUEST_CODE_SIGNATURE );
                }
            } );
    }

    @Override
    protected void onActivityResult(
        int requestCode,
        int resultCode,
        Intent data ) {
        if ( requestCode == REQUEST_CODE_SIGNATURE ) {
            SignatureActivityResultHandler handler = new SignatureActivityResultHandler(
                resultCode,
                data );

            if ( handler.isEmpty() ) {
                onSignatureEmpty();
            } else {
                PathDescriptor pathDescriptor = handler.getPathDescriptor();
                getSignaturePreviewWidget().set( pathDescriptor );
                onSignatureReceived( pathDescriptor );
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
