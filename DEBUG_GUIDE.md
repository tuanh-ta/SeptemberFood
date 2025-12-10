# Hướng dẫn Debug lỗi "System UI isn't responding"

## 🔍 Nguyên nhân có thể

Lỗi "System UI isn't responding" thường xảy ra khi:
1. Ứng dụng crash ngay khi khởi động
2. ANR (Application Not Responding) - ứng dụng không phản hồi
3. Lỗi trong quá trình khởi tạo database
4. Lỗi trong layout XML
5. Lỗi trong ViewModel initialization

## ✅ Đã sửa

1. ✅ Sửa cách khởi tạo ViewModel trong MainActivity (dùng ViewModelProvider)
2. ✅ Thêm try-catch để bắt lỗi
3. ✅ Thêm `allowMainThreadQueries()` tạm thời trong database để tránh crash

## 🛠️ Các bước debug

### Bước 1: Xem Logcat

1. Mở **Logcat** trong Android Studio (View → Tool Windows → Logcat)
2. Chọn thiết bị/emulator của bạn
3. Tìm các dòng màu đỏ (ERROR) hoặc màu vàng (WARNING)
4. Copy toàn bộ stack trace và kiểm tra

### Bước 2: Kiểm tra lỗi cụ thể

**Nếu thấy lỗi về Database:**
```
Cannot access database on the main thread
```
→ Đã được sửa bằng `allowMainThreadQueries()`

**Nếu thấy lỗi về ViewModel:**
```
Cannot create an instance of class UserController
```
→ Đã được sửa bằng ViewModelProvider

**Nếu thấy lỗi về Layout:**
```
Binary XML file line #X: Error inflating class
```
→ Kiểm tra layout XML có đúng không

### Bước 3: Clean và Rebuild

1. **Build** → **Clean Project**
2. **Build** → **Rebuild Project**
3. Xóa app trên thiết bị/emulator
4. Chạy lại ứng dụng

### Bước 4: Kiểm tra Logcat khi chạy

Khi chạy ứng dụng, xem Logcat để tìm:
- `MainActivity: onCreate`
- `AppDatabase: getDatabase`
- Bất kỳ exception nào

## 🔧 Giải pháp nếu vẫn lỗi

### Giải pháp 1: Kiểm tra thiết bị/Emulator

- Đảm bảo thiết bị có đủ RAM
- Thử trên emulator khác hoặc thiết bị thật
- Kiểm tra Android version (phải >= API 24)

### Giải pháp 2: Xóa dữ liệu ứng dụng

Nếu đã cài đặt app trước đó:
1. **Settings** → **Apps** → **September Food** → **Storage** → **Clear Data**
2. Gỡ cài đặt app
3. Cài đặt lại

### Giải pháp 3: Kiểm tra dependencies

Đảm bảo trong `app/build.gradle` có đầy đủ:
```gradle
dependencies {
    implementation 'androidx.room:room-runtime:2.6.1'
    implementation 'androidx.room:room-ktx:2.6.1'
    kapt 'androidx.room:room-compiler:2.6.1'
    // ... các dependencies khác
}
```

### Giải pháp 4: Kiểm tra AndroidManifest.xml

Đảm bảo MainActivity được khai báo đúng:
```xml
<activity
    android:name=".view.MainActivity"
    android:exported="true"
    android:theme="@style/Theme.SeptemberFood">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity>
```

## 📝 Test nhanh

1. Mở Logcat
2. Chạy ứng dụng
3. Xem có lỗi nào không
4. Nếu có lỗi, copy stack trace và tìm kiếm trên Google

## 💡 Lưu ý

- `allowMainThreadQueries()` chỉ nên dùng để debug. Trong production, nên xóa dòng này và đảm bảo tất cả database operations chạy trên background thread.
- Nếu vẫn gặp vấn đề, hãy kiểm tra Logcat để xem lỗi cụ thể là gì.

## 🆘 Nếu vẫn không giải quyết được

1. Copy toàn bộ log từ Logcat (filter: ERROR)
2. Kiểm tra xem có thiếu file nào không
3. Đảm bảo đã sync Gradle thành công
4. Thử tạo project mới và copy code sang

