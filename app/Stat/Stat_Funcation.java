package Stat;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thossain on 13/02/2018.
 */

public class Stat_Funcation {
    //X represents the figure you want to examine
    private static double X_Value = 7.5;

    //1. calculate the mean
    //**********************************************************************************************
    private double mean(List<Double> dataset){
        double mean_value = 0;
        mean_value = calculateAverage(dataset);
        return mean_value;
    }

    //2. finding the variance
    //**********************************************************************************************
    /*The variance is a figure that represents how far your data in your sample is clustered about the mean. [6]
     - This calculation will give you an idea about how far your data is spread out.
     - Samples with low variance have data that is clustered closely about the mean.
     - Samples with high variance have data that is spread far from the mean.
     - Variance is often used to compare the distributions between two data sets or samples.
    */
    private double variance(List<Double> dataset){
        double variance_value = 0;
        double mean_value = mean(dataset);

        //Subtract the mean from each of the numbers in your sample
        double substruct_result_temp = 0;
        List<Double> substruct_result_set = new ArrayList<Double>();
        for (Double data : dataset) {
            substruct_result_temp = data - mean_value;
            //square result
            substruct_result_set.add(substruct_result_temp * substruct_result_temp);
        }

        //Square all of the answers from the subtractions you just did
        //Add the squared numbers together
        double sum_result = 0;
        for (Double data : substruct_result_set) {
            sum_result += data;
        }

        //Divide the sum of squares by (n-1)
        variance_value = sum_result/(dataset.size()-1);

        return variance_value;
    }

    //3. calculate the standard deviation
    //**********************************************************************************************
    /*You will need this to find the standard deviation for your sample. [11]
     - Variance is how spread out your data is from the mean or mathematical average.
     - Standard deviation is a figure that represents how spread out your data is in your sample.
    */
    private double standard_deviation(List<Double> dataset){
        //Take the square root of the variance
        return Math.sqrt(variance(dataset));
    }

    //4. calculate z scores
    //**********************************************************************************************
    public double get_z_score(List<Double> dataset){
        DecimalFormat df = new DecimalFormat("#.##");
        List<Double> data = new ArrayList<Double>();
        data.add(7.0);
        data.add(8.0);
        data.add(8.0);
        data.add(7.5);
        data.add(9.0);

        //Z = (X-M)/sigma
        double X = X_Value;
        double M = mean(data);
        //double SD = Double.parseDouble(String.format("%.2f",standard_deviation(data)));
        double SD = standard_deviation(data);
        return (X-M)/SD;
    }


    private double calculateAverage(List <Double> marks) {
        if (marks == null || marks.isEmpty()) {
            return 0;
        }
        double sum = 0;
        for (Double mark : marks) {
            sum += mark;
        }

        return sum / marks.size();
    }
}
