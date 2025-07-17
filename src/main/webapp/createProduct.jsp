<%-- 
    Document   : createProduct
    Created on : Jun 27, 2025, 1:34:17 PM
    Author     : nguye
--%>

<%@ page import="java.util.Map" %>
<%@ page import="model.Car" %>
<%@ page import="constant.CarStatus" %>

<%! 
    String getError(Map<String, String> errors, String field) {
        if (errors == null) return "";
        String msg = errors.get(field);
        return msg != null ? msg : "";
    }

    String getBorderStyle(Map<String, String> errors, String field) {
        return (errors != null && errors.get(field) != null) ? "border:1px solid red;" : "";
    }
%>

<%
    Map<String, String> errors = (Map<String, String>) request.getAttribute("errors");
    Car car = (Car) request.getAttribute("product");
%>

<form action="ProductManagement" method="post">
    <input type="hidden" name="action" value="create">
    <div>
        VIN:
        <input type="text" name="vin" value="<%= car != null ? car.getVin() : "" %>"
               style="<%= getBorderStyle(errors, "vin") %>">
        <span style="color:red"><%= getError(errors, "vin") %></span>
    </div>

    <div>
        Import Price:
        <input type="text" name="importPrice"
               value="<%= car != null && car.getImportPrice() != null ? car.getImportPrice() : "" %>"
               style="<%= getBorderStyle(errors, "importPrice") %>">
        <span style="color:red"><%= getError(errors, "importPrice") %></span>
    </div>

    <div>
        Selling Price:
        <input type="text" name="sellingPrice"
               value="<%= car != null && car.getSellingPrice() != null ? car.getSellingPrice() : "" %>"
               style="<%= getBorderStyle(errors, "sellingPrice") %>">
        <span style="color:red"><%= getError(errors, "sellingPrice") %></span>
    </div>

    <div>
        Import Date:
        <input type="date" name="importDate"
               value="<%= car != null && car.getImportDate() != null ? car.getImportDate() : "" %>"
               style="<%= getBorderStyle(errors, "importDate") %>">
        <span style="color:red"><%= getError(errors, "importDate") %></span>
    </div>

    <div>
        Status:
        <select name="status" style="<%= getBorderStyle(errors, "status") %>">
            <option value="AVAILABLE" <%= car != null && car.getStatus() == CarStatus.AVAILABLE ? "selected" : "" %>>Available</option>
            <option value="SOLD" <%= car != null && car.getStatus() == CarStatus.SOLD ? "selected" : "" %>>Sold</option>
            <option value="UNAVAILABLE" <%= car != null && car.getStatus() == CarStatus.UNAVAILABLE ? "selected" : "" %>>Unavailable</option>
        </select>
        <span style="color:red"><%= getError(errors, "status") %></span>
    </div>

    <div>
        Car Model ID:
        <input type="text" name="carModelId"
               value="<%= car != null && car.getCarModel() != null ? car.getCarModel().getId() : "" %>"
               style="<%= getBorderStyle(errors, "carModel") %>">
        <span style="color:red"><%= getError(errors, "carModel") %></span>
    </div>

    <input type="submit" value="Create Car">
</form>

