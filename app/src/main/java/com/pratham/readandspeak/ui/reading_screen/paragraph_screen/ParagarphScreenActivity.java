package com.pratham.readandspeak.ui.reading_screen.paragraph_screen;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.speech.SpeechRecognizer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nex3z.flowlayout.FlowLayout;
import com.pratham.readandspeak.R;
import com.pratham.readandspeak.custom.RipplePulseLayout;
import com.pratham.readandspeak.services.TTSService;
import com.pratham.readandspeak.services.speech_recognition_service.ContinuousSpeechService;
import com.pratham.readandspeak.services.speech_recognition_service.STT_Result;
import com.pratham.readandspeak.utilities.BaseActivity;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class ParagarphScreenActivity extends BaseActivity implements /*RecognitionListener, */STT_Result {

    public static MediaPlayer mp, mPlayer;
    @BindView(R.id.myflowlayout)
    FlowLayout quesFlowLayout;
    @BindView(R.id.tv_story_title)
    TextView story_title;
    @BindView(R.id.iv_image)
    ImageView pageImage;
    @BindView(R.id.btn_prev)
    ImageButton btn_previouspage;
    @BindView(R.id.btn_next)
    ImageButton btn_nextpage;
    @BindView(R.id.btn_play)
    ImageButton btn_Play;
    @BindView(R.id.btn_read_mic)
    ImageButton btn_Mic;
    @BindView(R.id.myScrollView)
    ScrollView myScrollView;
    @BindView(R.id.layout_ripplepulse_right)
    RipplePulseLayout layout_ripplepulse_right;
    @BindView(R.id.layout_ripplepulse_left)
    RipplePulseLayout layout_ripplepulse_left;
    @BindView(R.id.layout_mic_ripple)
    RipplePulseLayout layout_mic_ripple;
    @BindView(R.id.ll_btn_play)
    LinearLayout ll_btn_play;

    ContinuousSpeechService continuousSpeechService;

    public static String storyId, StudentID = "", readingContentPath;
    TTSService ttsService;
    Context context;
    String contentType, storyPath, storyData, storyName, storyAudio, singAudio, storyBg, wordQuestion, resQuesId;
    String splitQuesNew[], readType, splitQues[];
    static int currentPage;
    public static Handler handler, audioHandler, soundStopHandler, colorChangeHandler, startReadingHandler, quesReadHandler;
    List<String> wordsDurationList = new ArrayList<String>();
    List<String> wordsResIdList = new ArrayList<String>();
    List<String> pageEndList = new ArrayList<String>();
    List<String> pageStartList = new ArrayList<String>();
    boolean fragFlg = false, lastPgFlag = false;
    JSONArray pageArray, readList;
    boolean playFlg = false, mediaPauseFlag = false, readingFinishFlag = false, pauseFlg = false;
    int wordCounter = 0, totalPages = 0, correctAnswerCount, pageNo = 1, quesNo = 0, quesPgNo = 0;
    Float pageDuration = 0f, stopPlayBack = 0f, stopSingPlayBack = 0f, startPlayBack = 0f, startSingPlayBack = 0f;
    Float soundFileWordEnd = 0f, soundFileWordStart = 0f;
    List<Integer> readSounds = new ArrayList<>();
    private Intent recognizerIntent;
    private SpeechRecognizer speech = null;
    private String LOG_TAG = "VoiceRecognitionActivity", /*myCurrentSentence,*/
            startTime;
    boolean correctArr[], voiceStart = false, flgPerMarked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //COS_Constants.SD_CARD_Content = true;
        setContentView(R.layout.activity_paragraph_screen);
       /* ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        context = this;
        continuousSpeechService = new ContinuousSpeechService(context, ReadingScreenActivity.this);

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) layout_ripplepulse_right.getLayoutParams();
        params.rightMargin = -(int) getResources().getDimension(R.dimen._25sdp);
        params.bottomMargin = -(int) getResources().getDimension(R.dimen._25sdp);
        params = (ViewGroup.MarginLayoutParams) layout_ripplepulse_left.getLayoutParams();
        params.leftMargin = -(int) getResources().getDimension(R.dimen._25sdp);
        params.bottomMargin = -(int) getResources().getDimension(R.dimen._25sdp);

        Intent intent = getIntent();
        contentType = getIntent().getStringExtra("contentType");
        storyData = intent.getStringExtra("storyData");
        storyPath = intent.getStringExtra("storyPath");
        storyId = intent.getStringExtra("storyId");
        StudentID = intent.getStringExtra("StudentID");
        readType = intent.getStringExtra("readType");
        storyName = intent.getStringExtra("storyTitle");
        ttsService = BaseActivity.ttsService;
        if (contentType.equalsIgnoreCase("rhymes"))
            layout_mic_ripple.setVisibility(View.GONE);

        readSounds.add(R.raw.tap_the_mic);
        readSounds.add(R.raw.your_turn_to_read);
        readSounds.add(R.raw.would_you_like_to_read);
        readSounds.add(R.raw.tap_the_mic_to_read_out);
        Collections.shuffle(readSounds);
        startTime = COSApplication.getCurrentDateTime();

        currentPage = 0;
        //setMute(0);
        if (COS_Constants.SMART_PHONE && !COS_Constants.SD_CARD_Content)
            readingContentPath = COSApplication.pradigiPath + "/.LLA/English/Game/" + storyPath + "/";
        else if (COS_Constants.SMART_PHONE && COS_Constants.SD_CARD_Content)
            readingContentPath = COS_Constants.ext_path + "/.LLA/English/Game/" + storyPath + "/";
        if (speech != null)
            speech.destroy();
        speech = SpeechRecognizer.createSpeechRecognizer(this);
        continuousSpeechService.resetSpeechRecognizer();

        try {
            pageArray = fetchJsonData(storyName);
            story_title.setText(storyName);
            getWordsOfStoryOfPage();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void Stt_onResult(String sttResult) {

    }
/*
    public JSONArray fetchJsonData(String jasonName) {
        JSONArray returnModalGames = null;
        try {
            InputStream is = new FileInputStream(readingContentPath + "/Data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String jsonStr = new String(buffer);
            JSONObject jsonObj = new JSONObject(jsonStr);
            returnModalGames = jsonObj.getJSONArray("pageList");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnModalGames;
    }

    private void getWordsOfStoryOfPage() {
        try {
            totalPages = pageArray.length();
            Log.d("totalPages", "getWordsOfStoryOfPage: " + totalPages);
            initializeContent(currentPage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeContent(final int currentPage) {

        if (currentPage == totalPages - 1) {
            lastPgFlag = true;
            layout_ripplepulse_right.setVisibility(View.GONE);
        }
        if (currentPage == 0) {
            lastPgFlag = false;
            layout_ripplepulse_left.setVisibility(View.GONE);
        }
        if (currentPage < totalPages - 1 && currentPage > 0) {
            lastPgFlag = false;
            layout_ripplepulse_left.setVisibility(View.VISIBLE);
            layout_ripplepulse_right.setVisibility(View.VISIBLE);
        }

        JSONObject page = null, readListObj = null;
        List<String> linesStringList = new ArrayList<String>();
        wordsDurationList.clear();
        quesFlowLayout.removeAllViews();

        try {
            page = pageArray.getJSONObject(currentPage);
            storyAudio = page.getString("readPageAudio");
            storyBg = page.getString("pageImage");

            File f = new File(readingContentPath + storyBg);
            if (f.exists()) {
                Bitmap bmImg = BitmapFactory.decodeFile(readingContentPath + storyBg);
                BitmapFactory.decodeStream(new FileInputStream(readingContentPath + storyBg));
                pageImage.setImageBitmap(bmImg);
            }

            readList = page.getJSONArray("readList");
            correctArr = new boolean[readList.length()];
            splitQues = new String[readList.length()];
            splitQuesNew = new String[readList.length()];
            stopPlayBack = 0f;

            for (int j = 0; j < readList.length(); j++) {
                splitQues[j] = readList.getJSONObject(j).getString("word");
                //splitQuesNew[j] = splitQues[j].replaceAll("[\\-\\+\\.\\^\\?\\'\\!\\\"\\@\\;\\%\\&\\,\\:,]", "");
//                splitQuesNew[j] = splitQues[j].replaceAll("\\p{Punct}", "");
                String regex = "(?:(?<!\\S)\\p{Punct}+)|(?:\\p{Punct}+(?!\\S))";
                splitQuesNew[j] = splitQues[j].replaceAll(regex, "");
                splitQuesNew[j] = splitQuesNew[j].replaceAll("[^A-Za-z]+", "");
                linesStringList.add(readList.getJSONObject(j).getString("word"));
                wordsDurationList.add(readList.getJSONObject(j).getString("wordDuration"));
                wordsResIdList.add(readList.getJSONObject(j).getString("wordId"));
                stopPlayBack = stopPlayBack + Float.valueOf(readList.getJSONObject(j).getString("wordDuration"));
            }
            startPlayBack = Float.valueOf(readList.getJSONObject(0).getString("wordFrom"));
            pageStartList.add(readList.getJSONObject(0).getString("wordFrom"));
            pageEndList.add(String.valueOf(stopPlayBack));

            setWordsToLayout(splitQues);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    btn_Play.performClick();
                }
            }, 200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void startAudioReading(int read) {
        try {
            mp = new MediaPlayer();
            float stopAudio;
            mp.setDataSource(readingContentPath + storyAudio);
            stopAudio = stopPlayBack;
            mp.prepare();
            mp.seekTo((int) (startPlayBack * 1000));
        } catch (Exception e) {
            e.printStackTrace();
        }
        startStoryReading(wordCounter);
    }

    public void startStoryReading(final int index) {
        float wordDuration = 1;
        handler = new Handler();
        colorChangeHandler = new Handler();
        mp.start();
        TextView myNextView = null;

        if (index < wordsDurationList.size()) {
            wordDuration = Float.parseFloat(wordsDurationList.get(index));
            final TextView myView = (TextView) quesFlowLayout.getChildAt(index);
            if (index < quesFlowLayout.getChildCount())
                myNextView = (TextView) quesFlowLayout.getChildAt(index + 1);

            if (myNextView != null)
                isScrollBelowVisible(myNextView);
            myView.setTextColor(getResources().getColor(R.color.colorRedDark));
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.reading_zoom_in);
            myView.startAnimation(animation);
//            wordPopUp(this, myView);
            colorChangeHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    myView.setTextColor(getResources().getColor(R.color.colorPrimary));
//                    wordPopDown(ReadingScreenActivity.this, myView);
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.reading_zoom_out);
                    myView.startAnimation(animation);
                }
            }, 350);
            if (index == wordsDurationList.size() - 1) {
                try {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                playFlg = false;
                                pauseFlg = true;
                                if (!contentType.equalsIgnoreCase("rhymes")) {
                                    layout_mic_ripple.setVisibility(View.VISIBLE);
                                    quesReadHandler = new Handler();
                                    quesReadHandler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Collections.shuffle(readSounds);
                                            mPlayer = MediaPlayer.create(ReadingScreenActivity.this, readSounds.get(0));
                                            mPlayer.start();
                                        }
                                    }, (long) (5000));
                                }
                                if (mp != null && mp.isPlaying())
                                    mp.stop();
                                btn_Play.setImageResource(R.drawable.ic_play_arrow);
                                layout_mic_ripple.startRippleAnimation();
                                layout_ripplepulse_right.startRippleAnimation();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, (long) (wordDuration * 1000));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mediaPauseFlag = true;
            }
        } else
            wordDuration = 1;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (index < quesFlowLayout.getChildCount()) {
                    wordCounter += 1;
                    if (!pauseFlg)
                        startStoryReading(wordCounter);
                } else {
                    for (int i = 0; i < wordsDurationList.size(); i++) {
                        TextView myView = (TextView) quesFlowLayout.getChildAt(i);
                        myView.setBackgroundColor(Color.TRANSPARENT);
                        myView.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }
                    wordCounter = 0;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //TODO start mic
                        }
                    }, (long) (900));
                }
            }
        }, (long) (wordDuration * 1000));

    }

    private void wordPopUp(Context c, final TextView tv_word) {
        final Animation anim_in = AnimationUtils.loadAnimation(c, R.anim.reading_zoom_in);
        anim_in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }
        });
        tv_word.startAnimation(anim_in);
    }

    private void wordPopDown(Context c, final TextView tv_word) {
        final Animation anim_out = AnimationUtils.loadAnimation(c, R.anim.reading_zoom_out);
        anim_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }
        });
        tv_word.startAnimation(anim_out);
    }

    //If you want to detect that the view is FULLY visible:
    private void isScrollBelowVisible(View view) {
        Rect scrollBounds = new Rect();
        myScrollView.getDrawingRect(scrollBounds);

        float top = view.getY();
        float bottom = top + view.getHeight();

        if (scrollBounds.top < top && scrollBounds.bottom > bottom) {
            //return true;
        } else {
            view.getParent().requestChildFocus(view, view);
            //myScrollView.smoothScrollTo(view.getTop(), view.getBottom());
        }
    }

    //If you want to detect that the view is FULLY visible:
    private void isScrollAboveVisible(View view) {
        Rect scrollBounds = new Rect();
        myScrollView.getDrawingRect(scrollBounds);

        float top = view.getY();
        float bottom = top + view.getHeight();

        if (scrollBounds.top < top && scrollBounds.bottom > bottom) {
            //return true;
        } else {
            myScrollView.fullScroll(View.FOCUS_UP);
        }
    }

    */
/*    private void stopSpeechInput() {
        speech.stopListening();
    }

    private void startSpeechInput() {
        setRecogniserIntent();
        speech.startListening(recognizerIntent);
    }*/
/*

    @OnClick(R.id.btn_read_mic)
    void sttMethod() {
        if (!voiceStart) {
            voiceStart = true;
            flgPerMarked = false;
            btn_Mic.setImageResource(R.drawable.ic_stop_black_24dp);
            layout_mic_ripple.stopRippleAnimation();
            ll_btn_play.setVisibility(View.GONE);
            layout_ripplepulse_right.stopRippleAnimation();
            try {
                if (quesReadHandler != null) {
                    quesReadHandler.removeCallbacksAndMessages(null);
                    try {
                        if (mPlayer.isPlaying() && mPlayer != null) {
                            mPlayer.stop();
                            mPlayer.reset();
                            mPlayer.release();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            continuousSpeechService.startSpeechInput();
        } else {
            voiceStart = false;
            ll_btn_play.setVisibility(View.VISIBLE);
            btn_Mic.setImageResource(R.drawable.ic_mic_black_24dp);
            layout_mic_ripple.startRippleAnimation();
            layout_ripplepulse_right.startRippleAnimation();
            continuousSpeechService.stopSpeechInput();
        }
    }

    @OnClick(R.id.btn_play)
    void playReading() {
        if (!playFlg || pauseFlg) {
            layout_mic_ripple.setVisibility(View.GONE);
            playFlg = true;
            pauseFlg = false;
            try {
                if (quesReadHandler != null) {
                    quesReadHandler.removeCallbacksAndMessages(null);
                    try {
                        if (mPlayer.isPlaying() && mPlayer != null) {
                            mPlayer.stop();
                            mPlayer.reset();
                            mPlayer.release();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            btn_Play.setImageResource(R.drawable.ic_stop_black_24dp);
//            btn_Play.setText("Stop");
            if (audioHandler != null)
                audioHandler.removeCallbacksAndMessages(null);
            if (handler != null)
                handler.removeCallbacksAndMessages(null);
            if (colorChangeHandler != null)
                colorChangeHandler.removeCallbacksAndMessages(null);
            if (quesReadHandler != null) {
                quesReadHandler.removeCallbacksAndMessages(null);
                try {
                    if (mPlayer.isPlaying() && mPlayer != null) {
                        mPlayer.stop();
                        mPlayer.reset();
                        mPlayer.release();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (startReadingHandler != null)
                startReadingHandler.removeCallbacksAndMessages(null);
            if (soundStopHandler != null)
                soundStopHandler.removeCallbacksAndMessages(null);
            wordCounter = 0;
            layout_ripplepulse_right.stopRippleAnimation();
            setMute(0);
            startAudioReading(1);
        } else {
            btn_Play.setImageResource(R.drawable.ic_play_arrow);
            layout_mic_ripple.startRippleAnimation();
            layout_ripplepulse_right.startRippleAnimation();
            int a = setBooleanGetCounter();
//            btn_Play.setText("Read");
            if (!contentType.equalsIgnoreCase("rhymes"))
                layout_mic_ripple.setVisibility(View.VISIBLE);
            wordCounter = 0;
            try {
                playFlg = false;
                pauseFlg = true;
                try {
                    if (mp.isPlaying()) {
                        mp.stop();
                        mp.reset();
                        mp.release();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (startReadingHandler != null)
                    startReadingHandler.removeCallbacksAndMessages(null);
                if (soundStopHandler != null)
                    soundStopHandler.removeCallbacksAndMessages(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int setBooleanGetCounter() {
        int counter = 0;
        for (int x = 0; x < correctArr.length; x++) {
            if (correctArr[x]) {
                ((TextView) quesFlowLayout.getChildAt(x)).setTextColor(getResources().getColor(R.color.colorGreenDark));
                counter++;
            }
        }

        return counter;
    }

    public float getPercentage() {
        int counter = 0;
        float perc = 0f;
        for (int x = 0; x < correctArr.length; x++) {
            if (correctArr[x]) {
                ((TextView) quesFlowLayout.getChildAt(x)).setTextColor(getResources().getColor(R.color.colorGreenDark));
                counter++;
            }
        }
        if (counter > 0)
            perc = ((float) counter / (float) correctArr.length) * 100;

        return perc;
    }

    @OnClick(R.id.btn_prev)
    void gotoPrevPage() {
        if (currentPage > 0) {
            wordCounter = 0;
            ButtonClickSound.start();
            try {
                if (audioHandler != null)
                    audioHandler.removeCallbacksAndMessages(null);
                if (handler != null)
                    handler.removeCallbacksAndMessages(null);
                if (colorChangeHandler != null)
                    colorChangeHandler.removeCallbacksAndMessages(null);
                if (quesReadHandler != null) {
                    quesReadHandler.removeCallbacksAndMessages(null);
                    try {
                        if (mPlayer.isPlaying() && mPlayer != null) {
                            mPlayer.stop();
                            mPlayer.reset();
                            mPlayer.release();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (soundStopHandler != null)
                    soundStopHandler.removeCallbacksAndMessages(null);
                if (startReadingHandler != null)
                    startReadingHandler.removeCallbacksAndMessages(null);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (voiceStart) {
                voiceStart = false;
                ll_btn_play.setVisibility(View.VISIBLE);
                btn_Mic.setImageResource(R.drawable.ic_mic_black_24dp);
                continuousSpeechService.stopSpeechInput();
                setMute(0);
            }
            Log.d("click", "totalPages: PreviousBtn: " + totalPages + "  currentPage: " + currentPage);
            try {
                if (mp != null && mp.isPlaying()) {
                    mp.stop();
                    mp.reset();
                    mp.release();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!flgPerMarked) {
                flgPerMarked = true;
                correctAnswerCount = setBooleanGetCounter();
                float perc = getPercentage();
                addScore(0, "perc - " + perc, correctAnswerCount, correctArr.length, "" + contentType);
            }

            try {
                currentPage--;
                pageNo--;
                playFlg = false;
                pauseFlg = true;
                flgPerMarked = false;
                initializeContent(currentPage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @OnClick(R.id.btn_next)
    void gotoNextPage() {
        if (currentPage < totalPages - 1) {
            layout_ripplepulse_right.startRippleAnimation();
            wordCounter = 0;
            setMute(0);
            try {
                if (audioHandler != null)
                    audioHandler.removeCallbacksAndMessages(null);
                if (handler != null)
                    handler.removeCallbacksAndMessages(null);
                if (colorChangeHandler != null)
                    colorChangeHandler.removeCallbacksAndMessages(null);
                if (quesReadHandler != null) {
                    quesReadHandler.removeCallbacksAndMessages(null);
                    try {
                        if (mPlayer.isPlaying() && mPlayer != null) {
                            mPlayer.stop();
                            mPlayer.reset();
                            mPlayer.release();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (soundStopHandler != null)
                    soundStopHandler.removeCallbacksAndMessages(null);
                if (startReadingHandler != null)
                    startReadingHandler.removeCallbacksAndMessages(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (voiceStart) {
                voiceStart = false;
                ll_btn_play.setVisibility(View.VISIBLE);
                btn_Mic.setImageResource(R.drawable.ic_mic_black_24dp);
                continuousSpeechService.stopSpeechInput();
                setMute(0);
            }
            ButtonClickSound.start();
            Log.d("click", "totalPages: NextBtn: " + totalPages + "  currentPage: " + currentPage);
            try {
                if (mp != null && mp.isPlaying()) {
                    mp.stop();
                    mp.reset();
                    mp.release();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!flgPerMarked) {
                flgPerMarked = true;
                correctAnswerCount = setBooleanGetCounter();
                float perc = getPercentage();
                addScore(0, "perc - " + perc, correctAnswerCount, correctArr.length, "" + contentType);
            }

            currentPage++;
            pageNo++;
            flgPerMarked = true;
            playFlg = false;
            pauseFlg = true;
            initializeContent(currentPage);
            Log.d("click", "NextBtn - totalPages: " + totalPages + "  currentPage: " + currentPage);
        }
    }

    public void showAcknowledgeDialog(boolean diaComplete) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.exit_dialog_reading);
        dialog.setCanceledOnTouchOutside(false);
        TextView dia_title = dialog.findViewById(R.id.dia_title);
        TextView dia_s_name = dialog.findViewById(R.id.dia_s_name);
        Button no_btn = dialog.findViewById(R.id.dia_btn_exit);
        Button yes_btn = dialog.findViewById(R.id.dia_btn_restart);
        ImageButton exit_ib = dialog.findViewById(R.id.exit_ib);
        dialog.show();

        if(!diaComplete)
            dia_s_name.setText("'" + storyName + "'");
        else{
            dia_title.setText("Good Job");
            dia_s_name.setText("Read another one???");
        }
        no_btn.setText("Yes");
        yes_btn.setText("NO");
        exit_ib.setVisibility(View.VISIBLE);

        if (fragFlg)
            yes_btn.setVisibility(View.GONE);

        exit_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                loadFragment();
                fragFlg = true;
            }
        });
        yes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        no_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
//                if (fragFlg)
//                    fragFlg=false;
                exitDBEntry();
                ReadingScreenActivity.super.onBackPressed();
            }
        });
    }

    public void exitDBEntry() {
        try {
            if (mp != null && mp.isPlaying()) {
                mp.stop();
                mp.reset();
                mp.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (colorChangeHandler != null)
                colorChangeHandler.removeCallbacksAndMessages(null);
            if (quesReadHandler != null) {
                quesReadHandler.removeCallbacksAndMessages(null);
                try {
                    if (mPlayer.isPlaying() && mPlayer != null) {
                        mPlayer.stop();
                        mPlayer.reset();
                        mPlayer.release();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (soundStopHandler != null)
                soundStopHandler.removeCallbacksAndMessages(null);
            if (handler != null)
                handler.removeCallbacksAndMessages(null);
            if (mp.isPlaying())
                mp.stop();
            if (startReadingHandler != null)
                startReadingHandler.removeCallbacksAndMessages(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (!flgPerMarked) {
                flgPerMarked = true;
                correctAnswerCount = setBooleanGetCounter();
                float perc = getPercentage();
                addScore(0, "perc - " + perc, correctAnswerCount, correctArr.length, "" + contentType);
            }
            addScore(0, "", 0, 0, contentType + " End");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        try {
            if (audioHandler != null)
                audioHandler.removeCallbacksAndMessages(null);
            if (handler != null)
                handler.removeCallbacksAndMessages(null);
            if (colorChangeHandler != null)
                colorChangeHandler.removeCallbacksAndMessages(null);
            if (quesReadHandler != null) {
                quesReadHandler.removeCallbacksAndMessages(null);
                try {
                    if (mPlayer.isPlaying() && mPlayer != null) {
                        mPlayer.stop();
                        mPlayer.reset();
                        mPlayer.release();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (soundStopHandler != null)
                soundStopHandler.removeCallbacksAndMessages(null);
            if (startReadingHandler != null)
                startReadingHandler.removeCallbacksAndMessages(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (voiceStart) {
            voiceStart = false;
            ll_btn_play.setVisibility(View.VISIBLE);
            btn_Mic.setImageResource(R.drawable.ic_mic_black_24dp);
            continuousSpeechService.stopSpeechInput();
            setMute(0);
        }
        try {
            if (mp != null && mp.isPlaying()) {
                mp.stop();
                mp.reset();
                mp.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!fragFlg) {
            showAcknowledgeDialog(false);
        } else {
            fragFlg = false;
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (voiceStart) {
            voiceStart = false;
            ll_btn_play.setVisibility(View.VISIBLE);
            btn_Mic.setImageResource(R.drawable.ic_mic_black_24dp);
            continuousSpeechService.stopSpeechInput();
            setMute(0);
        }
        try {

            if (audioHandler != null)
                audioHandler.removeCallbacksAndMessages(null);
            if (handler != null)
                handler.removeCallbacksAndMessages(null);
            if (colorChangeHandler != null)
                colorChangeHandler.removeCallbacksAndMessages(null);
            if (quesReadHandler != null) {
                quesReadHandler.removeCallbacksAndMessages(null);
                try {
                    if (mPlayer.isPlaying() && mPlayer != null) {
                        mPlayer.stop();
                        mPlayer.reset();
                        mPlayer.release();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (soundStopHandler != null)
                soundStopHandler.removeCallbacksAndMessages(null);
            if (mp.isPlaying())
                mp.stop();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

*/

    /*
    private void resetSpeechRecognizer() {
        continuousSpeechService.resetSpeechRecognizer();
        if (speech != null)
            speech.destroy();
        speech = SpeechRecognizer.createSpeechRecognizer(this);
        Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(this));
        if (SpeechRecognizer.isRecognitionAvailable(this))
            speech.setRecognitionListener(this);
        else
            finish();
    }

    private void setRecogniserIntent() {
        continuousSpeechService.setRecogniserIntent();
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                "en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-IN");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 10000);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 20000);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
    }

    @Override
    public void onBeginningOfSpeech() {
        setMute(1);
        Log.i(LOG_TAG, "onBeginningOfSpeech");
    }

    @Override
    public void onRmsChanged(float rmsdB) {
    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {
        speech.stopListening();
    }

    @Override
    public void onError(int error) {
        if (voiceStart) {
            resetSpeechRecognizer();
            speech.startListening(recognizerIntent);
        }
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
    }

    @Override
    public void onResults(Bundle results) {
        Log.i(LOG_TAG, "onResults");
        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        String sttResult = matches.get(0);
        Log.d("STT-Res", "\n");
        flgPerMarked = false;

        String splitRes[] = sttResult.split(" ");
        String word = " ";

        for (int j = 0; j < splitRes.length; j++) {
            splitRes[j] = splitRes[j].replaceAll("[^A-Za-z]+", "");
            for (int i = 0; i < splitQuesNew.length; i++) {
                if ((splitRes[j].equalsIgnoreCase(splitQuesNew[i])) && !correctArr[i]) {
                    correctArr[i] = true;
                    word = word + splitQuesNew[i] + "(" + wordsResIdList.get(i) + "),";
                    break;
                }
            }
        }

        int correctCnt = setBooleanGetCounter();
        addScore(0, "Words:" + word, correctCnt, correctArr.length, " ");
        float perc = getPercentage();

        if (perc >= 75) {
            for (int i = 0; i < splitQuesNew.length; i++) {
                ((TextView) quesFlowLayout.getChildAt(i)).setTextColor(getResources().getColor(R.color.colorGreenDark));
                correctArr[i] = true;
            }
            sttMethod();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setMute(0);
                    btn_nextpage.performClick();
                }
            }, 1000);
        }

        if (!voiceStart) {
            resetSpeechRecognizer();
            btn_Play.setVisibility(View.VISIBLE);
            btn_Mic.setImageResource(R.drawable.ic_mic_black_24dp);
            setMute(0);
        } else
            speech.startListening(recognizerIntent);
    }
    */
/*

    @Override
    public void Stt_onResult(String sttResult) {

        Log.d("STT-Res", "\n");
        flgPerMarked = false;

        String splitRes[] = sttResult.split(" ");
        String word = " ";

        for (int j = 0; j < splitRes.length; j++) {
            splitRes[j] = splitRes[j].replaceAll("[^A-Za-z]+", "");
            for (int i = 0; i < splitQuesNew.length; i++) {
                if ((splitRes[j].equalsIgnoreCase(splitQuesNew[i])) && !correctArr[i]) {
                    correctArr[i] = true;
                    word = word + splitQuesNew[i] + "(" + wordsResIdList.get(i) + "),";
                    break;
                }
            }
        }

        int correctCnt = setBooleanGetCounter();
        addScore(0, "Words:" + word, correctCnt, correctArr.length, " ");
        float perc = getPercentage();

        if (perc >= 75) {
            for (int i = 0; i < splitQuesNew.length; i++) {
                ((TextView) quesFlowLayout.getChildAt(i)).setTextColor(getResources().getColor(R.color.colorGreenDark));
                correctArr[i] = true;
            }
            sttMethod();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //setMute(0);
                    if (lastPgFlag)
                        showAcknowledgeDialog(true);
                    else {
                        btn_nextpage.performClick();
                    }
                }
            }, 1000);
        }
*/
/*        if (!voiceStart) {
            resetSpeechRecognizer();
            btn_Play.setVisibility(View.VISIBLE);
            btn_Mic.setImageResource(R.drawable.ic_mic_black_24dp);
            setMute(0);
        } else
            speech.startListening(recognizerIntent);*//*

    }

    public void loadFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("contentType", contentType);
        COS_Utility.showFragment(ReadingScreenActivity.this, new fragment_acknowledge(), R.id.story_ll,
                bundle, fragment_acknowledge.class.getSimpleName());
    }

    public void addScore(final int wID, final String Word, final int scoredMarks, final int totalMarks, final String Label) {
        boolean _wasSuccessful = false;

        new AsyncTask<Object, Void, Object>() {

            @Override
            protected Object doInBackground(Object[] objects) {
                try {

                    String deviceId = appDatabase.getStatusDao().getValue("DeviceId");

                    Score score = new Score();
                    score.setSessionID(COS_Constants.currentSession);
                    score.setResourceID(storyId);
                    score.setQuestionId(wID);
                    score.setScoredMarks(scoredMarks);
                    score.setTotalMarks(totalMarks);
                    score.setStudentID(COS_Constants.currentStudentID);
                    score.setStartDateTime(startTime);
                    score.setDeviceID(deviceId.equals(null) ? "0000" : deviceId);
                    score.setEndDateTime(COSApplication.getCurrentDateTime());
                    score.setLevel(0);
                    score.setLabel(Word + " - " + Label);
                    score.setSentFlag(0);
                    appDatabase.getScoreDao().insert(score);
                    BackupDatabase.backup(ReadingScreenActivity.this);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        }.execute();
    }

*/
}
