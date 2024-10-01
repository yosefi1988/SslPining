package ir.sajjadyosefi.android.sslPinTest;

import android.app.Application;
import android.content.Context;
//import com.datatheorem.android.trustkit.TrustKit;

import com.datatheorem.android.trustkit.TrustKit;


/**
 * Created by s.yousefi on 2/6/2018.
 */

public class Global extends Application {

    public static Context mContext ;
    public static RetrofitHelper retrofitHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        TrustKit.initializeWithNetworkSecurityConfiguration(this, R.xml.network_security_config);
        retrofitHelper = RetrofitHelper.getInstance(mContext);
    }

}
