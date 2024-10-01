package ir.sajjadyosefi.android.sslPinTest;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class customRetrofitCallback<Object> implements Callback<java.lang.Object> {
    @SuppressWarnings("unused")
    private static final String TAG = "RetrofitCallback";
    private final Context mContext;
    private final View buttonSubmit;
    private final boolean showResult;
    private View rootView;
    private final Callback<java.lang.Object> mCallback;

    public customRetrofitCallback(Context context, View rootView, boolean showResult, View buttonSubmit, Callback<java.lang.Object> callback) {
        this.mContext = context;
        this.rootView = rootView;
        this.buttonSubmit = buttonSubmit;
        this.mCallback = callback;
        this.showResult = showResult;
        preRequest();
    }

    private void preRequest() {
        if (buttonSubmit != null)
            buttonSubmit.setEnabled(false);

    }

    private void afterResponse() {
        if (buttonSubmit != null)
            buttonSubmit.setEnabled(true);
    }

    @Override
    public void onResponse(Call<java.lang.Object> call, Response<java.lang.Object> response) {
        afterResponse();

        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(response.body());

        //todo comment
        Log.i("sajjad Response" , jsonElement.toString());

        ServerResponse responseX = gson.fromJson(jsonElement, ServerResponse.class);

        try {
            if (response.body() != null ) {
                if (responseX.getException() != null) {
                    if (
                            //responseX.getException().getCode() == 2222 ||
                            responseX.getException().getCode() == 1001 || responseX.getException().getCode() == 1026) {
                        //otpValueValidateResponseComplete(response.body().getResponse());
                        if (call != null && response != null)
                            mCallback.onResponse(call, response);
                    } else {
                        throw new CustomException(responseX.getException().getCode());
                    }
                }else {
                    throw new CustomException();
                }
            }else {
                throw new CustomException();
            }
        } catch (CustomException customException) {
            if (showResult)
                customException.handleServerMessage(mContext,rootView,responseX);
        }

    }


    @Override
    public void onFailure(final Call<java.lang.Object> call, Throwable t) {

        if (t.getMessage().contains("SSL handshake timed out")) {
            Toast.makeText(mContext,"Not ok SSL handshake timed out",Toast.LENGTH_LONG).show();

        }else if (t.getMessage().contains("SSL handshake aborted: ssl")) {
            Toast.makeText(mContext,"Not ok SSL handshake aborted",Toast.LENGTH_LONG).show();

        }else if (t.getMessage().contains("Certificate validation failed for www.bmi.ir")) {
            Toast.makeText(mContext,"Not ok ( provide UI indication of an insecure connection )  Certificate validation failed ",Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(mContext,"Not ok",Toast.LENGTH_LONG).show();

        }

        int a = 5;
    }


    public static void showConnectionLostDialog(Context context, final ProgressBar progressBar, final Runnable runnable) {

        int a = 5;
    }

    private void retry(Call<java.lang.Object> call) {
        call.clone().enqueue(this);
    }



    public void retry(Call<java.lang.Object> call, Throwable t) {
        int a = 5;

    }
}