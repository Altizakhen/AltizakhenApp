package com.altizakhen.altizakhenapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class onFlyContainer extends LinearLayout{
	private View menu;
	private View content;
	private final int menuMargin = 200;
	private int contentOffSet = 0;
	private enum State{
		OPENED, CLOSED
	}
	
	private State currentMenuState = State.CLOSED;
	public onFlyContainer(Context context) {
		super(context);
	}
	
	public onFlyContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@SuppressLint("NewApi")
	public onFlyContainer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}	
	
	@Override
	protected void onAttachedToWindow(){
		super.onAttachedToWindow();
		
		this.menu = this.getChildAt(0);
		this.content = this.getChildAt(1); 
		
		this.menu.setVisibility(View.GONE);
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom){
		if (changed){
			this.recalculateChildDimension();
		}
		this.menu.layout(left, top, right - this.menuMargin, bottom);
		this.content.layout(left + this.menuMargin, top, right + this.menuMargin, bottom);
		
	}
	
	void toggleMenu(){
		switch (this.currentMenuState) {
		case OPENED:
			this.menu.setVisibility(View.VISIBLE);
			this.currentMenuState = State.OPENED;
			this.content.offsetLeftAndRight(this.contentOffSet);
			this.contentOffSet = this.menu.getLayoutParams().width;
			break;
		case CLOSED:
			this.content.offsetLeftAndRight(-this.contentOffSet);
			this.contentOffSet = 0;
			this.currentMenuState = State.CLOSED;
			this.menu.setVisibility(View.GONE);
			break;
		}
		this.invalidate();
	}

	private void recalculateChildDimension() {
		this.content.getLayoutParams().height = this.getHeight();
		this.content.getLayoutParams().width = this.getWidth();
		
		this.menu.getLayoutParams().height = this.getHeight();
		this.menu.getLayoutParams().width = this.getWidth() - this.menuMargin;
	}
}
