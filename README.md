# September Food - Ứng dụng bán thức ăn cho thú cưng

Ứng dụng Android được xây dựng bằng Kotlin theo kiến trúc MVC để quản lý và bán thức ăn cho thú cưng.

## Tính năng

### Chức năng khách hàng:
- ✅ Xem danh sách sản phẩm (trang đầu tiên khi mở ứng dụng)
- ✅ Tìm kiếm sản phẩm với thanh tìm kiếm cải tiến
- ✅ Lọc sản phẩm theo danh mục (Thức ăn cho chó, Thức ăn cho mèo, Pate, Sữa, v.v.)
- ✅ Xem chi tiết sản phẩm
- ✅ Thêm sản phẩm vào giỏ hàng
- ✅ Quản lý giỏ hàng (tăng/giảm số lượng, xóa)
- ✅ Đặt hàng
- ✅ Thanh toán tiền mặt
- ✅ Đăng ký tài khoản mới
- ✅ Đăng nhập/Đăng xuất
- ✅ Xem thông tin tài khoản

## Cấu trúc dự án

Dự án tuân theo kiến trúc MVC:

```
app/src/main/java/com/septemberfood/
├── model/          # Models (Product, Order, CartItem, User, OrderItem)
├── database/       # Room Database và DAOs
├── controller/     # Controllers/ViewModels (business logic)
├── view/           # Views (Activities, Fragments)
│   ├── customer/   # Activities cho khách hàng
│   │   ├── ProductListActivity    # Trang danh sách sản phẩm (trang đầu tiên)
│   │   ├── ProductDetailActivity  # Trang chi tiết sản phẩm
│   │   ├── CartActivity           # Trang giỏ hàng
│   │   ├── CheckoutActivity       # Trang thanh toán
│   │   └── ProfileActivity        # Trang thông tin tài khoản
│   └── adapter/    # RecyclerView Adapters
├── MainActivity    # Activity đăng nhập/đăng ký
└── util/           # Utilities (UserSession)
```

## Yêu cầu hệ thống

- Android Studio Hedgehog | 2023.1.1 trở lên
- JDK 17
- Android SDK 24 (Android 7.0) trở lên
- Target SDK: 34 (Android 14)

## Hướng dẫn chạy trên Android Studio

### Bước 1: Mở dự án

1. **Khởi động Android Studio**
   - Mở Android Studio trên máy tính của bạn

2. **Mở dự án:**
   - Chọn **File** > **Open** (hoặc **Open an Existing Project**)
   - Duyệt đến thư mục `SeptemberFood` và chọn nó
   - Nhấn **OK**

3. **Đợi Android Studio index và sync:**
   - Android Studio sẽ tự động bắt đầu đồng bộ Gradle
   - Bạn sẽ thấy thanh tiến trình ở dưới cùng của cửa sổ
   - **Lưu ý:** Lần đầu tiên có thể mất vài phút để tải các dependencies

### Bước 2: Kiểm tra cấu hình

1. **Kiểm tra SDK:**
   - Vào **File** > **Project Structure** > **SDK Location**
   - Đảm bảo Android SDK đã được cài đặt
   - Kiểm tra **Compile SDK Version** là 34
   - Kiểm tra **Min SDK Version** là 24

2. **Kiểm tra JDK:**
   - Vào **File** > **Project Structure** > **SDK Location**
   - Đảm bảo **JDK location** được cấu hình đúng (JDK 17)

### Bước 3: Đồng bộ Gradle

1. **Nếu Gradle chưa tự động sync:**
   - Chọn **File** > **Sync Project with Gradle Files**
   - Hoặc nhấn nút **Sync Now** khi có thông báo

2. **Kiểm tra lỗi:**
   - Xem tab **Build** ở dưới cùng để kiểm tra lỗi
   - Nếu có lỗi, đảm bảo bạn đã cài đặt đầy đủ Android SDK và Build Tools

### Bước 4: Chạy ứng dụng trên Emulator/Thiết bị

#### Tùy chọn A: Chạy trên Emulator (Android Virtual Device)

1. **Tạo Emulator (nếu chưa có):**
   - Chọn **Tools** > **Device Manager**
   - Nhấn **Create Device**
   - Chọn thiết bị (ví dụ: Pixel 5)
   - Chọn hệ điều hành (API 24 trở lên, khuyến nghị API 33-34)
   - Nhấn **Finish**

2. **Chạy ứng dụng:**
   - Chọn emulator từ dropdown ở thanh toolbar (bên cạnh nút Run)
   - Nhấn nút **Run** (▶️) hoặc nhấn **Shift + F10**
   - Hoặc chọn **Run** > **Run 'app'**

#### Tùy chọn B: Chạy trên thiết bị thật

1. **Bật USB Debugging:**
   - Trên điện thoại: **Settings** > **About Phone** > Nhấn 7 lần vào **Build Number**
   - Quay lại **Settings** > **Developer Options** > Bật **USB Debugging**

2. **Kết nối thiết bị:**
   - Kết nối điện thoại với máy tính qua USB
   - Chấp nhận yêu cầu "Allow USB Debugging" trên điện thoại

3. **Chạy ứng dụng:**
   - Thiết bị sẽ xuất hiện trong dropdown
   - Chọn thiết bị và nhấn **Run** (▶️)

### Bước 5: Build APK để cài đặt thủ công

#### Build APK Debug (để test):

1. **Tạo APK Debug:**
   - Chọn **Build** > **Build Bundle(s) / APK(s)** > **Build APK(s)**
   - Đợi quá trình build hoàn tất
   - Khi hoàn tất, nhấn **locate** trong thông báo
   - Hoặc tìm file tại: `app/build/outputs/apk/debug/app-debug.apk`

2. **Cài đặt APK:**
   - Copy file APK vào điện thoại
   - Trên điện thoại: **Settings** > **Security** > Bật **Unknown Sources**
   - Mở file APK và cài đặt

#### Build APK Release (để xuất bản):

1. **Tạo keystore (lần đầu tiên):**
   - Chọn **Build** > **Generate Signed Bundle / APK**
   - Chọn **APK** > **Next**
   - Nhấn **Create new...** để tạo keystore mới
   - Điền thông tin:
     - Key store path: Chọn vị trí lưu
     - Password: Nhập mật khẩu
     - Key alias: Tên alias
     - Key password: Mật khẩu cho key
   - Nhấn **OK**

2. **Build APK Release:**
   - Chọn keystore vừa tạo
   - Nhập password
   - Chọn **release** build variant
   - Nhấn **Finish**
   - APK sẽ được tạo tại: `app/build/outputs/apk/release/app-release.apk`

### Xử lý lỗi thường gặp

1. **Lỗi Gradle sync failed:**
   - Kiểm tra kết nối internet (cần tải dependencies)
   - Vào **File** > **Invalidate Caches / Restart** > **Invalidate and Restart**
   - Xóa thư mục `.gradle` trong dự án và sync lại

2. **Lỗi SDK không tìm thấy:**
   - Vào **Tools** > **SDK Manager**
   - Cài đặt Android SDK Platform 34
   - Cài đặt Android SDK Build-Tools

3. **Lỗi "Cannot resolve symbol":**
   - Chọn **File** > **Invalidate Caches / Restart**
   - Đảm bảo đã sync Gradle thành công

4. **Lỗi khi chạy trên thiết bị:**
   - Kiểm tra USB Debugging đã bật
   - Thử cài đặt driver USB cho thiết bị
   - Chạy `adb devices` trong terminal để kiểm tra thiết bị được nhận diện

### Cấu trúc thư mục quan trọng

```
SeptemberFood/
├── app/
│   ├── build.gradle          # Cấu hình dependencies
│   ├── src/
│   │   └── main/
│   │       ├── java/         # Mã nguồn Kotlin
│   │       ├── res/          # Tài nguyên (layout, drawable, values)
│   │       └── AndroidManifest.xml
│   └── build/outputs/apk/    # APK được build ở đây
├── build.gradle              # Cấu hình project-level
└── settings.gradle           # Cấu hình modules
```

## Tài khoản mặc định

### Customer:
- Username: `customer`
- Password: `customer123`

**Lưu ý:** 
- Ứng dụng sẽ mở trực tiếp vào trang danh sách sản phẩm
- Bạn có thể sử dụng ứng dụng với tư cách khách mà không cần đăng nhập
- Bạn có thể đăng ký tài khoản mới từ menu profile (nếu chưa đăng nhập) hoặc từ màn hình đăng nhập
- Menu profile sẽ hiển thị "Đăng nhập" nếu chưa đăng nhập, hoặc "Thông tin tài khoản" và "Đăng xuất" nếu đã đăng nhập

## Công nghệ sử dụng

- **Language:** Kotlin
- **Architecture:** MVC (Model-View-Controller)
- **Database:** Room Database
- **UI:** Material Design Components (CardView, Chip, TextInputLayout, MaterialButton, SearchView)
- **Image Loading:** Glide
- **Coroutines:** Kotlin Coroutines (cho các tác vụ bất đồng bộ)
- **View Binding:** Android View Binding (để truy cập views an toàn)
- **LiveData:** Để quan sát thay đổi dữ liệu
- **ViewModel:** AndroidViewModel để quản lý dữ liệu UI
- **SharedPreferences:** Để lưu session người dùng

## Cấu trúc Database

### Bảng Products
- id (Primary Key)
- productCode (Mã sản phẩm)
- name (Tên sản phẩm)
- description (Mô tả)
- price (Giá)
- imageUrl (URL hình ảnh)
- category (Danh mục)
- stock (Tồn kho)
- createdAt (Ngày tạo)

### Bảng CartItems
- id (Primary Key)
- productId (Foreign Key -> Products)
- quantity (Số lượng)
- addedAt (Ngày thêm)

### Bảng Orders
- id (Primary Key)
- orderNumber (Số đơn hàng)
- totalAmount (Tổng tiền)
- status (Trạng thái)
- paymentMethod (Phương thức thanh toán)
- customerName (Tên khách hàng)
- customerPhone (Số điện thoại)
- customerAddress (Địa chỉ)
- createdAt (Ngày tạo)

### Bảng OrderItems
- id (Primary Key)
- orderId (Foreign Key -> Orders)
- productId (Foreign Key -> Products)
- quantity (Số lượng)
- price (Giá)

### Bảng Users
- id (Primary Key)
- username (Tên đăng nhập)
- password (Mật khẩu)
- role (Vai trò: Customer)
- name (Tên)
- email (Email)
- phone (Số điện thoại)

## Hướng dẫn sử dụng

### Cho khách hàng:

**Khi mở ứng dụng lần đầu:**
1. Ứng dụng sẽ tự động mở vào trang danh sách sản phẩm
2. Bạn có thể duyệt sản phẩm ngay mà không cần đăng nhập

**Sử dụng ứng dụng:**
1. **Duyệt danh sách sản phẩm:** Xem tất cả sản phẩm có sẵn
2. **Tìm kiếm sản phẩm:** Sử dụng thanh tìm kiếm ở đầu trang để tìm sản phẩm theo tên
3. **Lọc theo danh mục:** Chọn chip danh mục (Tất cả, Thức ăn cho chó, Thức ăn cho mèo, Pate, Sữa, v.v.) để lọc sản phẩm
4. **Xem chi tiết:** Nhấn vào sản phẩm để xem thông tin chi tiết
5. **Thêm vào giỏ hàng:** Từ trang chi tiết, chọn số lượng và nhấn "Thêm vào giỏ hàng"
6. **Xem giỏ hàng:** Nhấn icon giỏ hàng ở toolbar để xem và quản lý giỏ hàng
7. **Điều chỉnh số lượng:** Trong giỏ hàng, tăng/giảm số lượng hoặc xóa sản phẩm
8. **Thanh toán:** Nhấn "Thanh toán" và điền thông tin khách hàng để đặt hàng

**Quản lý tài khoản:**
- **Đăng ký:** Nhấn icon profile → "Đăng nhập" → "Đăng ký" (hoặc từ màn hình đăng nhập)
- **Đăng nhập:** Nhấn icon profile → "Đăng nhập" → Nhập username và password
- **Xem thông tin:** Nhấn icon profile → "Thông tin tài khoản"
- **Đăng xuất:** Nhấn icon profile → "Đăng xuất"

## Tính năng UI/UX

- **Custom Toolbar:** Tất cả các trang đều có toolbar tùy chỉnh với nút back, tiêu đề, và các icon chức năng
- **Thanh tìm kiếm cải tiến:** Thanh tìm kiếm với Material Design, icon tìm kiếm luôn hiển thị, không tự động focus khi vào trang
- **Lọc theo danh mục:** Chip danh mục có thể cuộn ngang, dễ dàng chọn danh mục muốn xem
- **Menu profile động:** Menu profile tự động thay đổi dựa trên trạng thái đăng nhập của người dùng
- **Responsive Design:** Giao diện được thiết kế để hoạt động tốt trên nhiều kích thước màn hình

## Lưu ý

- Ứng dụng sử dụng Room Database để lưu trữ dữ liệu cục bộ
- Hình ảnh sản phẩm được load từ URL (hiện tại sử dụng placeholder URLs từ via.placeholder.com)
- Để test với hình ảnh thật, bạn có thể:
  - Sử dụng URL từ các dịch vụ như Unsplash, Pexels
  - Lưu hình ảnh trong thư mục `res/drawable` và sử dụng `@drawable/image_name`
  - Lưu hình ảnh trong thư mục `assets` và load bằng AssetManager
- Dữ liệu mẫu (sản phẩm, tài khoản customer) được tự động tạo khi lần đầu mở ứng dụng
- Session được lưu trong SharedPreferences, vì vậy người dùng sẽ vẫn đăng nhập sau khi đóng và mở lại ứng dụng

## Giấy phép

Dự án này được tạo cho mục đích giáo dục và học tập.

