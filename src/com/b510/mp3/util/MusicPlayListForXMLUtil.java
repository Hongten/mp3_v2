/**
 * 
 */
package com.b510.mp3.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.b510.mp3.common.Common;
import com.b510.mp3.vo.PlayListVO;

/**
 * @author Hongten
 * @created 2014-7-30
 */
public class MusicPlayListForXMLUtil {

	static Logger logger = Logger.getLogger(MusicPlayListForXMLUtil.class);

	private Document generateDocument(List<PlayListVO> list) {
		Document document = null;
		document = DocumentHelper.createDocument();
		// root element
		Element playlist = document.addElement(Common.ROOT_ELEMENT);
		playlist.addComment(Common.COMMENT);
		if (null != list && list.size() > 0) {
			playlist.addComment(Common.COMMENT_TOTAL + list.size());
			logger.debug(Common.COMMENT_TOTAL + list.size());
			// son element
			for (PlayListVO item : list) {
				Element song = playlist.addElement(Common.SUB_ELEMENT_SONG);
				song.addAttribute(Common.SUB_ELEMENT_ATTR_ID, String.valueOf(item.getId()));
				Element name = song.addElement(Common.SUB_ELEMENT_NAME);
				name.setText(item.getName());
				Element time = song.addElement(Common.SUB_ELEMENT_TIME);
				time.setText(item.getTime() == null ? Common.EMPTY : item.getTime());
				Element path = song.addElement(Common.SUB_ELEMENT_PATH);
				path.setText(item.getPath());
				Element size = song.addElement(Common.SUB_ELEMENT_SIZE);
				size.setText(String.valueOf(item.getSize()));
			}
		}
		return document;
	}

	private int writeDocumentToFile(Document document, File outputXml) {
		int result = -1;
		try {
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding(Common.ENCODING_UTF_8);
			XMLWriter output = new XMLWriter(new FileWriter(outputXml), format);
			output.write(document);
			output.close();
			result = 1;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean save(List<PlayListVO> list) {
		Document document = generateDocument(list);
		File file = new File(Common.PLAY_LIST_SAVE_PATH);
		int result = writeDocumentToFile(document, file);
		return (result != -1);
	}

	@SuppressWarnings({ "rawtypes" })
	public List<PlayListVO> loadPlayListFromXML() {
		logger.debug("loading play list from [" + Common.PLAY_LIST_SAVE_PATH + "] file.");
		List<PlayListVO> playLists = new ArrayList<PlayListVO>();
		File file = new File(Common.PLAY_LIST_SAVE_PATH);
		if (file.exists()) {
			logger.debug("..... begin to loading .....");
			try {
				SAXReader saxReader = new SAXReader();
				Document document = saxReader.read(Common.PLAY_LIST_SAVE_PATH);
				Element root = document.getRootElement();
				PlayListVO playList = null;
				for (Iterator song = root.elementIterator(Common.SUB_ELEMENT_SONG); song.hasNext();) {
					Element songElement = (Element) song.next();
					Attribute idAttribute = songElement.attribute(Common.SUB_ELEMENT_ATTR_ID);
					int id = Integer.valueOf(idAttribute.getValue());
					String name = songElement.elementText(Common.SUB_ELEMENT_NAME);
					String time = songElement.elementText(Common.SUB_ELEMENT_TIME);
					String path = songElement.elementText(Common.SUB_ELEMENT_PATH);
					String size = songElement.elementText(Common.SUB_ELEMENT_SIZE);

					playList = new PlayListVO();
					playList.setId(id);
					playList.setName(name);
					playList.setTime(time);
					playList.setPath(path);
					playList.setSize(Long.valueOf(size));

					playLists.add(playList);
				}
				logger.debug("..... end to loading .....");
				logger.debug("Total : " + (playLists == null ? 0 : playLists.size()));
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
		return playLists;
	}
}
