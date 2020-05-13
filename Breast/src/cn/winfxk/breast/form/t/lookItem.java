package cn.winfxk.breast.form.t;

import java.util.List;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.item.Item;
import cn.winfxk.breast.form.FormBase;
import cn.winfxk.breast.form.ShowItemData;
import cn.winfxk.breast.tool.CustomForm;
import cn.winfxk.breast.tool.Tool;

/**
 * 玩家查看物品数据
 * 
 * @Createdate 2020/05/13 16:49:57
 * @author Winfxk
 */
public class lookItem extends FormBase {
	private List<Item> list;

	public lookItem(Player player, FormBase upForm, List<Item> list) {
		super(player, upForm);
		this.list = list;
	}

	@Override
	public boolean MakeMain() {
		String[] KK = { "{Player}", "{Money}", "{ItemName}", "{ItemID}", "{ItemDamage}", "{ItemCount}", "{ItemPath}" };
		for (Item item : list)
			listKey.add(getString("ItemFormat", KK, new Object[] { player.getName(), myPlayer.getMoney(),
					itemList.getName(item), item.getId(), item.getDamage(), item.count, itemList.getPath(item) }));
		CustomForm form = new CustomForm(getID(), getTitle());
		form.addLabel(getContent());
		form.addDropdown(getString("SelectItem"), listKey);
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean wasClosed() {
		return setForm(upForm).make();
	}

	@Override
	public boolean disMain(FormResponse data) {
		return setForm(
				new ShowItemData(player, upForm, list.get(Tool.ObjToInt(getCustom(data).getDropdownResponse(1))), true))
						.make();
	}
}
