add README.md in order to introduce this project



# chapter12对话框
## question
1. 将信息传递给timepicker
2. timePicker选择的时间数据需要返回到crimeFragment
## solution
第一题解决方法：
1. CrimeFragment 中通过DatePickerFragment的newInstance（Date date）静态方法来创建实例。
```java
public class DatePickerFragment extends DialogFragment {

    /**
     * 创建定制化实例，根据传入的date。
     * @param date
     * @return
     */    
    public static DatePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
```

# Fragment

<img src="https://developer.android.com/images/guide/fragments/fragment-view-lifecycle.png" alt="fragment lifecycle states and their relation both the fragment's             lifecycle callbacks and the fragment's view lifecycle" style="zoom: 67%;" />



# 第12章 对话框

## Fragment 间的数据传递

### 1. CrimeFragment传递数据给DatePickerFragment

### 2. DatePickerFragment返回数据给 CrimeFragment
1. CrimeFragment.java 创建DatePickerFragment，调用dialog实例的setTargetFragment方法为其设置目标Fragment以便回调
用Bundle在fragment间传递数据
        mDateButton.setOnClickListener(v1 -> {
            FragmentManager manager = getFragmentManager();
            DatePickerFragment dialog = DatePickerFragment
                    .newInstance(mCrime.getmDate());
            dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
            dialog.show(manager, DIALOG_DATE);
        });
2. 通过dialog.show(manager, DIALOG_DATE);启动DatePickerFragment 
3. 运行class DatePickerFragment的 onCreateDialog(...)方法
    //读取来自crimeFragment传入的arguments数据
            Bundle arguments = getArguments();
            Date date = (Date) arguments.getSerializable(ARG_DATE); 
    //解析数据用于初始化Date组建
            mDatePicker.init(year, month, day, null);

## 
.CrimeListActivity

#chapter17
别名资源是一种指向其他资源的特殊资源。它存放在res/values/目录下,并按照约定定义在
refs.xml文件中。
# 在 XML 文件中定义菜单
菜单是一种类似于布局的资源。创建菜单定义文件并将其放置在res/menu目录下,Android
会自动生成相应的资源ID。随后,在代码中实例化菜单时,就可以直接使用。
在项目工具窗口中,右键单击res目录,选择New → Android resource file菜单项。在弹出的窗
口界面,选择Menu资源类型,并命名资源文件为fragment_crime_list,点击OK按钮确认,如图13-3
所示
