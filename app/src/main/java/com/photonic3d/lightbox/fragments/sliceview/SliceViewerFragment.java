package com.photonic3d.lightbox.fragments.sliceview;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.photonic3d.lightbox.NavigationActivity;
import com.photonic3d.lightbox.R;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SliceViewerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SliceViewerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SliceViewerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String TAG = "SliceViewerFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String selectedFileName = null;

    private OnFragmentInteractionListener mListener;

    public SliceViewerFragment() {
        // Required empty public constructor
    }

    ImageView contentImage;
    private ImageButton previousButton;
    private FloatingActionButton actionButton;
    private ImageButton nextButton;

    MediaPlayer mediaPlayer = null;
    private SeekBar seekbar;

    ViewPager viewPager;

    private static final String ARG_SECTION_NUMBER = "selectedFileName";
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OpenStlFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SliceViewerFragment newInstance(String param1, String param2) {
        SliceViewerFragment fragment = new SliceViewerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static SliceViewerFragment newInstance(String selectedFileName) {
        SliceViewerFragment fragment = new SliceViewerFragment();
        if(selectedFileName != null) {
            Bundle args = new Bundle();
            args.putString(ARG_SECTION_NUMBER, selectedFileName);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            this.selectedFileName = getArguments().getString(ARG_SECTION_NUMBER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.view_slice_view_player, container, false);

        viewPager = (ViewPager)rootView.findViewById(R.id.pager);
        viewPager.setAdapter(buildAdapter());

        return rootView;
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
                Integer.parseInt(getArguments().getString(ARG_SECTION_NUMBER)));

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

    private PagerAdapter buildAdapter() {

        /*
        You got a habit, got to have it ’til it’s finished don’t drop
         */
        // Unpack the files
        File unpackDir = new File(getContext().getFilesDir(),"unpacked");
        File archiveDir = new File(getContext().getFilesDir(),"archives");
        try{
            FileUtils.deleteDirectory(unpackDir);
        }catch(IOException ex){
            Log.e("file",ex.toString());
        }
        // get the name of the file
        SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();  // Put the values from the UI
//        editor.putString("file", item.getFile().getName());
        String archiveName =preferences.getString("file","SLAcer.zip");
        File archiveFile = new File(archiveDir, archiveName);

        ZipFile zipFile;
        try {
            zipFile = new ZipFile(archiveFile);
            FileUtils.forceMkdir(unpackDir);
            for(int i = 0; i < zipFile.getFileHeaders().size(); i++){
                // stopped here - unpack archive to working directory
            }

            zipFile.extractAll(unpackDir.getAbsolutePath());
        } catch (ZipException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return(new SliceViewPagerAdapter(getActivity(), getChildFragmentManager(),84));
    }
}
