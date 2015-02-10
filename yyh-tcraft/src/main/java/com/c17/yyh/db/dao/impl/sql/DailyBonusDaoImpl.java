package com.c17.yyh.db.dao.impl.sql;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import javax.sql.DataSource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.apache.commons.io.FilenameUtils;

import com.c17.yyh.config.ServerConfig;
import com.c17.yyh.constants.Constants;
import com.c17.yyh.db.dao.IDailyBonusDao;
import com.c17.yyh.db.entities.XmlDailyBonus;
import com.c17.yyh.models.User;
import com.c17.yyh.server.ErrorCodes;
import com.c17.yyh.server.SQLReq;
import com.c17.yyh.exceptions.ServerException;
import com.c17.yyh.util.FileHelper;

@Repository("dailyBonusService")
public class DailyBonusDaoImpl implements IDailyBonusDao{

    @Autowired
    private ServerConfig serverConfig;
    
    private String dailyBonusConfigPath = null;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private DataSource dataSource;
    
	@Autowired
    public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
    }	
	
	@Override
	public HashMap<Integer, XmlDailyBonus> getDailyBonuses(int limit) {

		HashMap<Integer, XmlDailyBonus> dailyBonuses;
		
		String dirPath = getDailyBonusConfigPath();
		File[] allFiles = null;
		
		JAXBContext jc;
		Unmarshaller u;
		
		try {
			allFiles = FileHelper.fileFinder(dirPath, "xml");
		} catch (FileNotFoundException e1) {
			logger.error("Error loading daily bonuses: directory does not exists (\""+ dirPath + "\")");
			throw new ServerException(0, "Error loading daily bonuses: directory does not exists (\""+ dirPath + "\")", 
					true, e1);
		}

		if (allFiles.length == 0){
			logger.error("Error loading daily bonuses: there is no daily bonuses files in directory");
			throw new ServerException(0, "Error loading daily bonuses: there is no daily bonuses files in directory", 
					true);
		}

		LinkedList<File> files = new LinkedList<File>();
		for (int i = 0; i < limit && i < allFiles.length; i++) {
			files.add(allFiles[i]);
		}
		dailyBonuses = new LinkedHashMap<>();
		try {
			jc = JAXBContext.newInstance(XmlDailyBonus.class);
			u = jc.createUnmarshaller();
			for (File file : files) {
				XmlDailyBonus db = (XmlDailyBonus) u.unmarshal(file);
				int day = Integer.parseInt(FilenameUtils.removeExtension(file.getName()));
				db.setDay(day);
				dailyBonuses.put(day, db);
			}

		} catch (JAXBException e) {
			logger.error("Error loading daily bonuses: exception on unmarshalling daily bonuses from XML");
			e.printStackTrace();
			throw new ServerException(0, "Error loading daily bonuses: exception on unmarshalling daily bonuses from XML", 
				true);
		}

		logger.info("Loaded {} daily bonuses", dailyBonuses.size());

		return dailyBonuses;
	}
	
	public String getDailyBonusConfigPath(){
		if(dailyBonusConfigPath != null) return dailyBonusConfigPath;
		StringBuilder sb = new StringBuilder();
		String dirPath = serverConfig.getConfigPath();
		sb.append(dirPath);
		
		if(!dirPath.endsWith(Constants.separator))	
			sb.append(Constants.separator);
		
		sb.append(serverConfig.dailyBonus.dailyBonusConfigDirName);
		
		return dailyBonusConfigPath = sb.toString();
	}

	@Override
	public int updateDailyBonusState(User user) {
		
		PreparedStatement updateDailyBonusState = null;
		Timestamp lastDb = new Timestamp(user.getLastTimeDailyBonusLogin());
		Connection con = null;
		int state = -1;
		try {
			con = dataSource.getConnection();
			try {
				try {
					updateDailyBonusState = con.prepareStatement(SQLReq.UPDATE_DAILY_BONUS_STATE);
					updateDailyBonusState.setTimestamp(1, lastDb);
					updateDailyBonusState.setInt(2, user.getDailyBonusMarker());
					updateDailyBonusState.setInt(3, user.getUserId());
					state = updateDailyBonusState.executeUpdate();
				} finally {
					if (updateDailyBonusState != null)
						updateDailyBonusState.close();
				}
			} finally {
				con.close();
			}
		} catch (SQLException e) {
			LoggerFactory.getLogger(Thread.currentThread().getName()).error(e.toString());
			throw new ServerException(ErrorCodes.TEMP_DB_ERROR, e.toString(), true);
		}
		return state;		
	}

}
