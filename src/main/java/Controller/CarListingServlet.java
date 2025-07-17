package Controller;

import dto.CarFilterDataDTO;
import dto.CarListPageDTO;
import dto.CarSearchCriteriaDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import service.car.CarService;

@WebServlet("/cars")
public class CarListingServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_PAGE_SIZE = 10;
    private final CarService carService = new CarService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        int page = safeParseInt(req.getParameter("page"), 1);
        if (page < 1) page = 1;

        CarSearchCriteriaDTO criteria = buildCriteriaFromRequest(req);

        // validate min/max price
        if (criteria.getMinPrice() != null && criteria.getMaxPrice() != null
                && criteria.getMinPrice().compareTo(criteria.getMaxPrice()) > 0) {
            req.setAttribute("error", "Min price cannot be greater than max price");
            BigDecimal tmp = criteria.getMinPrice();
            criteria.setMinPrice(criteria.getMaxPrice());
            criteria.setMaxPrice(tmp);
        }

        CarFilterDataDTO filterData = carService.getFilterData();
        CarListPageDTO pageData  = carService.searchCars(criteria, page, DEFAULT_PAGE_SIZE);

        // nếu page lớn hơn totalPages sau khi truy vấn thì load lại trang cuối
        if (page > pageData.getTotalPages()) {
            page = pageData.getTotalPages();
            pageData = carService.searchCars(criteria, page, DEFAULT_PAGE_SIZE);
        }

        req.setAttribute("filterData", filterData);
        req.setAttribute("pageData",  pageData);
        req.setAttribute("searchCriteria", criteria);

        req.getRequestDispatcher("/car_listing.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);   // reuse logic
    }

    // ---------- helper ----------
    private int safeParseInt(String s, int defaultVal) {
        try { return (s == null || s.isBlank()) ? defaultVal : Integer.parseInt(s); }
        catch (NumberFormatException e) { return defaultVal; }
    }

    private Integer safeParseInteger(String s) {
        try { return (s == null || s.isBlank()) ? null : Integer.valueOf(s); }
        catch (NumberFormatException e) { return null; }
    }

    private BigDecimal safeParseBigDecimal(String s) {
        try { return (s == null || s.isBlank()) ? null : new BigDecimal(s); }
        catch (NumberFormatException e) { return null; }
    }

    private CarSearchCriteriaDTO buildCriteriaFromRequest(HttpServletRequest req) {
        CarSearchCriteriaDTO c = new CarSearchCriteriaDTO();
        c.setKeyword(req.getParameter("keyword"));
        c.setManufactureId( safeParseInteger(req.getParameter("manufactureId")) );
        c.setCarTypeId(     safeParseInteger(req.getParameter("carTypeId")) );
        c.setYear(          safeParseInteger(req.getParameter("year")) );
        c.setColorId(       safeParseInteger(req.getParameter("colorId")) );
        c.setEngineTypeId(  safeParseInteger(req.getParameter("engineTypeId")) );
        c.setMinPrice(      safeParseBigDecimal(req.getParameter("minPrice")) );
        c.setMaxPrice(      safeParseBigDecimal(req.getParameter("maxPrice")) );
        return c;
    }
}
