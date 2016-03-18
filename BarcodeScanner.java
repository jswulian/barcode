package com.suwusoft.barcode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;

import android.app.Activity;
import android.content.Intent;

public class BarcodeScanner extends CordovaPlugin {
	public static final String TAG = "CarrierPlugin";
	public static final String ACTION_SCAN_CODE = "scan";
	public static final String ACTION_CREATE_CODE = "create";
	public static final String INTENT_SCAN_CODE = "com.suwusoft.barcode.android.SCAN";
	public static final String INTENT_CREATE_CODE = "com.suwusoft.barcode.android.CREATE";
	public static final int REQUEST_CODE = 0x0ba7c0de;
	private CallbackContext callbackContext;

	@Override
	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {
		 this.callbackContext = callbackContext;
		if (ACTION_SCAN_CODE.equals(action)) {
			Intent intentScan = new Intent(INTENT_SCAN_CODE);
			intentScan.addCategory(Intent.CATEGORY_DEFAULT);
			intentScan.setPackage(this.cordova.getActivity().getApplicationContext().getPackageName());
			this.cordova.startActivityForResult((CordovaPlugin) this,intentScan, REQUEST_CODE);
			return true;
		} else if (ACTION_CREATE_CODE.equals(action)) {

			return true;
		} else {
			return false;
		}
	}
	
	 @Override
	    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
	        if (requestCode == REQUEST_CODE) {
	            if (resultCode == Activity.RESULT_OK) {
	                JSONObject obj = new JSONObject();
	                try {
	                    obj.put("text", intent.getStringExtra("codedContent"));
//	                    obj.put("format", intent.getStringExtra("SCAN_RESULT_FORMAT"));
//	                    obj.put("cancelled", false);
	                } catch (JSONException e) {
//	                    Log.d(LOG_TAG, "This should never happen");
	                }
	                //this.success(new PluginResult(PluginResult.Status.OK, obj), this.callback);
	                this.callbackContext.success(obj);
	            } else if (resultCode == Activity.RESULT_CANCELED) {
	                JSONObject obj = new JSONObject();
	                try {
	                    obj.put("text", "");
//	                    obj.put("format", "");
//	                    obj.put("cancelled", true);
	                } catch (JSONException e) {
//	                    Log.d(LOG_TAG, "This should never happen");
	                }
	                //this.success(new PluginResult(PluginResult.Status.OK, obj), this.callback);
	                this.callbackContext.success(obj);
	            } else {
	                //this.error(new PluginResult(PluginResult.Status.ERROR), this.callback);
	                this.callbackContext.error("Unexpected error");
	            }
	        }
	    }
}
