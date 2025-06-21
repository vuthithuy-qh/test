-- Script cập nhật database để thêm các cột mới cho chức năng đăng ký
-- Thêm cột StudentCode (Mã sinh viên) vào bảng Students

-- Kiểm tra xem cột StudentCode đã tồn tại chưa
IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.COLUMNS 
               WHERE TABLE_NAME = 'Students' AND COLUMN_NAME = 'StudentCode')
BEGIN
    ALTER TABLE Students ADD StudentCode VARCHAR(12);
    PRINT 'Đã thêm cột StudentCode vào bảng Students';
END
ELSE
BEGIN
    -- Cập nhật độ dài cột nếu đã tồn tại
    ALTER TABLE Students ALTER COLUMN StudentCode VARCHAR(12);
    PRINT 'Đã cập nhật độ dài cột StudentCode thành VARCHAR(12)';
END

-- Kiểm tra xem cột CCCD đã tồn tại chưa
IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.COLUMNS 
               WHERE TABLE_NAME = 'Students' AND COLUMN_NAME = 'CCCD')
BEGIN
    ALTER TABLE Students ADD CCCD VARCHAR(12);
    PRINT 'Đã thêm cột CCCD vào bảng Students';
END
ELSE
BEGIN
    PRINT 'Cột CCCD đã tồn tại trong bảng Students';
END

-- Tạo index cho các cột mới để tối ưu hiệu suất tìm kiếm
IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_Students_StudentCode')
BEGIN
    CREATE INDEX IX_Students_StudentCode ON Students(StudentCode);
    PRINT 'Đã tạo index cho cột StudentCode';
END

IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_Students_CCCD')
BEGIN
    CREATE INDEX IX_Students_CCCD ON Students(CCCD);
    PRINT 'Đã tạo index cho cột CCCD';
END

-- Thêm ràng buộc UNIQUE cho StudentCode và CCCD (tùy chọn)
-- Uncomment các dòng dưới nếu muốn đảm bảo mã sinh viên và CCCD là duy nhất

-- IF NOT EXISTS (SELECT * FROM sys.objects WHERE name = 'UQ_Students_StudentCode')
-- BEGIN
--     ALTER TABLE Students ADD CONSTRAINT UQ_Students_StudentCode UNIQUE (StudentCode);
--     PRINT 'Đã thêm ràng buộc UNIQUE cho StudentCode';
-- END

-- IF NOT EXISTS (SELECT * FROM sys.objects WHERE name = 'UQ_Students_CCCD')
-- BEGIN
--     ALTER TABLE Students ADD CONSTRAINT UQ_Students_CCCD UNIQUE (CCCD);
--     PRINT 'Đã thêm ràng buộc UNIQUE cho CCCD';
-- END

PRINT 'Hoàn thành cập nhật database!'; 