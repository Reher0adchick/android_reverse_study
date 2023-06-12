package com.example.ndk_jintest;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.ndk_jintest.databinding.ActivityMainBinding;
public class MainActivity extends AppCompatActivity {

    // Used to load the 'ndk_jintest' library on application startup.
    static {
        System.loadLibrary("ndk_jintest");
    }//载入库

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());// 1. 将布局文件通过布局加载器（getLayoutInflater()）转换成对应的数据绑定类对象，这里的 ActivityMainBinding 是通过 Data Binding 编译器自动生成的一个类。
        setContentView(binding.getRoot());//2. 设置当前页面的布局为数据绑定类对象的根视图，即布局文件本身。

        TextView tv = binding.sampleText;   // Example of a call to a native method
        tv.setText(stringFromJNI());
    }

    public native String stringFromJNI();
}