<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>T?o Hóa ??n</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px;
        }

        .container {
            max-width: 900px;
            margin: 0 auto;
            background: white;
            border-radius: 20px;
            box-shadow: 0 20px 40px rgba(0,0,0,0.1);
            overflow: hidden;
        }

        .header {
            background: linear-gradient(135deg, #2c3e50 0%, #34495e 100%);
            color: white;
            padding: 30px;
            text-align: center;
        }

        .header h1 {
            font-size: 2.5em;
            margin-bottom: 10px;
            text-shadow: 2px 2px 4px rgba(0,0,0,0.3);
        }

        .form-container {
            padding: 40px;
        }

        .form-section {
            margin-bottom: 30px;
            padding: 25px;
            border-radius: 15px;
            background: #f8f9fa;
            border-left: 5px solid #667eea;
        }

        .form-section h3 {
            color: #2c3e50;
            margin-bottom: 20px;
            font-size: 1.3em;
            display: flex;
            align-items: center;
        }

        .form-section h3::before {
            content: "?";
            margin-right: 10px;
            font-size: 1.2em;
        }

        .form-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
            margin-bottom: 20px;
        }

        .form-group {
            display: flex;
            flex-direction: column;
        }

        .form-group.full-width {
            grid-column: 1 / -1;
        }

        label {
            font-weight: 600;
            color: #2c3e50;
            margin-bottom: 8px;
            font-size: 0.95em;
        }

        input, textarea, select {
            padding: 12px 15px;
            border: 2px solid #e9ecef;
            border-radius: 10px;
            font-size: 1em;
            transition: all 0.3s ease;
            background: white;
        }

        input:focus, textarea:focus, select:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
            transform: translateY(-2px);
        }

        .car-section {
            background: #fff;
            border: 2px dashed #667eea;
            border-radius: 15px;
            padding: 25px;
        }

        .car-section h3::before {
            content: "?";
        }

        .car-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
        }

        .car-table th {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 15px;
            text-align: left;
            font-weight: 600;
        }

        .car-table td {
            padding: 12px 15px;
            border-bottom: 1px solid #e9ecef;
            background: white;
        }

        .car-table tr:hover {
            background: #f8f9fa;
        }

        .car-table input {
            width: 100%;
            margin: 0;
            border: 1px solid #ddd;
            padding: 8px 10px;
        }

        .btn {
            padding: 12px 25px;
            border: none;
            border-radius: 25px;
            font-size: 1em;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            text-transform: uppercase;
            letter-spacing: 1px;
        }

        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            box-shadow: 0 5px 15px rgba(102, 126, 234, 0.3);
        }

        .btn-primary:hover {
            transform: translateY(-3px);
            box-shadow: 0 8px 25px rgba(102, 126, 234, 0.4);
        }

        .btn-success {
            background: linear-gradient(135deg, #56ab2f 0%, #a8e6cf 100%);
            color: white;
            margin-right: 10px;
        }

        .btn-success:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(86, 171, 47, 0.3);
        }

        .btn-danger {
            background: linear-gradient(135deg, #ff416c 0%, #ff4b2b 100%);
            color: white;
            padding: 8px 15px;
            font-size: 0.9em;
        }

        .btn-danger:hover {
            transform: scale(1.05);
        }

        .btn-submit {
            background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
            color: white;
            padding: 15px 40px;
            font-size: 1.1em;
            margin-top: 20px;
            width: 100%;
        }

        .btn-submit:hover {
            transform: translateY(-3px);
            box-shadow: 0 10px 30px rgba(17, 153, 142, 0.3);
        }

        .error-message {
            background: linear-gradient(135deg, #ff6b6b 0%, #ee5a52 100%);
            color: white;
            padding: 15px;
            border-radius: 10px;
            margin-bottom: 20px;
            text-align: center;
            font-weight: 600;
        }

        .car-controls {
            display: flex;
            justify-content: center;
            margin-bottom: 20px;
        }

        @media (max-width: 768px) {
            .form-row {
                grid-template-columns: 1fr;
            }
            
            .container {
                margin: 10px;
            }
            
            .form-container {
                padding: 20px;
            }
        }

        .animate-in {
            animation: slideInUp 0.6s ease-out;
        }

        @keyframes slideInUp {
            from {
                opacity: 0;
                transform: translateY(30px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
    </style>
</head>
<body>
    <div class="container animate-in">
        <div class="header">
            <h1>? T?o Hóa ??n M?i</h1>
            <p>?i?n thông tin chi ti?t ?? t?o hóa ??n</p>
        </div>

        <div class="form-container">
            <!-- Hi?n th? l?i n?u có -->
            <c:if test="${not empty error}">
                <div class="error-message">
                    ${error}
                </div>
            </c:if>

            <form action="create-order" method="post" id="orderForm">
                <!-- Thông tin c? b?n -->
                <div class="form-section">
                    <h3>Thông tin c? b?n</h3>
                    <div class="form-row">
                        <div class="form-group">
                            <label for="customerId">ID Khách hàng *</label>
                            <input type="number" id="customerId" name="customerId" required min="1">
                        </div>
                        <div class="form-group">
                            <label for="sellerId">ID Nhân viên bán *</label>
                            <input type="number" id="sellerId" name="sellerId" required min="1">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group">
                            <label for="saleDate">Ngày bán</label>
                            <input type="date" id="saleDate" name="saleDate" value="${currentDate}">
                        </div>
                        <div class="form-group">
                            <label for="status">Tr?ng thái</label>
                            <select id="status" name="status">
                                <option value="PENDING">?ang x? lý</option>
                                <option value="COMPLETED">Hoàn thành</option>
                                <option value="CANCELLED">?ã h?y</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group full-width">
                            <label for="note">Ghi chú</label>
                            <textarea id="note" name="note" rows="3" placeholder="Nh?p ghi chú cho ??n hàng..."></textarea>
                        </div>
                    </div>
                </div>

                <!-- Danh sách xe -->
                <div class="form-section car-section">
                    <h3>Danh sách xe trong hóa ??n</h3>
                    
                    <div class="car-controls">
                        <button type="button" class="btn btn-success" onclick="addCarRow()">
                            ? Thêm xe
                        </button>
                    </div>

                    <table class="car-table" id="carTable">
                        <thead>
                            <tr>
                                <th style="width: 15%">ID Xe *</th>
                                <th style="width: 25%">Giá bán (VN?) *</th>
                                <th style="width: 20%">Chi?t kh?u (%)</th>
                                <th style="width: 20%">Thành ti?n</th>
                                <th style="width: 10%">Xóa</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- Dòng xe s? ???c t?o b?ng JavaScript -->
                        </tbody>
                        <tfoot>
                            <tr style="background: #f8f9fa; font-weight: bold;">
                                <td colspan="3" style="text-align: right; padding-right: 20px;">T?ng c?ng:</td>
                                <td id="totalAmount">0 VN?</td>
                                <td></td>
                            </tr>
                        </tfoot>
                    </table>
                </div>

                <button type="submit" class="btn btn-submit" id="submitBtn">
                    ? T?o Hóa ??n
                </button>
            </form>
        </div>
    </div>

    <script>
        // T? ??ng thêm dòng xe ??u tiên khi trang load
        document.addEventListener('DOMContentLoaded', function() {
            addCarRow();
            
            // Set ngày hi?n t?i n?u ch?a có
            const dateInput = document.getElementById('saleDate');
            if (!dateInput.value) {
                const today = new Date().toISOString().split('T')[0];
                dateInput.value = today;
            }
        });

        function addCarRow() {
            const tbody = document.querySelector('#carTable tbody');
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>
                    <input type="number" name="carId" required min="1" onchange="calculateRowTotal(this)">
                </td>
                <td>
                    <input type="number" name="price" required min="0" step="0.01" onchange="calculateRowTotal(this)" placeholder="0.00">
                </td>
                <td>
                    <input type="number" name="discount" min="0" max="100" step="0.01" value="0" onchange="calculateRowTotal(this)" placeholder="0.00">
                </td>
                <td class="row-total">0 VN?</td>
                <td>
                    <button type="button" class="btn btn-danger" onclick="removeCarRow(this)">??</button>
                </td>
            `;
            tbody.appendChild(row);
            
            // Animate new row
            row.style.opacity = '0';
            row.style.transform = 'translateY(-20px)';
            setTimeout(() => {
                row.style.transition = 'all 0.3s ease';
                row.style.opacity = '1';
                row.style.transform = 'translateY(0)';
            }, 10);
        }

        function removeCarRow(button) {
            const row = button.closest('tr');
            const tbody = document.querySelector('#carTable tbody');
            
            // Không xóa n?u ch? còn 1 dòng
            if (tbody.children.length <= 1) {
                alert('Ph?i có ít nh?t m?t xe trong hóa ??n!');
                return;
            }
            
            // Animate remove
            row.style.transition = 'all 0.3s ease';
            row.style.opacity = '0';
            row.style.transform = 'translateX(-100%)';
            
            setTimeout(() => {
                row.remove();
                calculateTotal();
            }, 300);
        }

        function calculateRowTotal(input) {
            const row = input.closest('tr');
            const price = parseFloat(row.querySelector('input[name="price"]').value) || 0;
            const discount = parseFloat(row.querySelector('input[name="discount"]').value) || 0;
            
            const discountAmount = price * (discount / 100);
            const total = price - discountAmount;
            
            const totalCell = row.querySelector('.row-total');
            totalCell.textContent = formatCurrency(total);
            
            calculateTotal();
        }

        function calculateTotal() {
            const rows = document.querySelectorAll('#carTable tbody tr');
            let grandTotal = 0;
            
            rows.forEach(row => {
                const price = parseFloat(row.querySelector('input[name="price"]').value) || 0;
                const discount = parseFloat(row.querySelector('input[name="discount"]').value) || 0;
                const discountAmount = price * (discount / 100);
                const total = price - discountAmount;
                grandTotal += total;
            });
            
            document.getElementById('totalAmount').textContent = formatCurrency(grandTotal);
        }

        function formatCurrency(amount) {
            return new Intl.NumberFormat('vi-VN', {
                style: 'currency',
                currency: 'VND'
            }).format(amount);
        }

        // Validation form tr??c khi submit
        document.getElementById('orderForm').addEventListener('submit', function(e) {
            const carRows = document.querySelectorAll('#carTable tbody tr');
            let hasValidCar = false;
            
            carRows.forEach(row => {
                const carId = row.querySelector('input[name="carId"]').value;
                const price = row.querySelector('input[name="price"]').value;
                
                if (carId && price && parseFloat(price) > 0) {
                    hasValidCar = true;
                }
            });
            
            if (!hasValidCar) {
                e.preventDefault();
                alert('Vui lòng nh?p ít nh?t m?t xe v?i giá h?p l?!');
                return;
            }
            
            // Hi?u ?ng submit
            const submitBtn = document.getElementById('submitBtn');
            submitBtn.innerHTML = '? ?ang x? lý...';
            submitBtn.disabled = true;
            
            // N?u có l?i, enable l?i button
            setTimeout(() => {
                submitBtn.innerHTML = '? T?o Hóa ??n';
                submitBtn.disabled = false;
            }, 3000);
        });
    </script>
</body>
</html>