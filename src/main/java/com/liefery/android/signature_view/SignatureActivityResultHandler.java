package com.liefery.android.signature_view;

import android.app.Activity;
import android.content.Intent;

public class SignatureActivityResultHandler {
    final private int resultCode;

    final private Intent data;

    public SignatureActivityResultHandler( int resultCode, Intent data ) {
        this.resultCode = resultCode;
        this.data = data;
    }

    public PathDescriptor getPathDescriptor() {
        return data.getParcelableExtra( "result" );
    }

    public boolean isEmpty() {
        return resultCode != Activity.RESULT_OK;
    }
}
