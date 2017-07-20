package com.liefery.android.signature_view.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
    public SignaturePreviewWidget getSignaturePreviewWidget() {
        return signatureView;
    }
}