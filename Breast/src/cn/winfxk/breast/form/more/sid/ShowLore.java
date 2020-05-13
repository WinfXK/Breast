package cn.winfxk.breast.form.more.sid;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.item.Item;
import cn.winfxk.breast.form.FormBase;
import cn.winfxk.breast.tool.SimpleForm;

/**
 * 查看物品的Lore
 * 
 * @Createdate 2020/05/13 15:05:47
 * @author Winfxk
 */
public class ShowLore extends FormBase {
	private Item item;

	public ShowLore(Player player, FormBase upForm, Item item) {
		super(player, upForm);
		this.item = item;
		setSon("ShowItemDataOfShowLore");
		setK("{Player}", "{Money}", "{ItemName}", "{ItemID}", "{ItemDamage}", "{ItemPatn}", "{ItemCount}");
		setD(player.getName(), myPlayer.getMoney(), itemList.getName(item), item.getId(), item.getDamage(),
				itemList.getPath(item), item.getCount());
	}

	@Override
	public boolean MakeMain() {
		if (item.getLore().length <= 0) {
			player.sendMessage(getString("notLore"));
			return isBack();
		}
		SimpleForm form = new SimpleForm(getID(), getTitle(), getContent());
		String string = "";
		for (String s : item.getLore())
			string += "\n" + s;
		form.setContent(form.getContent() + string);
		form.addButton(getBack());
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		return isBack();
	}
}
