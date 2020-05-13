package cn.winfxk.breast.form.more;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.item.Item;
import cn.winfxk.breast.form.FormBase;
import cn.winfxk.breast.tool.CustomForm;

/**
 * 上架物品页面
 * 
 * @Createdate 2020/05/12 11:06:10
 * @author Winfxk
 */
public class SelectItem extends FormBase {
	private Map<Integer, Item> all;
	private List<Integer> list = new ArrayList<>();

	public SelectItem(Player player, FormBase upForm) {
		super(player, upForm);
		all = player.getInventory().getContents();
	}

	@Override
	public boolean MakeMain() {
		if (all.size() <= 0) {
			player.sendMessage(getString("notItem"));
			return isBack();
		}
		String[] ShopItemK = { "{Player}", "{Money}", "{ItemName}", "{ItemID}", "{ItemDamage}", "{ItemCount}" };
		Item item;
		for (Integer i : all.keySet()) {
			item = all.get(i);
			if (item == null || item.getId() == 0)
				continue;
			listKey.add(getString("ItemFormat", ShopItemK, new Object[] { player.getName(), myPlayer.getMoney(),
					itemList.getName(item), item.getId(), item.getDamage(), item.getCount() }));
			list.add(i);
		}
		CustomForm form = new CustomForm(getID(), getTitle());
		form.addLabel(getContent());
		form.addDropdown(getString("SelectItem"), listKey,
				list.contains(player.getInventory().getHeldItemIndex())
						? list.indexOf(player.getInventory().getHeldItemIndex())
						: 0);
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		return setForm(new UpItem(player, this, list.get(getSimple(data).getClickedButtonId()))).make();
	}
}
