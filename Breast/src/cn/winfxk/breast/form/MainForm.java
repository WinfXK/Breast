package cn.winfxk.breast.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.item.Item;
import cn.winfxk.breast.form.more.AdminSetting;
import cn.winfxk.breast.form.more.ClickItem;
import cn.winfxk.breast.form.more.SearchItem;
import cn.winfxk.breast.form.more.SelectItem;
import cn.winfxk.breast.tool.SimpleForm;
import cn.winfxk.breast.tool.Tool;

/**
 * 主页
 * 
 * @Createdate 2020/05/09 17:35:43
 * @author Winfxk
 */
public class MainForm extends FormBase {
	private Map<String, Object> map;
	private List<String> more = new ArrayList<>();

	public MainForm(Player player, FormBase upForm) {
		super(player, upForm);
		map = ac.getShopConfig().getAll();
	}

	@Override
	public boolean MakeMain() {
		SimpleForm form = new SimpleForm(getID(), getTitle(), getContent());
		if (map.size() > 0) {
			Map<String, Object> map2;
			Item item;
			String[] ShopItemK = { "{Player}", "{Money}", "{ItemName}", "{ItemID}", "{ItemDamage}", "{ItemPrice}",
					"{ItemCount}", "{ByPlayer}", "{EconomyName}" };
			for (Object obj : map.values()) {
				map2 = obj != null && obj instanceof Map ? (HashMap<String, Object>) obj : new HashMap<>();
				if (map2.size() <= 0)
					continue;
				item = Tool.loadItem((Map<String, Object>) map2.get("Item"));
				form.addButton(getString("ShopItem", ShopItemK,
						new Object[] { player.getName(), myPlayer.getMoney(), itemList.getName(item, true, null),
								item.getId(), item.getDamage(), map2.get("Money"), item.getCount(), map2.get("Player"),
								map2.get("EconomyName") }));
				listKey.add(Tool.objToString(map2.get("Key")));
			}
			more.add("si");
			form.addButton(getString("SearchItem"));
		} else
			form.setContent(form.getContent() + "\n" + getString("notItem"));
		more.add("up");
		form.addButton(getString("UpItem"));
		if (myPlayer.isAdmin()) {
			more.add("as");
			form.addButton(getString("AdminSetting"));
		}
		if (player.getInventory().getContents().size() >= 1) {
			more.add("tt");
			form.addButton(getString("Transaction"));
		}
		more.add("bk");
		form.addButton(getBack());
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		int ID = getSimple(data).getClickedButtonId();
		if (listKey.size() < ID)
			return setForm(new ClickItem(player, this, listKey.get(ID))).make();
		switch (more.get(ID - listKey.size())) {
		case "tt":
			setForm(new cn.winfxk.breast.form.t.SelectItem(player, this));
			break;
		case "si":
			setForm(new SearchItem(player, this));
			break;
		case "as":
			setForm(new AdminSetting(player, this));
			break;
		case "up":
			setForm(new SelectItem(player, this));
			break;
		default:
			return isBack();
		}
		return make();
	}
}
