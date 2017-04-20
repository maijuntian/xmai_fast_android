package com.mai.xmai_fast_lib.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Toast;

import com.mai.xmai_fast_lib.utils.MLog;
import com.mai.xmai_fast_lib.utils.XAppManager;

/**
 * 
 * 公共基础类
 * @author maijuntian
 *
 */
public abstract class BaseFragmentActivity extends AppCompatActivity {

	protected ProgressDialog pDialog;
	private String log_tag = this.getClass().getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);		
        
        XAppManager.getInstance().addActivity(this);
	}
	
	protected void log(String msg){
		MLog.log(log_tag, msg);
	}
	
	@Override
	public void finish() {
		XAppManager.getInstance().removeActivity(this);
		super.finish();
	}
	
	protected void showToast(String msg){
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	protected void showToast(int resId){
		showToast(getString(resId));
	}
	
	protected void showProgressDialog(String msg){
		if(pDialog == null){
			pDialog =  new ProgressDialog(this);
			pDialog.setCancelable(false);
		}
		pDialog.setMessage(msg);
		pDialog.show();
	}
	
	protected void dismissProgressDialog(){
		if(pDialog != null){
			if(pDialog.isShowing())
				pDialog.dismiss();
		}
	}
	
	protected void showProgressDialog(int resId){
		showProgressDialog(getString(resId));
	}

	public void startActivity(Class claz,boolean isFinishSelf){
		Intent intent=new Intent(this,claz);
		startActivity(intent);
		if(isFinishSelf){
			finish();
		}
	}

	public void startActivity(Class claz,Bundle bundle,boolean isFinishSelf){
		Intent intent=new Intent(this,claz);
		intent.putExtras(bundle);
		startActivity(intent);
		if(isFinishSelf){
			finish();
		}
	}

	public void startActivityForResult(Class claz,Bundle bundle,int requestCode){
		Intent intent=new Intent(this,claz);
		intent.putExtras(bundle);
		startActivityForResult(intent, requestCode);
	}
	public void startActivityForResult(Class claz,int requestCode){
		Intent intent=new Intent(this,claz);
		startActivityForResult(intent, requestCode);
	}
}
