package service.car; 

import dao.car.CarColorDAO;
import dao.car.CarDAO;
import dao.car.CarModelDAO;
import dao.car.CarTypeDAO;
import dao.car.EngineTypeDAO;
import dao.car.ManufactureDAO;
import dto.CarFilterDataDTO;
import dto.CarListPageDTO;
import dto.CarSearchCriteriaDTO;
import java.math.BigDecimal;
import java.util.List;
import model.Car;
import model.CarType;

public class CarService{
    private final CarDAO carDAO= new CarDAO(); 
    private final ManufactureDAO manufactureDAO = new ManufactureDAO(); 
    private final CarTypeDAO carTypeDAO = new CarTypeDAO(); 
    private final CarModelDAO carModelDAO= new CarModelDAO();
    private final CarColorDAO carColorDAO = new CarColorDAO(); 
    private final EngineTypeDAO engineTypeDAO = new EngineTypeDAO(); 
    //lay du lieu cho cac dropdown cua bi loc
    public CarFilterDataDTO getFilterData(){
        CarFilterDataDTO filterDataDTO = new CarFilterDataDTO(); 
        filterDataDTO.setManufactures(manufactureDAO.findAll());
        filterDataDTO.setCarTypes(carTypeDAO.finAll());
        filterDataDTO.setYears(carModelDAO.findAvailableYears());
        filterDataDTO.setColors(carColorDAO.findAll());
        filterDataDTO.setEngineTypes(engineTypeDAO.findAll());
        filterDataDTO.setPriceRanges(List.of(
        new CarFilterDataDTO.PriceRangeOption(new BigDecimal("0"), new BigDecimal("700000000"), "Under 700tr"),
                
        new CarFilterDataDTO.PriceRangeOption(new BigDecimal("700000001"), new BigDecimal("1200000000"), "BW 700tr and 1T2"),
        new CarFilterDataDTO.PriceRangeOption(new BigDecimal("1200000001"), null, "Above 1T2")
        ));
        
        return filterDataDTO; 
        
    }
    
    public CarListPageDTO searchCars(CarSearchCriteriaDTO criteria, int page, int pageSize){
        if(page < 1) page = 1; 
        if(pageSize <= 0) pageSize=12; 
        List<Car> cars = carDAO.searchCars(criteria, page, pageSize); 
        
        long totalCars = carDAO.countCarsByCriteria(criteria); 
        int totalPages = (int) Math.ceil((double)totalCars/pageSize); 
        
        CarListPageDTO pageDTO = new CarListPageDTO(); 
        pageDTO.setCars(cars);
        pageDTO.setCurrentPage(page);
        pageDTO.setTotalPages(totalPages);
        
        return pageDTO;
    }
}