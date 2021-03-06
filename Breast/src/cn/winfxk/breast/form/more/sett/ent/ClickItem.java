package cn.winfxk.breast.form.more.sett.ent;

import java.util.HashMap;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.utils.Config;
import cn.winfxk.breast.form.FormBase;
import cn.winfxk.breast.tool.SimpleForm;

/**
 * 玩家点击了附魔项目
 * 
 * @Createdate 2020/05/12 11:55:53
 * @author Winfxk
 */
public class ClickItem extends FormBase {
	private String Key;
	private Map<String, Object> map;
	private Config config;

	/**
	 * 玩家点击了附魔项目
	 * 
	 * @param player
	 * @param upForm
	 * @param Key
	 */
	public ClickItem(Player player, FormBase upForm, String Key) {
		super(player, upForm);
		this.Key = Key;
		config = ac.getEnchantListConfig();
		map = config.get(Key) == null || !(config.get(Key) instanceof Map) ? new HashMap<>()
				: (HashMap<String, Object>) config.get(Key);
		setSon("EnchantOfClickItem");
		setK("{Player}", "{Money}", "{EnchantID}", "{EnchantName}");
		setD(player.getName(), myPlayer.getMoney(), map.get("ID"), map.get("Name"));
	}

	@Override
	public boolean MakeMain() {
		if (map.size() <= 0) {
			player.sendMessage(getString("NotItem"));
			return isBack();
		}
		if (!myPlayer.isAdmin()) {
			player.sendMessage(getString("权限不足"));
			return isBack();
		}
		SimpleForm form = new SimpleForm(getID(), getTitle(), getContent());
		form.addButton(getString("del"));
		form.addButton(getBack());
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		if (getSimple(data).getClickedButtonId() == 0)
			return setForm(new delItem(player, upForm, Key)).make();
		return isBack();
	}
}
