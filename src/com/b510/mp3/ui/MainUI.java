package com.b510.mp3.ui;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;

import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import com.b510.mp3.common.Common;
import com.b510.mp3.core.PlayerThread;
import com.b510.mp3.core.TimeBarThread;
import com.b510.mp3.util.CommonUtil;
import com.b510.mp3.util.MusicPlayListForXMLUtil;
import com.b510.mp3.util.TrayUtil;
import com.b510.mp3.util.SkinMangerUtil;
import com.b510.mp3.vo.PlayListVO;

/**
 * Main user interface.
 * 
 * @author hongten
 * @created 22-Dec-2015
 */
public class MainUI extends Mp3UI {

	private static final long serialVersionUID = -2299267016129366047L;
	
	static Logger logger = Logger.getLogger(MainUI.class);

	// the four buttons for previous, play/pause, next, play mode
	private JButton jButtonNext, jButtonPlayMode, jButtonPlayOrPause, jButtonPrevious;
	private JMenu jMenuFile;
	private JSeparator line;
	private JMenuItem addItem, miniItem, playModeItem, exitItem, aboutItem, changeSkinItem;
	private JMenuBar jMenuBar;
	private JPanel jPanelBottom, jPanelTop;
	private JProgressBar jProgressTimeBar;
	private JLabel jLabelTimer, jLabelTotleTimer;
	// for play list
	private JPanel jPanelPlayList;
	private JScrollPane tableScrollPane;
	private JTable playListTable;
	private JScrollBar jscrollBar;
	// the selected item is 0 in play list.
	public static int selectId = Common.ZERO;
	public static List<PlayListVO> playList = new ArrayList<PlayListVO>();

	private static String CURRENT_DIR = Common.DEFAULT_DIRECTORY_PATH;
	// mini window status. Ctrl + M can set the mini/normal window.
	public static boolean MINI_STATUS = true;

	MusicPlayListForXMLUtil xmlUtil;
	PlayerThread playerThread;
	private static int PAUSE_ACCOUNT = 0;
	public static boolean PAUSE_STATUS;

	// Default play mode is Shuffle and the value is 0
	public static int play_mode_value = 0;
	TimeBarThread timeBarThread;
	
	// Tray tool
	private TrayUtil tray;
	
	public static int pointX = 0;
	public static int pointY = 0;

	/**
	 * Creates new form MainUI
	 */
	public MainUI(String title) {
		super(title);
		this.setTitle(title);
	}

	public void init() {
		jPanelTop = new JPanel();
		jButtonPrevious = new JButton();
		jButtonPlayOrPause = new JButton();
		jButtonNext = new JButton();
		jButtonPlayMode = new JButton();
		jPanelBottom = new JPanel();
		jProgressTimeBar = new JProgressBar();
		jMenuBar = new JMenuBar();
		jMenuFile = new JMenu();
		jPanelPlayList = new JPanel();
		tableScrollPane = new JScrollPane();
		playListTable = new JTable();
		jLabelTimer = new JLabel();
		jLabelTotleTimer = new JLabel();

		// time bar displays the percentage
		jProgressTimeBar.setStringPainted(true);

		// time bar
		jLabelTimer.setText(Common.TIME_DEFAULT);
		jLabelTotleTimer.setText(Common.TIME_DEFAULT);

		// ======================================
		// == start button setting
		// ======================================
		jButtonPrevious.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(Common.IMAGE_PREVIOUS)));
		jButtonPrevious.addActionListener(this);
		jButtonPrevious.setToolTipText(Common.PREVIOUS);

		jButtonPlayOrPause.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(Common.IMAGE_PLAY)));
		jButtonPlayOrPause.addActionListener(this);
		jButtonPlayOrPause.setToolTipText(Common.PLAY);

		jButtonNext.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(Common.IMAGE_NEXT)));
		jButtonNext.addActionListener(this);
		jButtonNext.setToolTipText(Common.NEXT);

		jButtonPlayMode.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(Common.IMAGE_SHUFFLE)));
		jButtonPlayMode.addActionListener(this);
		jButtonPlayMode.setToolTipText(Common.SHUFFLE_MODE);
		// ======================================
		// == end button setting
		// ======================================

		// ======================================
		// == start File menu setting
		// ======================================
		// for menu
		jMenuFile.setText(Common.FILE);
		jMenuBar.add(jMenuFile);

		addItem = new JMenuItem(Common.ADD);
		addItem.setAccelerator(KeyStroke.getKeyStroke(Common.N, InputEvent.CTRL_MASK));
		addItem.addActionListener(this);
		jMenuFile.add(addItem);

		miniItem = new JMenuItem(Common.MINI);
		miniItem.setAccelerator(KeyStroke.getKeyStroke(Common.M, InputEvent.CTRL_MASK));
		miniItem.addActionListener(this);
		jMenuFile.add(miniItem);

		line = new JSeparator();
		jMenuFile.add(line);
		
		changeSkinItem = new JMenuItem(Common.SKIN);
		changeSkinItem.addActionListener(this);
		jMenuFile.add(changeSkinItem);
		
		playModeItem = new JMenuItem(Common.SHUFFLE_MODE);
		playModeItem.setAccelerator(KeyStroke.getKeyStroke(Common.M, InputEvent.CTRL_MASK + InputEvent.SHIFT_MASK));
		playModeItem.addActionListener(this);
		jMenuFile.add(playModeItem);
		
		line = new JSeparator();
		jMenuFile.add(line);
		
		aboutItem = new JMenuItem(Common.ABOUT);
		aboutItem.setAccelerator(KeyStroke.getKeyStroke(Common.A, InputEvent.CTRL_MASK));
		aboutItem.addActionListener(this);
		jMenuFile.add(aboutItem);

		line = new JSeparator();
		jMenuFile.add(line);

		exitItem = new JMenuItem(Common.EXIT);
		exitItem.setAccelerator(KeyStroke.getKeyStroke(Common.W, InputEvent.CTRL_MASK));
		exitItem.addActionListener(this);
		jMenuFile.add(exitItem);

		setJMenuBar(jMenuBar);
		// ======================================
		// == end File menu setting
		// ======================================

		// load play list
		loadPlayList();

		initTablePanel();
		mainUILayout();

		jscrollBarOperation();
		setSize(Common.WINDOW_WIDTH, Common.WINDOW_HEIGHT);
		this.setResizable(false);
		//setting window location
		this.setLocationRelativeTo(null);
		setMainUIXY();
		// select one song and double click left button to play.
		actionForPlayListItem();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowIconified(WindowEvent e) {
				setVisible(false);
			}

			@Override
			public void windowClosing(WindowEvent e) {
				int option = JOptionPane.showConfirmDialog(MainUI.this, Common.CONFIRM_MINIMIZE, Common.NOTICE, JOptionPane.YES_NO_OPTION);
				if (option == JOptionPane.YES_OPTION) {
					//hide the window
					setVisible(false);
				} else {
					// exit system
					existSystem();
				}
			}
		});
		
		// Loading the tray when the application start.
		if (null == tray) {
			tray = new TrayUtil(MainUI.this);
		} else {
			tray.initTray();
		}
		//main window icon setting
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(Common.IMAGE_TRAY)));
		this.setVisible(true);
	}
	
	public void setMainUIXY() {
		pointX = getMainUIX();
		pointY = getMainUIY();
	}
	
	private int getMainUIY() {
		return (int) getLocation().getY();
	}

	private int getMainUIX() {
		return (int) getLocation().getX();
	}

	/**
	 * If you select one row in the play list and double click left button, then
	 * the selected song will be play.
	 */
	public void actionForPlayListItem() {
		playListTable.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// to play
				if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
					selectId = playListTable.getSelectedRow();
					logger.debug("selectId = " + selectId + ", and play from double click.");
					play();
				}
				// to remove
				if (e.getButton() == MouseEvent.BUTTON3 && e.getClickCount() == 2) {
					int removeId = playListTable.getSelectedRow();
					String songName = "";
					if (playList != null && playList.size() > 0) {
						for (PlayListVO song : playList) {
							if (song.getId() == removeId) {
								songName = song.getName();
								logger.debug("remove " + songName + " from play list.");
							}
						}
					}
					int option = JOptionPane.showConfirmDialog(MainUI.this, Common.REMOVE_SONG + songName + Common.BLANK + Common.QUESTION_MARK, Common.CONFIRM_REMOVE, JOptionPane.YES_NO_OPTION);
					if (option == JOptionPane.YES_OPTION) {
						if (removeId == selectId) {
							logger.debug("remove current song.");
							if (playerThread != null) {
								playerThread.stopPlaying();
								playerThread = null;
							}
							stopTimeBar();
							removeSoneFromPlayList(removeId);
						} else if (removeId > selectId) {
							logger.debug("remove song after current song.");
							removeSoneFromPlayList(removeId);
						} else if (removeId < selectId) {
							logger.debug("remove song before current song.");
							selectId--;
							removeSoneFromPlayList(removeId);
						}
					}
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
	}

	/**
	 * remove one song from play list.
	 */
	private void removeSoneFromPlayList(int removeId) {
		logger.debug("remove one song from play list.");
		if (playList != null && playList.size() > 0) {
			for (PlayListVO song : playList) {
				if (song.getId() == removeId) {
					playList.remove(removeId);
					playList = convertPlayList(playList);
					refreshPlayList();
				}
			}
		}
	}

	/**
	 * refresh play list
	 */
	private List<PlayListVO> convertPlayList(List<PlayListVO> oldPlayList) {
		logger.debug("refresh play list");
		List<PlayListVO> newPlayList = new ArrayList<PlayListVO>();
		if (null != oldPlayList && oldPlayList.size() > 0) {
			for (int i = 0; i < oldPlayList.size(); i++) {
				PlayListVO vo = oldPlayList.get(i);
				vo.setId(i);
				newPlayList.add(vo);
			}
		}
		return newPlayList;
	}

	// the action listener
	@Override
	public void actionPerformed(ActionEvent e) {
		actionForFileMenuItem(e);
		actionForPanelTop(e);
	}

	/**
	 * add action listener for all menus. e.g. when you click the 'Add' menu,
	 * then execute the add operation.
	 */
	private void actionForFileMenuItem(ActionEvent e) {
		if (e.getSource() == addItem) {
			// add operation
			addNewSongs();
		} else if (e.getSource() == miniItem) {
			// Ctrl + M can change the mini/normal window.
			if (MINI_STATUS) {
				setSize(Common.WINDOW_WIDTH, Common.WINDOW_MINI_HEIGHT);
			} else {
				setSize(Common.WINDOW_WIDTH, Common.WINDOW_HEIGHT);
			}
			MINI_STATUS = !MINI_STATUS;
		} else if (e.getSource() == exitItem) {
			// exit system
			int option = JOptionPane.showConfirmDialog(MainUI.this, Common.EXIT_SYSTEM, Common.CONFIRM_EXIT, JOptionPane.YES_NO_OPTION);
			if (option == JOptionPane.YES_OPTION) {
				existSystem();
			}
		} else if (e.getSource() == aboutItem) {
			// show about information
			JOptionPane.showMessageDialog(this, Common.ABOUT_INFO, Common.ABOUT, JOptionPane.INFORMATION_MESSAGE);
		} else if(e.getSource() == playModeItem){
			logger.debug("play mode ....from menu...");
			playModeOperation();
		} else if(e.getSource() == changeSkinItem){
			logger.debug("play mode ....from menu...");
			changeSkin();
		}
	}

	/**
	 * add action listener for all buttons. e.g. when you click the 'Next'
	 * button, then execute play next song operation.
	 */
	private void actionForPanelTop(ActionEvent e) {
		if (e.getSource() == jButtonPrevious) {
			logger.debug("play previous ....");
			playWithPalyMode(true, false);
		} else if (e.getSource() == jButtonPlayOrPause) {
			if (PAUSE_ACCOUNT == 0 && PAUSE_STATUS) {
				logger.debug("playing ....");
				play();
			} else {
				pause();
			}
			PAUSE_ACCOUNT++;
		} else if (e.getSource() == jButtonNext) {
			logger.debug("play next ....");
			playWithPalyMode(false, true);
		} else if (e.getSource() == jButtonPlayMode) {
			logger.debug("play mode ....");
			playModeOperation();
		}
	}

	/**
	 * play mode exchange
	 */
	public void playModeOperation() {
		if (play_mode_value == Common.SHUFFLE_MODE_VALUE) {
			setPlayMode(Common.IMAGE_REPEAT, Common.REPEAT_ONCE_MODE, Common.REPEAT_ONCE_MODE_VALUE);
		} else if (play_mode_value == Common.REPEAT_ONCE_MODE_VALUE) {
			setPlayMode(Common.IMAGE_ORDER, Common.ORDER_MODE, Common.ORDER_MODE_VALUE);
		} else if (play_mode_value == Common.ORDER_MODE_VALUE) {
			setPlayMode(Common.IMAGE_SHUFFLE, Common.SHUFFLE_MODE, Common.SHUFFLE_MODE_VALUE);
		}
	}

	/**
	 * play mode operation
	 */
	private void setPlayMode(String icon, String desc, int value) {
		jButtonPlayMode.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(icon)));
		jButtonPlayMode.setToolTipText(desc);
		play_mode_value = value;
		switch (play_mode_value) {
		case 0:
			logger.debug("mode : " + Common.SHUFFLE_MODE);
			playModeItem.setText(Common.SHUFFLE_MODE);
			tray.setPlayModeItemText(Common.SHUFFLE_MODE);
			break;
		case 1:
			logger.debug("mode : " + Common.REPEAT_ONCE_MODE);
			playModeItem.setText(Common.REPEAT_ONCE_MODE);
			tray.setPlayModeItemText(Common.REPEAT_ONCE_MODE);
			break;
		case 2:
			logger.debug("mode : " + Common.ORDER_MODE);
			playModeItem.setText(Common.ORDER_MODE);
			tray.setPlayModeItemText(Common.ORDER_MODE);
			break;

		}
	}

	/**
	 * play song
	 */
	public void play() {
		jButtonPlayOrPause.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(Common.IMAGE_PAUSE)));
		jButtonPlayOrPause.setToolTipText(Common.PAUSE);

		// refresh the play list selected item.
		jscrollBarOperation();
		PlayListVO song = playList.get(selectId);
		String path = song.getPath();
		File f = new File(path);
		// init the time bar
		initTimeBar(f);

		if (playerThread != null) {
			playerThread.stopPlaying();
			playerThread = null;
		}
		playerThread = PlayerThread.startPlaying(f);
		PAUSE_STATUS = false;
	}

	private void initTimeBar(File f) {
		logger.debug("init time bar begin...");
		int amount = 0;
		try {
			// get one song time
			Encoder encoder = new Encoder();
			MultimediaInfo m = encoder.getInfo(f);
			long ls = m.getDuration();
			long min = ((ls % (60 * 60 * 1000)) / 60000 * 60);
			long s = (((ls % (60 * 60 * 1000)) % 60000) / 1000);
			long ms = (((ls % (60 * 60 * 1000)) % 60000) % 1000);
			if (ms > 0) {
				amount = (int) (min + s + 1);
			} else {
				amount = (int) (min + s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		timeBarThread = new TimeBarThread(amount);
		timeBarThread.setCurrent(0);
		new Thread(timeBarThread).start();
		jProgressTimeBar.setMinimum(0);
		jProgressTimeBar.setMaximum(amount);
		jLabelTotleTimer.setText(refreshTimer(amount));
		logger.debug("Total time : " + refreshTimer(amount));

		Timer timer = new Timer(50, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// update time bar status/percentage.
				if (timeBarThread != null) {
					jProgressTimeBar.setValue(timeBarThread.getCurrent());
					jLabelTimer.setText(refreshTimer(timeBarThread.getCurrent()));
					// when one song finished. then the application will check
					// play mode and play next song.
					if (timeBarThread.getCurrent() >= timeBarThread.getAmount()) {
						if (playerThread != null) {
							playerThread.stopPlaying();
							stopTimeBar();
							playWithPalyMode(false, false);
						}
					}
				}
			}
		});
		timer.start();
		logger.debug("init time bar end...");
	}

	/**
	 * when finish the music, then the application will check the PLAY_MODE to
	 * start play. <li>if the play_mode_value = 0(Shuffle_mode), then then
	 * application will generate a random number for selected row.</li> <li>if
	 * the play_mode_value = 1(Repeat_one_mode), then it will play again as last
	 * time.</li> <li>if the play_mdoe_value = 2(Order_mode), then it will play
	 * next song.</li>
	 */
	public void playWithPalyMode(boolean isPriousBtn, boolean isNextBtn) {
		if (play_mode_value == Common.SHUFFLE_MODE_VALUE) {
			randomId();
			play();
		} else if (isPriousBtn) {
			if (play_mode_value == Common.ORDER_MODE_VALUE || play_mode_value == Common.REPEAT_ONCE_MODE_VALUE) {
				if (selectId >= 0) {
					selectId--;
					if (selectId <= 0) {
						selectId = playList.size() - 1;
					}
					stopTimeBar();
					play();
				}
			}
		} else if (isNextBtn) {
			if (play_mode_value == Common.ORDER_MODE_VALUE || play_mode_value == Common.REPEAT_ONCE_MODE_VALUE) {
				if (selectId <= playList.size() - 1) {
					selectId++;
					stopTimeBar();
					// if the last finished, then will play the first one.
					if (selectId >= playList.size()) {
						selectId = 0;
					}
					play();
				}
			}
		} else if (!isPriousBtn && !isNextBtn) {
			if (play_mode_value == Common.ORDER_MODE_VALUE) {
				if (selectId <= playList.size() - 1) {
					selectId++;
					stopTimeBar();
					// if the last finished, then will play the first one.
					if (selectId >= playList.size()) {
						selectId = 0;
					}
					play();
				}
			} else if (play_mode_value == Common.REPEAT_ONCE_MODE_VALUE) {
				stopTimeBar();
				play();
			}
		}
	}

	/**
	 * refresh timer
	 */
	private String refreshTimer(int current) {
		int min = current / 60;
		int s = current % 60;
		String sec = "";
		if (s <= 9) {
			sec = Common.CHAR_ZERO + s;
		} else {
			sec = Common.EMPTY + s;
		}
		return Common.CHAR_ZERO + min + Common.COLOR + sec;
	}

	/**
	 * stop time bar thread
	 */
	private void stopTimeBar() {
		if (timeBarThread != null) {
			timeBarThread.setStop(true);
			timeBarThread = null;
		}
	}

	private void randomId() {
		Random random = new Random();
		selectId = random.nextInt(playList.size());
	}

	/**
	 * pause
	 */
	private void pause() {
		if (playerThread != null) {
			PAUSE_STATUS = playerThread.pause();
			logger.debug("PAUSE_STATUS : " + PAUSE_STATUS);
			if (PAUSE_STATUS) {
				logger.debug("pause ....");
				jButtonPlayOrPause.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(Common.IMAGE_PLAY)));
				jButtonPlayOrPause.setToolTipText(Common.PLAY);
			} else {
				logger.debug("resume ....");
				jButtonPlayOrPause.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(Common.IMAGE_PAUSE)));
				jButtonPlayOrPause.setToolTipText(Common.PAUSE);
			}
		}
	}

	// load play list
	private void loadPlayList() {
		xmlUtil = new MusicPlayListForXMLUtil();
		playList = xmlUtil.loadPlayListFromXML();
	}

	// select playing song, update the play list status.
	private void jscrollBarOperation() {
		logger.debug("select playing song, update the play list status.");
		int rowCount = playListTable.getRowCount();
		if (rowCount > 0) {
			playListTable.setRowSelectionInterval(selectId, selectId);
			jscrollBar = tableScrollPane.getVerticalScrollBar();
			if (null != jscrollBar) {
				if (selectId > 17) {
					jscrollBar.setValue((selectId - 7) * Common.TABLE_ROW_HEIGHT);
				} else {
					jscrollBar.setValue((selectId) * Common.TABLE_ROW_HEIGHT);
				}
			}
		}
	}

	/**
	 * load play list to main user interface.
	 */
	@SuppressWarnings("serial")
	private void initTablePanel() {
		logger.debug("START ------ load play list to main user interface.");
		tableScrollPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		tableScrollPane.setVerifyInputWhenFocusTarget(false);

		Object[][] objects = new Object[][] { { null, null, null, null, null } };

		if (null != playList && playList.size() > 0) {
			objects = new Object[playList.size()][5];
			for (int i = 0; i < playList.size(); i++) {
				PlayListVO item = playList.get(i);
				objects[i][0] = String.valueOf(i + 1);
				objects[i][1] = item.getName();
				objects[i][2] = item.getTime();
				objects[i][3] = item.getPath();
				objects[i][4] = String.valueOf(item.getSize());
				logger.debug("load file : " + item.getName());
			}
		}

		playListTable.setModel(new DefaultTableModel(objects, new String[] { "No", "Name", "Time", "Path", "Size" }) {
			boolean[] canEdit = new boolean[] { false, false, false, false, false };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		playListTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		playListTable.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		playListTable.setOpaque(false);
		playListTable.setRowHeight(Common.TABLE_ROW_HEIGHT);
		playListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		playListTable.setSurrendersFocusOnKeystroke(true);
		playListTable.getTableHeader().setReorderingAllowed(false);
		tableScrollPane.setViewportView(playListTable);
		logger.debug("END ------ load play list to main user interface.");
		logger.debug("Total : " + (playList == null ? 0 : playList.size()));
	}

	/**
	 * add new songs operation when you click the Add menu.
	 */
	public void addNewSongs() {
		String dir = getPathOfDirectory();
		if (null != dir) {
			File file = new File(dir);
			File[] files = file.listFiles();
			if (null != files && files.length > 0) {
				for (File f : files) {
					if (f.isFile()) {
						String name = f.getName();
						String postfix = CommonUtil.getPostfix(f.getAbsolutePath());
						if (null != postfix && Common.MP3.equalsIgnoreCase(postfix)) {
							add2PlayList(name, f.getAbsolutePath());
						}
					}
				}
				refreshPlayList();
			}
		}
	}

	/**
	 * refresh play list
	 */
	private void refreshPlayList() {
		generatePlayListXML();
		initTablePanel();
		jscrollBarOperation();
	}

	/**
	 * skip the duplicate song, then add new song to play list
	 */
	public int add2PlayList(String name, String filePath) {
		if (null != playList && playList.size() > 0) {
			int size = playList.size();
			int id = -1;
			for (PlayListVO item : playList) {
				if (filePath.replace(Common.BLACKSLASH, Common.ELLIPSIS_SIGN).equals(item.getPath())) {
					id = item.getId();
					break;
				} else {
					size--;
				}
			}
			if (size == 0 && id == -1) {
				PlayListVO song = addNewSong(name, filePath, false);
				return song.getId();
			} else {
				return id;
			}
		} else {
			PlayListVO song = addNewSong(name, filePath, true);
			return song.getId();
		}
	}

	/**
	 * add new song
	 */
	private PlayListVO addNewSong(String name, String filePath, boolean listIsEmpty) {
		PlayListVO vo = new PlayListVO();
		vo.setName(name.substring(0, name.lastIndexOf(Common.FULL_SPOT)));
		vo.setPath(filePath.replace(Common.BLACKSLASH, Common.ELLIPSIS_SIGN));
		vo.setSize(CommonUtil.getFileSize(filePath));
		vo.setTime(CommonUtil.getSongTime(filePath));
		playList.add(vo);
		playList = convertPlayList(playList);
		return vo;
	}

	/**
	 * show select directory dialog to select directory
	 * 
	 * @return the selected path
	 */
	public String getPathOfDirectory() {
		String dir = Common.DEFAULT_DIRECTORY_PATH;
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setDialogTitle(Common.SELECT_DIR);
		chooser.setCurrentDirectory(new File(CURRENT_DIR));
		int ret = chooser.showOpenDialog(null);
		if (ret == JFileChooser.APPROVE_OPTION) {
			dir = chooser.getSelectedFile().getAbsolutePath();
			CURRENT_DIR = dir;
		} else if (ret == JFileChooser.CANCEL_OPTION) {
			return null;
		}
		return dir;
	}

	/**
	 * generate play list XML file
	 */
	public void generatePlayListXML() {
		logger.debug(" generate play list XML file");
		if (null == xmlUtil) {
			xmlUtil = new MusicPlayListForXMLUtil();
			xmlUtil.save(playList);
		} else {
			xmlUtil.save(playList);
		}
	}
	
	public void changeSkin(){
		setMainUIXY();
		SkinMangerUtil util = new SkinMangerUtil(Common.EMPTY);
		util.skin(MainUI.this);
	}

	// exit system
	public void existSystem() {
		logger.debug("Exit System.....");
		System.exit(0);
	}

	// ===================================================
	// == the user interface of the application as below layout.
	// == WARNING: Do NOT modify this code.
	// ===================================================
	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void mainUILayout() {
		topLayout();
		middleLayout();
		bottomPlaylistLayout();

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false).addComponent(jPanelTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jPanelBottom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jPanelPlayList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jPanelTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE).addComponent(jPanelBottom, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jPanelPlayList, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap()));
		pack();
	}

	/**
	 * WARNING: Do NOT modify this code.
	 */
	private void topLayout() {
		javax.swing.GroupLayout jPanelTopLayout = new javax.swing.GroupLayout(jPanelTop);
		jPanelTop.setLayout(jPanelTopLayout);
		jPanelTopLayout.setHorizontalGroup(jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanelTopLayout.createSequentialGroup().addContainerGap().addComponent(jButtonPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jButtonPlayOrPause, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jButtonNext, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jButtonPlayMode, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jPanelTopLayout.setVerticalGroup(jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanelTopLayout.createSequentialGroup().addContainerGap().addGroup(jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jButtonPlayMode, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jButtonNext, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jButtonPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jButtonPlayOrPause, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
	}

	/**
	 * WARNING: Do NOT modify this code.
	 */
	private void middleLayout() {
		jPanelBottom.setPreferredSize(new java.awt.Dimension(330, 36));
		javax.swing.GroupLayout jPanelBottomLayout = new javax.swing.GroupLayout(jPanelBottom);
		jPanelBottom.setLayout(jPanelBottomLayout);
		jPanelBottomLayout.setHorizontalGroup(jPanelBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanelBottomLayout.createSequentialGroup().addContainerGap().addGroup(jPanelBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jProgressTimeBar, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE).addGroup(jPanelBottomLayout.createSequentialGroup().addComponent(jLabelTimer).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabelTotleTimer))).addContainerGap()));
		jPanelBottomLayout.setVerticalGroup(jPanelBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanelBottomLayout.createSequentialGroup().addContainerGap().addComponent(jProgressTimeBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanelBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabelTimer).addComponent(jLabelTotleTimer)).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
	}

	/**
	 * WARNING: Do NOT modify this code.
	 */
	private void bottomPlaylistLayout() {
		jPanelPlayList.setPreferredSize(new java.awt.Dimension(330, 403));
		javax.swing.GroupLayout jPanelPlayListLayout = new javax.swing.GroupLayout(jPanelPlayList);
		jPanelPlayList.setLayout(jPanelPlayListLayout);
		jPanelPlayListLayout.setHorizontalGroup(jPanelPlayListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanelPlayListLayout.createSequentialGroup().addContainerGap().addComponent(tableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE).addContainerGap()));
		jPanelPlayListLayout.setVerticalGroup(jPanelPlayListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanelPlayListLayout.createSequentialGroup().addGap(11, 11, 11).addComponent(tableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE).addGap(11, 11, 11)));
	}

}
