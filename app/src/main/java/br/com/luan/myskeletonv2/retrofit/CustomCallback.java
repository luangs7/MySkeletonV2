package br.com.luan.myskeletonv2.retrofit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import br.com.luan.myskeletonv2.R;
import br.com.luan.myskeletonv2.core.MyApplication;
import br.com.luan.myskeletonv2.data.model.BaseRequest;
import retrofit2.Call;
import retrofit2.Callback;


/**
 * Created by luan on 7/11/16.
 */
public  class CustomCallback<T> implements Callback<T> {
    Context context;
    ProgressDialog dialog;
    OnResponse onResponse;
    View viewLayout;

    public CustomCallback(Context context,OnResponse<T> onResponse){
        this.context = context;
        dialog = new ProgressDialog(context, R.style.AppTheme_AlertDialog);
        dialog.setCancelable(false);
        dialog.setMessage("Buscando dados...");
        dialog.show();
        this.onResponse = onResponse;
    }

        public CustomCallback(Context context, View viewLayout,OnResponse<T> onResponse){
        this.context = context;
        dialog = new ProgressDialog(context, R.style.AppTheme_AlertDialog);
            dialog.setCancelable(false);
            dialog.setMessage("Buscando dados...");
            dialog.show();
        this.onResponse = onResponse;
        this.viewLayout = viewLayout;
    }

    public CustomCallback(Activity context, OnResponse<T> onResponse, View viewLayout){
        this(context,onResponse,true);
        this.viewLayout = viewLayout;
    }

    public CustomCallback(Activity context, OnResponse<T> onResponse, boolean loadDialog){
        this.context = context;
        if(loadDialog){
            try {
                dialog = new ProgressDialog(context, R.style.AppTheme_AlertDialog);
                dialog.setCancelable(false);
                dialog.setMessage("Buscando dados...");
                dialog.show();
            }catch (Throwable ex){
                Log.e("dialog", "CustomCallback: ",ex);
            }
        }else{

        }

        this.onResponse = onResponse;
    }

    public CustomCallback(Context context, OnResponse<T> onResponse, boolean loadDialog){
        this.context = context;
        if(loadDialog){
            try {
                dialog = new ProgressDialog(context, R.style.AppTheme_AlertDialog);
                dialog.setCancelable(false);
                dialog.setMessage("Buscando dados...");
                dialog.show();
            }catch (Throwable ex){
                Log.e("dialog", "CustomCallback: ",ex);
            }
        }else{

        }

        this.onResponse = onResponse;
    }



    public CustomCallback(Context context,String dialogText,OnResponse<T> onResponse) {
        this.context = context;
        dialog = new ProgressDialog(context, R.style.AppTheme_AlertDialog);
        dialog.setCancelable(false);
        dialog.setMessage(dialogText);
        dialog.show();
        this.onResponse = onResponse;
    }

    public CustomCallback(Activity context,String dialogText,OnResponse<T> onResponse) {
        this.context = context;
        dialog = new ProgressDialog(context, R.style.AppTheme_AlertDialog);
        dialog.setCancelable(false);
        dialog.setMessage(dialogText);
        dialog.show();
        this.onResponse = onResponse;
    }




    public CustomCallback(Context context,String dialogText, View viewLayout,OnResponse<T> onResponse) {
        this.context = context;
        dialog = new ProgressDialog(context, R.style.AppTheme_AlertDialog);
        dialog.setCancelable(false);
        dialog.setMessage(dialogText);
        dialog.show();
        this.onResponse = onResponse;
        this.viewLayout = viewLayout;
    }


    @Override
    public void onResponse(Call<T> call, retrofit2.Response<T> response) {
        String error = "";
    try {
        dialog.dismiss();
    }catch (Exception e){

    }
        if(response.isSuccessful())
            onResponse.onResponse(response.body());
        else {
            if (response.code() == 202) {
                try {
                    onResponse.onResponse(response.body());
                } catch (Exception ex) {
                    try {
                        onResponse.onFailure(new Throwable(new Gson().fromJson(error, BaseRequest.class).getMessage()));
                    } catch (Exception ex2) {
                        onResponse.onFailure(new Throwable(context.getResources().getString(R.string.error401)));
                    }
                }
            }
            if (response.code() == 401) {
                try {
                    error = response.errorBody().string();
                    onResponse.onFailure(new Throwable(new Gson().fromJson(error, BaseRequest.class).getMessage()));
                } catch (Exception ex) {
                    try {
                        onResponse.onFailure(new Throwable(new Gson().fromJson(error, BaseRequest.class).getMessage()));
                    } catch (Exception ex2) {
                        onResponse.onFailure(new Throwable(context.getResources().getString(R.string.error401)));
                    }
                }
            }
            else if (response.code() == 404) {
                try {
                    error = response.errorBody().string();
                    onResponse.onResponse(new Gson().fromJson(error, BaseRequest.class));
                } catch (Exception ex) {
                    try {
                        onResponse.onFailure(new Throwable(new Gson().fromJson(error, BaseRequest.class).getMessage()));
                    } catch (Exception ex2) {
                        onResponse.onFailure(new Throwable(context.getResources().getString(R.string.error404)));
                    }
                }
            }
            else if (response.code() == 422) {
                try {
                    error = response.errorBody().string();
                    String message = "";

                    JSONObject jsnobject = new JSONObject(error);
                    JSONArray jsonArray = jsnobject.getJSONArray("reason");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        message = jsonArray.get(i).toString();
                    }
                    onResponse.onFailure(new Throwable(message));
                } catch (Exception ex) {
                    try {
                        onResponse.onFailure(new Throwable(new Gson().fromJson(error, BaseRequest.class).getMessage()));
                    } catch (Exception ex2) {
                        onResponse.onFailure(new Throwable("Ocorreu um erro"));
                    }
                }
            }
            else if(response.code() == 500){
                try {
                    onResponse.onFailure(new Throwable(context.getResources().getString(R.string.error500)));
                } catch (Exception ex) {
                    try {
                        onResponse.onFailure(new Throwable(new Gson().fromJson(error, BaseRequest.class).getMessage()));
                    } catch (Exception ex2) {
                        onResponse.onFailure(new Throwable("Ocorreu um erro"));
                    }
                }
            }
            else if(response.code() == 429){
                try {
                    onResponse.onRetry(new Throwable(context.getResources().getString(R.string.error401)));
                } catch (Exception ex) {
                    try {
                        onResponse.onFailure(new Throwable(new Gson().fromJson(error, BaseRequest.class).getMessage()));
                    } catch (Exception ex2) {
                        onResponse.onFailure(new Throwable("Ocorreu um erro"));
                    }
                }
            }

            else {
                try {
                    error = response.errorBody().string();
                    BaseRequest errorBody = new Gson().fromJson(error,BaseRequest.class);
                    onResponse.onFailure(new Throwable(errorBody.getMessage()));
                } catch (Exception ex) {
                    onResponse.onFailure(new Throwable("Ocorreu um erro"));
                }
            }
        }
    }

    @Override
    public void onFailure(Call<T> call, final Throwable t) {
        if(dialog != null)
            dialog.dismiss();
        if(viewLayout != null){
            Snackbar snackbar = Snackbar
                .make(viewLayout, context.getResources().getString(R.string.noConnect), Snackbar.LENGTH_LONG)
                    .setAction("Tentar novamente", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onResponse.onRetry(t);
                        }
                    });
                snackbar.show();
        }else {
            //Cria o gerador do AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppTheme_AlertDialog);
            //define o titulo
                builder.setTitle(context.getResources().getString(R.string.noConnect));
            //define a mensagem
            builder.setMessage("Gostaria de tentar novamente?");
            //define um botão como positivo
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.dismiss();
                    onResponse.onRetry(t);
                }
            });
            //define um botão como negativo.
            builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.dismiss();
                    if(MyApplication.Companion.isNetworkConnected(context))
                        onResponse.onFailure(t);
                    else
                        onResponse.onFailure(new Throwable(context.getResources().getString(R.string.noConnect)));
                }
            });
            //cria o AlertDialog
            AlertDialog alerta = builder.create();
            //Exibe
            alerta.show();
        }
    }

    public void useSnackBar(View view){
        viewLayout = view;
    }

    public interface OnResponse<T>{
        public void onResponse(T response);
        public void onFailure(Throwable t);
        public void onRetry(Throwable t);
    }




}