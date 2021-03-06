package cn.winfxk.breast.form.more.sett.ent;

import java.util.HashMap;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.utils.Config;
import cn.winfxk.breast.form.FormBase;
import cn.winfxk.breast.tool.CustomForm;
import cn.winfxk.breast.tool.Tool;

/**
 * 新建附魔文档
 * 
 * @Createdate 2020/05/12 11:56:32
 * @author Winfxk
 */
public class addItem extends FormBase {

	public addItem(Player player, FormBase upForm) {
		super(player, upForm);
		setSon("EnchantOfaddItem");
	}

	@Override
	public boolean MakeMain() {
		if (!myPlayer.isAdmin()) {
			player.sendMessage(getString("权限不足"));
			return isBack();
		}
		CustomForm form = new CustomForm(getID(), getTitle());
		form.addLabel(getString("Content"));
		form.addInput(getString("InputID"), "", getString("InputID"));
		form.addInput(getString("InputName"), "", getString("InputName"));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		FormResponseCustom d = getCustom(data);
		String string = d.getInputResponse(1);
		if (string == null || string.isEmpty() || !Tool.isInteger(string)) {
			player.sendMessage(getString("NotInputID"));
			return isBack();
		}
		int ID = Tool.ObjToInt(string);
		string = d.getInputResponse(2);
		if (string == null || string.isEmpty()) {
			player.sendMessage(getString("NotInputName"));
			return isBack();
		}
		player.sendMessage(getString("newOK"));
		return newItem(ID, string) && isBack();
	}

	/**
	 * 添加一个新的Enchant条目
	 * 
	 * @param ID   EnchantID
	 * @param Name EnchantName
	 * @return
	 */
	public boolean newItem(int ID, String Name) {
		Config config = ac.getEnchantListConfig();
		Map<String, Object> map = new HashMap<>();
		map.put("ID", ID);
		map.put("Name", Name);
		config.set(getKey(), map);
		return config.save();
	}

	/**
	 * 获取一个不重复的EnchantKey
	 * 
	 * @return
	 */
	private String getKey() {
		Map<String, Object> config = ac.getEnchantListConfig().getAll();
		int ii = 0;
		for (int i = 0; config.containsKey("Enchant" + i); i++)
			ii = i;
		return "Enchant" + (ii + 1);
	}
}
