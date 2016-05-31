package com.mai.xmai_fast_lib.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.mai.xmai_fast_lib.utils.MLog;


/**
 * 基础fragment
 * @author maijuntian
 *
 */
public class BaseFragment extends Fragment {
	protected ProgressDialog pDialog;
	private String log_tag = this.getClass().getSimpleName();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	
	protected void log(String msg){
		MLog.log(log_tag , msg);
	}
	
	protected void showToast(String msg){
		Toast.makeText(this.getActivity(), msg, Toast.LENGTH_SHORT).show();
	}
	protected void showToast(int resId){
		Toast.makeText(this.getActivity(), resId, Toast.LENGTH_SHORT).show();
	}
	
	protected void showProgressDialog(String msg){
		if(pDialog == null){
			pDialog =  new ProgressDialog(this.getActivity());
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

	public void startActivity(Class clazz,boolean isFinishSelf){
		Intent intent=new Intent(getActivity(),clazz);
		startActivity(intent);
		if(isFinishSelf){
			getActivity().finish();
		}
	}

	public void startActivity(Class clazz,Bundle bundle,boolean isFinishSelf){
		Intent intent=new Intent(getActivity(),clazz);
		intent.putExtras(bundle);
		startActivity(intent);
		if(isFinishSelf){
			getActivity().finish();
		}
	}

	public void startActivityForResult(Class clazz,Bundle bundle,int requestCode){
		Intent intent=new Intent(getActivity(),clazz);
		intent.putExtras(bundle);
		startActivityForResult(intent, requestCode);
	}
	public void startActivityForResult(Class clazz,int requestCode){
		Intent intent=new Intent(getActivity(),clazz);
		startActivityForResult(intent, requestCode);
	}
}
