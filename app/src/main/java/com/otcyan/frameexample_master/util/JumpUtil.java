package com.otcyan.frameexample_master.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


/**
 * Created by 01096612 on 2016/5/11.
 *
 */
public class JumpUtil {

//    public static void jumpGeneralFragment(FragmentActivity activity , Fragment fragment , String tag, boolean isToStack){
//        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction() ;
//        fragmentTransaction.setCustomAnimations(R.anim.slide_right_in , R.anim.slide_left_out)  ;
//        fragmentTransaction.replace(R.id.general_content, fragment, tag);
//        if (isToStack)
//            fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commitAllowingStateLoss();
//    }

//    public static void jumpGeneralFragment(Fragment preFragment , Fragment fragment , String tag, boolean isToStack){
//        jumpGeneralFragment(preFragment.getActivity(), fragment , tag , isToStack);
//    }
//
//    public static void jumpGeneralFragment(Fragment preFragment , Fragment fragment , String tag , boolean isToStack , Bundle bundle){
//        fragment.setArguments(bundle);
//        jumpGeneralFragment(preFragment.getActivity(), fragment , tag , isToStack);
//    }

//    public static void jumpActivity(Context context , String action , Bundle bundle){
//        Intent intent = new Intent(action) ;
//        if(bundle!=null)
//            intent.putExtra("bundle" , bundle) ;
//
//        context.startActivity(intent);
//    }


//    public static void jumpActivity(Fragment fragment , String action , Bundle bundle){
//
//        Activity activity = fragment.getActivity() ;
//
//        if(activity!=null){
//            jumpActivity(activity , action , bundle);
//        }
//
//    }

    public static void jumpActivity(Context context , Class claz , Bundle bundle){
        Intent intent = new Intent(context , claz) ;
        if(bundle!=null)
            intent.putExtras(bundle);
        context.startActivity(intent);
    }

}
