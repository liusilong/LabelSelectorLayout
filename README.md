# LabelSelectorLayout
### 标签选择器
**使用方法**

xml

```xml
<com.lsl.labelselectorlayout.view.LabelLayout
        android:id="@+id/labelLayout"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```

Activity 

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val list = (0..14).map { "Label$it" }
        labelLayout.setList(list)
    }
```


**可以使用 `LabelSelectorLayout#getSelectedList()`方法来获取选中的标签集合**

### 效果如下
[效果链接](http://upload-images.jianshu.io/upload_images/995581-91dc0a49c643b281.gif?imageMogr2/auto-orient/strip)

![LabelSelector.gif](http://upload-images.jianshu.io/upload_images/995581-91dc0a49c643b281.gif?imageMogr2/auto-orient/strip)

