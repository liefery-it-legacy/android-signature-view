package com.liefery.android.signature_view.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;
import com.liefery.android.signature_view.PathDescriptor;
import com.liefery.android.signature_view.SignaturePreviewActivity;
import com.liefery.android.signature_view.SignaturePreviewWidget;

public class Activity extends SignaturePreviewActivity {

    SignaturePreviewWidget signatureView;

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.main );

        signatureView = (SignaturePreviewWidget) findViewById( R.id.signature_preview );
    }

    @Override
    public String getSignatureActivityTitle() {
        return "Kundenunterschrift";
    }

    @Override
    public String getSignatureActivitySubtitle() {
        return "HS9H-AX1U";
    }

    @Override
    public void onSignatureEmpty() {
        Toast.makeText(
            this,
            "Signature was returned empty!",
            Toast.LENGTH_SHORT ).show();
    }

    @Override
    public void onSignatureReceived( PathDescriptor signature ) {
        Toast.makeText( this, "We recieved a signature!", Toast.LENGTH_SHORT )
                        .show();
    }

    @Override
    public SignaturePreviewWidget getSignaturePreviewWidget() {
        return signatureView;
    }
}