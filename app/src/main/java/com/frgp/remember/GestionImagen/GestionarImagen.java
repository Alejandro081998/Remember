package com.frgp.remember.GestionImagen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.frgp.remember.Principal.MainActivity;
import com.frgp.remember.R;
import com.frgp.remember.Session.Session;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;



public class GestionarImagen extends AppCompatActivity implements View.OnClickListener {

    public static final String UPLOAD_URL = "http://femina.webcindario.com/uploadR.php";
    public static final String UPLOAD_KEY = "image";
    public static String ID_USUARIO = "";
    public static final String TAG = "MY MESSAGE";

    private int PICK_IMAGE_REQUEST = 1;

    private Button buttonChoose;
    private Button buttonUpload;
    private Button buttonView;

    private ImageView imageView;

    private Bitmap bitmap;

    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gestion_foto);

        //buttonChoose = findViewById(R.id.buttonChoose);
        //buttonUpload =  findViewById(R.id.buttonUpload);
        //buttonView =  findViewById(R.id.buttonViewImage);

        imageView =  findViewById(R.id.imageView);

        //buttonChoose.setOnClickListener(this);
        //buttonUpload.setOnClickListener(this);
        //buttonView.setOnClickListener(this);

        showFileChooser();
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
                uploadImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp){
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bmp, 350 /*Ancho*/, 300 /*Alto*/, false /* filter*/);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

        /*ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] imageBytes = baos.toByteArray();

        int ancho = 100;
        int alto = 100;

        Bitmap foto = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
        bmp = Bitmap.createScaledBitmap(foto,alto,ancho,true);*/


    }

    private void uploadImage(){
        class UploadImage extends AsyncTask<Bitmap,Void,String>{

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(GestionarImagen.this, "Uploading...", null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();

                Intent intent = new Intent(GestionarImagen.this, MainActivity.class);
                GestionarImagen.this.startActivity(intent);

            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                Log.d("ALTO:","" + bitmap.getHeight());
                Log.d("ANCHO:","" + bitmap.getWidth());
                String uploadImage = getStringImage(bitmap);

                HashMap<String,String> data = new HashMap<>();
                data.put(UPLOAD_KEY, uploadImage);
                Session ses = new Session();
                ses.setCt(GestionarImagen.this);
                ses.cargar_session();
                ID_USUARIO = "" + ses.getId_usuario();
                Log.d("IDUSUARIO",ID_USUARIO);
                data.put("idusuario",ID_USUARIO);

                String result = rh.sendPostRequest(UPLOAD_URL,data);

                return result;
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }

    @Override
    public void onClick(View v) {
        if (v == buttonChoose) {
            showFileChooser();
        }
        if(v == buttonUpload){
            uploadImage();
        }
        if(v == buttonView){
            viewImage();
        }
    }

    private void viewImage() {
        startActivity(new Intent(this, ViewImage.class));
    }
}