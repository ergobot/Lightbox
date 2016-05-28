package com.photonic3d.lightbox.fragments.sliceview;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.photonic3d.lightbox.R;
import com.squareup.picasso.Picasso;

import java.io.File;

public class SliceFragment extends Fragment {

    private static final String KEY_POSITION = "position";

    static SliceFragment newInstance(int position){
        SliceFragment frag = new SliceFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION,position);
        frag.setArguments(args);
        return(frag);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.view_slice_image, container, false);

        ImageView slice = (ImageView)rootView.findViewById(R.id.slice);
        int position = getArguments().getInt(KEY_POSITION,-1);

        String fileName = String.valueOf(position) + ".png";
        File unpackDir = new File(getContext().getFilesDir(),"unpacked");
        File imageFile = new File(unpackDir,fileName);

//        int picId = getResources().getIdentifier(String.valueOf(position)+ ".png", "raw.slices", getActivity().getApplicationContext().getPackageName());

        Picasso.with(getActivity()).load(imageFile).into(slice);

        return rootView;
    }


}
