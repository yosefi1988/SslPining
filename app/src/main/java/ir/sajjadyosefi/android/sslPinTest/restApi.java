package ir.sajjadyosefi.android.sslPinTest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import static ir.sajjadyosefi.android.sslPinTest.Url.Suffix;

public interface restApi {

    String level2M = "/api/Maintenance/";
    @POST(level2M + Suffix)
    Call<Object> maintenanceMode(@Body MaintenanceModeRequest user);
}
