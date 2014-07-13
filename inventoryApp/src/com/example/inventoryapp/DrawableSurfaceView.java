package com.example.inventoryapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class DrawableSurfaceView extends SurfaceView {

	private RectF mOriginalRect;

	public DrawableSurfaceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public DrawableSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DrawableSurfaceView(Context context) {
		super(context);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		if (mOriginalRect != null) {
			Matrix mCanvasMatrix = canvas.getMatrix();  
			mCanvasMatrix.mapRect(mOriginalRect); // 
	        Paint myPaint = new Paint();
	        myPaint.setColor(Color.GREEN);
	        myPaint.setStyle(Paint.Style.STROKE);
	        myPaint.setStrokeWidth(1);
	        canvas.drawRect(mOriginalRect, myPaint);
	        canvas.setMatrix(mCanvasMatrix);
	        mOriginalRect = null;
		}
	}

	public void setDrawableRect(RectF mOriginalRect) {
		this.mOriginalRect = mOriginalRect;
	}

}
