package com.example.fihhuda.azkar;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.fihhuda.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.shockwave.pdfium.PdfDocument;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class AzkarFragment extends Fragment implements OnPageChangeListener, OnLoadCompleteListener,
        OnPageErrorListener, View.OnClickListener {
//here list of Azkar names

    protected View rootView;
    protected PDFView pdfView;
    protected TextView searchBar;
    protected Toolbar toolbar;
    protected ImageView azkarImage1;
    protected ConstraintLayout first;
    protected ImageView azkarImage2;
    protected ConstraintLayout second;
    protected ImageView azkarImage3;
    protected ConstraintLayout third;
    protected ImageView azkarImage4;
    protected ConstraintLayout fourth;
    protected ImageView azkarImage5;
    protected ConstraintLayout fifth;
    protected ImageView azkarImage6;
    protected ConstraintLayout sexth;
    protected ImageView azkarImage7;
    protected ConstraintLayout seven;
    private final static int REQUEST_CODE = 42;
    public static final int PERMISSION_CODE = 42042;

    public static final String SAMPLE_FILE = "sample.pdf";
    public static final String READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";


    Integer pageNumber = 0;

    String pdfFileName;

    public AzkarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_azkar, container, false);

        return rootView;
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(rootView);
        //  pickFile();
        //  afterViews();
    }

    void pickFile() {
        int permissionCheck = ContextCompat.checkSelfPermission(getContext(),
                READ_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{READ_EXTERNAL_STORAGE},
                    PERMISSION_CODE
            );

            return;
        }

        launchPicker();
    }

    void launchPicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            //alert user that file manager not working
            Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
        }
    }


    void afterViews() {
        pdfView.setBackgroundColor(Color.LTGRAY);

        displayFromAsset(SAMPLE_FILE);

    }

    private void displayFromAsset(String assetFileName) {
        pdfFileName = assetFileName;

        pdfView.fromAsset(SAMPLE_FILE)
                .defaultPage(pageNumber)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(getContext()))
                .spacing(10) // in dp
                .onPageError(this)
                .swipeHorizontal(false)
                .pageSnap(true)
                .pageFling(true)
                .pageFitPolicy(FitPolicy.BOTH) // mode to fit pages in the view
                .fitEachPage(true) // fit each page to the view, else smaller pages are scaled relative to largest page.
                .nightMode(false)
                .load();
    }


    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        //setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }


    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        Log.e(TAG, "title = " + meta.getTitle());
        Log.e(TAG, "author = " + meta.getAuthor());
        Log.e(TAG, "subject = " + meta.getSubject());
        Log.e(TAG, "keywords = " + meta.getKeywords());
        Log.e(TAG, "creator = " + meta.getCreator());
        Log.e(TAG, "producer = " + meta.getProducer());
        Log.e(TAG, "creationDate = " + meta.getCreationDate());
        Log.e(TAG, "modDate = " + meta.getModDate());

        printBookmarksTree(pdfView.getTableOfContents(), "-");

    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }

    /**
     * Listener for response to user permission request
     *
     * @param requestCode  Check that permission request code matches
     * @param permissions  Permissions that requested
     * @param grantResults Whether permissions granted
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchPicker();
            }
        }
    }

    @Override
    public void onPageError(int page, Throwable t) {
        Log.e(TAG, "Cannot load page " + page);
    }

    private void initView(View rootView) {
        // pdfView = (PDFView) rootView.findViewById(R.id.pdfView);
        searchBar = (TextView) rootView.findViewById(R.id.search_bar);
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        azkarImage1 = (ImageView) rootView.findViewById(R.id.azkar_image1);
        first = (ConstraintLayout) rootView.findViewById(R.id.first);
        first.setOnClickListener(AzkarFragment.this);
        azkarImage2 = (ImageView) rootView.findViewById(R.id.azkar_image2);
        second = (ConstraintLayout) rootView.findViewById(R.id.second);
        second.setOnClickListener(AzkarFragment.this);
        azkarImage3 = (ImageView) rootView.findViewById(R.id.azkar_image3);
        third = (ConstraintLayout) rootView.findViewById(R.id.third);
        third.setOnClickListener(AzkarFragment.this);
        azkarImage4 = (ImageView) rootView.findViewById(R.id.azkar_image4);
        fourth = (ConstraintLayout) rootView.findViewById(R.id.fourth);
        fourth.setOnClickListener(AzkarFragment.this);
        azkarImage5 = (ImageView) rootView.findViewById(R.id.azkar_image5);
        fifth = (ConstraintLayout) rootView.findViewById(R.id.fifth);
        fifth.setOnClickListener(AzkarFragment.this);
        azkarImage6 = (ImageView) rootView.findViewById(R.id.azkar_image6);
        sexth = (ConstraintLayout) rootView.findViewById(R.id.sexth);
        sexth.setOnClickListener(AzkarFragment.this);
        azkarImage7 = (ImageView) rootView.findViewById(R.id.azkar_image7);
        seven = (ConstraintLayout) rootView.findViewById(R.id.seven);
        seven.setOnClickListener(AzkarFragment.this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.first) {

        } else if (view.getId() == R.id.second) {


        } else if (view.getId() == R.id.third) {

        } else if (view.getId() == R.id.fourth) {

        } else if (view.getId() == R.id.fifth) {

        } else if (view.getId() == R.id.sexth) {

        } else if (view.getId() == R.id.seven) {

        }
    }
}


