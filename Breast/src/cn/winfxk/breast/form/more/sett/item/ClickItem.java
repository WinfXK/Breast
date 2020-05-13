package cn.winfxk.breast.form.more.sett.item;

import java.util.HashMap;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.breast.form.FormBase;
import cn.winfxk.breast.tool.SimpleForm;

/**
 * 点击了服务器支持的物品列表页面
 * 
 * @Createdate 2020/05/13 10:17:25
 * @author Winfxk
 */
public class ClickItem extends FormBase {
	private String Key;
	private Map<String, Object> item;

	public ClickItem(Player player, FormBase upForm, String Key) {
		super(player, upForm);
		this.Key = Key;
		setSon("ItemListOfClickItem");
		item = ac.getItemListConfig().get(Key) != null && ac.getItemListConfig().get(Key) instanceof Map
				? (HashMap<String, Object>) ac.getItemListConfig().get(Key)
				: new HashMap<>();
		setK("{Player}", "{Money}", "{ItemName}", "{ItemID}", "{ItemDamage}", "{ItemPatn}");
		setD(player.getName(), myPlayer.getMoney(), item.get("Name"), item.get("ID"), item.get("Damage"),
				item.get("Path"));
	}

	@Override
	public boolean MakeMain() {
		if (item.size() <= 0) {
			player.sendMessage(getString("NotItem"));
			return isBack();
		}
		if (!myPlayer.isAdmin()) {
			player.sendMessage(getString("权限不足"));
			return isBack();
		}
		SimpleForm form = new SimpleForm(getID(), getTitle(), getContent());
		form.addButton(getString("del"));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		return getSimple(data).getClickedButtonId() == 0 ? setForm(new delItem(player, upForm, Key)).make() : isBack();
	}
}
