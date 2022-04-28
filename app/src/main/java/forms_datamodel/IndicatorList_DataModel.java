package forms_datamodel;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import Common.Connection;

public class IndicatorList_DataModel {
    private String _Serial = "";
    public String getSerial(){
        return _Serial;
    }
    public void setSerial(String newValue){
        _Serial = newValue;
    }

    private String _typeid = "";
    public String gettypeid(){
        return _typeid;
    }
    public void settypeid(String newValue){
        _typeid = newValue;
    }

    private String _typename = "";
    public String gettypename(){
        return _typename;
    }
    public void settypename(String newValue){
        _typename = newValue;
    }

    private String _indicatorid = "";
    public String getindicatorid(){
        return _indicatorid;
    }
    public void setindicatorid(String newValue){
        _indicatorid = newValue;
    }

    private String _indicator_name = "";
    public String getindicator_name(){
        return _indicator_name;
    }
    public void setindicator_name(String newValue){
        _indicator_name = newValue;
    }

    private String _themeid = "";
    public String getthemeid(){
        return _themeid;
    }
    public void setthemeid(String newValue){
        _themeid = newValue;
    }

    private String _theme = "";
    public String gettheme(){
        return _theme;
    }
    public void settheme(String newValue){
        _theme = newValue;
    }

    private String _surveyid = "";
    public String getsurveyid(){
        return _surveyid;
    }
    public void setsurveyid(String newValue){
        _surveyid = newValue;
    }

    private String _survey_name = "";
    public String getsurvey_name(){
        return _survey_name;
    }
    public void setsurvey_name(String newValue){
        _survey_name = newValue;
    }

    private String _country = "";
    public String getcountry(){
        return _country;
    }
    public void setcountry(String newValue){
        _country = newValue;
    }

    private String _impl_partner = "";
    public String getimpl_partner(){
        return _impl_partner;
    }
    public void setimpl_partner(String newValue){
        _impl_partner = newValue;
    }

    private String _survey_type = "";
    public String getsurvey_type(){
        return _survey_type;
    }
    public void setsurvey_type(String newValue){
        _survey_type = newValue;
    }

    private String _sample_size = "";
    public String getsample_size(){
        return _sample_size;
    }
    public void setsample_size(String newValue){
        _sample_size = newValue;
    }

    private String _status = "";
    public String getstatus(){
        return _status;
    }
    public void setstatus(String newValue){
        _status = newValue;
    }

    private String _startdate = "";
    public String getstartdate(){
        return _startdate;
    }
    public void setstartdate(String newValue){
        _startdate = newValue;
    }

    private String _enddate = "";
    public String getenddate(){
        return _enddate;
    }
    public void setenddate(String newValue){
        _enddate = newValue;
    }

    private String _survey_character = "";
    public String getsurvey_character(){
        return _survey_character;
    }
    public void setsurvey_character(String newValue){
        _survey_character = newValue;
    }

    private String _indicator_desc = "";
    public String getindicator_desc(){
        return _indicator_desc;
    }
    public void setindicator_desc(String newValue){
        _indicator_desc = newValue;
    }

    public List<IndicatorList_DataModel> SelectAll(Context context, String SQL)
    {
        Connection C = new Connection(context);
        List<IndicatorList_DataModel> data = new ArrayList<IndicatorList_DataModel>();
        IndicatorList_DataModel d;
        Cursor cur = C.ReadData(SQL);

        cur.moveToFirst();
        while(!cur.isAfterLast())
        {
            d = new IndicatorList_DataModel();
            d._Serial = cur.getString(cur.getColumnIndex("Serial"));
            d._typeid = cur.getString(cur.getColumnIndex("typeid"));
            d._typename = cur.getString(cur.getColumnIndex("typename"));
            d._indicatorid = cur.getString(cur.getColumnIndex("indicatorid"));
            d._indicator_name = cur.getString(cur.getColumnIndex("indicator_name"));
            d._themeid = cur.getString(cur.getColumnIndex("themeid"));
            d._theme = cur.getString(cur.getColumnIndex("theme"));
            d._surveyid = cur.getString(cur.getColumnIndex("surveyid"));
            d._survey_name = cur.getString(cur.getColumnIndex("survey_name"));
            d._country = cur.getString(cur.getColumnIndex("country"));
            d._impl_partner = cur.getString(cur.getColumnIndex("impl_partner"));
            d._survey_type = cur.getString(cur.getColumnIndex("survey_type"));
            d._sample_size = cur.getString(cur.getColumnIndex("sample_size"));
            d._status = cur.getString(cur.getColumnIndex("status"));
            d._startdate = cur.getString(cur.getColumnIndex("startdate"));
            d._enddate = cur.getString(cur.getColumnIndex("enddate"));
            d._survey_character = cur.getString(cur.getColumnIndex("survey_character"));


            data.add(d);

            cur.moveToNext();
        }
        cur.close();
        return data;
    }

    public List<IndicatorList_DataModel> SelectIndicators(Context context, String SQL)
    {
        Connection C = new Connection(context);
        List<IndicatorList_DataModel> data = new ArrayList<IndicatorList_DataModel>();
        IndicatorList_DataModel d;
        Cursor cur = C.ReadData(SQL);

        cur.moveToFirst();
        while(!cur.isAfterLast())
        {
            d = new IndicatorList_DataModel();
            d._Serial = cur.getString(cur.getColumnIndex("serial"));
            d._themeid = cur.getString(cur.getColumnIndex("themeid"));
            d._theme = cur.getString(cur.getColumnIndex("theme"));
            d._indicatorid = cur.getString(cur.getColumnIndex("indicatorid"));
            d._indicator_name = cur.getString(cur.getColumnIndex("indicator_name"));
            d._indicator_desc = cur.getString(cur.getColumnIndex("indicator_desc"));

            data.add(d);

            cur.moveToNext();
        }
        cur.close();
        return data;
    }

    private String _filter1 = "";
    public String getfilter1(){
        return _filter1;
    }
    public void setfilter1(String newValue){
        _filter1 = newValue;
    }
    public List<IndicatorList_DataModel> SelectIndicators_Filter(Context context, String SQL)
    {
        Connection C = new Connection(context);
        List<IndicatorList_DataModel> data = new ArrayList<IndicatorList_DataModel>();
        IndicatorList_DataModel d;
        Cursor cur = C.ReadData(SQL);

        cur.moveToFirst();
        while(!cur.isAfterLast())
        {
            d = new IndicatorList_DataModel();
            d._filter1 = cur.getString(cur.getColumnIndex("filter1"));

            data.add(d);

            cur.moveToNext();
        }
        cur.close();
        return data;
    }
}
