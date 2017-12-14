package com.liefery.android.signature_view;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;

public class SignatureActivityResultHandler {
    final private int resultCode;

    final private Intent data;

    public SignatureActivityResultHandler( int resultCode, @Nullable Intent data ) {
        this.resultCode = resultCode;
        this.data = data;
    }

    @Nullable
    public PathDescriptor getPathDescriptor() {
        if ( data != null ) {
            return data.getParcelableExtra( "result" );
        } else {
            return null;
        }
    }

    public boolean isEmpty() {
        return resultCode != Activity.RESULT_OK;
    }
}
