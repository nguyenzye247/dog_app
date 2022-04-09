package eu.tutorial.dogappdb.helper;

import androidx.room.TypeConverter;

import eu.tutorial.dogappdb.model.Height;
import eu.tutorial.dogappdb.model.Weight;

public class Converters {
    @TypeConverter
    public static String fromHeigthToString(Height height){
        return height == null ? "" : height.getMetric() + "," + height.getImperial();
    }
    @TypeConverter
    public static Height fromStringToHeight(String heigthString){
        if(heigthString == null){
            return null;
        }
        String[] splitedByMeasureSystem = heigthString.split(",");
        String imperial = splitedByMeasureSystem[0];
        String metric = splitedByMeasureSystem[1];
        return new Height(imperial, metric);
    }

    @TypeConverter
    public static String fromWeigthToString(Weight weight){
        return weight == null ? "" : weight.getMetric() + "," + weight.getImperial();
    }
    @TypeConverter
    public static Weight fromStringToWeight(String weightString){
        if(weightString == null){
            return null;
        }
        String[] splitedByMeasureSystem = weightString.split(",");
        String imperial = splitedByMeasureSystem[0];
        String metric = splitedByMeasureSystem[1];
        return new Weight(imperial, metric);
    }
}
