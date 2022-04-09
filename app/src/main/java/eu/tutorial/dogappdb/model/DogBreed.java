package eu.tutorial.dogappdb.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "dogbreed")
public class DogBreed implements Serializable {
    @PrimaryKey()
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    @ColumnInfo()
    private String name;
    @SerializedName("life_span")
    @ColumnInfo()
    private String lifeSpan;
    @SerializedName("bred_for")
    @ColumnInfo()
    private String bred;
    @SerializedName("breed_group")
    @ColumnInfo()
    private String breedGroup;
    @SerializedName("origin")
    @ColumnInfo()
    private String origin;
    @SerializedName("temperament")
    @ColumnInfo()
    private String temperament;
    @SerializedName("url")
    @ColumnInfo()
    private String url;
    @SerializedName("height")
    @ColumnInfo
    private Height height;
    @SerializedName("weight")
    @ColumnInfo
    private Weight weight;

    @Ignore()
    private boolean showDetail;
    @Ignore()
    private boolean liked;

    public DogBreed(String name, String lifeSpan, String bred,
                    String breedGroup, String origin, String temperament,
                    String url, Height height, Weight weight) {
        this.name = name;
        this.lifeSpan = lifeSpan;
        this.bred = bred;
        this.breedGroup = breedGroup;
        this.origin = origin;
        this.temperament = temperament;
        this.url = url;
        this.height = height;
        this.weight = weight;
        this.showDetail = false;
        this.liked = false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLifeSpan(String lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    public void setBred(String bred) {
        this.bred = bred;
    }

    public void setBreedGroup(String breedGroup) {
        this.breedGroup = breedGroup;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setTemperament(String temperament) {
        this.temperament = temperament;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setHeight(Height height) {
        this.height = height;
    }

    public void setWeight(Weight weight) {
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getLifeSpan() {
        return lifeSpan;
    }

    public String getBred() {
        return bred;
    }

    public String getBreedGroup() {
        return breedGroup;
    }

    public String getOrigin() {
        return origin;
    }

    public String getTemperament() {
        return temperament;
    }

    public String getUrl() {
        return url;
    }

    public Height getHeight() {
        return height;
    }

    public String getHeigthMetric(){
        return this.height.getMetric();
    }

    public Weight getWeight() {
        return weight;
    }

    public String getWeigthMetric(){
        return this.weight.getMetric();
    }

    public boolean isShowDetail() {
        return showDetail;
    }

    public void setShowDetail(boolean showDetail) {
        this.showDetail = showDetail;
    }

    public void changeLikeState(){
        this.liked = !this.liked;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
