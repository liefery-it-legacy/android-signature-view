package com.liefery.android.signature_view.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import com.liefery.android.signature_view.PathDescriptor;
import com.liefery.android.signature_view.SignatureActivityResultHandler;
import com.liefery.android.signature_view.SignaturePreviewView;

public class SampleActivity extends AppCompatActivity {

    SignaturePreviewView signatureView;

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.main );
        setSupportActionBar( (Toolbar) findViewById( R.id.toolbar ) );

        signatureView = (SignaturePreviewView) findViewById( R.id.signature_preview );
        signatureView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent intent = SampleSignatureActivity.newInstance(
                    SampleActivity.this,
                    signatureView.getSignature() );
                startActivityForResult( intent, 777 );
            }
        } );
    }

    @Override
    protected void onActivityResult(
        int requestCode,
        int resultCode,
        Intent data ) {
        super.onActivityResult( requestCode, resultCode, data );

        if ( requestCode == 777 ) {
            SignatureActivityResultHandler handler = new SignatureActivityResultHandler(
                resultCode,
                data );

            if ( handler.isEmpty() ) {
                onSignatureEmpty();
            } else {
                onSignatureReceived( handler.getPathDescriptor() );
            }
        }
    }

    public void onSignatureEmpty() {
        Toast.makeText(
            this,
            "Signature was returned empty!",
            Toast.LENGTH_SHORT ).show();
    }

    public void onSignatureReceived( PathDescriptor signature ) {
        signatureView.setSignature( signature );
        Toast.makeText( this, "We recieved a signature!", Toast.LENGTH_SHORT )
                        .show();
    }
}