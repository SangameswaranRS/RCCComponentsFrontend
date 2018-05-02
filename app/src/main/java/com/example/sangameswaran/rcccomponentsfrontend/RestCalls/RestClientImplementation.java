package com.example.sangameswaran.rcccomponentsfrontend.RestCalls;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.sangameswaran.rcccomponentsfrontend.Constants.CommonFunctions;
import com.example.sangameswaran.rcccomponentsfrontend.Constants.Constants;
import com.example.sangameswaran.rcccomponentsfrontend.Entities.ErrorEntity;
import com.example.sangameswaran.rcccomponentsfrontend.Entities.GetComponentApiEntity;
import com.example.sangameswaran.rcccomponentsfrontend.Entities.GetUserRequestsApiEntity;
import com.example.sangameswaran.rcccomponentsfrontend.Entities.InventoryComponentEntity;
import com.example.sangameswaran.rcccomponentsfrontend.Entities.LoginEntity;
import com.example.sangameswaran.rcccomponentsfrontend.Entities.PostComponentRequestApiEntity;
import com.example.sangameswaran.rcccomponentsfrontend.Entities.UpdateRequestStatusEntity;
import com.example.sangameswaran.rcccomponentsfrontend.Entities.UserSignupAPIEntity;
import com.example.sangameswaran.rcccomponentsfrontend.Entities.ViewInventoryAPIEntity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by Sangameswaran on 29-04-2018.
 */

public class RestClientImplementation {
    static RequestQueue queue;

    public static String getAbsoluteURL(String relativeURL){
        return Constants.BASE_URL+relativeURL;
    }

    public static void loginApi(LoginEntity entity, final LoginEntity.RCCRCI restCientInterface, final Context context){
        queue = VolleySingleton.getInstance(context).getRequestQueue();
        String API_URL=getAbsoluteURL("/login");
        final Gson gson=new Gson();
        String JsonString=gson.toJson(entity);
        try {
            JSONObject postParams=new JSONObject(JsonString);
            JsonBaseRequest postRequest = new JsonBaseRequest(Request.Method.POST, API_URL, postParams, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    LoginEntity returnResponse=gson.fromJson(response.toString(),LoginEntity.class);
                    restCientInterface.onLogin(returnResponse,null);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        ErrorEntity errorEntity = gson.fromJson(new String(error.networkResponse.data), ErrorEntity.class);
                        CommonFunctions.toastString(errorEntity.getMessage(), context);
                        restCientInterface.onLogin(null, error);
                    }catch (Exception e){
                        CommonFunctions.toastString(e.getMessage(),context);
                    }
                }
            });
            queue.add(postRequest);
        } catch (JSONException e) {
            e.printStackTrace();
            CommonFunctions.toastString("Something went Wrong!. Try Again",context);
        }
    }

    public static void getMyRequestsApi(final GetUserRequestsApiEntity.RCCRCI restClientInterface, final Context context){
        queue=VolleySingleton.getInstance(context).getRequestQueue();
        String API_URL=getAbsoluteURL("/getUserRequests");
        final Gson gson=new Gson();
        JsonBaseRequest getRequest=new JsonBaseRequest(Request.Method.GET, API_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                GetUserRequestsApiEntity entity=gson.fromJson(response.toString(),GetUserRequestsApiEntity.class);
                restClientInterface.onGetRequestList(entity,null);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try{
                    ErrorEntity errorEntity=gson.fromJson(new String(error.networkResponse.data),ErrorEntity.class);
                    CommonFunctions.toastString(errorEntity.getMessage(),context);
                    restClientInterface.onGetRequestList(null,error);
                }catch (Exception e){
                    CommonFunctions.toastString(e.getMessage(),context);
                    restClientInterface.onGetRequestList(null,error);
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers=new HashMap<>();
                SharedPreferences sp=context.getSharedPreferences("LOGIN_CREDS",Context.MODE_PRIVATE);
                int uid=sp.getInt("uid",0);
                String uid_s=uid+"";
                headers.put("uid",uid_s);
                return headers;
            }
        };
        queue.add(getRequest);
    }

    public static void getComponentsForRid(int rid,final GetComponentApiEntity.RCCRCI restClientInterface,final Context context){
        String API_URL=getAbsoluteURL("/getComponentsForRid?rid="+rid);
        queue=VolleySingleton.getInstance(context).getRequestQueue();
        final Gson gson=new Gson();
        JsonBaseRequest getRequest=new JsonBaseRequest(Request.Method.GET, API_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                GetComponentApiEntity entity=gson.fromJson(response.toString(),GetComponentApiEntity.class);
                restClientInterface.onGetComponentInfo(entity,null);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try{
                    ErrorEntity errorEntity=gson.fromJson(new String(error.networkResponse.data),ErrorEntity.class);
                    CommonFunctions.toastString(errorEntity.getMessage(),context);
                    restClientInterface.onGetComponentInfo(null,error);
                }catch (Exception e){
                    CommonFunctions.toastString(e.getMessage(),context);
                    restClientInterface.onGetComponentInfo(null,error);
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers=new HashMap<>();
                SharedPreferences sp=context.getSharedPreferences("LOGIN_CREDS",Context.MODE_PRIVATE);
                int uid=sp.getInt("uid",0);
                String uid_s=uid+"";
                headers.put("uid",uid_s);
                return headers;
            }
        };
        queue.add(getRequest);
    }

    public static void getComponentsForRaisingRequest(final ViewInventoryAPIEntity.RCCRCI restClientInterface,final Context context){
        String API_URL=getAbsoluteURL("/viewComponentsForRaisingRequest");
        final Gson gson=new Gson();
        queue=VolleySingleton.getInstance(context).getRequestQueue();
        JsonBaseRequest getRequest=new JsonBaseRequest(Request.Method.GET, API_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ViewInventoryAPIEntity entity=gson.fromJson(response.toString(),ViewInventoryAPIEntity.class);
                restClientInterface.onGetInventoryDetails(entity,null);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try{
                    ErrorEntity errorEntity=gson.fromJson(new String(error.networkResponse.data),ErrorEntity.class);
                    CommonFunctions.toastString(errorEntity.getMessage(),context);
                    restClientInterface.onGetInventoryDetails(null,error);
                }catch (Exception e){
                    CommonFunctions.toastString(e.getMessage(),context);
                    restClientInterface.onGetInventoryDetails(null,error);
                }
            }
        });
        queue.add(getRequest);

    }

    public static void postComponentRequest(final PostComponentRequestApiEntity entity,final PostComponentRequestApiEntity.RCCRCI restClientInterface,final Context context){
        String API_URL=getAbsoluteURL("/raiseComponentRequest");
        queue=VolleySingleton.getInstance(context).getRequestQueue();
        final Gson gson=new Gson();
        try {
            String jsonString=gson.toJson(entity);
            JSONObject postParam=new JSONObject(jsonString);
            JsonBaseRequest postRequest = new JsonBaseRequest(Request.Method.POST, API_URL, postParam, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    ErrorEntity errorEntity=gson.fromJson(response.toString(),ErrorEntity.class);
                    restClientInterface.onSubmitComponentRequest(errorEntity,null);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        ErrorEntity errorEntity = gson.fromJson(new String(error.networkResponse.data), ErrorEntity.class);
                        CommonFunctions.toastString(errorEntity.getMessage(), context);
                        restClientInterface.onSubmitComponentRequest(null, error);
                    }catch (Exception e){
                        CommonFunctions.toastString(e.getMessage(),context);
                    }
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> headers=new HashMap<>();
                    SharedPreferences sp=context.getSharedPreferences("LOGIN_CREDS",Context.MODE_PRIVATE);
                    int uid=sp.getInt("uid",0);
                    String uid_s=uid+"";
                    headers.put("uid",uid_s);
                    return headers;
                }
            };
            queue.add(postRequest);
        }catch (Exception e){
            CommonFunctions.toastString(e.getMessage(),context);
            restClientInterface.onSubmitComponentRequest(null,new VolleyError());
        }
    }

    public static void getInventoryApi(final ViewInventoryAPIEntity.RCCRCI restClientInterface,final Context context){
        String API_URL=getAbsoluteURL("/viewInventory");
        queue=VolleySingleton.getInstance(context).getRequestQueue();
        final Gson gson=new Gson();
        JsonBaseRequest getRequest=new JsonBaseRequest(Request.Method.GET, API_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ViewInventoryAPIEntity entity=gson.fromJson(response.toString(),ViewInventoryAPIEntity.class);
                restClientInterface.onGetInventoryDetails(entity,null);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    ErrorEntity errorEntity = gson.fromJson(new String(error.networkResponse.data), ErrorEntity.class);
                    CommonFunctions.toastString(errorEntity.getMessage(), context);
                    restClientInterface.onGetInventoryDetails(null, error);
                }catch (Exception e){
                    CommonFunctions.toastString(e.getMessage(),context);
                    restClientInterface.onGetInventoryDetails(null,new VolleyError());
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers=new HashMap<>();
                SharedPreferences sp=context.getSharedPreferences("LOGIN_CREDS",Context.MODE_PRIVATE);
                int uid=sp.getInt("uid",0);
                String uid_s=uid+"";
                headers.put("uid",uid_s);
                return headers;
            }
        };
        queue.add(getRequest);
    }

    public static void addComponentApi(final InventoryComponentEntity entity, final InventoryComponentEntity.RCCRCI restClientInterface, final Context context){
        String API_URL=getAbsoluteURL("/addComponent");
        final Gson gson=new Gson();
        queue=VolleySingleton.getInstance(context).getRequestQueue();
        try{
            String jsonString=gson.toJson(entity);
            JSONObject postParams=new JSONObject(jsonString);
            JsonBaseRequest postRequest = new JsonBaseRequest(Request.Method.POST, API_URL, postParams, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    ErrorEntity errorEntity=gson.fromJson(response.toString(),ErrorEntity.class);
                    restClientInterface.onResult(errorEntity,null);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        ErrorEntity errorEntity = gson.fromJson(new String(error.networkResponse.data), ErrorEntity.class);
                        CommonFunctions.toastString(errorEntity.getMessage(), context);
                        restClientInterface.onResult(null, error);
                    }catch (Exception e){
                        CommonFunctions.toastString(e.getMessage(),context);
                        restClientInterface.onResult(null,new VolleyError());
                    }
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> headers=new HashMap<>();
                    SharedPreferences sp=context.getSharedPreferences("LOGIN_CREDS",Context.MODE_PRIVATE);
                    int uid=sp.getInt("uid",0);
                    String uid_s=uid+"";
                    headers.put("uid",uid_s);
                    return headers;
                }
            };
            queue.add(postRequest);
        }catch (Exception e){
            CommonFunctions.toastString(e.getMessage(),context);
        }
    }

    public static void getAllRequests(final GetUserRequestsApiEntity.RCCRCI restClientInterface, final Context context){
        queue=VolleySingleton.getInstance(context).getRequestQueue();
        String API_URL=getAbsoluteURL("/getAllRequests");
        final Gson gson=new Gson();
        JsonBaseRequest getRequest=new JsonBaseRequest(Request.Method.GET, API_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                GetUserRequestsApiEntity entity=gson.fromJson(response.toString(),GetUserRequestsApiEntity.class);
                restClientInterface.onGetRequestList(entity,null);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try{
                    ErrorEntity errorEntity=gson.fromJson(new String(error.networkResponse.data),ErrorEntity.class);
                    CommonFunctions.toastString(errorEntity.getMessage(),context);
                    restClientInterface.onGetRequestList(null,error);
                }catch (Exception e){
                    CommonFunctions.toastString(e.getMessage(),context);
                    restClientInterface.onGetRequestList(null,error);
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers=new HashMap<>();
                SharedPreferences sp=context.getSharedPreferences("LOGIN_CREDS",Context.MODE_PRIVATE);
                int uid=sp.getInt("uid",0);
                String uid_s=uid+"";
                headers.put("uid",uid_s);
                return headers;
            }
        };
        queue.add(getRequest);
    }

    public static void grantRequestAPI(int rid,final ErrorEntity.RCCRCI restClientInterface, final Context context){
        String API_URL=getAbsoluteURL("/grantRequest?rid="+rid);
        queue = VolleySingleton.getInstance(context).getRequestQueue();
        final Gson gson=new Gson();
        JsonBaseRequest getRequest = new JsonBaseRequest(Request.Method.GET, API_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ErrorEntity errorEntity=gson.fromJson(response.toString(),ErrorEntity.class);
                restClientInterface.onGetMessage(errorEntity,null);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try{
                    ErrorEntity errorEntity=gson.fromJson(new String(error.networkResponse.data),ErrorEntity.class);
                    CommonFunctions.toastString(errorEntity.getMessage(),context);
                    restClientInterface.onGetMessage(null,error);
                }catch (Exception e){
                    CommonFunctions.toastString(e.getMessage(),context);
                    restClientInterface.onGetMessage(null,error);
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers=new HashMap<>();
                SharedPreferences sp=context.getSharedPreferences("LOGIN_CREDS",Context.MODE_PRIVATE);
                int uid=sp.getInt("uid",0);
                String uid_s=uid+"";
                headers.put("uid",uid_s);
                return headers;
            }
        };
        queue.add(getRequest);
    }

    public static void returnRequestAPI(int rid,final ErrorEntity.RCCRCI restClientInterface, final Context context){
        String API_URL=getAbsoluteURL("/return?rid="+rid);
        queue = VolleySingleton.getInstance(context).getRequestQueue();
        final Gson gson=new Gson();
        JsonBaseRequest getRequest = new JsonBaseRequest(Request.Method.GET, API_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ErrorEntity errorEntity=gson.fromJson(response.toString(),ErrorEntity.class);
                restClientInterface.onGetMessage(errorEntity,null);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try{
                    ErrorEntity errorEntity=gson.fromJson(new String(error.networkResponse.data),ErrorEntity.class);
                    CommonFunctions.toastString(errorEntity.getMessage(),context);
                    restClientInterface.onGetMessage(null,error);
                }catch (Exception e){
                    CommonFunctions.toastString(e.getMessage(),context);
                    restClientInterface.onGetMessage(null,error);
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers=new HashMap<>();
                SharedPreferences sp=context.getSharedPreferences("LOGIN_CREDS",Context.MODE_PRIVATE);
                int uid=sp.getInt("uid",0);
                String uid_s=uid+"";
                headers.put("uid",uid_s);
                return headers;
            }
        };
        queue.add(getRequest);
    }

    public static void rejectAPI(UpdateRequestStatusEntity entity, final ErrorEntity.RCCRCI restClientInterface, final Context context){
        String API_URL=getAbsoluteURL("/updateRequestStatus");
        final Gson gs=new Gson();
        queue=VolleySingleton.getInstance(context).getRequestQueue();
        try{
            String jsonString=gs.toJson(entity);
            JSONObject postParams=new JSONObject(jsonString);
            JsonBaseRequest postRequest=new JsonBaseRequest(Request.Method.POST, API_URL, postParams, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    ErrorEntity errorEntity=gs.fromJson(response.toString(),ErrorEntity.class);
                    restClientInterface.onGetMessage(errorEntity,null);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try{
                        ErrorEntity errorEntity=gs.fromJson(new String(error.networkResponse.data),ErrorEntity.class);
                        CommonFunctions.toastString(errorEntity.getMessage(),context);
                        restClientInterface.onGetMessage(null,error);
                    }catch (Exception e){
                        CommonFunctions.toastString(e.getMessage(),context);
                        restClientInterface.onGetMessage(null,error);
                    }
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> headers=new HashMap<>();
                    SharedPreferences sp=context.getSharedPreferences("LOGIN_CREDS",Context.MODE_PRIVATE);
                    int uid=sp.getInt("uid",0);
                    String uid_s=uid+"";
                    headers.put("uid",uid_s);
                    return headers;
                }
            };
            queue.add(postRequest);
        }catch (Exception e){
            restClientInterface.onGetMessage(null,new VolleyError());
        }
    }

    public static void signUpAPI(UserSignupAPIEntity entity,final UserSignupAPIEntity.RCCRCI restClientInterface,final Context context){
        queue =VolleySingleton.getInstance(context).getRequestQueue();
        final Gson gson=new Gson();
        String API_URL=getAbsoluteURL("/createUserGeneric");
        try{
            String jsonString=gson.toJson(entity);
            JSONObject postParams=new JSONObject(jsonString);
            JsonBaseRequest postRequest=new JsonBaseRequest(Request.Method.POST, API_URL, postParams, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    ErrorEntity errorEntity=gson.fromJson(response.toString(),ErrorEntity.class);
                    restClientInterface.onSubmitDetails(errorEntity,null);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try{
                        ErrorEntity errorEntity=gson.fromJson(new String(error.networkResponse.data),ErrorEntity.class);
                        CommonFunctions.toastString(errorEntity.getMessage(),context);
                        restClientInterface.onSubmitDetails(null,error);
                    }catch (Exception e){
                        CommonFunctions.toastString(e.getMessage(),context);
                        restClientInterface.onSubmitDetails(null,error);
                    }
                }
            });
            queue.add(postRequest);
        }catch (Exception e){
            restClientInterface.onSubmitDetails(null,new VolleyError());
        }
    }
}
