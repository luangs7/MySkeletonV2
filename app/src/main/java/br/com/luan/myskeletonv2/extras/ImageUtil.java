package br.com.luan.myskeletonv2.extras;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DevMaker on 4/14/16.
 */
public class ImageUtil {

    // Scale and maintain aspect ratio given a desired width
    // BitmapScaler.scaleToFitWidth(bitmap, 100);
    public static Bitmap scaleToFitWidth(Bitmap b, int width)
    {
        float factor = width / (float) b.getWidth();
        if(b.getWidth() > width){
            return Bitmap.createScaledBitmap(b, width, (int) (b.getHeight() * factor), true);
        }else{
            return Bitmap.createBitmap(b);
        }

    }


    // Scale and maintain aspect ratio given a desired height
    // BitmapScaler.scaleToFitHeight(bitmap, 100);
    public static Bitmap scaleToFitHeight(Bitmap b, int height)
    {
        float factor = height / (float) b.getHeight();
        if(b.getHeight() > height){
            return Bitmap.createScaledBitmap(b, (int) (b.getWidth() * factor), height, true);
        }else{
            return Bitmap.createBitmap(b);
        }
    }

    public static String getBase64FromBitmap(Bitmap bitmap) {
        bitmap = scaleToFitWidth(bitmap,600);// getResizedBitmap(bitmap);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);

        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String baseImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

        Log.d("tamanho string Base64", String.valueOf(baseImage.getBytes().length));

        return baseImage;
    }


    public static String getBase64FromBitmap(Bitmap bitmap, boolean resize) {
        bitmap = scaleToFitWidth(bitmap,100);// getResizedBitmap(bitmap);
        bitmap = scaleToFitHeight(bitmap,75);// getResizedBitmap(bitmap);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);

        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String baseImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

        Log.d("tamanho string Base64", String.valueOf(baseImage.getBytes().length));

        return baseImage;
    }

    public static void getBase64FromBitmap(Context context, List<Bitmap> bitmap, ProcessBase64 processBase64) {
        task(context,bitmap,processBase64);
    }

    public static void getBase64FromBitmap(Context context, Bitmap bitmap, ProcessBase64 processBase64) {
        task(context,bitmap,processBase64);
    }


    private static void task(final Context context, final List<Bitmap> bitmap, final ProcessBase64 processBase64){
        AsyncTask<Void, String, List<String>> task = new AsyncTask<Void, String, List<String>>() {
            private boolean success = false;
            ProgressDialog dialog;

            @Override
            protected void onPreExecute() {
                dialog = new ProgressDialog(context);
                dialog.setMessage("Processando dados");
                dialog.setCancelable(false);
                dialog.setIndeterminate(true);
                dialog.show();
            }

            @Override
            protected List<String> doInBackground(Void... arg0) {
                List<String> strings = new ArrayList<>();

                try {
                    for (int i = 0; i < bitmap.size(); i++) {
                        strings.add(getBase64FromBitmap(bitmap.get(i)));
                    }
                    return strings;
                }catch (Exception ex)
                {
                    return strings;
                }
            }

            @Override
            protected void onPostExecute(List<String> result) {
                if (dialog!=null) {
                    dialog.dismiss();
                }
                if(result != null){
                    try {
                        for (int i = 0; i < bitmap.size(); i++) {
                            bitmap.get(i).recycle();
                        }
                        System.gc();
                    }catch (Exception ex){

                    }
                }
                processBase64.onProcessBase64(result);
            }
        };
        task.execute((Void[])null);
    }

    private static void task(final Context context, final Bitmap bitmap, final ProcessBase64 processBase64){
        AsyncTask<Void, String, String> task = new AsyncTask<Void, String, String>() {
            private boolean success = false;
            ProgressDialog dialog;

            @Override
            protected void onPreExecute() {
                dialog = new ProgressDialog(context);
                dialog.setMessage("Processando dados");
                dialog.setCancelable(false);
                dialog.setIndeterminate(true);
                dialog.show();
            }

            @Override
            protected String doInBackground(Void... arg0) {
                try {
                    return getBase64FromBitmap(bitmap);
                }catch (Exception ex)
                {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                if (dialog!=null) {
                    dialog.dismiss();
                }
                if(result != null){
                    try {
                        bitmap.recycle();
                        System.gc();
                    }catch (Exception ex){

                    }
                }
                processBase64.onProcessBase64(result);
            }
        };
        task.execute((Void[])null);
    }


    public interface ProcessBase64
    {
        public void onProcessBase64(List<String> base64);
        public void onProcessBase64(String base64);
    }

    public static File savebitmap(String filename) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        OutputStream outStream = null;

        File file = new File(filename + ".png");
        if (file.exists()) {
            file.delete();
            file = new File(extStorageDirectory, filename + ".png");
            Log.e("file exist", "" + file + ",Bitmap= " + filename);
        }
        try {
            // make a new bitmap from your file
            Bitmap bitmap = BitmapFactory.decodeFile(file.getName());

            outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("file", "" + file);
        return file;

    }

    public static File createImageFile(
            Context context,
            String dirName,
            String fileName,
            String fileType) {
        try {
            File file = createDir(context, dirName);
            File image = new File(file.getAbsoluteFile() + File.separator + fileName + fileType);
            image.createNewFile();
            return image;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param context
     * @param dirName
     * @return
     */
    public static File createDir(
            Context context,
            String dirName){
        File file = new File(context.getExternalFilesDir(null) + File.separator + dirName);
        if(!file.exists()){
            file.mkdir();
        }
        return file;
    }

    /**
     *
     * @param file
     * @param requiredHeight
     * @return
     */
    public static Bitmap decodeFile(File file, int requiredHeight) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(file), null, o);

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while(o.outWidth / scale / 2 >= requiredHeight &&
                    o.outHeight / scale / 2 >= requiredHeight) {
                scale *= 2;
            }
            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(file), null, o2);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param bitmap
     * @param filePath
     * @param imageType
     * @param compression
     */
    public static void saveBitmap(Bitmap bitmap, String filePath, String imageType, int compression){

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filePath);
            // PNG is a loss less format, the compression factor (100) is ignored
            if(imageType.equals("png") || imageType.equals("PNG") || imageType.equals(".png")){
                bitmap.compress(Bitmap.CompressFormat.PNG, compression, out);
            }
            else if(imageType.equals("jpg") || imageType.equals("JPG") || imageType.equals(".jpg")){
                bitmap.compress(Bitmap.CompressFormat.JPEG, compression, out);
            }
            else if(imageType.equals("jpeg") || imageType.equals("JPEG") || imageType.equals(".jpeg")){
                bitmap.compress(Bitmap.CompressFormat.JPEG, compression, out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
            }
        }
    }


    public static Bitmap fixOrientation(Bitmap mBitmap) {
        if (mBitmap.getWidth() > mBitmap.getHeight()) {
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            mBitmap = Bitmap.createBitmap(mBitmap , 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);
        }
        return  mBitmap;
    }


}
