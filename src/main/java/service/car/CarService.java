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
import java.util.Optional;
import model.Car;
import model.CarType;
import util.CarValidation;

public class CarService{
    private final CarDAO carDAO= new CarDAO(); 
    private final ManufactureDAO manufactureDAO = new ManufactureDAO(); 
    private final CarTypeDAO carTypeDAO = new CarTypeDAO(); 
    private final CarModelDAO carModelDAO= new CarModelDAO();
    private final CarColorDAO carColorDAO = new CarColorDAO(); 
    private final EngineTypeDAO engineTypeDAO = new EngineTypeDAO(); 
    //lay du lieu cho cac dropdown cua bi loc
    
    /**
     * Retrieves all filter data used for car search filtering UI components.
     * This includes car manufacturers, types, years, colors, engine types, and
     * predefined price ranges.
     *
     * @return a {@link CarFilterDataDTO} object containing all filter options
     */
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
    
    
    /**
     * Searches for cars that match the specified search criteria with
     * pagination. This method returns a paginated list of cars and total page
     * information based on the given criteria.
     *
     * @param criteria the {@link CarSearchCriteriaDTO} containing filter and
     * search keywords
     * @param page the current page number (1-based index)
     * @param pageSize the number of cars per page
     * @return a {@link CarListPageDTO} containing a list of cars and pagination
     * metadata
     */
    public CarListPageDTO searchCars(CarSearchCriteriaDTO criteria, int page, int pageSize){
        if(page < 1) page = 1; 
        if(pageSize <= 0) pageSize=12; 
        List<Car> cars = carDAO.searchCars(criteria, page, pageSize); 
        
        long totalCars = carDAO.countCarsByCriteria(criteria);  // dem tong so xe thoa man dieu kien
        int totalPages = (int) Math.ceil((double)totalCars/pageSize); 
        
        CarListPageDTO pageDTO = new CarListPageDTO(); 
        pageDTO.setCars(cars);
        pageDTO.setCurrentPage(page);
        pageDTO.setTotalPages(totalPages);
        
        return pageDTO;
    }
    
    public Optional<Car> findById(int id) {
        return carDAO.findById((int) id);
    }
    
     public boolean createNewProduct(Car car) {
        if (!CarValidation.validateCar(car, "create").isEmpty()) return false;
        return carDAO.createCar(car);
    }

    public boolean updateProduct(Car car) {
        if (!CarValidation.validateCar(car, "update").isEmpty()) return false;
        return carDAO.updateCar(car);
    }

    public boolean deleteProductById(int carId) {
        if (carId==0 || !CarValidation.isCarExist(null, carId)) return false;
        return carDAO.deleteCarById(carId);
    }

}