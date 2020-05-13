package cn.winfxk.breast.form.more.sett.item;

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
 * 添加服务器支持的物品列表页面
 * 
 * @Createdate 2020/05/13 10:18:40
 * @author Winfxk
 */
public class addItem extends FormBase {

	public addItem(Player player, FormBase upForm) {
		super(player, upForm);
		setSon("ItemListOfaddItem");
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
		form.addInput(getString("InputDamage"), "", getString("InputDamage"));
		form.addInput(getString("InputName"), "", getString("InputName"));
		form.addInput(getString("InputPath"), "", getString("InputPath"));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		FormResponseCustom d = getCustom(data);
		String string = d.getInputResponse(1);
		if (string == null || string.isEmpty() || !Tool.isInteger(string)) {
			player.sendMessage(getString("notInputID"));
			return isBack();
		}
		int ID = Tool.ObjToInt(string);
		string = d.getInputResponse(2);
		if (string == null || string.isEmpty() || !Tool.isInteger(string)) {
			player.sendMessage(getString("notInputDamage"));
			return isBack();
		}
		int Damage = Tool.ObjToInt(string);
		String Name = d.getInputResponse(3);
		if (Name == null || Name.isEmpty()) {
			player.sendMessage(getString("notInputName"));
			return isBack();
		}
		String Path = d.getInputResponse(4);
		if (Path == null || Path.isEmpty()) {
			player.sendMessage(getString("notInputPath"));
			return isBack();
		}
		player.sendMessage(getString("newOK"));
		return newItem(ID, Damage, Name, Path) && isBack();
	}

	/**
	 * 新建Item支持对象
	 * 
	 * @param ID     物品ID
	 * @param Damage 物品特殊值
	 * @param Name   物品名称
	 * @param Path   物品贴图路径
	 * @return
	 */
	public boolean newItem(int ID, int Damage, String Name, String Path) {
		Config config = ac.getItemListConfig();
		Map<String, Object> map = new HashMap<>();
		map.put("ID", ID);
		map.put("Damage", Damage);
		map.put("Name", Name);
		map.put("Path", Path);
		config.set(getKey(), map);
		return config.save();
	}

	/**
	 * 获取一个不重复的ID
	 * 
	 * @return
	 */
	private String getKey() {
		Map<String, Object> map = ac.getItemListConfig().getAll();
		int ii = 0;
		for (int i = 0; map.containsKey("Item" + i); i++)
			ii = i;
		return "Item" + (ii + 1);
	}
}
