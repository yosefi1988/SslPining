package ir.sajjadyosefi.android.sslPinTest;

import android.content.Context;
import android.view.View;

public class CustomException extends Exception {

    public CustomException() {

    }

    public CustomException(int errorCode) {
        switch (errorCode){
            case 1030:{
                //log
                break;
            }

            default:{
            }
        }
    }

    public static void handleServerMessage(Context mContext, View view, int code) {
        int a = 5 ;
    }

    public void handleServerMessage(Context mContext, View view, ServerResponse response) {
        if (response != null) {
            handleServerMessage(mContext,view,response.getException().getCode());
        }else {
            int a = 5 ;
        }
    }
}