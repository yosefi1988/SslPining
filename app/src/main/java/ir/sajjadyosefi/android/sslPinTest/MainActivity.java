package ir.sajjadyosefi.android.sslPinTest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

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


            }

        });
        Global.retrofitHelper.maintenanceMode(requestmaintenanceModeCheck, xxxxxxxxxxxxx);


        //2
        //2
        createRequest(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                Gson gson = new Gson();
                JsonElement jsonElement = gson.toJsonTree(response.body());
            }
        });



        //3
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        CertificatePinner certificatePinner = new CertificatePinner.Builder()
                .add("bmi.ir", "sha256/S4AbJNGvyS57nzJwv8sPMUML8VHSqH1vbiBftdPcErI=")
                .build();
        OkHttpClient client1 = httpBuilder
                .certificatePinner(certificatePinner)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client1)
                .baseUrl("https://bmi.ir/").addConverterFactory(GsonConverterFactory.create()).build();
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


    public okhttp3.Call createRequest(okhttp3.Callback callback) {

        URL url = null;
        try {
            url = new URL("https://www.bmi.ir");
//            url = new URL(REST_API_IP_ADDRESS_M);

            String host = url.getHost();
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

//            SSLSocketFactory sslSocketFactory = TrustKit.getInstance().getSSLSocketFactory(host);
//            X509TrustManager trustManager = TrustKit.getInstance().getTrustManager(host);
//            connection.setSSLSocketFactory(sslSocketFactory);

            OkHttpClient client = new OkHttpClient().newBuilder()
//                    .sslSocketFactory(sslSocketFactory, trustManager)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            okhttp3.Call call = client.newCall(request);
            call.enqueue(callback);

            return call;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;

        }
    }

}