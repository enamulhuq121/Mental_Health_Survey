package print;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.print.PrintHelper;

import org.icddrb.standard.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PrintExampleActivity extends AppCompatActivity {

    private LinearLayout llRoot;
    public Button btnPrint,btnSave;
//    private AppCompatDelegate delegate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_print_example);


        final LinearLayout llRoot = findViewById(R.id.llRoot);
        btnPrint=findViewById(R.id.btnPrint);
        btnSave=findViewById(R.id.btnSave);

        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                print(llRoot);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPdf(llRoot);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_print:
                print(llRoot);
                return true;
            case R.id.menu_pdf:
                createPdf(llRoot);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //test function
    public void printDocument(View view)
    {
        /*LinearLayout layout=findViewById(R.id.start);
        saveViewImage(layout);
        Bitmap bitmap=createBitmapFromView(layout);
        PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);

        String jobName = this.getString(R.string.app_name) +
                " Document";

        printManager.print(jobName, new
                        PrintCustomDocumentAdapter(this),
                null);
*/
       /* new PdfDocument.Builder(this).addPage(page).orientation(PdfDocument.A4_MODE.LANDSCAPE)
                .progressMessage("Creating").progressTitle("Message")
                .renderWidth(2115).renderHeight(1500)
                .saveDirectory(this.getExternalFilesDir(null))
                         .filename("test")
            .listener(new PdfDocument.Callback() {
                @Override
                public void onComplete(File file) {
                    Log.i(PdfDocument.TAG_PDF_MY_XML, "Complete");
                }

                @Override
                public void onError(Exception e) {
                    Log.i(PdfDocument.TAG_PDF_MY_XML, "Error");
                }
            }).create().createPdf(this);*/
    }
    public void print(LinearLayout llPrint) {
        Bitmap bm = loadBitmapFromView(llPrint, llPrint.getWidth(), llPrint.getHeight());
        String fileName = getApplicationName(this) + "_" + getCurrentTimeStamp() + ".png";
        printImage(fileName, bm);
    }

    public void printImage(String fileName, Bitmap bitmap) {
        if (PrintHelper.systemSupportsPrint()) {
            PrintHelper printHelper = new PrintHelper(this);
            printHelper.setColorMode(PrintHelper.COLOR_MODE_COLOR);
            printHelper.setScaleMode(PrintHelper.SCALE_MODE_FIT);
            printHelper.printBitmap(fileName, bitmap);
        } else {
            Toast.makeText(this, "Print not supported!!", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void createPdf(LinearLayout llPrint) {
        Bitmap bitmap = loadBitmapFromView(llPrint, llPrint.getWidth(), llPrint.getHeight());
        String fileName = getApplicationName(this) + "_" + getCurrentTimeStamp() + ".pdf";
        createPdf(fileName, bitmap);
    }

    public void createPdf(String fiilename, Bitmap bitmap) {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels;
        float width = displaymetrics.widthPixels;
        int convertHighet = (int) hight, convertWidth = (int) width;

        PdfDocument document = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            document = new PdfDocument();
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
            PdfDocument.Page page = document.startPage(pageInfo);
            Canvas canvas = page.getCanvas();
            Paint paint = new Paint();
            canvas.drawPaint(paint);
            bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);
            paint.setColor(Color.BLUE);
            canvas.drawBitmap(bitmap, 0, 0, null);
            document.finishPage(page);
            // write the document content
            String targetPdf = "/sdcard/" + fiilename;
            File filePath;
            filePath = new File(targetPdf);
            try {
                document.writeTo(new FileOutputStream(filePath));

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
            }

            // close the document
            document.close();
            Toast.makeText(this, "PDF is created!!!", Toast.LENGTH_SHORT).show();

            openGeneratedPDF(filePath);
        }


    }

    public void openGeneratedPDF(File filePath) {
        File file = filePath;
        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }
    }

    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }

    public static String getApplicationName(Context context) {
        String result = "";

        ApplicationInfo applicationInfo = context.getApplicationInfo();
        int stringId = applicationInfo.labelRes;
        result = stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : context.getString(stringId);

        return result;
    }

    public static String getCurrentTimeStamp() {
        String result = "";

        Long tsLong = System.currentTimeMillis() / 1000;
        result = tsLong.toString();

        return result;
    }


    @Nullable
    @Override
    public ActionMode onWindowStartingSupportActionMode(ActionMode.Callback callback) {
        return null;
    }
}
