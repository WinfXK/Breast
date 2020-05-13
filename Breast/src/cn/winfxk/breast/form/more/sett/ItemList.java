package cn.winfxk.breast.form.more.sett;

import java.util.HashMap;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.breast.form.FormBase;
import cn.winfxk.breast.form.more.sett.item.ClickItem;
import cn.winfxk.breast.form.more.sett.item.addItem;
import cn.winfxk.breast.tool.SimpleForm;

/**
 * 显示当前服务器已经支持的物品
 * 
 * @Createdate 2020/05/12 11:31:36
 * @author Winfxk
 */
public class ItemList extends FormBase {
	private Map<String, Object> map;

	public ItemList(Player player, FormBase upForm) {
		super(player, upForm);
		map = ac.getItemListConfig().getAll();
	}

	@Override
	public boolean MakeMain() {
		SimpleForm form = new SimpleForm(getID(), getTitle(), getContent());
		Map<String, Object> item;
		String[] KK = { "{Player}", "{Money}", "{ItemName}", "{ItemID}", "{ItemDamage}", "{ItemPatn}" };
		for (String Key : map.keySet()) {
			item = map.get(Key) != null && map.get(Key) instanceof Map ? (HashMap<String, Object>) map.get(Key)
					: new HashMap<>();
			if (item.size() <= 0)
				continue;
			form.addButton(getString("ItemFormat", KK, new Object[] { player.getName(), myPlayer.getMoney(),
					item.get("Name"), item.get("ID"), item.get("Damage"), item.get("Path") }));
			listKey.add(Key);
		}
		form.addButton(getString("newItem"));
		form.addButton(getBack());
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		int ID = getSimple(data).getClickedButtonId();
		if (listKey.size() < ID)
			return setForm(new ClickItem(player, this, listKey.get(ID))).make();
		if (ID == listKey.size())
			return setForm(new addItem(player, this)).make();
		return isBack();
	}
}
