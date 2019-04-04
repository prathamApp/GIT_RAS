package com.pratham.readandspeak.ui.reading_screen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.pratham.readandspeak.R;
import com.pratham.readandspeak.modalclasses.CreditsModal;

import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pratham.readandspeak.ui.reading_screen.ReadingScreenActivity.readingContentPath;


public class fragment_acknowledge extends Fragment {

    ArrayList<JSONObject> creditsData;
    ArrayList<CreditsModal> listForAdapter;
    ArrayAdapter<CreditsModal> listAdapter;
    CreditsModal creditsModal;

    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.credits_main)
    RelativeLayout ll_convo_main;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_acknowledge, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        String contentType = getArguments().getString("contentType", "Rhymes");
        String jsonName="RhymesCredits";

        if(contentType.equalsIgnoreCase("Rhymes"))
            jsonName="Credits";
        if(contentType.equalsIgnoreCase("Stories"))
            jsonName="StoriesCredits";

        listForAdapter = new ArrayList<>();
        JSONObject creditsArray = fetchCredits(jsonName);

        listForAdapter = getList(creditsArray);
        setValuesToAdapter();
    }

    private ArrayList<CreditsModal> getList(JSONObject creditsObject) {
        ArrayList<CreditsModal> listForAdapter = new ArrayList<>();
        Iterator<String> keysItr = null;
        int itr=0;
        try {
            keysItr = creditsObject.getJSONObject("creditsList").keys();
        } catch(Exception e) {
            e.printStackTrace();
        }

        try {
/*            for (int i = 0; i < creditsArray.length(); i++) {
                if (creditsArray.getJSONObject(i).getString("resourceId").equalsIgnoreCase(storyId)) {
                    keysItr = creditsArray.getJSONObject(i).getJSONObject("ack").keys();
                    itr=i;
                    break;
                }
            }*/
            while (keysItr.hasNext()) {
                String key = keysItr.next();
                Object value = creditsObject.getJSONObject("creditsList").getString(key);
                listForAdapter.add(new CreditsModal(key,value.toString()));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return listForAdapter;
    }

    public void setValuesToAdapter() {
        listAdapter = new AcknowledgementAdapter(getActivity(), this.listForAdapter);
        listView.setAdapter(listAdapter);
    }

    public JSONObject fetchCredits(String jasonName) {
        JSONObject jsonObj = null;
        try {
            InputStream is = new FileInputStream(readingContentPath + "Credits.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String jsonStr = new String(buffer);
            jsonObj = new JSONObject(jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

}