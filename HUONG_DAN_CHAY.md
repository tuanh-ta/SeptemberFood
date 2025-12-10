# Hướng dẫn chi tiết chạy ứng dụng September Food trên Android Studio

## 📋 Yêu cầu trước khi bắt đầu

- ✅ Android Studio Hedgehog (2023.1.1) trở lên
- ✅ JDK 17 đã cài đặt
- ✅ Android SDK (API 24 trở lên)
- ✅ Kết nối internet (để tải dependencies lần đầu)

---

## 🚀 Các bước thực hiện

### BƯỚC 1: Mở dự án trong Android Studio

1. **Khởi động Android Studio**
   ```
   Mở Android Studio từ Start Menu hoặc Desktop
   ```

2. **Mở dự án:**
   - Cách 1: Chọn **File** → **Open** → Chọn thư mục `SeptemberFood`
   - Cách 2: Nếu Android Studio đang mở, chọn **File** → **Open Recent** → Chọn dự án
   - Cách 3: Kéo thả thư mục `SeptemberFood` vào cửa sổ Android Studio

3. **Đợi Android Studio load dự án:**
   - Android Studio sẽ tự động bắt đầu đồng bộ Gradle
   - Bạn sẽ thấy thanh tiến trình ở dưới cùng: "Gradle sync in progress..."
   - ⏱️ **Lần đầu tiên có thể mất 5-10 phút** để tải tất cả dependencies

---

### BƯỚC 2: Kiểm tra và cấu hình

#### 2.1. Kiểm tra Gradle Sync

- Nếu thấy thông báo lỗi, nhấn **Sync Now** hoặc **Try Again**
- Nếu thành công, bạn sẽ thấy "Gradle sync finished" ở thanh trạng thái

#### 2.2. Kiểm tra SDK và JDK

1. Vào **File** → **Project Structure** (hoặc nhấn `Ctrl + Alt + Shift + S`)
2. Tab **SDK Location**:
   - ✅ **Android SDK location**: Đường dẫn đến Android SDK
   - ✅ **JDK location**: Đường dẫn đến JDK 17
3. Tab **Modules** → **app**:
   - ✅ **Compile SDK Version**: 34
   - ✅ **Min SDK Version**: 24
   - ✅ **Target SDK Version**: 34

#### 2.3. Cài đặt thiếu (nếu có)

Nếu thiếu SDK hoặc Build Tools:
1. Vào **Tools** → **SDK Manager**
2. Tab **SDK Platforms**: Chọn **Android 14.0 (API 34)**
3. Tab **SDK Tools**: Chọn **Android SDK Build-Tools**
4. Nhấn **Apply** và đợi cài đặt

---

### BƯỚC 3: Chạy ứng dụng

#### 🎯 Tùy chọn 1: Chạy trên Emulator (Android Virtual Device)

**A. Tạo Emulator mới (nếu chưa có):**

1. Chọn **Tools** → **Device Manager**
2. Nhấn **Create Device** (hoặc biểu tượng ➕)
3. Chọn thiết bị (ví dụ: **Pixel 5** hoặc **Pixel 6**)
4. Nhấn **Next**
5. Chọn hệ điều hành:
   - Khuyến nghị: **API 33 (Android 13)** hoặc **API 34 (Android 14)**
   - Nếu chưa có, nhấn **Download** bên cạnh
6. Nhấn **Next** → **Finish**

**B. Chạy ứng dụng:**

1. Ở thanh toolbar phía trên, chọn emulator từ dropdown (bên cạnh nút Run)
2. Nhấn nút **Run** (▶️ màu xanh) hoặc nhấn **Shift + F10**
3. Hoặc chọn **Run** → **Run 'app'**
4. Đợi ứng dụng build và khởi động trên emulator
5. ⏱️ Lần đầu build có thể mất 2-5 phút

---

#### 📱 Tùy chọn 2: Chạy trên thiết bị thật

**A. Bật USB Debugging trên điện thoại:**

1. Vào **Settings** → **About Phone**
2. Tìm **Build Number** và nhấn **7 lần** liên tiếp
3. Quay lại **Settings** → **Developer Options** (hoặc **System** → **Developer Options**)
4. Bật **USB Debugging**
5. Bật **Install via USB** (nếu có)

**B. Kết nối và chạy:**

1. Kết nối điện thoại với máy tính qua USB
2. Trên điện thoại, chấp nhận thông báo **"Allow USB Debugging"** → Chọn **Always allow** → **OK**
3. Trong Android Studio, thiết bị sẽ xuất hiện trong dropdown
4. Chọn thiết bị và nhấn **Run** (▶️)
5. Đợi ứng dụng cài đặt và chạy

**Kiểm tra thiết bị được nhận diện:**
- Mở **Terminal** trong Android Studio (View → Tool Windows → Terminal)
- Gõ: `adb devices`
- Nếu thấy thiết bị, bạn đã kết nối thành công

---

### BƯỚC 4: Build APK để cài đặt thủ công

#### 📦 Build APK Debug (để test)

1. Chọn **Build** → **Build Bundle(s) / APK(s)** → **Build APK(s)**
2. Đợi quá trình build hoàn tất (xem thanh tiến trình ở dưới)
3. Khi hoàn tất, sẽ có thông báo **"Build completed successfully"**
4. Nhấn **locate** trong thông báo để mở thư mục chứa APK
5. Hoặc tìm file tại: `app/build/outputs/apk/debug/app-debug.apk`

**Cài đặt APK trên điện thoại:**
- Copy file APK vào điện thoại (qua USB, email, hoặc cloud)
- Trên điện thoại: **Settings** → **Security** → Bật **Install from Unknown Sources**
- Mở file APK và nhấn **Install**

---

#### 📦 Build APK Release (để xuất bản)

**Lần đầu tiên - Tạo keystore:**

1. Chọn **Build** → **Generate Signed Bundle / APK**
2. Chọn **APK** → **Next**
3. Nhấn **Create new...** để tạo keystore mới
4. Điền thông tin:
   ```
   Key store path: [Chọn vị trí lưu, ví dụ: D:\keystore\septemberfood.jks]
   Password: [Nhập mật khẩu, nhớ lưu lại!]
   Confirm: [Nhập lại mật khẩu]
   
   Key alias: septemberfood
   Key password: [Nhập mật khẩu cho key]
   Validity (years): 25
   Certificate: [Điền thông tin nếu muốn]
   ```
5. Nhấn **OK** → **Next**

**Build APK Release:**

1. Chọn keystore vừa tạo
2. Nhập **Key store password** và **Key password**
3. Chọn **release** trong **Build Variants**
4. Nhấn **Finish**
5. Đợi build hoàn tất
6. APK tại: `app/build/outputs/apk/release/app-release.apk`

---

## 🔧 Xử lý lỗi thường gặp

### ❌ Lỗi: "Gradle sync failed"

**Nguyên nhân:** Thiếu dependencies hoặc cấu hình sai

**Giải pháp:**
1. Kiểm tra kết nối internet
2. Vào **File** → **Invalidate Caches / Restart** → **Invalidate and Restart**
3. Xóa thư mục `.gradle` trong dự án (nếu có)
4. Sync lại: **File** → **Sync Project with Gradle Files**

---

### ❌ Lỗi: "SDK location not found"

**Giải pháp:**
1. Vào **File** → **Project Structure** → **SDK Location**
2. Chọn đường dẫn đến Android SDK (thường là `C:\Users\[Tên]\AppData\Local\Android\Sdk`)
3. Hoặc cài đặt SDK: **Tools** → **SDK Manager**

---

### ❌ Lỗi: "Cannot resolve symbol"

**Giải pháp:**
1. **File** → **Invalidate Caches / Restart** → **Invalidate and Restart**
2. Đảm bảo Gradle sync thành công
3. Kiểm tra file `build.gradle` có đầy đủ dependencies

---

### ❌ Lỗi: "Device not found" khi chạy trên thiết bị thật

**Giải pháp:**
1. Kiểm tra USB Debugging đã bật trên điện thoại
2. Thử cài đặt driver USB cho điện thoại (từ trang web nhà sản xuất)
3. Thử cáp USB khác
4. Chạy `adb kill-server` rồi `adb start-server` trong Terminal
5. Kiểm tra: `adb devices` để xem thiết bị có xuất hiện không

---

### ❌ Lỗi: "INSTALL_FAILED_INSUFFICIENT_STORAGE"

**Giải pháp:**
- Xóa một số ứng dụng trên điện thoại để giải phóng dung lượng
- Hoặc xóa cache: **Settings** → **Storage** → **Clear Cache**

---

### ❌ Lỗi khi build APK: "Execution failed for task"

**Giải pháp:**
1. **Build** → **Clean Project**
2. **Build** → **Rebuild Project**
3. Đảm bảo không có lỗi trong code (kiểm tra tab **Build**)

---

## 📝 Checklist trước khi chạy

- [ ] Android Studio đã cài đặt và cập nhật
- [ ] JDK 17 đã cài đặt
- [ ] Android SDK đã cài đặt (API 24-34)
- [ ] Gradle sync thành công (không có lỗi)
- [ ] Emulator đã tạo HOẶC thiết bị thật đã kết nối
- [ ] USB Debugging đã bật (nếu dùng thiết bị thật)

---

## 🎯 Test ứng dụng

Sau khi chạy thành công:

1. **Test đăng nhập:**
   - Admin: `admin` / `admin123`
   - Customer: `customer` / `customer123`
   - Hoặc nhấn "Tiếp tục với tư cách khách"

2. **Test chức năng khách hàng:**
   - Xem danh sách sản phẩm
   - Tìm kiếm sản phẩm
   - Thêm vào giỏ hàng
   - Đặt hàng

3. **Test chức năng admin:**
   - Đăng nhập admin
   - Thêm sản phẩm mới
   - Sửa/xóa sản phẩm

---

## 💡 Mẹo hữu ích

1. **Tăng tốc build:**
   - Vào **File** → **Settings** → **Build, Execution, Deployment** → **Compiler**
   - Tăng **Build process heap size** lên 2048 MB

2. **Xem log khi chạy:**
   - Mở **Logcat** ở dưới cùng của Android Studio
   - Chọn thiết bị và ứng dụng để xem log

3. **Debug ứng dụng:**
   - Đặt breakpoint bằng cách click vào số dòng
   - Chạy ở chế độ Debug (🐛 icon thay vì ▶️)
   - Sử dụng **Debugger** để xem giá trị biến

---

## 📞 Hỗ trợ

Nếu gặp vấn đề không giải quyết được:
1. Kiểm tra lại các bước trên
2. Xem log trong tab **Build** hoặc **Logcat**
3. Tìm kiếm lỗi trên Google với từ khóa cụ thể

---

**Chúc bạn thành công! 🎉**

