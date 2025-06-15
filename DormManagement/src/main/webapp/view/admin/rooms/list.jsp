<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Room Management - Dorm Management System</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css" rel="stylesheet">
        <style>
            .sidebar {
                min-height: 100vh;
                background-color: #343a40;
                color: white;
            }
            .sidebar .nav-link {
                color: rgba(255,255,255,.75);
            }
            .sidebar .nav-link:hover {
                color: rgba(255,255,255,1);
            }
            .sidebar .nav-link.active {
                color: white;
                background-color: rgba(255,255,255,.1);
            }
        </style>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <!-- Sidebar -->
                <div class="col-md-3 col-lg-2 px-0 sidebar">
                    <div class="p-3">
                        <h4>Admin Panel</h4>
                        <hr>
                        <ul class="nav flex-column">
                            <li class="nav-item">
                                <a class="nav-link" href="../dashboard">
                                    <i class="bi bi-speedometer2"></i> Dashboard
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="../buildings">
                                    <i class="bi bi-building"></i> Buildings
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link active" href="../rooms">
                                    <i class="bi bi-door-open"></i> Rooms
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="../students">
                                    <i class="bi bi-people"></i> Students
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="../services">
                                    <i class="bi bi-gear"></i> Services
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="../invoices">
                                    <i class="bi bi-receipt"></i> Invoices
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="../register-forms">
                                    <i class="bi bi-file-text"></i> Register Forms
                                </a>
                            </li>
                            <li class="nav-item mt-3">
                                <a class="nav-link text-danger" href="../../logout">
                                    <i class="bi bi-box-arrow-right"></i> Logout
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>

                <!-- Main Content -->
                <div class="col-md-9 col-lg-10 p-4">
                    <div class="d-flex justify-content-between align-items-center mb-4">
                        <h2>Room Management</h2>
                        <a href="add" class="btn btn-primary">
                            <i class="bi bi-plus-circle"></i> Add New Room
                        </a>
                    </div>

                    <c:if test="${not empty error}">
                        <div class="alert alert-danger" role="alert">
                            ${error}
                        </div>
                    </c:if>

                    <div class="card">
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                            <th>Room ID</th>
                                            <th>Room Number</th>
                                            <th>Building</th>
                                            <th>Room Type</th>
                                            <th>Status</th>
                                            <th>Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="room" items="${rooms}">
                                            <tr>
                                                <td>${room.roomId}</td>
                                                <td>${room.roomNumber}</td>
                                                <td>${room.building.buildingName}</td>
                                                <td>${room.roomType.roomTypeName}</td>
                                                <td>
                                                    <span class="badge bg-${room.status == 'available' ? 'success' : 'danger'}">
                                                        ${room.status}
                                                    </span>
                                                </td>
                                                <td>
                                                    <a href="view?id=${room.roomId}" class="btn btn-info btn-sm">
                                                        <i class="bi bi-eye"></i>
                                                    </a>
                                                    <a href="edit?id=${room.roomId}" class="btn btn-warning btn-sm">
                                                        <i class="bi bi-pencil"></i>
                                                    </a>
                                                    <button type="button" class="btn btn-danger btn-sm" 
                                                            onclick="deleteRoom(${room.roomId})">
                                                        <i class="bi bi-trash"></i>
                                                    </button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Delete Confirmation Modal -->
        <div class="modal fade" id="deleteModal" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Confirm Delete</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        Are you sure you want to delete this room?
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                        <form id="deleteForm" method="POST" action="delete">
                            <input type="hidden" name="id" id="deleteRoomId">
                            <button type="submit" class="btn btn-danger">Delete</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            function deleteRoom(roomId) {
                document.getElementById('deleteRoomId').value = roomId;
                var modal = new bootstrap.Modal(document.getElementById('deleteModal'));
                modal.show();
            }
        </script>
    </body>
</html> 