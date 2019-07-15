package ir.FiroozehCorp.GameService.UnityPackage.Utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import ir.FiroozehCorp.GameService.UnityPackage.App.Models.SysInfo;
import ir.FiroozehCorp.GameService.UnityPackage.Native.Contrast.URLs;
import ir.FiroozehCorp.GameService.UnityPackage.Native.Handlers.UnityGameServiceNative;
import ir.FiroozehCorp.GameService.UnityPackage.Native.Interfaces.JsonArrayCallbackListener;
import ir.FiroozehCorp.GameService.UnityPackage.Native.Interfaces.JsonObjectCallbackListener;

import static ir.FiroozehCorp.GameService.UnityPackage.Native.Handlers.UnityGameServiceNative.PT;

public final class ApiRequestUtil {


    private static final String TAG = "ApiRequestUtil";

    public static void loginUser (
            Context context
            , String email
            , String pass
            , final JsonObjectCallbackListener listener) {

        HashMap<String, String> params = new HashMap<>();
        params.put("mode", "login");
        params.put("email", email);
        params.put("password", pass);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST
                , URLs.LoginUser, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse (JSONObject jsonObject) {
                listener.onResponse(jsonObject);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse (VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    try {
                        JSONObject object = new JSONObject(new String(volleyError.networkResponse.data));
                        listener.onError(object.getString("msg"));
                    } catch (Exception ignored) {
                        listener.onError("Exception : " + ignored.toString());
                    }
                }
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(0
                , DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);

        Volley.newRequestQueue(context).add(request);

    }

    public static void registerUser (
            Context context
            , String UserName
            , String email
            , String pass
            , final JsonObjectCallbackListener listener) {

        HashMap<String, String> params = new HashMap<>();
        params.put("mode", "register");
        params.put("email", email);
        params.put("password", pass);
        params.put("name", UserName);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST
                , URLs.LoginUser, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse (JSONObject jsonObject) {
                listener.onResponse(jsonObject);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse (VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    try {
                        JSONObject object = new JSONObject(new String(volleyError.networkResponse.data));
                        listener.onError(object.getString("msg"));
                    } catch (Exception e) {
                        listener.onError("Exception : " + e.toString());
                    }
                } else listener.onError("ServerError");
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(0
                , DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);

        Volley.newRequestQueue(context).add(request);

    }

    public static void UpdatePlayTokenAsync (
            final Activity activity,
            String clientId,
            String clientSecret,
            SysInfo sysInfo,
            final JsonObjectCallbackListener listener
    ) {


        HashMap<String, String> params = new HashMap<>();
        params.put("token", NativeUtil.GetJWT(activity));
        params.put("game", clientId);
        params.put("secret", clientSecret);
        params.put("system_info", sysInfo.ToJSON());

        Log.e(TAG, new JSONObject(params).toString());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST
                , URLs.Start, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse (JSONObject jsonObject) {
                listener.onResponse(jsonObject);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse (VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    try {
                        JSONObject object = new JSONObject(new String(volleyError.networkResponse.data));
                        listener.onError(object.getString("msg"));
                    } catch (Exception e) {
                        listener.onError("Exception : " + e.toString());
                    }
                } else listener.onError("ServerError");
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(0
                , DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);

        Volley.newRequestQueue(activity).add(request);
    }


    public static void getUserData (
            final Activity activity
            , final JsonObjectCallbackListener listener) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET
                , URLs.UserData, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse (JSONObject jsonObject) {
                listener.onResponse(jsonObject);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse (VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    try {
                        JSONObject object = new JSONObject(new String(volleyError.networkResponse.data));
                        listener.onError(object.getString("msg"));
                    } catch (Exception ignored) {
                    }
                } else listener.onError("ServerError");
            }
        }) {
            @Override
            public Map<String, String> getHeaders () {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("x-access-token", PT);
                return headers;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(0
                , DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);

        Volley.newRequestQueue(activity).add(request);
    }


    public static void removeUserSaveFile (
            final Activity activity
            , final JsonObjectCallbackListener listener) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE
                , URLs.DeleteLastSave, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse (JSONObject jsonObject) {
                listener.onResponse(jsonObject);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse (VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    try {
                        JSONObject object = new JSONObject(new String(volleyError.networkResponse.data));
                        listener.onError(object.getString("msg"));
                    } catch (Exception ignored) {
                    }
                } else listener.onError("ServerError");
            }
        }) {
            @Override
            public Map<String, String> getHeaders () {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("x-access-token", PT);
                return headers;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(0
                , DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);

        Volley.newRequestQueue(activity).add(request);
    }


    public static void GetAchievement (
            final Activity activity
            , final JsonArrayCallbackListener listener) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET
                , URLs.GetAchievements, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse (JSONObject jsonObject) {
                try {
                    listener.onResponse(jsonObject.getJSONArray("achievement"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse (VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    try {
                        JSONObject object = new JSONObject(new String(volleyError.networkResponse.data));
                        listener.onError(object.getString("msg"));
                    } catch (Exception ignored) {
                    }
                } else listener.onError("ServerError");
            }
        }) {
            @Override
            public Map<String, String> getHeaders () {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("x-access-token", PT);
                return headers;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(0
                , DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);

        Volley.newRequestQueue(activity).add(request);
    }

    public static void EarnAchievement (
            final Activity activity
            , final String ID
            , final JsonObjectCallbackListener listener) {

        HashMap<String, String> params = new HashMap<>();
        params.put("token", PT);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST
                , URLs.EarnAchievement + ID, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse (JSONObject jsonObject) {
                listener.onResponse(jsonObject);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse (VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    try {
                        JSONObject object = new JSONObject(new String(volleyError.networkResponse.data));
                        listener.onError(object.getString("msg"));
                    } catch (Exception ignored) {
                    }
                } else listener.onError("ServerError");
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(0
                , DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);

        Volley.newRequestQueue(activity).add(request);
    }


    public static void SubmitScore (
            final Activity activity
            , final String AchievementID
            , final int Score
            , final JsonObjectCallbackListener listener) {

        HashMap<String, Integer> params = new HashMap<>();
        params.put("value", Score);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST
                , URLs.SubmitScore + AchievementID, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse (JSONObject jsonObject) {
                listener.onResponse(jsonObject);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse (VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    try {
                        JSONObject object = new JSONObject(new String(volleyError.networkResponse.data));
                        listener.onError(object.getString("msg"));
                    } catch (Exception ignored) {
                    }
                } else listener.onError("ServerError");
            }
        }) {
            @Override
            public Map<String, String> getHeaders () {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("x-access-token", PT);
                return headers;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(0
                , DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);

        Volley.newRequestQueue(activity).add(request);
    }


    public static void SaveGameData (
            final Activity activity
            , final String SaveGameName
            , final String SaveGameDes
            , final String CoverBase64
            , final String SaveGameJSON
            , final JsonObjectCallbackListener listener) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("name", SaveGameName);
        params.put("desc", SaveGameDes);
        params.put("playedtime", Math.abs(UnityGameServiceNative.StartTime - System.currentTimeMillis()));
        params.put("cover", CoverBase64);
        params.put("data", SaveGameJSON);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST
                , URLs.SetSavegame, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse (JSONObject jsonObject) {
                listener.onResponse(jsonObject);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse (VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    try {
                        JSONObject object = new JSONObject(new String(volleyError.networkResponse.data));
                        listener.onError(object.getString("msg"));
                    } catch (Exception ignored) {
                    }
                } else listener.onError("ServerError");
            }
        }) {
            @Override
            public Map<String, String> getHeaders () {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("x-access-token", PT);
                return headers;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(0
                , DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);

        Volley.newRequestQueue(activity).add(request);
    }

    public static void GetGameData (
            final Activity activity
            , final JsonObjectCallbackListener listener) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET
                , URLs.GetSavegame, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse (JSONObject jsonObject) {
                listener.onResponse(jsonObject);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse (VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    try {
                        JSONObject object = new JSONObject(new String(volleyError.networkResponse.data));
                        listener.onError(object.getString("msg"));
                    } catch (Exception ignored) {
                    }
                } else listener.onError("ServerError");
            }
        }) {
            @Override
            public Map<String, String> getHeaders () {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("x-access-token", PT);
                return headers;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(0
                , DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);

        Volley.newRequestQueue(activity).add(request);
    }


    public static void GetLeaderBoards (
            final Activity activity
            , final JsonObjectCallbackListener listener) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET
                , URLs.GetLeaderboard, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse (JSONObject jsonObject) {
                listener.onResponse(jsonObject);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse (VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    try {
                        JSONObject object = new JSONObject(new String(volleyError.networkResponse.data));
                        listener.onError(object.getString("msg"));
                    } catch (Exception ignored) {
                    }
                } else listener.onError("ServerError");
            }
        }) {
            @Override
            public Map<String, String> getHeaders () {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("x-access-token", PT);
                return headers;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(0
                , DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);

        Volley.newRequestQueue(activity).add(request);
    }

    public static void GetLeaderBoardData (
            final Activity activity
            , final String ID
            , final JsonObjectCallbackListener listener) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET
                , URLs.GetLeaderboard + ID, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse (JSONObject jsonObject) {
                listener.onResponse(jsonObject);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse (VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    try {
                        JSONObject object = new JSONObject(new String(volleyError.networkResponse.data));
                        listener.onError(object.getString("msg"));
                    } catch (Exception ignored) {
                    }
                } else listener.onError("ServerError");
            }
        }) {
            @Override
            public Map<String, String> getHeaders () {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("x-access-token", PT);
                return headers;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(0
                , DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);

        Volley.newRequestQueue(activity).add(request);
    }


    public static void getDataPackInfo (
            final Activity activity
            , final String GameName
            , final String tag
            , final JsonObjectCallbackListener listener) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET
                , URLs.BaseURL + "/game/" + GameName + "/datapack/?tag=" + tag, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse (JSONObject jsonObject) {
                listener.onResponse(jsonObject);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse (VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    try {
                        JSONObject object = new JSONObject(new String(volleyError.networkResponse.data));
                        listener.onError(object.getString("msg"));
                    } catch (Exception ignored) {
                    }
                } else listener.onError("ServerError");
            }
        }) {
            @Override
            public Map<String, String> getHeaders () {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("x-access-token", PT);
                return headers;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(0
                , DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);

        Volley.newRequestQueue(activity).add(request);
    }


    public static void GetAllBucketDataByID (
            final Activity activity
            , final String BucketID
            , final JsonArrayCallbackListener listener) {

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET
                , URLs.Bucket + BucketID, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse (JSONArray array) {
                listener.onResponse(array);
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse (VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    try {
                        JSONObject object = new JSONObject(new String(volleyError.networkResponse.data));
                        listener.onError(object.getString("msg"));
                    } catch (Exception ignored) {
                    }
                } else listener.onError("ServerError");
            }
        }) {
            @Override
            public Map<String, String> getHeaders () {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("x-access-token", PT);
                return headers;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(0
                , DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);

        Volley.newRequestQueue(activity).add(request);
    }


    public static void GetOneBucketDataByID (
            final Activity activity
            , final String BucketID
            , final String DataID
            , final JsonObjectCallbackListener listener) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET
                , URLs.Bucket + BucketID + '/' + DataID, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse (JSONObject object) {
                listener.onResponse(object);
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse (VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    try {
                        JSONObject object = new JSONObject(new String(volleyError.networkResponse.data));
                        listener.onError(object.getString("msg"));
                    } catch (Exception ignored) {
                    }
                } else listener.onError("ServerError");
            }
        }) {
            @Override
            public Map<String, String> getHeaders () {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("x-access-token", PT);
                return headers;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(0
                , DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);

        Volley.newRequestQueue(activity).add(request);
    }


    public static void UpdateOneBucketDataByID (
            final Activity activity
            , final String BucketID
            , final String DataID
            , final String BucketJSON
            , final JsonObjectCallbackListener listener) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT
                , URLs.Bucket + BucketID + '/' + DataID, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse (JSONObject object) {
                listener.onResponse(object);
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse (VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    try {
                        JSONObject object = new JSONObject(new String(volleyError.networkResponse.data));
                        listener.onError(object.getString("msg"));
                    } catch (Exception ignored) {
                    }
                } else listener.onError("ServerError");
            }
        }) {
            @Override
            public Map<String, String> getHeaders () {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("x-access-token", PT);
                return headers;
            }

            @Override
            public byte[] getBody () {
                try {
                    return BucketJSON.getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    return null;
                }
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(0
                , DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);

        Volley.newRequestQueue(activity).add(request);
    }


    public static void AddOneBucketByID (
            final Activity activity
            , final String BucketID
            , final String BucketJSON
            , final JsonObjectCallbackListener listener) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST
                , URLs.Bucket + BucketID, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse (JSONObject object) {
                listener.onResponse(object);
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse (VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    try {
                        JSONObject object = new JSONObject(new String(volleyError.networkResponse.data));
                        listener.onError(object.getString("msg"));
                    } catch (Exception ignored) {
                    }
                } else listener.onError("ServerError");
            }
        }) {
            @Override
            public Map<String, String> getHeaders () {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("x-access-token", PT);
                return headers;
            }

            @Override
            public byte[] getBody () {
                try {
                    return BucketJSON.getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    return null;
                }
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(0
                , DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);

        Volley.newRequestQueue(activity).add(request);
    }


    public static void DeleteOneBucketByID (
            final Activity activity
            , final String BucketID
            , final String ID
            , final JsonObjectCallbackListener listener) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE
                , URLs.Bucket + BucketID + '/' + ID, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse (JSONObject object) {
                listener.onResponse(object);
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse (VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    try {
                        JSONObject object = new JSONObject(new String(volleyError.networkResponse.data));
                        listener.onError(object.getString("msg"));
                    } catch (Exception ignored) {
                    }
                } else listener.onError("ServerError");
            }
        }) {
            @Override
            public Map<String, String> getHeaders () {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("x-access-token", PT);
                return headers;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(0
                , DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);

        Volley.newRequestQueue(activity).add(request);
    }


    public static void DeleteAllBucketByID (
            final Activity activity
            , final String BucketID
            , final JsonObjectCallbackListener listener) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE
                , URLs.Bucket + BucketID, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse (JSONObject object) {
                listener.onResponse(object);
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse (VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    try {
                        JSONObject object = new JSONObject(new String(volleyError.networkResponse.data));
                        listener.onError(object.getString("msg"));
                    } catch (Exception ignored) {
                    }
                } else listener.onError("ServerError");
            }
        }) {
            @Override
            public Map<String, String> getHeaders () {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("x-access-token", PT);
                return headers;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(0
                , DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);

        Volley.newRequestQueue(activity).add(request);
    }

}
