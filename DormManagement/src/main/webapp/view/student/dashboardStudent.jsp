<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Dashboard - Quản Lý Ký Túc Xá FPT</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
        }
        .sidebar, .main-content {
            background: rgba(255, 255, 255, 0.95);
            backdrop-filter: blur(10px);
            border-radius: 15px;
            box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
            padding: 20px;
            margin: 20px;
        }
        .main-content {
            padding: 30px;
        }
        .nav-link {
            color: #333;
            padding: 12px 15px;
            border-radius: 10px;
            margin-bottom: 5px;
            transition: all 0.3s ease;
        }
        .nav-link:hover, .nav-link.active {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            transform: translateX(5px);
        }
        .card {
            border: none;
            border-radius: 15px;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease;
        }
        .card:hover {
            transform: translateY(-5px);
        }
        .welcome-section {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border-radius: 15px;
            padding: 30px;
            margin-bottom: 30px;
        }
        .stat-card {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border-radius: 15px;
            padding: 20px;
            text-align: center;
        }
        .stat-card.secondary {
            background: linear-gradient(135deg, #ff6200 0%, #e65c00 100%);
        }
        .stat-card.success {
            background: linear-gradient(135deg, #28a745 0%, #20c997 100%);
        }
        .stat-card.info {
            background: linear-gradient(135deg, #17a2b8 0%, #6f42c1 100%);
        }
    </style>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <!-- Sidebar -->
        <div class="col-md-3">
            <div class="sidebar">
                <div class="text-center mb-4">
                    <h4 class="fw-bold text-primary">Student Portal</h4>
                    <p class="text-muted">Ký Túc Xá FPT</p>
                </div>
                <nav class="nav flex-column">
                    <a class="nav-link active" href="#dashboard"><i class="fas fa-tachometer-alt me-2"></i> Dashboard</a>
                    <a class="nav-link" href="#profile" id="profile-link"><i class="fas fa-user me-2"></i> Thông tin cá nhân</a>
                    <a class="nav-link" href="#room"><i class="fas fa-bed me-2"></i> Thông tin phòng</a>
                    <a class="nav-link" href="#payment"><i class="fas fa-credit-card me-2"></i> Thanh toán</a>
                    <a class="nav-link" href="#maintenance"><i class="fas fa-tools me-2"></i> Bảo trì</a>
                    <a class="nav-link" href="#announcements"><i class="fas fa-bullhorn me-2"></i> Thông báo</a>
                    <a class="nav-link" href="#logout"><i class="fas fa-sign-out-alt me-2"></i> Đăng xuất</a>
                </nav>
            </div>
        </div>

        <!-- Main Content -->
        <div class="col-md-9">
            <div class="main-content">
                <div class="welcome-section">
                    <h2><i class="fas fa-user-graduate me-2"></i>Chào mừng, ${sessionScope.student.fullName}!</h2>
                    <p class="mb-0">Họ và tên: ${sessionScope.student.fullName}</p>
                    <p class="mb-0">Email: ${sessionScope.student.email}</p>
                    <p class="mb-0">Trạng thái: ${sessionScope.student.statusRoom}</p>
                </div>

                <!-- Stats -->
                <div class="row mb-4">
                    <div class="col-md-3">
                        <div class="stat-card">
                            <i class="fas fa-bed fa-2x mb-2"></i>
                            <h4>Chưa có thông tin phòng</h4>
                            <p class="mb-0">Vui lòng cập nhật</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="stat-card secondary">
                            <i class="fas fa-calendar-alt fa-2x mb-2"></i>
                            <h4>Chưa có ngày hết hạn</h4>
                            <p class="mb-0">Vui lòng cập nhật</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="stat-card success">
                            <i class="fas fa-check-circle fa-2x mb-2"></i>
                            <h4>Chưa có thông tin thanh toán</h4>
                            <p class="mb-0">Vui lòng cập nhật</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="stat-card info">
                            <i class="fas fa-bell fa-2x mb-2"></i>
                            <h4>0</h4>
                            <p class="mb-0">Thông báo mới</p>
                        </div>
                    </div>
                </div>

                <!-- Activities and Announcements -->
                <div class="row">
                    <div class="col-md-6">
                        <div class="card">
                            <div class="card-header bg-primary text-white">
                                <h5 class="mb-0"><i class="fas fa-history me-2"></i>Hoạt động gần đây</h5>
                            </div>
                            <div class="card-body">
                                <div class="text-center text-muted">Chưa có hoạt động nào gần đây.</div>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="card">
                            <div class="card-header bg-warning text-white">
                                <h5 class="mb-0"><i class="fas fa-bullhorn me-2"></i>Thông báo mới</h5>
                            </div>
                            <div class="card-body">
                                <div class="alert alert-info mb-0 text-center">
                                    Chưa có thông báo mới.
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>

<!-- Modal cập nhật thông tin cá nhân -->
<div class="modal fade" id="profileModal" tabindex="-1" aria-labelledby="profileModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="profileModalLabel">Cập nhật thông tin cá nhân</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <form id="profileForm">
          <div class="mb-3">
            <label for="fullName" class="form-label">Họ và tên</label>
            <input type="text" class="form-control" id="fullName" name="fullName" placeholder="Nhập họ và tên">
          </div>
          <div class="mb-3">
            <label for="gender" class="form-label">Giới tính</label>
            <select class="form-select" id="gender" name="gender">
              <option value="">Chọn giới tính</option>
              <option value="Nam">Nam</option>
              <option value="Nữ">Nữ</option>
              <option value="Khác">Khác</option>
            </select>
          </div>
          <div class="mb-3">
            <label for="address" class="form-label">Địa chỉ</label>
            <input type="text" class="form-control" id="address" name="address" placeholder="Nhập địa chỉ">
          </div>
          <div class="mb-3">
            <label for="phone" class="form-label">Số điện thoại</label>
            <input type="text" class="form-control" id="phone" name="phone" placeholder="Nhập số điện thoại">
          </div>
          <button type="submit" class="btn btn-primary">Cập nhật</button>
        </form>
      </div>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Xử lý chuyển tab và logout
    document.querySelectorAll('.nav-link').forEach(link => {
        link.addEventListener('click', function (e) {
            if (this.getAttribute('href') === '#logout') {
                e.preventDefault();
                if (confirm('Bạn có chắc muốn đăng xuất?')) {
                    window.location.href = '${pageContext.request.contextPath}/logout';
                }
            } else if (this.getAttribute('href') === '#profile') {
                e.preventDefault();
                var profileModal = new bootstrap.Modal(document.getElementById('profileModal'));
                profileModal.show();
            } else {
                document.querySelectorAll('.nav-link').forEach(l => l.classList.remove('active'));
                this.classList.add('active');
            }
        });
    });
</script>
</body>
</html>
