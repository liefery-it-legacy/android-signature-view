package com.liefery.android.signature_view.sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.liefery.android.signature_view.PathDescriptor;
import com.liefery.android.signature_view.SignatureActivity;
import com.liefery.android.signature_view.SignaturePreviewWidget;

public class Activity extends android.app.Activity {

    SignaturePreviewWidget signatureView;

    @Override
    public void onCreate( Bundle state ) {
        super.onCreate( state );

        setContentView( R.layout.main );

        signatureView = (SignaturePreviewWidget) findViewById( R.id.signature_preview );
        signatureView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                Intent i = new Intent(
                    getApplicationContext(),
                    SignatureActivity.class );
                startActivityForResult( i, 1 );
            }
        } );
    }

    @Override
    protected void onActivityResult(
        int requestCode,
        int resultCode,
        Intent data ) {
        if ( requestCode == 1 ) {
            if ( resultCode == Activity.RESULT_OK ) {
                PathDescriptor result = data.getParcelableExtra( "result" );
                signatureView.set( result );
            }
        }
    }

}