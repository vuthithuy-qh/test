/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.math.BigDecimal;

/**
 *
 * @author ADMIN
 */
public class CarSearchCriteriaDTO {
    private String keyword; // tim kiem theo ten model
    private Integer manufactureId; 
    private Integer carTypeId; 
    private Integer year; 
    private BigDecimal minPrice; 
    private BigDecimal maxPrice; 
    private Integer colorId; 
    
    private Integer engineTypeId; 

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getColorId() {
        return colorId;
    }

    public void setColorId(Integer colorId) {
        this.colorId = colorId;
    }

    public Integer getEngineTypeId() {
        return engineTypeId;
    }

    public void setEngineTypeId(Integer engineTypeId) {
        this.engineTypeId = engineTypeId;
    }
    
    

    public Integer getManufactureId() {
        return manufactureId;
    }

    public void setManufactureId(Integer manufactureId) {
        this.manufactureId = manufactureId;
    }

    public Integer getCarTypeId() {
        return carTypeId;
    }

    public void setCarTypeId(Integer carTypeId) {
        this.carTypeId = carTypeId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }
    
    
    
}
