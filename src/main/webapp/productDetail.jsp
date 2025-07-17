<%-- 
    Document   : productDetail
    Created on : Jul 02, 2025
    Author     : nguye
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Car" %>
<%@ page import="constant.CarStatus" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%
    Car product = (Car) request.getAttribute("product");
    if (product == null) {
        response.sendRedirect("ProductManagement");
        return;
    }
    
    // Format tiền tệ VND
    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi tiết sản phẩm - <%= product.getVin() %></title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 20px;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background: white;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            overflow: hidden;
        }
        .header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 30px;
            text-align: center;
        }
        .header h1 {
            margin: 0;
            font-size: 2.5em;
            font-weight: 300;
        }
        .header p {
            margin: 10px 0 0 0;
            opacity: 0.9;
            font-size: 1.1em;
        }
        .content {
            padding: 40px;
        }
        .detail-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 30px;
            margin-bottom: 30px;
        }
        .detail-item {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 8px;
            border-left: 4px solid #667eea;
        }
        .detail-label {
            font-weight: 600;
            color: #495057;
            font-size: 0.9em;
            text-transform: uppercase;
            letter-spacing: 1px;
            margin-bottom: 8px;
        }
        .detail-value {
            font-size: 1.2em;
            color: #212529;
            font-weight: 500;
        }
        .status-badge {
            display: inline-block;
            padding: 8px 16px;
            border-radius: 20px;
            font-weight: 600;
            text-transform: uppercase;
            font-size: 0.85em;
            letter-spacing: 1px;
        }
        .status-available {
            background: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        .status-sold {
            background: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        .status-unavailable {
            background: #fff3cd;
            color: #856404;
            border: 1px solid #ffeaa7;
        }
        .actions {
            display: flex;
            gap: 15px;
            justify-content: center;
            padding: 30px;
            border-top: 1px solid #dee2e6;
            background: #f8f9fa;
        }
        .btn {
            padding: 12px 24px;
            border: none;
            border-radius: 6px;
            text-decoration: none;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 1px;
            cursor: pointer;
            transition: all 0.3s ease;
            font-size: 0.9em;
        }
        .btn-primary {
            background: #007bff;
            color: white;
        }
        .btn-primary:hover {
            background: #0056b3;
            transform: translateY(-2px);
        }
        .btn-success {
            background: #28a745;
            color: white;
        }
        .btn-success:hover {
            background: #1e7e34;
            transform: translateY(-2px);
        }
        .btn-secondary {
            background: #6c757d;
            color: white;
        }
        .btn-secondary:hover {
            background: #545b62;
            transform: translateY(-2px);
        }
        .profit-info {
            background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
            color: white;
            padding: 20px;
            border-radius: 8px;
            text-align: center;
            margin-top: 20px;
        }
        .profit-value {
            font-size: 1.8em;
            font-weight: bold;
            margin-top: 5px;
        }
        .section-title {
            font-size: 1.3em;
            font-weight: 600;
            color: #495057;
            margin: 30px 0 20px 0;
            padding-bottom: 10px;
            border-bottom: 2px solid #667eea;
            position: relative;
        }
        .section-title:first-of-type {
            margin-top: 0;
        }
        .section-title::after {
            content: '';
            position: absolute;
            bottom: -2px;
            left: 0;
            width: 50px;
            height: 2px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        }
        @media (max-width: 768px) {
            .detail-grid {
                grid-template-columns: 1fr;
                gap: 20px;
            }
            .actions {
                flex-direction: column;
            }
            .btn {
                text-align: center;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1><%= product.getVin() %></h1>
            <p>Chi tiết thông tin xe</p>
        </div>
        
        <div class="content">
            <!-- Thông tin cơ bản -->
            <div class="section-title">Thông tin cơ bản</div>
            <div class="detail-grid">
                <div class="detail-item">
                    <div class="detail-label">VIN Number</div>
                    <div class="detail-value"><%= product.getVin() %></div>
                </div>
                
                <div class="detail-item">
                    <div class="detail-label">Trạng thái</div>
                    <div class="detail-value">
                        <%
                            String statusClass = "";
                            String statusText = "";
                            if (product.getStatus() == CarStatus.AVAILABLE) {
                                statusClass = "status-available";
                                statusText = "Có sẵn";
                            } else if (product.getStatus() == CarStatus.SOLD) {
                                statusClass = "status-sold";
                                statusText = "Đã bán";
                            } else {
                                statusClass = "status-unavailable";
                                statusText = "Không có sẵn";
                            }
                        %>
                        <span class="status-badge <%= statusClass %>"><%= statusText %></span>
                    </div>
                </div>
                
                <div class="detail-item">
                    <div class="detail-label">Ngày nhập</div>
                    <div class="detail-value">
                        <%= product.getImportDate() != null ? dateFormat.format(product.getImportDate()) : "Chưa có thông tin" %>
                    </div>
                </div>
                
                <div class="detail-item">
                    <div class="detail-label">ID Sản phẩm</div>
                    <div class="detail-value">#<%= product.getId() %></div>
                </div>
            </div>

            <!-- Thông tin model và loại xe -->
            <div class="section-title">Thông tin xe</div>
            <div class="detail-grid">
                <div class="detail-item">
                    <div class="detail-label">Tên Model</div>
                    <div class="detail-value">
                        <%= product.getCarModel() != null ? product.getCarModel().getName() : "Chưa có thông tin" %>
                    </div>
                </div>
                
                <div class="detail-item">
                    <div class="detail-label">Năm sản xuất</div>
                    <div class="detail-value">
                        <%= product.getCarModel() != null && product.getCarModel().getYear() != null ? 
                            product.getCarModel().getYear() : "Chưa có thông tin" %>
                    </div>
                </div>
                
                <div class="detail-item">
                    <div class="detail-label">Hãng sản xuất</div>
                    <div class="detail-value">
                        <%= product.getCarModel() != null && product.getCarModel().getManufacturer() != null ? 
                            product.getCarModel().getManufacturer() : "Chưa có thông tin" %>
                    </div>
                </div>
                
                <div class="detail-item">
                    <div class="detail-label">Loại xe</div>
                    <div class="detail-value">
                        <%= product.getCarModel() != null && product.getCarModel().getCarType() != null ? 
                            product.getCarModel().getCarType().getName() : "Chưa có thông tin" %>
                        <% if (product.getCarModel() != null && product.getCarModel().getCarType() != null && 
                               product.getCarModel().getCarType().getDescription() != null) { %>
                        <br><small style="color: #6c757d;"><%= product.getCarModel().getCarType().getDescription() %></small>
                        <% } %>
                    </div>
                </div>
            </div>

            <!-- Thông tin động cơ -->
            <div class="section-title">Thông tin động cơ</div>
            <div class="detail-grid">
                <div class="detail-item">
                    <div class="detail-label">Loại động cơ</div>
                    <div class="detail-value">
                        <%= product.getCarModel() != null && product.getCarModel().getEngineType() != null ? 
                            product.getCarModel().getEngineType().getName() : "Chưa có thông tin" %>
                    </div>
                </div>
                
                <div class="detail-item">
                    <div class="detail-label">Loại nhiên liệu</div>
                    <div class="detail-value">
                        <%= product.getCarModel() != null && product.getCarModel().getEngineType() != null && 
                            product.getCarModel().getEngineType().getFuelType() != null ? 
                            product.getCarModel().getEngineType().getFuelType() : "Chưa có thông tin" %>
                    </div>
                </div>
                
                <div class="detail-item">
                    <div class="detail-label">Công suất</div>
                    <div class="detail-value">
                        <%= product.getCarModel() != null && product.getCarModel().getEngineType() != null && 
                            product.getCarModel().getEngineType().getHorsepower() != null ? 
                            product.getCarModel().getEngineType().getHorsepower() + " HP" : "Chưa có thông tin" %>
                    </div>
                </div>
                
                <div class="detail-item">
                    <div class="detail-label">Dung tích</div>
                    <div class="detail-value">
                        <%= product.getCarModel() != null && product.getCarModel().getEngineType() != null && 
                            product.getCarModel().getEngineType().getDisplacement() != null ? 
                            product.getCarModel().getEngineType().getDisplacement() : "Chưa có thông tin" %>
                    </div>
                </div>
            </div>

            <!-- Thông tin màu sắc và giá cả -->
            <div class="section-title">Màu sắc & Giá cả</div>
            <div class="detail-grid">
                <div class="detail-item">
                    <div class="detail-label">Màu sắc</div>
                    <div class="detail-value">
                        <% if (product.getCarModel() != null && product.getCarModel().getCarColor() != null) { %>
                            <div style="display: flex; align-items: center; gap: 10px;">
                                <% if (product.getCarModel().getCarColor().getHexCode() != null) { %>
                                <div style="width: 20px; height: 20px; border-radius: 50%; background-color: <%= product.getCarModel().getCarColor().getHexCode() %>; border: 1px solid #ddd;"></div>
                                <% } %>
                                <span><%= product.getCarModel().getCarColor().getName() %></span>
                            </div>
                            <% if (product.getCarModel().getCarColor().getDescription() != null) { %>
                            <small style="color: #6c757d;"><%= product.getCarModel().getCarColor().getDescription() %></small>
                            <% } %>
                        <% } else { %>
                            Chưa có thông tin
                        <% } %>
                    </div>
                </div>
                
                <div class="detail-item">
                    <div class="detail-label">Giá nhập</div>
                    <div class="detail-value">
                        <%= product.getImportPrice() != null ? currencyFormat.format(product.getImportPrice()) : "Chưa có thông tin" %>
                    </div>
                </div>
                
                <div class="detail-item">
                    <div class="detail-label">Giá bán</div>
                    <div class="detail-value">
                        <%= product.getSellingPrice() != null ? currencyFormat.format(product.getSellingPrice()) : "Chưa có thông tin" %>
                    </div>
                </div>
                
                <div class="detail-item">
                    <div class="detail-label">Model ID</div>
                    <div class="detail-value">
                        #<%= product.getCarModel() != null ? product.getCarModel().getId() : "N/A" %>
                    </div>
                </div>
            </div>
            
            <%
                // Tính toán lợi nhuận nếu có đầy đủ thông tin
                if (product.getImportPrice() != null && product.getSellingPrice() != null) {
                    java.math.BigDecimal profit = product.getSellingPrice().subtract(product.getImportPrice());
                    double profitPercent = profit.divide(product.getImportPrice(), 4, java.math.RoundingMode.HALF_UP)
                                              .multiply(new java.math.BigDecimal(100)).doubleValue();
            %>
            <div class="profit-info">
                <div class="detail-label" style="color: rgba(255,255,255,0.9);">Lợi nhuận dự kiến</div>
                <div class="profit-value">
                    <%= currencyFormat.format(profit) %>
                    <small style="font-size: 0.6em; opacity: 0.9;">
                        (<%= String.format("%.2f", profitPercent) %>%)
                    </small>
                </div>
            </div>
            <% } %>
        </div>
        
        <div class="actions">
            <a href="ProductManagement?action=edit&productId=<%= product.getId() %>" class="btn btn-primary">
                ✏️ Chỉnh sửa
            </a>
            <a href="ProductManagement" class="btn btn-secondary">
                ← Quay lại danh sách
            </a>
            <% if (product.getStatus() == CarStatus.AVAILABLE) { %>
            <a href="#" onclick="markAsSold(<%= product.getId() %>)" class="btn btn-success">
                💰 Đánh dấu đã bán
            </a>
            <% } %>
        </div>
    </div>

    <script>
        function markAsSold(productId) {
            if (confirm('Bạn có chắc chắn muốn đánh dấu xe này là đã bán?')) {
                // Tạo form ẩn để submit
                const form = document.createElement('form');
                form.method = 'POST';
                form.action = 'ProductManagement';
                
                const actionInput = document.createElement('input');
                actionInput.type = 'hidden';
                actionInput.name = 'action';
                actionInput.value = 'markSold';
                
                const idInput = document.createElement('input');
                idInput.type = 'hidden';
                idInput.name = 'productId';
                idInput.value = productId;
                
                form.appendChild(actionInput);
                form.appendChild(idInput);
                document.body.appendChild(form);
                form.submit();
            }
        }
    </script>
</body>
</html>