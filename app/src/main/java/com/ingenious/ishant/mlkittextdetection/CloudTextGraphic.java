package com.ingenious.ishant.mlkittextdetection;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

//import com.google.firebase.ml.vision.document.FirebaseVisionDocumentText;
import com.google.firebase.ml.vision.document.FirebaseVisionDocumentText;
import com.ingenious.ishant.mlkittextdetection.GraphicOverlay.Graphic;
//import com.google.firebase.ml.vision.cloud.text.FirebaseVisionCloudText;
//import com.google.firebase.ml.vision.document.FirebaseVisionDocumentText;

import java.util.List;

public class CloudTextGraphic extends Graphic {
    private static final int TEXT_COLOR = Color.BLUE;
    private static final float TEXT_SIZE = 60.0f;
    private static final float STROKE_WIDTH = 5.0f;

    private final Paint rectPaint;
    private final Paint textPaint;
    private final FirebaseVisionDocumentText.Word word;
    private final GraphicOverlay overlay;

    CloudTextGraphic(GraphicOverlay overlay, FirebaseVisionDocumentText.Word word) {
        super(overlay);

        this.word = word;
        this.overlay = overlay;

        rectPaint = new Paint();
        rectPaint.setColor(TEXT_COLOR);
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setStrokeWidth(STROKE_WIDTH);

        textPaint = new Paint();
        textPaint.setColor(TEXT_COLOR);
        textPaint.setTextSize(TEXT_SIZE);
        postInvalidate();
    }

    @Override
    public void draw(Canvas canvas) {
        if (word == null) {
            throw new IllegalStateException("Attempting to draw a null text.");
        }

        float x = overlay.getWidth() / 4.0f;
        float y = overlay.getHeight() / 4.0f;

        StringBuilder wordStr = new StringBuilder();
        Rect wordRect = word.getBoundingBox();
        canvas.drawRect(wordRect, rectPaint);
        List<FirebaseVisionDocumentText.Symbol> symbols = word.getSymbols();
        for (int m = 0; m < symbols.size(); m++) {
            wordStr.append(symbols.get(m).getText());
        }
        canvas.drawText(wordStr.toString(), wordRect.left, wordRect.bottom, textPaint);
    }
}
