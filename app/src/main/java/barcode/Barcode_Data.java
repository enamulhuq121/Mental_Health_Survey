package barcode;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by tasrul on 18-Jan-18.
 */

public class Barcode_Data {

    private static String xml_data = null;
    private static New_NID new_nid=null;
    private static Old_NID old_nid;

    public Barcode_Data(String xml_data) {
        this.xml_data = xml_data;

    }

    public New_NID dataNewID( )
    {
        String[] separator={"NW","OL","BR","PE","PR","VA","DT","PK","SG"};
        String currentString;
        String[] data = new String[10];

//        Log.e("Sakib","************* Full data "+xml_data);

        String[] separated = xml_data.split("NM");
        if(separated.length<2)
            return new_nid;
        currentString=separated[1];

        for(int i=0; i<separator.length;i++)
        {
            String[] array=currentString.split(separator[i]);
            String value=array[0];
            currentString=array[1];
            data[i]=value;
            if(i==separator.length-1)
            {
                data[i+1]=array[1];
            }
        }
//        for (int i=0;i<separator.length;i++)
//        {
//            data[i+1]=separator[i]+data[i+1];
//        }

        String name,nw,ol,br,pe,pr,va,dt,pk,sg;
        name=data[0];
        nw=data[1];
        ol=data[2];
        br=data[3];
        pe=data[4];
        pr=data[5];
        va=data[6];
        dt=data[7];
        pk=data[8];
        sg=data[9];

        new_nid=new New_NID(name,nw,ol,br,pe,pr,va,dt,pk,sg);

        return new_nid;

    }

    public Old_NID dataOldID()
    {
        XmlPullParserFactory pullParserFactory;
        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            InputStream in_s = new ByteArrayInputStream(xml_data.getBytes("UTF-8"));
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);

            parseXML(parser);

        } catch (XmlPullParserException e) {

            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return old_nid;

    }

    private void parseXML(XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList<Old_NID> products = null;
        int eventType = parser.getEventType();
        Old_NID currentProduct = new Old_NID();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String name = null;
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    products = new ArrayList();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();

//                    Log.e("Sakib","************* "+name+"== "+parser.nextText());
                    switch (name) {
                        case "pin":
                            currentProduct.setPin(parser.nextText());
                            break;
                        case "name":
                            currentProduct.setName(parser.nextText());
                            break;
                        case "DOB":
                            currentProduct.setDOB(parser.nextText());
//                            break;
                            old_nid=currentProduct;
                            return;

                        case "FP":
                            //currentProduct.setFP(parser.nextText());
                            break;
                        case "F":
                            currentProduct.setF(parser.nextText());
                            break;
                        case "TYPE":
                            currentProduct.setTYPE(parser.nextText());
                            break;
                        case "V":
                            currentProduct.setV(parser.nextText());
                            break;
                        case "ds":
                            currentProduct.setDs(parser.nextText());
                            break;
                    }
                    break;

                case XmlPullParser.END_TAG:
                    name = parser.getName();
//                    if (name.equalsIgnoreCase("product") && currentProduct != null){
//                        products.add(currentProduct);
//                    }
            }
            eventType = parser.next();
        }

        old_nid=currentProduct;

    }

}



