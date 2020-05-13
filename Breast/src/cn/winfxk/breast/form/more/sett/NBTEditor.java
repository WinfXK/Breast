package cn.winfxk.breast.form.more.sett;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.item.Item;
import cn.winfxk.breast.form.FormBase;
import cn.winfxk.breast.form.more.sett.nbtedit.EditItem;
import cn.winfxk.breast.tool.CustomForm;

/**
 * 物品编辑器
 * 
 * @Createdate 2020/05/12 11:34:49
 * @author Winfxk
 */
public class NBTEditor extends FormBase {
	private Map<Integer, Item> items;
	private List<Integer> list = new ArrayList<>();

	public NBTEditor(Player player, FormBase upForm) {
		super(player, upForm);
	}

	@Override
	public boolean MakeMain() {
		if (!myPlayer.isAdmin()) {
			player.sendMessage(getString("权限不足"));
			return isBack();
		}
		String[] KK = { "{Player}", "{Money}", "{ItemName}", "{ItemID}", "{ItemDamage}", "{ItemPatn}" };
		CustomForm form = new CustomForm(getID(), getTitle());
		items = player.getInventory().getContents();
		Item item;
		for (Integer i : items.keySet()) {
			item = items.get(i);
			if (item == null || item.getId() == 0)
				continue;
			listKey.add(getString("ItemFormat", KK, new Object[] { player.getName(), myPlayer.getMoney(),
					itemList.getName(item), item.getId(), item.getDamage(), itemList.getPath(item) }));
			list.add(i);
		}
		int index = list.contains(player.getInventory().getHeldItemIndex())
				? list.indexOf(player.getInventory().getHeldItemIndex())
				: 0;
		form.addDropdown(getString("SelectItem"), listKey, index);
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		int Index = list.get(getSimple(data).getClickedButtonId());
		return setForm(new EditItem(player, upForm, Index)).make();
	}
}
