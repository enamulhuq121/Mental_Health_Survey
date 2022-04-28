package Common;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class Download_Data_Request extends AsyncTask<String, Void, String> {
		//Method Name
		public String Method_Name = "DownloadDataJSON";
		
		public String WSDL_TARGET_NAMESPACE = ProjectSetting.Namespace;
		public String SOAP_ACTION  = ProjectSetting.Namespace+Method_Name;
		public String SOAP_ADDRESS = ProjectSetting.Soap_Address;
		ProgressDialog dialog;
		String Data=null;
		
		@Override
	    protected void onPreExecute() {			
	    }
		
	    @Override
	    protected String doInBackground(String... urls) {
	    	
	        try {
	                SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,Method_Name);
	                HttpTransportSE androidHttpTransport = new HttpTransportSE(SOAP_ADDRESS);

	                request.addProperty("SQL",urls[0]);
	                
	                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	                envelope.dotNet=true;
	                envelope.setOutputSoapObject(request);

	                androidHttpTransport.call(ProjectSetting.Namespace+Method_Name, envelope);
	                SoapObject result = (SoapObject)envelope.bodyIn;

                    Data = result.getProperty(0).toString();


                return Data;
	            }
	             catch (Exception e) {
	                e.printStackTrace();
	            }
	        return Data;
	    }
	    
	    @Override
	    protected void onPostExecute(String result) {
	    	super.onPostExecute(result);	   
	    }
	    
	}