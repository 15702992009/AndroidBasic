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
