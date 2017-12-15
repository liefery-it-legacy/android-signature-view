package com.liefery.android.signature_view.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.liefery.android.signature_view.PathDescriptor;
import com.liefery.android.signature_view.SignatureActivity;

public class SampleSignatureActivity extends SignatureActivity {
    public static Intent newInstance( Context context, PathDescriptor signature ) {
        Intent intent = new Intent( context, SampleSignatureActivity.class );
        intent.putExtra( "signature", signature );
        return intent;
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        if ( savedInstanceState == null ) {
            PathDescriptor signature = getIntent().getParcelableExtra(
                "signature" );
            getSignatureView().setSignature( signature );
        }
    }
}