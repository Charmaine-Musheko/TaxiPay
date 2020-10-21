package com.example.taxipay;

import android.Manifest;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerQR extends AppCompatActivity implements ZXingScannerView.ResultHandler{

        private ZXingScannerView scannerView;
        private TextView txtResult;

        @Override 
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            scannerView = (ZXingScannerView) findViewById(R.id.zxscan);
            txtResult = (TextView) findViewById(R.id.txt_result);

            Dexter.withActivity(this)
                    .withPermission(Manifest.permission.CAMERA)
                    .withListener(new PermissionListener() {

                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {
                            scannerView.setResultHandler(ScannerQR.this);
                            scannerView.startCamera();
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {
                            Toast.makeText(ScannerQR.this, "You must ask for permission", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                        }
                    }).check();
        }


        @Override
        protected void onDestroy() {
            scannerView.stopCamera();
            super.onDestroy();
        }

        @Override
        public void handleResult(Result rawResult) {
            processRawResult(rawResult.getText());
            scannerView.startCamera();

        }



        private void processRawResult(String text) {
            if(text.startsWith("BEGIN:"))
            {
                String[] tokens = text.split("\n");
                QRVcardModel qrVcardModel = new QRVcardModel();
                for (int i=0; i<tokens.length;i++) {
                    if (tokens[i].startsWith("BEGIN:"))
                    {
                        qrVcardModel.setType(tokens[i].substring("BEGINS:".length()));
                    }

                    else if(tokens[i].startsWith("N"))
                    {
                        qrVcardModel.setName(tokens[i].substring("BEGINS:".length()));
                    }
                    else if(tokens[i].startsWith("ORG"))
                    {
                        qrVcardModel.setOrg(tokens[i].substring("ORG:".length()));
                    }
                    else if(tokens[i].startsWith("TEL"))
                    {
                        qrVcardModel.setTel(tokens[i].substring("TEL:".length()));
                    }
                    else if(tokens[i].startsWith("URL"))
                    {
                        qrVcardModel.setUrl(tokens[i].substring("URL:".length()));
                    }
                    else if(tokens[i].startsWith("EMAIL"))
                    {
                        qrVcardModel.setEmail(tokens[i].substring("EMAIL:".length()));
                    }
                    else if(tokens[i].startsWith("AGE"))
                    {
                        qrVcardModel.setAge(tokens[i].substring("AGE:".length()));
                    }
                    else if(tokens[i].startsWith("NOTE"))
                    {
                        qrVcardModel.setNote(tokens[i].substring("NOTE:".length()));
                    }
                    else if(tokens[i].startsWith("SUMMARY"))
                    {
                        qrVcardModel.setSummary(tokens[i].substring("SUMMARY:".length()));
                    }
                    else if(tokens[i].startsWith("DTSTART"))
                    {
                        qrVcardModel.setDtstart(tokens[i].substring("DTSTART:".length()));
                    }
                    else if(tokens[i].startsWith("DTEND"))
                    {
                        qrVcardModel.setDtend(tokens[i].substring("DTEND:".length()));
                    }
                    txtResult.setText(qrVcardModel.getType());
                }
            }
            else if (text.startsWith("http://") ||
                    text.startsWith("http://") ||
                    text.startsWith("www.")) {
                QrTaxiModel qrTaxiModel = new QrTaxiModel(text);
                txtResult.setText(qrTaxiModel.getUrl());
            }
            else if (text.startsWith("geo"))
            {
                QRGeoModel qrGeoModel = new QRGeoModel();
                String delims = "[ , ?q ]+";
                String tokens[] = text.split(delims);

                for (int i=0; i<tokens.length; i++)
                {
                    if (tokens[i].startsWith("geo"))
                    {
                        qrGeoModel.setLat(tokens[i].substring("geo:".length()));
                    }

                }
                qrGeoModel.setLat(tokens[0].substring("geo:".length()));
                qrGeoModel.setLang(tokens[1]);
                qrGeoModel.setGeo_place(tokens[2]);

                txtResult.setText(qrGeoModel.getLat()+"/"+qrGeoModel.getLang());
            }
            else
            {
                txtResult.setText(text);
            }
            scannerView.resumeCameraPreview(this);
        }

    }

