#可以实现多个Activity页面跳转后的通信框架
有些时候，跳转多个页面后，返回第一个页面时，需要获取之前设置的数据，如果使用startAcitvityForResult是麻烦的。使用SharedPreferences就可以很方便做到了。
效果：

![1](http://i.imgur.com/WlG6KCF.gif)

图解1：

其他任意页面，都可以设置主页面是否要刷新

![2](http://i.imgur.com/MW9CF7i.png)

图解2：
在其他页面选中勾选框时，使用代码设置数据

![3](http://i.imgur.com/DVFyf7Z.png)



#使用：
也是比较简单的，这里的使用封装了一个SharedPreferences的工具类。
项目中一般都是有Base类的吧，在BaseActivity中初始化后，其他的任意界面都是可以调用这个对象来获取数据或写入数据。
##使用代码---1.先实例化：
```
SharedPreferencesUtils  spUtils = new SharedPreferencesUtils(this, "setting");
```
##使用代码---2.数据的存储：
###（1）存储一个：
```  
spUtils.putValues(new SharedPreferencesUtils.ContentValue("refresh", true));
```
 ###(2)存储多个：
```
    spUtils.putValues(new SharedPreferencesUtils.ContentValue("name", "liewnzhi")
                , new SharedPreferencesUtils.ContentValue("sex", "男")
                , new SharedPreferencesUtils.ContentValue("age", 26)
                , new SharedPreferencesUtils.ContentValue("old", true)
                , new SharedPreferencesUtils.ContentValue("love", "meinv")
                , new SharedPreferencesUtils.ContentValue("sport", "...")
        );


```

##使用代码---3.数据的获取：
```
		//各种基本类型和String类型都可以，存和取
 		String name = spUtils.getString("name");
        String sex = spUtils.getString("sex");
        Integer age = spUtils.getInt("age");
        boolean old = spUtils.getBoolean("old", false);
```

#框架示例的代码

##BaseActivity的代码：
```


/**
 * Base类
 */
public class BaseAcitvity extends Activity {

    private LoadingDialog mLoadingDialog; //加载数据时显示的简单的对话框

    SharedPreferencesUtils spUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spUtils = new SharedPreferencesUtils(this, "setting");
    }

...

}

```

##在主页面获取数据的主要代码：
```package com.example.communication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.communication.R;
import com.example.communication.utils.SharedPreferencesUtils;

/**
 * 主页面
 */
public class MyActivity extends BaseAcitvity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //判断是否加载
        if (spUtils.getBoolean("refresh", false)) {   //如果没有保存数据，默认false
            showLoading();//显示加载进度条
        }

        //每次过后把属性值设置成false
        spUtils.putValues(new SharedPreferencesUtils.ContentValue("refresh", false));

    }


}



```

##在其他任意页面设置数据的主要代码

```
package com.example.communication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import com.example.communication.R;
import com.example.communication.utils.SharedPreferencesUtils;

/**
 * 第二个页面
 */
public class MyActivity2 extends BaseAcitvity implements CompoundButton.OnCheckedChangeListener {

    CheckBox checkbox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);
        checkbox = (CheckBox) findViewById(R.id.checkbox);
        checkbox.setOnCheckedChangeListener(this);
    }

   

    /**
     * CheckBox的监听方法
     *
     * @param buttonView
     * @param isChecked
     */
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (isChecked) {   //如果被选中了，就设置刷新属性值为true
            spUtils.putValues(new SharedPreferencesUtils.ContentValue("refresh", true));
        } else {
            spUtils.putValues(new SharedPreferencesUtils.ContentValue("refresh", false));
        }
    }

    
}



```

到这里这个Activity通信的功能就可以实现了，并且效果还可以。一般都是在另一个Activity中onResume中判断数据后刷新就可以。
值得注意的是，获取不存在的数据属性也是可以的，返回的是默认值而已。
对同一个属性设置多次，只是覆盖掉而已。


#我这个框架中还有几个其他的知识点，大家也是可以借鉴。
##一个是全屏的进度条的页面，里面有完整的资源文件的布局设计的代码。
##一个是两次点击首页按钮退出（比较简单）：
```
  /**
     * 两次点击关闭退出页面
     */
    long currentTime = 0;

    public void finishForDoubleTimes() {
        if ((System.currentTimeMillis() - currentTime) < 3000) {
            super.onBackPressed();
        } else {
            // CommonUtils.showToast(this, "再次点击关闭程序");
            currentTime = System.currentTimeMillis();
        }
    }
```

##当然最重要的还是SharedPreferences工具类的代码，这里展示下：

```
package com.example.communication.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 这是一个SharePreference的根据类，使用它可以更方便的数据进行简单存储
 * 这里只要知道基本调用方法就可以了
 * 1.通过构造方法来传入上下文和文件名
 * 2.通过putValue方法传入一个或多个自定义的ContentValue对象，进行数据存储
 * 3.通过get方法来获取数据
 * 4.通过clear方法来清除这个文件的数据
 * 这里没有提供清除单个key的数据，是因为存入相同的数据会自动覆盖，没有必要去理会
 * <p/>
 * //存储数据示例
 * 先实例化：  SharedPreferencesUtils  spUtils = new SharedPreferencesUtils(this, "setting");
 * 1.存储一个：  spUtils.putValues(new SharedPreferencesUtils.ContentValue("refresh", true));
 * 2.存储多个：
 * spUtils.putValues(new SharedPreferencesUtils.ContentValue("name", "liewnzhi")
 * , new SharedPreferencesUtils.ContentValue("sex", "男")
 * , new SharedPreferencesUtils.ContentValue("age", 26)
 * , new SharedPreferencesUtils.ContentValue("old", true)
 * , new SharedPreferencesUtils.ContentValue("love", "meinv")
 * , new SharedPreferencesUtils.ContentValue("sport", "...")
 * );
 * //获取数据示例
 * Integer age = spUtils.getInt("age");
 * boolean old = spUtils.getBoolean("old", false);
 */
public class SharedPreferencesUtils {
    //定义一个SharePreference对象
    SharedPreferences sharedPreferences;
    //定义一个上下文对象

    //创建SharePreference对象时要上下文和存储的模式
    //通过构造方法传入一个上下文
    public SharedPreferencesUtils(Context context, String fileName) {
        //实例化SharePreference对象，使用的是get方法，而不是new创建
        //第一个参数是文件的名字
        //第二个参数是存储的模式，一般都是使用私有方式：Context.MODE_PRIVATE
        sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    /**
     * 存储数据
     * 这里要对存储的数据进行判断在存储
     * 只能存储简单的几种数据
     * 这里使用的是自定义的ContentValue类，来进行对多个数据的处理
     */
    //创建一个内部类使用，里面有key和value这两个值
    public static class ContentValue {
        String key;
        Object value;

        //通过构造方法来传入key和value
        public ContentValue(String key, Object value) {
            this.key = key;
            this.value = value;
        }
    }

    //一次可以传入多个ContentValue对象的值
    public void putValues(ContentValue... contentValues) {
        //获取SharePreference对象的编辑对象，才能进行数据的存储
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //数据分类和存储
        for (ContentValue contentValue : contentValues) {
            //如果是字符型类型
            if (contentValue.value instanceof String) {
                editor.putString(contentValue.key, contentValue.value.toString()).commit();
            }
            //如果是int类型
            if (contentValue.value instanceof Integer) {
                editor.putInt(contentValue.key, Integer.parseInt(contentValue.value.toString())).commit();
            }
            //如果是Long类型
            if (contentValue.value instanceof Long) {
                editor.putLong(contentValue.key, Long.parseLong(contentValue.value.toString())).commit();
            }
            //如果是布尔类型
            if (contentValue.value instanceof Boolean) {
                editor.putBoolean(contentValue.key, Boolean.parseBoolean(contentValue.value.toString())).commit();
            }

        }
    }


    //获取数据的方法
    public String getString(String key) {
        return sharedPreferences.getString(key, null);
    }

    public boolean getBoolean(String key, Boolean b) {
        return sharedPreferences.getBoolean(key, b);
    }

    public int getInt(String key) {
        return sharedPreferences.getInt(key, -1);
    }

    public long getLong(String key) {
        return sharedPreferences.getLong(key, -1);
    }

    //清除当前文件的所有的数据
    public void clear() {
        sharedPreferences.edit().clear().commit();
    }

}



```

其他的东西，如果需要可以看我的项目代码：
https://github.com/liwenzhi/ActivityCommunication


共勉：春去春会来。。。（好像有这样一首歌。。。）