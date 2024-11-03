package ir.sajjadyosefi.android.sslPinTest;

import ir.sajjadyosefi.android.sslPinTest.MaintenanceModeRequest;
import ir.sajjadyosefi.android.sslPinTest.restApi;
import okhttp3.CertificatePinner;
import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.net.URL;

import static ir.sajjadyosefi.android.sslPinTest.Url.REST_API_IP_ADDRESS;

import android.content.Context;

public class RetrofitHelper {

    private static restApi service2;
    private static RetrofitHelper retrofitHelper2;

    //trustKit and network xml not need
    //اینجا تنها از OkHttpClient و CertificatePinner برای پین کردن گواهی‌ها استفاده شده است.
    private RetrofitHelper() {
        try {
            URL url = new URL("https://apigw.sb24.ir");
            String host = url.getHost();

            Dispatcher dispatcher = new Dispatcher();
            dispatcher.setMaxRequests(1);

            Interceptor interceptor = new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    return chain.proceed(chain.request());
                }
            };

            OkHttpClient.Builder httpBuilder = new OkHttpClient().newBuilder();

            // تنظیم CertificatePinner
            CertificatePinner certificatePinner = new CertificatePinner.Builder()
                    .add(host, "sha256/tr0KDvDiF22ccuLlujt0e/N9Mait5awTTZAokDILLiY=")
                    .build();

            // ساخت OkHttpClient با CertificatePinner
            OkHttpClient client = httpBuilder
                    .addInterceptor(interceptor)
                    .certificatePinner(certificatePinner)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(REST_API_IP_ADDRESS)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

            service2 = retrofit.create(restApi.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static RetrofitHelper getInstance(Context mContext) {
        if (retrofitHelper2 == null) {
            retrofitHelper2 = new RetrofitHelper();
        }
        return retrofitHelper2;
    }

    public void maintenanceMode(MaintenanceModeRequest request, Callback<Object> objectCallback) {
        Call<Object> callBack = service2.maintenanceMode(request);
        callBack.enqueue(objectCallback);
    }
}
