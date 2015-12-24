/**
 * 
 */
package com.b510.mp3.common;


/**
 *  All variables will be here.
 * @author Hongten
 * @created 23 Dec, 2015
 */
public class Common {

	public static final String EMPTY = "";
	public static final String BLANK = " ";
	public static final String FULL_SPOT = ".";
	public static final String COLOR = ":";
	public static final String POUND_SIGN = "#";
	public static final String CHAR_ZERO = "0";
	public static final String QUESTION_MARK = "?";
	public static final String ELLIPSIS_SIGN = "/";
	public static final String DOUBLE_ELLIPSIS = ELLIPSIS_SIGN + ELLIPSIS_SIGN;
	public static final String BLACKSLASH = "\\";
	public static final String ENCODING_UTF_8 = "UTF-8";
	public static final String MP3_PLAYER = "Mp3 Player";
	public static final String TIME_DEFAULT = "00:00";
	
	// Menu in MainUI
	public static final String FILE = "File";

	// button and menu description
	public static final String EXIT = "Exit";
	public static final String PLAY = "Play";
	public static final String PAUSE = "Pause";
	public static final String NEXT = "Next";
	public static final String PREVIOUS = "Previous";
	public static final String ADD = "Add";
	public static final String MINI = "Mini";
	public static final String ABOUT = "About " + MP3_PLAYER;

	// Play mode
	public static final String SHUFFLE_MODE = "Shuffle";
	public static final int SHUFFLE_MODE_VALUE = 0; // Default
	public static final String REPEAT_ONCE_MODE = "Repeat Once";
	public static final int REPEAT_ONCE_MODE_VALUE = 1;
	public static final String ORDER_MODE = "Order";
	public static final int ORDER_MODE_VALUE = 2;
	
	//image resource path
	public static final String BASE_IMAGE_PATH = "com/b510/mp3/resources/images/";
	public static final String IMAGE_PREVIOUS = BASE_IMAGE_PATH + "previous.png";
	public static final String IMAGE_PLAY = BASE_IMAGE_PATH + "play.png";
	public static final String IMAGE_NEXT = BASE_IMAGE_PATH + "next.png";
	public static final String IMAGE_SHUFFLE = BASE_IMAGE_PATH + "shuffle.png";
	public static final String IMAGE_PAUSE = BASE_IMAGE_PATH + "pause.png";
	public static final String IMAGE_REPEAT = BASE_IMAGE_PATH + "repeat.png";
	public static final String IMAGE_ORDER = BASE_IMAGE_PATH + "order.png";
	public static final String IMAGE_TRAY = BASE_IMAGE_PATH + "tray.png";
	
	//tray
	public static final String OPEN_MAIN_PANEL = "Open main panel";
	public static final String RESTORE = "Restore";
	
	//skin
	public static final String DESCRIPTION = "Desctiption";
	public static final String CURRENT_SINK = "Current Skin" + BLANK + COLOR + BLANK;
	public static final String DESCRIPTION_WITH_COLOR = DESCRIPTION + BLANK + COLOR + BLANK;
	public static final String HOME_PAGE = "http://www.cnblogs.com/hongten";
	public static final String SUBSTANCE_SKINS_PAGE =  HOME_PAGE + "/p/hongten_notepad_substance_skins.html";
	public static final String SKIN = "Change Skin";
	public static final String ACCESS_URL_COMMAND = "rundll32 url.dll,FileProtocolHandler ";
	
	// KeyStroke
	public static final char N = 'N';
	public static final char W = 'W';
	public static final char M = 'M';
	public static final char A = 'A';

	// Dialog messages and titles
	public static final String CONFIRM_EXIT = "Confirm Exit";
	public static final String EXIT_SYSTEM = "Exit MP3 Player?";
	public static final String CONFIRM_REMOVE = "Confirm Remove";
	public static final String REMOVE_SONG = "Remove ";
	public static final String CONFIRM_MINIMIZE = "Minimize to tray?";
	public static final String NOTICE = "Notice";
	
	// mp3(wav) format
	public static final String MP3 = "mp3";
	public static final String DEFAULT_DIRECTORY_PATH = "c:/";
	public static final String PLAY_LIST_SAVE_PATH = DEFAULT_DIRECTORY_PATH + "mp3Player_playlist.xml";
	public static final String SELECT_DIR = "Select Directory";
	
	public static final int ZERO = 0;
	public static final int TABLE_ROW_HEIGHT = 20;
	public static final int WINDOW_WIDTH = 350;
	public static final int WINDOW_HEIGHT = 590;
	public static final int WINDOW_MINI_HEIGHT = 197;
	
	//mp3Player_playlist.xml element
	public static final String ROOT_ELEMENT = "playlist";
	public static final String COMMENT = "Playlist for all songs.";
	public static final String COMMENT_TOTAL = "Total : ";
	public static final String SUB_ELEMENT_SONG = "song";
	public static final String SUB_ELEMENT_ATTR_ID = "id";
	public static final String SUB_ELEMENT_NAME = "name";
	public static final String SUB_ELEMENT_PATH = "path";
	public static final String SUB_ELEMENT_TIME = "time";
	public static final String SUB_ELEMENT_SIZE = "size";
	
	public static final String ABOUT_INFO =
			"<html><head><style>"
			+ "table {"
			+"width:100%;}"
			+"table, td {"
			+"border: 1px solid black;}"
			+ "</style>"
			+ "</head>"
	
			+ "<body>"
			+ "<br>"
				+ "<table id='t01'>"
					+ "<tr>"
				    	+ "<td>Application Name</td>"
				    	+ "<td>Mp3 Player</td>"		
				    + "</tr>"
				    + "<tr>"
				    	+ "<td>Version</td>"
				    	+ "<td>1.0</td>"	
				    + "</tr>"
				    + "<tr>"
				    	+ " <td>Author</td>"
				    	+ " <td>Hongten</td>"		
				    + "</tr>"
				    + " <tr>"
				  		+ " <td>Home Page</td>"
				  		+ "<td><a href='http://www.cnblogs.com/hongten' target='_blank'><font color='#880000'><b>http://www.cnblogs.com/hongten</b></font></a></td>"	
				    + "</tr>"
				    + " <tr>"
			  			+ " <td>github</td>"
			  			+ "<td><a href='https://github.com/Hongten' target='_blank'><font color='#880000'><b>https://github.com/Hongten</b></font></a></td>"	
			  		+ "</tr>"
				+ "</table>"

		+ "</body>" + "</html>";

	public static final String[][] SKINS = { { "AutumnSkin", "1", "<html><a href=''>What is the AutumnSkin skin?</a></html>" }, { "BusinessBlackSteelSkin", "2", "<html><a href=''>What is the BusinessBlackSteelSkin skin?</a></html>" }, { "ChallengerDeepSkin", "3", "<html><a href=''>What is the ChallengerDeepSkin skin?</a></html>" }, { "CremeCoffeeSkin", "4", "<html><a href=''>What is the CremeCoffeeSkin skin?</a></html>" }, { "CremeSkin", "5", "<html><a href=''>What is the CremeSkin skin?</a></html>" }, { "EbonyHighContrastSkin", "6", "<html><a href=''>What is the EbonyHighContrastSkin skin?</a></html>" }, { "EmeraldDuskSkin", "7", "<html><a href=''>What is the EmeraldDuskSkin skin?</a></html>" }, { "FieldOfWheatSkin", "8", "<html><a href=''>What is the FieldOfWheatSkin skin?</a></html>" }, { "FindingNemoSkin", "9", "<html><a href=''>What is the FindingNemoSkin skin?</a></html>" }, { "GreenMagicSkin", "10", "<html><a href=''>What is the GreenMagicSkin skin?</a></html>" }, { "MagmaSkin", "11", "<html><a href=''>What is the MagmaSkin skin?</a></html>" }, { "MangoSkin", "12", "<html><a href=''>What is the MangoSkin skin?</a></html>" }, { "MistSilverSkin", "13", "<html><a href=''>What is the MistSilverSkin skin?</a></html>" },
			{ "ModerateSkin", "14", "<html><a href=''>What is the ModerateSkin skin?</a></html>" }, { "NebulaBrickWallSkin", "15", "<html><a href=''>What is the NebulaBrickWallSkin skin?</a></html>" }, { "NebulaSkin", "16", "<html><a href=''>What is the NebulaSkin skin?</a></html>" }, { "OfficeBlue2007Skin", "17", "<html><a href=''>What is the OfficeBlue2007Skin skin?</a></html>" }, { "RavenGraphiteGlassSkin", "18", "<html><a href=''>What is the RavenGraphiteGlassSkin skin?</a></html>" }, { "RavenGraphiteSkin", "19", "<html><a href=''>What is the RavenGraphiteSkin skin?</a></html>" }, { "RavenSkin", "20", "<html><a href=''>What is the RavenSkin skin?</a></html>" }, { "SaharaSkin", "21", "<html><a href=''>What is the SaharaSkin skin?</a></html>" } };

}
