package cn.winfxk.breast.form.more.sett.item;

import java.util.HashMap;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.utils.Config;
import cn.winfxk.breast.form.FormBase;
import cn.winfxk.breast.tool.SimpleForm;

/**
 * 准备删除服务器支持的物品列表ed项目
 * 
 * @Createdate 2020/05/13 10:18:06
 * @author Winfxk
 */
public class delItem extends FormBase {
	private String Key;
	private Map<String, Object> item;
	private Config config;

	public delItem(Player player, FormBase upForm, String Key) {
		super(player, upForm);
		this.Key = Key;
		setSon("ItemListOfdelItem");
		config = ac.getItemListConfig();
		item = config.get(Key) != null && config.get(Key) instanceof Map ? (HashMap<String, Object>) config.get(Key)
				: new HashMap<>();
		setK("{Player}", "{Money}", "{ItemName}", "{ItemID}", "{ItemDamage}", "{ItemPatn}");
		setD(player.getName(), myPlayer.getMoney(), item.get("Name"), item.get("ID"), item.get("Damage"),
				item.get("Path"));
	}

	@Override
	public boolean MakeMain() {
		if (!myPlayer.isAdmin()) {
			player.sendMessage(getString("权限不足"));
			return isBack();
		}
		if (item.size() <= 0) {
			player.sendMessage(getString("NotItem"));
			return isBack();
		}
		SimpleForm form = new SimpleForm(getID(), getTitle(), getContent());
		form.addButton(getString("Confirm"));
		form.addButton(getBack());
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		if (getSimple(data).getClickedButtonId() == 0) {
			config.remove(Key);
			player.sendMessage(getString("DelOK"));
		}
		return config.save() && isBack();
	}
}
