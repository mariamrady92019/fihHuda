package com.example.fihhuda.Base;

import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Mohamed Nabil Mohamed on 9/13/2019.
 * m.nabil.fci2015@gmail.com
 */
public class BaseActivity extends AppCompatActivity {


    public AlertDialog showMessage(String message, String posActionName){
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton(posActionName, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return builder.show();
    }
    public AlertDialog showMessage(int message, int posActionName){
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton(posActionName, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return builder.show();
    }

    public String reName(String name){
        String rename = name;
        if(name.equals("هود")){
            rename="هٌود";
        }else if (name.equals("الشرح")){
            rename="الإنشراح";
        }else if(name.equals("العلق")){
            rename="العًلق";
        }else if(name.equals("ابراهيم")){
            rename="إبراهيم";
        }else if(name.equals("النبإ")){
            rename="النبأ";
        }else if(name.equals("الانسان")){
            rename="الإنسان";
        }
        return rename;
    }


    public AlertDialog showMessage(String message, String posActionName,
                                   DialogInterface.OnClickListener onClickListener,
                                   boolean isCancelable){
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton(posActionName,onClickListener );
        builder.setCancelable(isCancelable);
        return builder.show();
    }
    public AlertDialog showMessage(String message, String posActionName,
                                   DialogInterface.OnClickListener onPosClick,
                                   String negativeText,
                                   DialogInterface.OnClickListener onNegativeClick,
                                   boolean isCancelable){
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton(posActionName,onPosClick );
        builder.setNegativeButton(negativeText,onNegativeClick );
        builder.setCancelable(isCancelable);
        return builder.show();
    }
public AlertDialog showMessage(int message, int posActionName,
                               DialogInterface.OnClickListener onClickListener,
                               boolean isCancelable
){
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton(posActionName,onClickListener);
        builder.setCancelable(isCancelable);
        return builder.show();
    }
    public AlertDialog showMessage(int message, int posActionName,
                                   DialogInterface.OnClickListener onPosClick,
                                   int negativeText,
                                   DialogInterface.OnClickListener onNegativeClick,
                                   boolean isCancelable
    ){
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton(posActionName,onPosClick );
        builder.setNegativeButton(negativeText,onNegativeClick );
        builder.setCancelable(isCancelable);
        return builder.show();
    }

}
