package com.kkaysheva.task65apps;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kkaysheva.task65apps.database.model.Person;

/**
 * HostActivity
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public class HostActivity extends AppCompatActivity implements SpecialityFragment.CLickSpecialtyCallback, PersonFragment.CLickPersonCallback {

    private static final String TAG = HostActivity.class.getSimpleName();
    private FragmentManager fragmentManager;
    private Fragment personFragment;
    private Fragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        fragmentManager = getSupportFragmentManager();
        Fragment specialityFragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (specialityFragment == null) {
            specialityFragment = new SpecialityFragment();
        }
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, specialityFragment)
                .commit();
    }

    @Override
    public void specialtyClicked(int specialtyId) {
        if (personFragment == null) {
            personFragment = new PersonFragment();
        }
        Bundle args = new Bundle();
        args.putInt(PersonFragment.SPECIALTY_ID, specialtyId);
        personFragment.setArguments(args);
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, personFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void personClicked(Person person) {
        if (detailFragment == null) {
            detailFragment = new DetailPersonFragment();
        }
        Bundle args = new Bundle();
        args.putSerializable(DetailPersonFragment.PERSON, person);
        detailFragment.setArguments(args);
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit();
    }
}
