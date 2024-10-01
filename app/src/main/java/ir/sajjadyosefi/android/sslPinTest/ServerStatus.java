package ir.sajjadyosefi.android.sslPinTest;

import java.io.Serializable;

/**
 * Created by sajjad on 2/27/2017.
 */
public class ServerStatus implements Serializable {


    int Code ;
    String TraceCode ;
    String Translate ;


    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getTraceCode() {
        return TraceCode;
    }

    public void setTraceCode(String traceCode) {
        TraceCode = traceCode;
    }

    public String getTranslate() {
        return Translate;
    }

    public void setTranslate(String translate) {
        Translate = translate;
    }



}
