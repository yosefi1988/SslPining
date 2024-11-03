package ir.sajjadyosefi.android.sslPinTest;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Toast;

import com.datatheorem.android.trustkit.TrustKit;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public Gson gson = new Gson();
    public ViewGroup rootView ;
    public Context mContext ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rootView = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        mContext = this;


        //1 OK
        final MaintenanceModeRequest requestmaintenanceModeCheck = prepareMaintenanceModeRequest();
        Callback<Object> xxxxxxxxxxxxx = new customRetrofitCallback<Object>(mContext, rootView, true, null, new Callback<java.lang.Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                JsonElement jsonElement = gson.toJsonTree(response.body());
                //Object responseX0 = gson.fromJson(jsonElement, MaintenanceModeResponse.class);
                Toast.makeText(mContext,"ok",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

                Gson gson = new Gson();
                gson = new Gson();
            }

        });
//        Global.retrofitHelper.maintenanceMode(requestmaintenanceModeCheck, xxxxxxxxxxxxx);



        //2 ok
//        try {
//            createRequest(new okhttp3.Callback() {
//                @Override
//                public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
//                    Gson gson = new Gson();
//                    JsonElement jsonElement = gson.toJsonTree(response.body());
//                }
//
//                @Override
//                public void onFailure(okhttp3.Call call, IOException e) {
//                    Gson gson = new Gson();
//                    JsonElement jsonElement = gson.toJsonTree(e);
//                }
//            });
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }


        //3
        //RetrofitHelper ساده شده ی
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        CertificatePinner certificatePinner = new CertificatePinner.Builder()
                .add("apigw.sb24.ir", "sha256/tr0KDvDiF22ccuLlujt0e/N9Mait5awTTZAokDILLiY=")
                .build();
        OkHttpClient client1 = httpBuilder
                .certificatePinner(certificatePinner)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client1)
                .baseUrl("https://apigw.sb24.ir/").addConverterFactory(GsonConverterFactory.create()).build();
        restApi userClient = retrofit.create(restApi.class);
        Call<Object> call = userClient.maintenanceMode(requestmaintenanceModeCheck);
        call.enqueue(xxxxxxxxxxxxx);
    }

    public static MaintenanceModeRequest prepareMaintenanceModeRequest() {
        MaintenanceModeRequest request = new MaintenanceModeRequest();
        request.setUb1e6d0e_00d5_4934_bbc1_0de5de92725d(Url.userM);
        request.setPa369647_2eb2_469d_b894_09ad85a0e347(Url.passM);
        request.setApplicationName("TEST APP");
        return request;
    }


    public void createRequest(okhttp3.Callback callback) {
        URL url = null;
        try {
            url = new URL("https://apigw.sb24.ir");
            String host = url.getHost();

            OkHttpClient client = new OkHttpClient.Builder()
                    .sslSocketFactory(TrustKit.getInstance().getSSLSocketFactory(host), TrustKit.getInstance().getTrustManager(host))
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            client.newCall(request).enqueue(callback);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}