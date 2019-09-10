package com.team.smart.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.team.smart.R;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParkingUserCarInfo extends Activity {

    RelativeLayout addLayout,resultLayout;
    ImageView resultIV;
    Button reSelectBtn;
    EditText carNumEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_user_car_info);
        resultIV = findViewById(R.id.resultIV);
        resultLayout= findViewById(R.id.resultLayout);
        reSelectBtn = findViewById(R.id.reSelectBtn);
        carNumEdit = findViewById(R.id.carNumEdit);
        //뒤로가기
        ImageView backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //차량 추가 클릭시
        addLayout = findViewById(R.id.addLayout);
        addLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertImageDialog();
            }
        });
        //제선택 클릭시
        reSelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertImageDialog();
            }
        });
    }
    private void alertImageDialog(){
        final CharSequence[] items = { "카메라로 가져오기", "갤러리에서 가져오기"};
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ParkingUserCarInfo.this);
        // 제목셋팅
        alertDialogBuilder.setTitle("차량 번호판 사진 가져오기");
        alertDialogBuilder.setItems(items,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // 프로그램을 종료한다
                        Toast.makeText(getApplicationContext(), items[id] + " 선택했습니다.", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        //권한 요청
                        tedPermission();
                        if (id ==0){
                            takePhoto();
                        }else{
                            goToAlbum();
                        }
                    }
                });

        // 다이얼로그 생성
        AlertDialog alertDialog = alertDialogBuilder.create();

        // 다이얼로그 보여주기
        alertDialog.show();
    }
    //권한 요청
    private void tedPermission() {

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                // 권한 요청 성공
                Log.d("권한요청~~~~~~~~~~~:","성공");
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                // 권한 요청 실패
                Log.d("권한요청~~~~~~~~~~~:","실패");
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getResources().getString(R.string.permission_2))
                .setDeniedMessage(getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();

    }
    //앨범에서 이미지 가져오기
    private static final int PICK_FROM_ALBUM = 1;
    private void goToAlbum() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    private File tempFile;//이미지저장용
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != Activity.RESULT_OK) {

            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();

            if(tempFile != null) {
                if (tempFile.exists()) {
                    if (tempFile.delete()) {
                        Log.e("삭제요청~~~~~:", tempFile.getAbsolutePath() + " 삭제 성공");
                        tempFile = null;
                    }
                }
            }

            return;
        }

        if (requestCode == PICK_FROM_ALBUM) {

            Uri photoUri = data.getData();

            Cursor cursor = null;

            try {

                /*
                 *  Uri 스키마를
                 *  content:/// 에서 file:/// 로  변경한다.
                 */
                String[] proj = { MediaStore.Images.Media.DATA };

                assert photoUri != null;
                cursor = getContentResolver().query(photoUri, proj, null, null, null);

                assert cursor != null;
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                cursor.moveToFirst();

                tempFile = new File(cursor.getString(column_index));

            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }

            setImage();

        }else if (requestCode == PICK_FROM_CAMERA) {
            setImage();
        }
    }

    //갤러리에서 받아온 이미지 넣기
    Bitmap originalBm;
    private void setImage() {
        addLayout.setVisibility(View.GONE);
        resultLayout.setVisibility(View.VISIBLE);
        BitmapFactory.Options options = new BitmapFactory.Options();
        originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);
        Log.d("파일저장 경로~~~~~~~~~~~~",tempFile+","+originalBm);

        //resultIV.setImageBitmap(originalBm);
        changeGrayScale();
    }
    //카메라에서 이미지 가져오기
    private static final int PICK_FROM_CAMERA = 2;
    private void takePhoto() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
            tempFile = createImageFile();

        } catch (IOException e) {
            Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            finish();
            e.printStackTrace();
        }
        if (tempFile != null) {

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {

                Uri photoUri = FileProvider.getUriForFile(this,
                        "{package name}.provider", tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, PICK_FROM_CAMERA);

            } else {

                Uri photoUri = Uri.fromFile(tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, PICK_FROM_CAMERA);

            }
        }
    }

    //카메라에서 찍은 사진을 저장할 파일 만들기
    private File createImageFile() throws IOException {

        // 이미지 파일 이름 ( blackJin_{시간}_ )
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = "smartusercar_" + timeStamp + "_";

        // 이미지가 저장될 폴더 이름 ( blackJin )
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/userCarNum/");

        if (!storageDir.exists()) storageDir.mkdirs();

        // 빈 파일 생성
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        return image;
    }
    //이미지 전처리 작업 : 기본적으로 GrayScale(흑백) 및 ThresHold(이진화) 처리를 수행합니다.
    ImageView image_result;
    int[][] listarr;
    Bitmap roi;
    private void changeGrayScale(){
        Log.d("회색처리","시작~~~~~~~~~~");
        Bitmap image1 = originalBm;
        OpenCVLoader.initDebug();
        Mat img1=new Mat();
        Utils.bitmapToMat(image1 ,img1);
        Mat imageGray1 = new Mat();
        Mat imageCny1 = new Mat();
        Imgproc.cvtColor(img1, imageGray1, Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(imageGray1, imageCny1, 160, 255, Imgproc.THRESH_BINARY);
        //Utils.matToBitmap(imageCny1,image1);
        //resultIV.setImageBitmap(image1);//이진화까지는 됨.

        //흑백 처리와 이진화된 이미지 가져오기
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(imageCny1, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        for(int idx = 0; idx >= 0; idx = (int) hierarchy.get(0, idx)[0]) {
            MatOfPoint matOfPoint = contours.get(idx);
            Rect rect = Imgproc.boundingRect(matOfPoint);
            if(rect.width < 30 || rect.height < 30 || rect.width <= rect.height || rect.x < 20 || rect.y < 20
                    || rect.width <= rect.height * 3 || rect.width >= rect.height * 6) continue; // 사각형 크기에 따라 출력 여부 결정
            // ROI 출력
            Utils.matToBitmap(imageCny1,image1); //추가한 부분
            roi = Bitmap.createBitmap(image1, (int)rect.tl().x, (int)rect.tl().y, rect.width, rect.height);
            ImageView imageView = (ImageView)findViewById(R.id.image_result);
            imageView.setImageBitmap(roi);// 번호판만 자르고 넣기
        }
        image1= Bitmap.createBitmap(img1.cols(), img1.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(img1, image1); // Mat to Bitmap
        resultIV.setImageBitmap(image1);

        datapath = getFilesDir()+ "/tesseract/";

        checkFile(new File(datapath + "tessdata/"));

        String lang = "kor";
        mTess = new TessBaseAPI();
        mTess.init(datapath, lang);
        processImage();
    }
    private TessBaseAPI mTess;
    String datapath = "";
    private void copyFiles() {
        try {
            //location we want the file to be at
            String filepath = datapath + "/tessdata/kor.traineddata";

            //get access to AssetManager
            AssetManager assetManager = getAssets();

            //open byte streams for reading/writing
            InputStream instream = assetManager.open("tessdata/kor.traineddata");
            OutputStream outstream = new FileOutputStream(filepath);

            //copy the file to the location specified by filepath
            byte[] buffer = new byte[1024];
            int read;
            while ((read = instream.read(buffer)) != -1) {
                outstream.write(buffer, 0, read);
            }
            outstream.flush();
            outstream.close();
            instream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkFile(File dir) {
        //directory does not exist, but we can successfully create it
        if (!dir.exists()&& dir.mkdirs()){
            copyFiles();
        }
        //The directory exists, but there is no data file in it
        if(dir.exists()) {
            String datafilepath = datapath+ "/tessdata/kor.traineddata";
            File datafile = new File(datafilepath);
            if (!datafile.exists()) {
                copyFiles();
            }
        }
    }

    public void processImage(){
        String OCRresult = null;
        mTess.setImage(roi);
        OCRresult = mTess.getUTF8Text();
        Log.d("이미지 결과~~~~~:",""+OCRresult);
        carNumEdit.setText(OCRresult);
    }

}
