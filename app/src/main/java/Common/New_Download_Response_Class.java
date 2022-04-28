package Common;

public class New_Download_Response_Class {
    private String requestSQL;
    public void setRequestSQL(String _requestSQL){this.requestSQL=_requestSQL;}
    public String getRequestSQL(){return this.requestSQL;}

    private Integer total_batch;
    public void setTotal_batch(Integer _total_batch){this.total_batch=_total_batch;}
    public Integer getTotal_batch(){return this.total_batch;}

    private Integer batch_size;
    public void setBatch_Size(Integer _batch_size){this.batch_size=_batch_size;}
    public Integer getBatch_Size(){return this.batch_size;}

    private String variableList;
    public void setVariableList(String _variableList){this.variableList=_variableList;}
    public String getVariableList(){return this.variableList;}

    private String uniqueField;
    public void setUniqueField(String _uniqueField){this.uniqueField=_uniqueField;}
    public String getUniqueField(){return this.uniqueField;}

}
