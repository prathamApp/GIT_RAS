package com.pratham.readandspeak.utilities;

public class RAS_Constants {

    public static final String FTP_CLIENT_CONNECTED = "ftp_client_connected";
    public static final String FOLDER = "folder";
    public static final int TCP_SERVER_RECEIVE_PORT = 4447;
    public static final String ACTION_NEW_MSG = "action.NEW_MSG";
    public static final String EXTRA_NEW_MSG_CONTENT = "new_msg_content";
    public static final String EXTRA_NEW_MSG_TYPE = "new_msg_type";
    public static final int NEW_MSG_TYPE_TXT = 0x401;
    public static final int NEW_MSG_TYPE_IMAGE = 0x402;
    public static final int NEW_MSG_TYPE_VOICE = 0x403;
    public static final int NEW_MSG_TYPE_FILE = 0x404;
    public static final int LOCATION_GRANTED = 1;
    public static final int NEW_MSG_TYPE_VEDIO = 0x405;
    public static final int NEW_MSG_TYPE_MUSIC = 0x406;
    public static final int NEW_MSG_TYPE_APK = 0x407;
    public static final String CONTENT = "CONTENT";
    //wifi constants
    public static final int ApScanResult = 201;
    public static final int WiFiConnectSuccess = 202;
    public static final int ApCreateApSuccess = 203;
    public static final String WIFI_AP_HEADER = "Pratham_";
    public static final String WIFI_AP_PASSWORD = "pratham123";
    public static final String FILE_SHARE_PROGRESS = "file_share_progress";
    public static final String SHARE_BACK = "share_back";
    public static final String CLOSE_FTP_SERVER = "close_ftp_server";
    public static final String REVEALX = "REVEALX";
    public static final String REVEALY = "REVEALY";
    public static final String GAME = "game";
    public static final String VIDEO = "video";
    public static final String PDF = "pdf";
    public static final String VIEW_TYPE = "VIEW_TYPE";
    public static final String DOWNLOAD = "DOWNLOAD";
    public static final String SETTINGS = "SETTINGS";
    public static final String DIR_PATH = "dir_path";
    public static final String FILE_NAME = "file_name";
    public static final String DOWNLOAD_ID = "download_id";
    public static final String DOWNLOAD_CONTENT = "download_content";
    public static final String DOWNLOAD_STARTED = "download_started";
    public static final String DOWNLOAD_UPDATE = "download_update";
    public static final String FOLDER_NAME = "folder_name";
    public static final String FILE_DOWNLOAD_ERROR= "file_download_error";
    public static final String FILE_DOWNLOAD_COMPLETE = "file_download_complete";
    public static final String DOWNLOAD_COMPLETE = "download_complete";
    public static final String API_PARENT = "api_parent";
    public static final String API_CHILD = "api_child";
    public static String HINDI = "Hindi";
    public static String ENGLISH = "English";
    public static String MARATHI = "Marathi";
    public static String KANNADA = "Kannada";
    public static String TELUGU = "Telugu";
    public static String BENGALI = "Bengali";
    public static String GUJARATI = "Gujarati";
    public static String PUNJABI = "Punjabi";
    public static String TAMIL = "Tamil";
    public static String ODIYA = "Odiya";
    public static String MALAYALAM = "Malayalam";
    public static String ASSAMESE = "Assamese";
    public static final String LANGUAGE = "language";
    public static int READ_BUFFER_SIZE = 1024 * 4;
    public static String jsonFolderPath = "JsonFiles/";
    public static final String APK = "apk";
    public static final String KEY_ASSET_COPIED = "key_asset_copied";
    public static final String KEY_MENU_COPIED = "key_menu_copied";
    public static final String SD_CARD_MENU_COPIED = "sd_card_menu_copied";
    public static final String STORAGE_ASKED = "storage_asked";
    public static final String SD_CARD_Content_STR = "sd_card_content_str";
    public static final String INITIAL_ENTRIES = "initial_entries";
    public static boolean SD_CARD_Content = false;

    public static String ext_path;
    public static String currentStudentID;
    public static String currentAssessmentStudentID;
    public static String currentsupervisorID;
    public static String currentSession;
    public static String assessmentSession;
    public static String STORING_IN;
    public static boolean assessmentFlag;
    public static boolean supervisedAssessment;
    public static final boolean SMART_PHONE = true;
    public static boolean GROUP_LOGIN = false;
    public static final String INTERNET_DOWNLOAD = "prathmApp";
    public static final String INTERNET_DOWNLOAD_API = "http://www.prodigi.openiscool.org/api/pos/Get?id=";
    public static final String INTERNET_DOWNLOAD_RESOURCE = "downloadResource";
    public static final String INTERNET_DOWNLOAD_RESOURCE_API = "http://prodigi.openiscool.org/api/pos/DownloadResource?resid=";
    public static final String GROUPID = "groupid";
    public static final String GROUPID1 = "group1";
    public static final String GROUPID2 = "group2";
    public static final String GROUPID3 = "group3";
    public static final String GROUPID4 = "group4";
    public static final String GROUPID5 = "group5";
    public static final String GROUP_AGE_BELOW_7 = "GROUP_AGE_BELOW_7";
    public static final String GROUP_AGE_ABOVE_7 = "GROUP_AGE_ABOVE_7";
    public static final String STUDENT_LIST = "STUDENT_LIST";
    public static final String SESSIONID = "sessionid";
    public static final String AVATAR = "avatar";
    public static final String PRATHAM_KOLIBRI_HOTSPOT = "prathamkolibri";

    public static final String FROMDATE = "fromDate";
    public static final String TODATE = "toDate";
    public static final String SCORE = "scores";
    public static final String ATTENDANCE = "attendances";
    public static final String ASSESSMENT = "assessment";
    public static final String SUPERVISOR = "supervisor";
    public static final String LEARNTWORDS = "learntwords";
    public static final String SESSION = "session";
    public static final String LOGS = "logs";
    public static final String SCORE_COUNT = "ScoreCount";
    public static final String STUDENTS = "students";
    public static final String METADATA = "metadata";
    public static final String USAGEDATA = "USAGEDATA";
    public static final String DOWNLOAD_URL = "download_url";
    public static final String BASE_URL = "http://prodigi.openiscool.org/api/pos/";
    public static String RASP_IP = "http://192.168.4.1:8080";
    public static final String SUCCESSFULLYPUSHED = "successfully_pushed";
    public static final String PUSHFAILED = "push_failed";
    public static  String FACILITY_ID = "facility_id";
    public static String pradigiObbPath = "";
    public static String FTP_USERNAME = "pratham";
    public static String FTP_PASSWORD = "pratham";
    public static String FTP_IP = "";
    public static String FTP_PORT = "";
    public static String BROWSE_RASPBERRY = "BROWSE_RASPBERRY";
    public static String RASPBERRY_HEADER = "RASPBERRY_HEADER";
    public static final String INTERNET_HEADER = "INTERNET_HEADER";
    public static final String BROWSE_INTERNET = "BROWSE_INTERNET";
    public static final String IS_GOOGLE_SIGNED_IN = "IS_GOOGLE_SIGNED_IN";
    public static final String GOOGLE_TOKEN = "GOOGLE_TOKEN";
    public static final String FILE_DOWNLOAD_STARTED = "file_download_started";
    public static final String FILE_DOWNLOAD_UPDATE= "file_download_update";
    public static String currentStudentName="";
    public static String CERTIFICATE_LBL = "certificate_lbl";
    public static final String PREFS_VERSION = "com.pratham.readandspeak";
    public static final String CURRENT_VERSION = "App Version";

    public static enum URL {
        BROWSE_BY_ID(BASE_URL + "get?id="),
        SEARCH_BY_KEYWORD(BASE_URL + "GetSearchList?"),
        POST_GOOGLE_DATA(BASE_URL + "PostGoogleSignIn"),
        GET_TOP_LEVEL_NODE(BASE_URL + "GetTopLevelNode?lang="),
        DATASTORE_RASPBERY_URL(RASP_IP + "/pratham/datastore/"),
        BROWSE_RASPBERRY_URL(RASP_IP + "/api/contentnode?parent="),
        GET_RASPBERRY_HEADER(RASP_IP + "/api/contentnode?content_id=f9da12749d995fa197f8b4c0192e7b2c"),
        POST_SMART_INTERNET_URL("http://www.rpi.prathamskills.org/api/pushdatasmartphone/post/"),
        POST_TAB_INTERNET_URL("http://www.rpi.prathamskills.org/api/pushdatapradigi/post/"),
        DOWNLOAD_RESOURCE(BASE_URL + "DownloadResource?resid=");


        private final String name;

        private URL(String s) {
            name = s;
        }

        public boolean equalsName(String otherName) {
            return (otherName == null) ? false : name.equals(otherName);
        }

        public String toString() {
            return name;
        }

    }

}
