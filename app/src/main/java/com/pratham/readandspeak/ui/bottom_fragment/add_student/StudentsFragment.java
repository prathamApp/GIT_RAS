package com.pratham.readandspeak.ui.bottom_fragment.add_student;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.pratham.readandspeak.RASApplication;
import com.pratham.readandspeak.RAS_Utility;
import com.pratham.readandspeak.R;
import com.pratham.readandspeak.database.AppDatabase;
import com.pratham.readandspeak.domain.Attendance;
import com.pratham.readandspeak.domain.Student;
import com.pratham.readandspeak.ui.bottom_fragment.StudentClickListener;
import com.pratham.readandspeak.ui.bottom_fragment.StudentsAdapter;
import com.pratham.readandspeak.utilities.RAS_Constants;
import com.pratham.readandspeak.ui.bottom_fragment.StudentClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class StudentsFragment extends Fragment implements StudentClickListener {
    @BindView(R.id.students_recyclerView)
    RecyclerView rl_students;
    @BindView(R.id.tv_no_student)
    TextView tv_no_student;
    private ArrayList avatars = new ArrayList();
    private List<Student> studentAvatarList;

  /*  @BindView(R.id.ib_add_student)
    ImageButton ib_add_student;
*/
    public StudentsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private void setStudentsToRecycler() {
        StudentsAdapter adapter = new StudentsAdapter(getActivity(),this,studentAvatarList, avatars);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        rl_students.setLayoutManager(mLayoutManager);
        rl_students.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(30), true));
        rl_students.setItemAnimator(new DefaultItemAnimator());
        rl_students.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void showStudents() {
        List<Student> studentList = null;

        //TODO fetch student Data and display
        studentList = AppDatabase.getDatabaseInstance(getActivity()).getStudentDao().getAllStudents();
        if (studentList != null) {
            for (int i = 0; i < studentList.size(); i++) {
                Student studentAvatar = new Student();
                studentAvatar.setStudentID(studentList.get(i).getStudentID());
                studentAvatar.setFullName(studentList.get(i).getFullName());
                studentAvatarList.add(studentAvatar);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_students, container, false);
        ButterKnife.bind(this, view);
        studentAvatarList = new ArrayList<>();
        showStudents();
        if (studentAvatarList.size() > 0) {
            rl_students.setVisibility(View.VISIBLE);
            tv_no_student.setVisibility(View.GONE);
            avatars = getRandomAvatars(studentAvatarList);
            setStudentsToRecycler();
        }else {
            rl_students.setVisibility(View.GONE);
            tv_no_student.setVisibility(View.VISIBLE);
        }
        return view;
    }

    public ArrayList<Integer> getRandomAvatars(List<Student> studentAvatarList) {
        int[] photoArray = {R.drawable.b1, R.drawable.b2, R.drawable.b3,
                R.drawable.g1, R.drawable.g2, R.drawable.g3,
                R.drawable.b1, R.drawable.b2, R.drawable.b3,
                R.drawable.g1, R.drawable.g2, R.drawable.g3,
                R.drawable.b1, R.drawable.b2, R.drawable.b3,
                R.drawable.g1, R.drawable.g2, R.drawable.g3,
                R.drawable.b1, R.drawable.b2, R.drawable.b3,
                R.drawable.g1, R.drawable.g2, R.drawable.g3,
                R.drawable.b1, R.drawable.b2, R.drawable.b3,
                R.drawable.g1, R.drawable.g2, R.drawable.g3,
                R.drawable.b1, R.drawable.b2, R.drawable.b3,
                R.drawable.g1, R.drawable.g2, R.drawable.g3,
                R.drawable.b1, R.drawable.b2, R.drawable.b3,
                R.drawable.g1, R.drawable.g2, R.drawable.g3,
                R.drawable.b1, R.drawable.b2, R.drawable.b3,
                R.drawable.g1, R.drawable.g2, R.drawable.g3,
                R.drawable.b1, R.drawable.b2, R.drawable.b3,
                R.drawable.g1, R.drawable.g2, R.drawable.g3,
                R.drawable.b1, R.drawable.b2, R.drawable.b3,
                R.drawable.g1, R.drawable.g2, R.drawable.g3,
                R.drawable.b1, R.drawable.b2, R.drawable.b3,
                R.drawable.g1, R.drawable.g2, R.drawable.g3};
        ArrayList<Integer> imageids = new ArrayList<>();

        try {
            Random rand = new Random();
            for (int i = 0; i < studentAvatarList.size(); i++) {
                int n = rand.nextInt(studentAvatarList.size()) + 1;
                imageids.add(photoArray[i]);
                Collections.shuffle(imageids);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageids;
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void onStudentClick(String studentName, String studentId) {

        String currentSession = "" + UUID.randomUUID().toString();
        RAS_Constants.currentSession = currentSession;
        AppDatabase.getDatabaseInstance(getContext()).getStatusDao().updateValue("CurrentSession", "" + currentSession);


        Attendance attendance= new Attendance();
        attendance.setSessionID(currentSession);
        attendance.setStudentID(studentId);
        attendance.setDate( RASApplication.getCurrentDateTime());
        attendance.setGroupID("PS");
        attendance.setSentFlag(0);
        RAS_Constants.currentStudentID = studentId;
        AppDatabase.getDatabaseInstance(getContext()).getAttendanceDao().insert(attendance);
        RAS_Constants.currentSession=currentSession;

//        startActivity(new Intent(getActivity(),ChooseLevelActivity.class));
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + avatar) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + avatar) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

   /* @OnClick(R.id.ib_add_student)
    public void addStudent() {
        Bundle bundle = new Bundle();
        bundle.putString("", "");
        RAS_Utility.showFragment(getActivity(), new AddStudentFragment(), R.id.student_frame,
                bundle, AddStudentFragment.class.getSimpleName());

    }*/

}
