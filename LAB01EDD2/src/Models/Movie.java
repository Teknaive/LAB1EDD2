package Models;

public class Movie {

    private String title;
    private Integer year;
    private Double worldwideEarnings;
    private Double domesticEarnings;
    private Double foreignEarnings;
    private Double DomesticPercentEarnings;
    private Double ForeignPercentEarning;
    private Movie left;
    private Movie right;
    private Integer height;

    public Movie() {
    }

    public Movie(String title, Integer year, Double worldwideEarnings, Double domesticEarnings, Double foreignEarnings, Double DomesticPercentEarnings, Double ForeignPercentEarning, Movie left, Movie right, Integer height) {
        this.title = title;
        this.year = year;
        this.worldwideEarnings = worldwideEarnings;
        this.domesticEarnings = domesticEarnings;
        this.foreignEarnings = foreignEarnings;
        this.DomesticPercentEarnings = DomesticPercentEarnings;
        this.ForeignPercentEarning = ForeignPercentEarning;
        this.left = left;
        this.right = right;
        this.height = height;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getWorldwideEarnings() {
        return worldwideEarnings;
    }

    public void setWorldwideEarnings(Double worldwideEarnings) {
        this.worldwideEarnings = worldwideEarnings;
    }

    public Double getDomesticEarnings() {
        return domesticEarnings;
    }

    public void setDomesticEarnings(Double domesticEarnings) {
        this.domesticEarnings = domesticEarnings;
    }

    public Double getForeignEarnings() {
        return foreignEarnings;
    }

    public void setForeignEarnings(Double foreignEarnings) {
        this.foreignEarnings = foreignEarnings;
    }

    public Double getDomesticPercentEarnings() {
        return DomesticPercentEarnings;
    }

    public void setDomesticPercentEarnings(Double DomesticPercentEarnings) {
        this.DomesticPercentEarnings = DomesticPercentEarnings;
    }

    public Double getForeignPercentEarning() {
        return ForeignPercentEarning;
    }

    public void setForeignPercentEarning(Double ForeignPercentEarning) {
        this.ForeignPercentEarning = ForeignPercentEarning;
    }

    public Movie getLeft() {
        return left;
    }

    public void setLeft(Movie left) {
        this.left = left;
    }

    public Movie getRight() {
        return right;
    }

    public void setRight(Movie right) {
        this.right = right;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

}
