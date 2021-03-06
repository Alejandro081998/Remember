package com.frgp.remember.GestionImagen;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.frgp.remember.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ViewImage extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextId;
    private Button buttonGetImage;
    private ImageView imageView;

    private RequestHandler requestHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_imagen);

        editTextId = findViewById(R.id.editTextId);
        buttonGetImage =  findViewById(R.id.buttonGetImage);
        imageView = findViewById(R.id.imageViewShow);

        requestHandler = new RequestHandler();

        buttonGetImage.setOnClickListener(this);
    }


    private void getImage() {
        String id = editTextId.getText().toString().trim();
        class GetImage extends AsyncTask<String,Void,Bitmap>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewImage.this, "Uploading...", null,true,true);
            }

            @Override
            protected void onPostExecute(Bitmap b) {
                super.onPostExecute(b);
                loading.dismiss();
                imageView.setImageBitmap(b);
            }

            @Override
            protected Bitmap doInBackground(String... params) {
                String id = params[0];
                String add = "http://femina.webcindario.com/getImageR.php?id="+id;
                URL url = null;
                Bitmap image = null;
                try {
                    url = new URL(add);
                    image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return image;
            }
        }

        GetImage gi = new GetImage();
        gi.execute(id);
    }

    @Override
    public void onClick(View v) {
        getImage();
    }
}
