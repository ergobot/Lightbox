package com.photonic3d.lightbox.fragments.archiveselector;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.photonic3d.lightbox.NavigationActivity;
import com.photonic3d.lightbox.R;
import com.photonic3d.lightbox.fragments.archiveselector.viewholder.SliceArchive;
import com.photonic3d.lightbox.fragments.archiveselector.viewholder.SliceArchiveAdapter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import android.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ArchiveSelectorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ArchiveSelectorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArchiveSelectorFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String TAG = "ArchiveSelectorFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    TextView archiveTitleTextView;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    List<SliceArchive> sliceArchiveList = null;

    public ArchiveSelectorFragment() {
        // Required empty public constructor
    }

    private static final String ARG_SECTION_NUMBER = "section_number";
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OpenStlFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ArchiveSelectorFragment newInstance(String param1, String param2) {
        ArchiveSelectorFragment fragment = new ArchiveSelectorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static ArchiveSelectorFragment newInstance(int sectionNumber) {
        ArchiveSelectorFragment fragment = new ArchiveSelectorFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.view_slice_view_selector, container, false);

        if(isExternalStorageReadable() && isExternalStorageWritable()) {
            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            mRecyclerView.setHasFixedSize(true);

            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mLayoutManager);

            // get demo files from download folder
            loadDemoFiles();

            // specify an adapter (see also next example)
            sliceArchiveList = getArchiveList();
            mAdapter = new SliceArchiveAdapter(sliceArchiveList, new SliceArchiveAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(SliceArchive item) {
                    // TODO: 5/20/16
                    // replace this fragment with the other fragment
                }
            });
            mRecyclerView.setAdapter(mAdapter);
        }
        return rootView;
    }

    private void loadDemoFiles(){
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "SLAcer.zip");
        try {
            FileUtils.copyFileToDirectory(file, getContext().getFilesDir());
        } catch (IOException e) {
            e.printStackTrace();
        }

//
//
//
        String filename = "myfile";
        String string = "Hello world!";
//        FileOutputStream outputStream;
//
//        try {
//            outputStream = getContext().openFileOutput(filename, Context.MODE_PRIVATE);
//            IOUtils.copy(FileUtils.openInputStream(file),)
//            outputStream.write();
//            outputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }



    }

    private List<SliceArchive> getArchiveList(){
        ArrayList<SliceArchive> files = new ArrayList<SliceArchive>();
        // Get the directory
            // Get all the files
            File sliceArchive = getSliceArchiveStorageDir();
            File file[] = sliceArchive.listFiles();
            Log.d("Files", "Size: "+ file.length);
            for (int i=0; i < file.length; i++)
            {
                Log.d("Files", "FileName:" + file[i].getName());
                SliceArchive archive = new SliceArchive();
                archive.setFile(file[i]);
                files.add(archive);
            }

        return files;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((NavigationActivity) context).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));

        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public File getSliceArchiveStorageDir() {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStorageDirectory(),"lightbox");
        if (!file.mkdirs()) {
            Log.e(ArchiveSelectorFragment.TAG, "Directory not created");
        }
        return file;
    }

    public File getSliceArchiveWorkingDir(){
        // Get the directory for the user's public pictures directory.
        File file = new File(getContext().getFilesDir(),"working");
        if (!file.mkdirs()) {
            Log.e(ArchiveSelectorFragment.TAG, "Directory not created");
        }
        return file;

    }


    /* Checks if external storage is available for read and write */
        public boolean isExternalStorageWritable() {
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
    return true;
                    }
             return false;
            }

                /* Checks if external storage is available to at least read */
                public boolean isExternalStorageReadable() {
                String state = Environment.getExternalStorageState();
                if (Environment.MEDIA_MOUNTED.equals(state) ||
                                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
                        return true;
                    }
                return false;
            }


}
