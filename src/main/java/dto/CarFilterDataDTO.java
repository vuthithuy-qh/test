/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;


import java.math.BigDecimal;
import java.util.List;
import model.CarColor;
import model.CarType;
import model.EngineType;
import model.Manufacture;

/**
 *
 * @author ADMIN
 */
public class CarFilterDataDTO {
    private List<Manufacture> manufactures; 
    private List<CarType> carTypes; 
    private List<CarColor> colors; 
    private List<EngineType> engineTypes; 
    private List<Integer> years; 
    private List<PriceRangeOption> priceRanges; 

    public List<CarColor> getColors() {
        return colors;
    }

    public void setColors(List<CarColor> colors) {
        this.colors = colors;
    }

    public List<EngineType> getEngineTypes() {
        return engineTypes;
    }

    public void setEngineTypes(List<EngineType> engineTypes) {
        this.engineTypes = engineTypes;
    }
    
    
    
    public List<Manufacture> getManufactures() {
        return manufactures;
    }

    public void setManufactures(List<Manufacture> manufactures) {
        this.manufactures = manufactures;
    }

    public List<CarType> getCarTypes() {
        return carTypes;
    }

    public void setCarTypes(List<CarType> carTypes) {
        this.carTypes = carTypes;
    }

    public List<Integer> getYears() {
        return years;
    }

    public void setYears(List<Integer> years) {
        this.years = years;
    }

    public List<PriceRangeOption> getPriceRanges() {
        return priceRanges;
    }

    public void setPriceRanges(List<PriceRangeOption> priceRanges) {
        this.priceRanges = priceRanges;
    }

    public static class PriceRangeOption {
        private BigDecimal min;
        private BigDecimal max; 
        private String label; 

        public PriceRangeOption(BigDecimal min, BigDecimal max, String label) {
            this.min = min;
            this.max = max;
            this.label = label;
        }

        public BigDecimal getMin() {
            return min;
        }

        public void setMin(BigDecimal min) {
            this.min = min;
        }

        public BigDecimal getMax() {
            return max;
        }

        public void setMax(BigDecimal max) {
            this.max = max;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        
    }
    
    
    
    
}
