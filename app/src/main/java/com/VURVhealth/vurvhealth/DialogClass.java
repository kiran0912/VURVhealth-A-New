package com.VURVhealth.vurvhealth;

/*
 * Class Name: DialogClass
 * Author: 
 * Description: DialogClass Class. 
 * Test Configuration will Happen Here
 * Change log: 
 * 03-Jan (Ravi Shankar P): Code Review Changes Incorporated. 
 * [1] All Variables Consolidated. 
 */
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.VURVhealth.vurvhealth.prescriptions.PrescriptionSearchActivity;

public abstract class DialogClass implements DialogInterface.OnClickListener {

	static Context context;

	public DialogClass(Context context) {
		super();
		DialogClass.context = context;
	}

	/*
	 * Create Alert Dialog is method with parameters message that message
	 * displays as dialog
	 */
	public static void createDAlertDialog(final Context context,
			final String message) {
		String setmessage = message;

		final Dialog customDialog = new Dialog(context);
		customDialog.setCancelable(true);
		customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		customDialog.setContentView(R.layout.custom_alert);

		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(customDialog.getWindow().getAttributes());
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		lp.gravity = Gravity.CENTER;

		TextView tv_title = (TextView) customDialog.findViewById(R.id.tv_title);
		TextView info_heading = (TextView) customDialog.findViewById(R.id.info_heading);

		Button cancelBtn = (Button) customDialog.findViewById(R.id.cancelBtn);
		Button yesBtn = (Button) customDialog.findViewById(R.id.yesBtn);

		cancelBtn.setVisibility(View.GONE);
		tv_title.setText(R.string.vurvhealth);
		info_heading.setText(setmessage);
		yesBtn.setText("Ok");
        tv_title.setTypeface(null, Typeface.BOLD);
        tv_title.setTextSize(20f);

		yesBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				customDialog.dismiss();
				customDialog.cancel();
			}
		});

		customDialog.getWindow().setAttributes(lp);

		customDialog.show();

	}

	/*
	 * Create Alert Dialog is method with parameters message that message
	 * displays as dialog
	 */
	public static void createDActionAlertDialog(final Context context,
										  final String message) {
		String setmessage = message;
		AlertDialog.Builder alertbox = new AlertDialog.Builder(context);
		alertbox.setTitle("Alert Message");
		alertbox.setMessage(setmessage);
		alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				Intent in = new Intent(context, PrescriptionSearchActivity.class);
				in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				context.startActivity(in);
			}
		});
		alertbox.show();

	}
}
