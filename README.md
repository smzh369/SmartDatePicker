# SmartDatePicker [![](https://jitpack.io/v/com.gitee.Zerlings/SmartDatePicker.svg)](https://jitpack.io/#com.gitee.Zerlings/SmartDatePicker)

SmartDatePicker是一款简单灵活的日期选择器，既能像DatePicker一样快速定义，又拥有和DatePickerDialog同样强大的功能。

Requirement
-----------
Kotlin / Java(需开启kotlin插件支持)

Android API 16+

1.0.0以上版本仅支持AndroidX

Usage
-------
### 1.XML中定义
```xml
 <com.zerlings.library.SmartDatePicker
        android:id="@+id/date_picker"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:style="wheel_dark"
        app:format="yyyy-MM-dd"
        app:timeMills="false"
        app:minDate="2020-04-25"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
```
* Note: `format`和minDate/maxDate需遵循标准格式，否则会报错。

### 2.Listeners
```kotlin
//Kotlin
date_picker.setOnDatePickListener { year, month, day, timeStamp, dateStr ->
    //todo
}
        
//Java
datePicker.setOnDatePickListener(new Function5<Integer, Integer, Integer, Long, String, Unit>() {
    @Override
    public Unit invoke(Integer integer, Integer integer2, Integer integer3, Long aLong, String s) {
        //todo
        return null;
    }
});

//Java with lambda
datePicker.setOnDatePickListener((year, month, day, timeStamp, dateStr) -> {
    //todo
    return null;
});
```
* Note: 在Java中使用需要返回一个任意值

### 3.Attributes
| name                      | type      | info                                                   |
|------------------------   |-----------|--------------------------------------------------------|
| format                 | string     | 日期显示格式                  |
| minDate                 | string     | 最小日期                  |
| maxDate                 | string     | 最大日期                  |
| style                 | enum   | 弹窗样式,可选轮盘或日历           |
| timeMills             | boolean | listener里的时间戳位数，false(默认)为10位，true为13位             |
* Note: 控件本身（不包括弹窗）是一个TextView，可以使自定义字体、背景等样式。

Include
-------
首先在项目根目录下的`build.gradle`中加入（已有则忽略）:
```groovy
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```
然后在app文件夹下的`build.gradle`中引入：
```
dependencies {
    implementation 'com.gitee.Zerlings:SmartDatePicker:1.0.0'
}
```

License
-------
    MIT License

    Copyright (c) 2020 Zerlings
    
    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:
    
    The above copyright notice and this permission notice shall be included in all
    copies or substantial    Copyright (c) 2020 Zerlings
    
    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:
    
    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.
    
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE. portions of the Software.
    
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.