package com.c17.yyh.db.entities.adventure;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.LoggerFactory;

import com.c17.yyh.constants.Constants;
import com.c17.yyh.server.ErrorCodes;
import com.c17.yyh.exceptions.ServerException;

public class Level implements Comparable<Level> {

	@JsonIgnore
	private String adventureConfigPath;

	@JsonIgnore
	private String levelSetDirPath;

	@JsonIgnore
	private String file_name;

	@JsonIgnore
	private String data = null;

	private String task = "", description = "", thresholds = "", name = "";
	private String recomend_boosts, recomend_tools, boost_set, tool_set,
			additional;
	private int number = 0, levelset_number = 0, parent = 0, moves_count = 0;

	private XmlPet pet;
	private XmlTreasure treasure;
	private XmlBoss boss;
	private XmlSecret secret;
	private XmlStartBonus start_bonus;
	private Award awards;

	@XmlTransient
	private int[] pets_list;

	@JsonIgnore
	private String petsListString;

	public int[] getPets_list() {
		return pets_list;
	}

	public void setPets_list(int[] pets_list) {
		this.pets_list = pets_list;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getThresholds() {
		return thresholds;
	}

	public void setThresholds(String thresholds) {
		this.thresholds = thresholds;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public int getMoves_count() {
		return moves_count;
	}

	public void setMoves_count(int moves_count) {
		this.moves_count = moves_count;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getAdventureConfigPath() {
		return adventureConfigPath;
	}

	public void setAdventureConfigPath(String adventureConfigPath) {
		this.adventureConfigPath = adventureConfigPath;
	}

	@JsonIgnore
	public String getFile() {
		if (data != null) {
			return data;
		}

		try {
			String fileName = adventureConfigPath + Constants.separator
					+ levelSetDirPath + Constants.separator + file_name;

			FileInputStream fis;

			fis = new FileInputStream(fileName);

			InputStreamReader is = new InputStreamReader(fis);

			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(is);
			StringWriter sw = new StringWriter();
			String line;
			while ((line = br.readLine()) != null) {
				sw.write(line);
			}

			data = sw.toString();
			return data;
		} catch (IOException e) {
			LoggerFactory
					.getLogger(Thread.currentThread().getName())
					.error("Error when try to get level data for levelSet {} and level {}",
							new Object[] { levelset_number, number });
			throw new ServerException(ErrorCodes.PARSE_ERROR,
					"Error when try to get level data", true);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevelset_number() {
		return levelset_number;
	}

	public void setLevelset_number(int levelset_number) {
		this.levelset_number = levelset_number;
	}

	public XmlTreasure getTreasure() {
		return treasure;
	}

	public void setTreasure(XmlTreasure treasure) {
		this.treasure = treasure;
	}

	public XmlPet getPet() {
		return pet;
	}

	public void setPet(XmlPet pet) {
		this.pet = pet;
	}

	public XmlBoss getBoss() {
		return boss;
	}

	public void setBoss(XmlBoss boss) {
		this.boss = boss;
	}

	public Award getAwards() {
		return awards;
	}

	public void setAwards(Award awards) {
		this.awards = awards;
	}

	public XmlSecret getSecret() {
		return secret;
	}

	public void setSecret(XmlSecret secret) {
		this.secret = secret;
	}

	public XmlStartBonus getStart_bonus() {
		return start_bonus;
	}

	public void setStart_bonus(XmlStartBonus start_bonus) {
		this.start_bonus = start_bonus;
	}

	public String getPetsListString() {
		return petsListString;
	}

	public String getLevelSetDirPath() {
		return levelSetDirPath;
	}

	public void setLevelSetDirPath(String levelSetDirPath) {
		this.levelSetDirPath = levelSetDirPath;
	}

	@XmlElement(name = "pets_list")
	public void setPetsListString(String petsListString) {
		this.petsListString = petsListString;
		if (petsListString != null) {
			String[] array = petsListString.split(",");
			this.pets_list = new int[array.length];
			for (int i = 0; i < array.length; i++) {
				this.pets_list[i] = Integer.parseInt(array[i].trim());
			}
		}
	}

	public String getRecomend_boosts() {
		return recomend_boosts;
	}

	public void setRecomend_boosts(String recomend_boosts) {
		this.recomend_boosts = recomend_boosts;
	}

	public String getRecomend_tools() {
		return recomend_tools;
	}

	public void setRecomend_tools(String recomend_tools) {
		this.recomend_tools = recomend_tools;
	}

	public String getBoost_set() {
		return boost_set;
	}

	public void setBoost_set(String boost_set) {
		this.boost_set = boost_set;
	}

	public String getTool_set() {
		return tool_set;
	}

	public void setTool_set(String tool_set) {
		this.tool_set = tool_set;
	}

	public String getAdditional() {
		return additional;
	}

	public void setAdditional(String additional) {
		this.additional = additional;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 19 * hash + this.number;
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Level other = (Level) obj;
		return this.number == other.number;
	}

	@Override
	public int compareTo(Level level) {
		return Integer.compare(number, level.getNumber());
	}

}
