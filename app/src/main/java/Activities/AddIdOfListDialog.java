package Activities;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.annotations.Nullable;


public class AddIdOfListDialog extends DialogFragment {

    private static final String TAG = "AddIdOfListDialog";


    public interface OnInputListener{
        void sendInput(String input);
    }
    public OnInputListener mOnInputListener;

    //widgets
    private EditText mInput;
    private TextView mActionOk, mActionCancel;


    //vars

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_goin_list, container, false);

        mActionCancel = view.findViewById(R.id.action_cancel);
        mActionOk = view.findViewById(R.id.action_ok);
        mInput = view.findViewById(R.id.input);

        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: closing dialog");
                getDialog().dismiss();
            }
        });


        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: capturing input");

                String input = mInput.getText().toString();
//                if(!input.equals("")){
//
//                    //Easiest way: just set the value
//                    ((friendsLists)getActivity()).mInputDisplay.setText(input);
//
//                }

                //"Best Practice" but it takes longer
                mOnInputListener.sendInput(input);

                getDialog().dismiss();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mOnInputListener = (OnInputListener) getActivity();
        }catch (ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage() );
        }
    }
}





