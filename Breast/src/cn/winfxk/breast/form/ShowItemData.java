package cn.winfxk.breast.form;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.item.Item;
import cn.winfxk.breast.form.more.sid.ShowEnchat;
import cn.winfxk.breast.form.more.sid.ShowLore;
import cn.winfxk.breast.tool.SimpleForm;

/**
 * 显示物品数据页面
 * 
 * @Createdate 2020/05/13 14:18:29
 * @author Winfxk
 */
public class ShowItemData extends FormBase {
	private Item item;
	private boolean isOk = false;

	public ShowItemData(Player player, FormBase upForm, Item item, boolean isOK) {
		super(player, upForm);
		isOK = true;
		this.isOk = isOK;
		setK("{Player}", "{Money}", "{ItemName}", "{ItemID}", "{ItemDamage}", "{ItemPatn}", "{ItemCount}");
		setD(player.getName(), myPlayer.getMoney(), itemList.getName(item), item.getId(), item.getDamage(),
				itemList.getPath(item), item.getCount());
	}

	public ShowItemData(Player player, FormBase upForm, Item item) {
		super(player, upForm);
		this.item = item;
		setK("{Player}", "{Money}", "{ItemName}", "{ItemID}", "{ItemDamage}", "{ItemPatn}", "{ItemCount}");
		setD(player.getName(), myPlayer.getMoney(), itemList.getName(item), item.getId(), item.getDamage(),
				itemList.getPath(item), item.getCount());
	}

	@Override
	public boolean wasClosed() {
		return isOk ? setForm(upForm).make() : true;
	}

	@Override
	public boolean MakeMain() {
		SimpleForm form = new SimpleForm(getID(), getTitle(), getContent());
		if (item.getEnchantments().length >= 1) {
			listKey.add("el");
			form.addButton(getString("EnchantList"));
		}
		if (item.getLore().length >= 1) {
			listKey.add("ll");
			form.addButton(getString("LoreList"));
		}
		listKey.add("bc");
		form.addButton(getBack());
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		switch (listKey.get(getSimple(data).getClickedButtonId())) {
		case "el":
			setForm(new ShowEnchat(player, this, item));
			break;
		case "ll":
			setForm(new ShowLore(player, this, item));
			break;
		default:
			return isBack();
		}
		return make();
	}
}
