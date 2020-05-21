package cn.winfxk.breast.form.more.sett.nbtedit;

import java.util.Arrays;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.item.Item;
import cn.winfxk.breast.form.FormBase;
import cn.winfxk.breast.form.more.sett.nbtedit.more.LoreEditOfaddLore;
import cn.winfxk.breast.tool.SimpleForm;

/**
 * 编辑物品的Lore
 * 
 * @Createdate 2020/05/13 11:22:10
 * @author Winfxk
 */
public class LoreEdit extends FormBase {
	private Item item;
	private int index;

	public LoreEdit(Player player, FormBase upForm, int Index) {
		super(player, upForm);
		index = Index;
		setSon("NBTEditorOfEditItemOfLoreEdit");
		setK("{Player}", "{Money}", "{ItemName}", "{ItemID}", "{ItemDamage}", "{ItemPatn}");
	}

	@Override
	public boolean MakeMain() {
		item = player.getInventory().getContents().get(index);
		if (!myPlayer.isAdmin()) {
			player.sendMessage(getString("权限不足"));
			return isBack();
		}
		setD(player.getName(), myPlayer.getMoney(), itemList.getName(item), item.getId(), item.getDamage(),
				itemList.getPath(item));
		SimpleForm form = new SimpleForm(getID(), getTitle(), getContent());
		for (String string : item.getLore())
			form.addButton(string);
		form.addButton(getString("addLore"));
		form.addButton(getBack());
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		int ID = getSimple(data).getClickedButtonId();
		listKey = Arrays.asList(item.getLore());
		if (ID == item.getLore().length)
			return setForm(new LoreEditOfaddLore(player, this, index)).make();
		if (item.getLore().length > ID) {
			listKey.remove(ID);
			item.setLore((String[]) listKey.toArray());
			player.getInventory().setItem(index, item);
			player.sendMessage(getString("del"));
			return MakeMain();
		}
		return isBack();
	}
}
