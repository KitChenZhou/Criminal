package com.d22395.android.criminalintent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by D22395 on 2017/7/31.
 */

public class DialogFragment extends Fragment{

    private File mPhotoFile;
    private Crime mCrime;
    private ImageView mDialogView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPhotoFile = CrimeLab.get(getActivity()).getPhotoFile(mCrime);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog, container, false);

        mDialogView = v.findViewById(R.id.dialog_photo);


        return v;
    }
}
