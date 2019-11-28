package com.vipapp.apk_share.widget;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.vipapp.apk_share.R;
import java.lang.reflect.Field;

public class MyBottomNavigationView extends BottomNavigationView {

	public MyBottomNavigationView(Context context, AttributeSet attrs) {
		super(context, attrs);
		centerMenuIcon2();
	}

	private void centerMenuIcon() {
		BottomNavigationMenuView menuView = getBottomMenuView();

		if (menuView != null) {
			for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView menuItemView = (BottomNavigationItemView) menuView.getChildAt(i);

                AppCompatImageView icon = (AppCompatImageView) menuItemView.getChildAt(0);

                FrameLayout.LayoutParams params = (LayoutParams) icon.getLayoutParams();
                params.gravity = Gravity.CENTER;

                menuItemView.setShiftingMode(true);
				
				removeTextLabel(i);
			}
		}
	}
	
	private void centerMenuIcon2() {
		BottomNavigationMenuView menuView = getBottomMenuView();
		if (menuView != null) {
			for (int i = 0; i < menuView.getChildCount(); i++) {
				BottomNavigationItemView menuItemView = (BottomNavigationItemView) menuView.getChildAt(i);
				TextView smallText = (TextView) menuItemView.findViewById(R.id.smallLabel);
				smallText.setVisibility(View.INVISIBLE);
				//TextView largeText = (TextView) menuItemView.findViewById(R.id.largeLabel);
				ImageView icon = (ImageView) menuItemView.findViewById(R.id.icon);
				FrameLayout.LayoutParams params = (LayoutParams) icon.getLayoutParams();
				params.gravity = Gravity.CENTER;
				menuItemView.setShiftingMode(true);
			}
		}
	}

	private BottomNavigationMenuView getBottomMenuView() {
		Object menuView = null;
		try {
			Field field = BottomNavigationView.class.getDeclaredField("mMenuView");
			field.setAccessible(true);
			menuView = field.get(this);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}

		return (BottomNavigationMenuView) menuView;
	}
	
	private void removeTextLabel(@IdRes int menuItemId) {
		View view = findViewById(menuItemId);
		if (view == null) return;
		if (view instanceof MenuView.ItemView) {
			ViewGroup viewGroup = (ViewGroup) view;
			int padding = 0;
			for (int i = 0; i < viewGroup.getChildCount(); i++) {
				View v = viewGroup.getChildAt(i);
				if (v instanceof ViewGroup) {
					padding = v.getHeight();
					viewGroup.removeViewAt(i);
				}
			}
			viewGroup.setPadding(view.getPaddingLeft(), (viewGroup.getPaddingTop() + padding) / 2, view.getPaddingRight(), view.getPaddingBottom());
		}
	}
}

