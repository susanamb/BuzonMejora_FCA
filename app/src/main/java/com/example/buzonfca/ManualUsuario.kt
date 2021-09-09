package com.example.buzonfca

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.barteksc.pdfviewer.PDFView

class ManualUsuario : AppCompatActivity() {

    private lateinit var pdfViewer: PDFView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual_usuario)


        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.title = ""

            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        pdfViewer = findViewById(R.id.pdfView)
        pdfViewer.fromAsset("manual_de_usuario.pdf")

            .enableSwipe(true) // allows to block changing pages using swipe
            .swipeHorizontal(false)
            .enableDoubletap(true)
            .defaultPage(0)
            // allows to draw something on the current page, usually visible in the middle of the screen
            .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
            .password(null)
            .scrollHandle(null)
            .enableAntialiasing(true) // improve rendering a little bit on low-res screens
            // spacing between pages in dp. To define spacing color, set view background
            .spacing(0)

            .load();

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}