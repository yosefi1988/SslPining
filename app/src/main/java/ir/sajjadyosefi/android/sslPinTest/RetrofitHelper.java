package ir.sajjadyosefi.android.sslPinTest;

import android.content.Context;

import com.datatheorem.android.trustkit.TrustKit;

import java.io.IOException;
import java.net.URL;

import okhttp3.CertificatePinner;
import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static ir.sajjadyosefi.android.sslPinTest.Url.REST_API_IP_ADDRESS;

/**
 * Created by sajjad on 11/7/2018.
 */

public class RetrofitHelper {

    private static restApi service2;
    private static RetrofitHelper retrofitHelper2;

    private RetrofitHelper() {
        try {
            URL url = new URL("https://www.bmi.ir");
//            URL url = new URL(Url.REST_API_IP_ADDRESS_M);
            String host = url.getHost();

//            HostSelectionInterceptor interceptor = new HostSelectionInterceptor();

            Dispatcher dispatcher = new Dispatcher();
            dispatcher.setMaxRequests(1);

            Interceptor interceptor = new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
//                    SystemClock.sleep(5000);
                    return chain.proceed(chain.request());
                }
            };

            OkHttpClient.Builder httpBuilder = new OkHttpClient().newBuilder();
            CertificatePinner certificatePinner = null ;

            try {
                certificatePinner = new CertificatePinner.Builder()
                        .add((new URL("https://www.bmi.ir")).getHost(), "sha256/HjdWh/siHywMS+PfpxB1PHEUU1ZjZhioRUdRlI4o7zc=")
                        .build();

            }catch (Exception ex){
                ex.printStackTrace();
                int a = 9 ;
                a ++ ;
            }

            // OkHttp 3
            OkHttpClient client =  httpBuilder
                    .addInterceptor(interceptor)
                    .certificatePinner(certificatePinner)
                    .sslSocketFactory(
                            TrustKit.getInstance().getSSLSocketFactory((new URL("https://www.bmi.ir")).getHost()),
                            TrustKit.getInstance().getTrustManager((new URL("https://www.bmi.ir")).getHost()))
                    .build();

            //3
//            OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(REST_API_IP_ADDRESS)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

            service2 = retrofit.create(restApi.class);

            int a = 4 ;
            a++;
        }catch (Exception e){
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
