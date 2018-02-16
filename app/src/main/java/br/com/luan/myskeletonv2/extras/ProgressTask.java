package br.com.luan.myskeletonv2.extras;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import br.com.luan.myskeletonv2.R;
import br.com.luan.myskeletonv2.view.ui.FindMovie.DetalhesProdutosActivity;
import br.com.luan.myskeletonv2.view.ui.FindMovie.Filme;

/**
 * Created by luan on 08/04/2017.
 */

//
//Essa classe serve para que o progress dialog funcione enquanto chamo a intent do Youtube,
//assim o usuario não fica sem resposta na tela

public class ProgressTask extends AsyncTask<Void,Void,Boolean> {

    private ProgressDialog dialog;
    private DetalhesProdutosActivity activity;
    private Context context;
    private Filme filme;

    public ProgressTask(DetalhesProdutosActivity activity, Filme filme) {
        this.activity = activity;
        this.context = activity;
        dialog = new ProgressDialog(context, R.style.AppTheme_AlertDialog);
        this.filme = filme;
    }

    @Override
    protected void onPreExecute() {
        dialog.setCancelable(false);
        dialog.setMessage("Aguarde um momento...");
        //dialog.show();
        Toast.makeText(activity, "Aguarde um momento...", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if(dialog.isShowing()){
            dialog.dismiss();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEARCH);
            intent.setPackage("com.google.android.youtube");
            intent.putExtra("query", filme.getOriginalTitle() + " trailer");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
