---
MethodTracker
---

### 概述

一个查看方法耗时的 Gradle Plugin。

### 使用

#### 第一步：

在根工程目录下的 build.gradle 添加：

```groovy
repositories {
	maven {
		url  "https://omooo2333.bintray.com/MethodTrack"
	}
}
```

```groovy
classpath 'top.omooo:method-track-plugin:0.1.2'
```

#### 第二步：

在要使用的模块的 build.gradle 添加：

```groovy
apply plugin: 'top.omooo.track_plugin'
```

#### 第三步：

```java
    @MethodTrack
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
```

查看 Logcat 输出：

```
D/MainActivity: ⇢ onCreate(Landroid/os/Bundle;)V: 101ms
```

当然，如果你想 Trace 整个类的所有方法的耗时，只需在类上添加该注解即可。

**注意：**目前 Trace 整个类时并不会自动 Trace 其内部类。

### 剩余未做

1. Trace 整个类时自动 Trace 其内部类
2. Trace 方法路径
3. 可视化