package musicdemo.jlang.com.mimu.fragment;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;

/**
 * Created by wm on 2016/3/17.
 */
public class AttachDialogFragment extends DialogFragment {

    public Context mContext;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.mContext = context;
    }


}
