package com.example.fitconnect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

// This is a custom View component created to display the BMI result in a visual gauge.
public class BmiGaugeView extends View {

    // Paint objects define how to draw shapes, colors, and text.
    private Paint backgroundPaint;
    private Paint arcPaint;
    private Paint textPaint;

    // A rectangle object that defines the boundaries for our arc.
    private RectF arcRect = new RectF();
    // The current BMI value to be displayed.
    private float bmi = 0;

    // Standard constructors required for a custom view.
    public BmiGaugeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(); // Call our setup method.
    }

    // This method initializes our Paint objects with their desired properties.
    private void init() {
        // Paint for the gray background arc.
        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(Color.LTGRAY);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(50);

        // Paint for the colored foreground arc that represents the BMI value.
        arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcPaint.setColor(Color.GREEN); // Default color.
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(50);

        // Paint for the text that displays the BMI number.
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(60);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    // This is the core method where all the drawing happens.
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Calculate the center and radius of our gauge based on the view's size.
        float centerX = getWidth() / 2f;
        float centerY = getHeight() / 2f;
        float radius = (Math.min(getWidth(), getHeight()) / 2f) - 50; // Subtract stroke width.

        // Define the drawing area for our semi-circle arc.
        arcRect.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);

        // First, draw the light gray background arc (a 180-degree semi-circle).
        canvas.drawArc(arcRect, 180, 180, false, backgroundPaint);

        // Calculate the angle for the colored arc based on the BMI value.
        float bmiAngle = calculateBmiAngle(bmi);
        // Then, draw the colored arc on top of the background.
        canvas.drawArc(arcRect, 180, bmiAngle, false, arcPaint);

        // Finally, draw the BMI number and label in the center.
        canvas.drawText(String.format("%.1f", bmi), centerX, centerY, textPaint);
        canvas.drawText("BMI", centerX, centerY + 70, textPaint);
    }

    /**
     * A public method that allows other classes (like BmiCalculatorActivity) to update the BMI value.
     * @param bmi The newly calculated BMI value.
     */
    public void setBmi(float bmi) {
        this.bmi = bmi;
        // Update the color of the arc based on standard BMI categories.
        if (bmi < 18.5) {
            arcPaint.setColor(Color.BLUE); // Underweight
        } else if (bmi < 25) {
            arcPaint.setColor(Color.GREEN); // Normal
        } else if (bmi < 30) {
            arcPaint.setColor(Color.parseColor("#FFA500")); // Orange for Overweight
        } else {
            arcPaint.setColor(Color.RED); // Obese
        }
        // invalidate() is a crucial method that tells the View it needs to redraw itself.
        invalidate();
    }

    // A helper method to convert a BMI value into a sweep angle (from 0 to 180 degrees).
    private float calculateBmiAngle(float bmi) {
        // We map a typical BMI range (e.g., 10 to 40) to our 180-degree arc.
        float angle = ((bmi - 10) / 30f) * 180f;
        // Clamp the value to ensure it stays within the visual bounds of the semi-circle.
        if (angle < 0) return 0;
        if (angle > 180) return 180;
        return angle;
    }
}