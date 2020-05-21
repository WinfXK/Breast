package cn.winfxk.breast.form.more.sett;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	private int ItemListCount, index;
	private List<String> ItemKey, ButtonKey;

	public ItemList(Player player, FormBase upForm) {
		super(player, upForm);
		ItemListCount = ac.getConfig().getInt("物品列表显示数量");
		index = 0;
	}

	@Override
	public boolean MakeMain() {
		ButtonKey = new ArrayList<>();
		map = ac.getItemListConfig().getAll();
		SimpleForm form = new SimpleForm(getID(), getTitle(), getContent());
		Map<String, Object> item;
		String[] KK = { "{Player}", "{Money}", "{ItemName}", "{ItemID}", "{ItemDamage}", "{ItemPatn}" };
		ItemKey = new ArrayList<>(map.keySet());
		String Key;
		listKey = new ArrayList<>();
		for (int i = 0; i < ItemListCount && i < ItemKey.size(); i++) {
			Key = ItemKey.get(i + index);
			item = map.get(Key) != null && map.get(Key) instanceof Map ? (HashMap<String, Object>) map.get(Key)
					: new HashMap<>();
			if (item.size() <= 0)
				continue;
			form.addButton(getString("ItemFormat", KK, new Object[] { player.getName(), myPlayer.getMoney(),
					item.get("Name"), item.get("ID"), item.get("Damage"), item.get("Path") }));
			listKey.add(Key);
		}
		index += listKey.size();
		ButtonKey.add("new");
		form.addButton(getString("newItem"));
		ButtonKey.add("b");
		form.addButton(index <= ItemListCount ? getBack() : getString("Last"));
		ButtonKey.add("n");
		form.addButton(getString("Next"));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		int ID = getSimple(data).getClickedButtonId();
		if (listKey.size() > ID)
			return setForm(new ClickItem(player, this, listKey.get(ID))).make();
		switch (ButtonKey.get(ID - listKey.size())) {
		case "new":
			return setForm(new addItem(player, this)).make();
		case "b":
			if (index > ItemListCount && index > 0) {
				index = index - ItemListCount * 2;
				index = index <= 0 ? 0 : index;
				return MakeMain();
			}
			return isBack();
		case "n":
			return MakeMain();
		}
		return isBack();
	}
}
