package cn.winfxk.breast.form.more.sett.nbtedit.more;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.item.Item;
import cn.winfxk.breast.form.FormBase;
import cn.winfxk.breast.tool.CustomForm;

/**
 * @Createdate 2020/05/13 12:46:02
 * @author Winfxk
 */
public class LoreEditOfaddLore extends FormBase {
	private Item item;
	private int index;

	public LoreEditOfaddLore(Player player, FormBase upForm, int index) {
		super(player, upForm);
		setSon("NBTEditorOfEditItemOfLoreEdit");
		item = player.getInventory().getContents().get(index);
		setK("{Player}", "{Money}", "{ItemName}", "{ItemID}", "{ItemDamage}", "{ItemPatn}");
		setD(player.getName(), myPlayer.getMoney(), itemList.getName(item), item.getId(), item.getDamage(),
				itemList.getPath(item));
		this.index = index;
	}

	@Override
	public boolean MakeMain() {
		if (!myPlayer.isAdmin()) {
			player.sendMessage(getString("权限不足"));
			return isBack();
		}
		CustomForm form = new CustomForm(getID(), getContent());
		form.addLabel(getContent());
		form.addInput(getString("addLore"), "", getString("addLore"));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		String string = getCustom(data).getInputResponse(1);
		if (string == null || string.isEmpty()) {
			player.sendMessage(getString("notInputLore"));
			return isBack();
		}
		String[] strings = new String[item.getLore().length + 1];
		for (int i = 0; i < item.getLore().length; i++)
			strings[i] = item.getLore()[i];
		strings[strings.length - 1] = getCustom(data).getInputResponse(1);
		item.setLore(strings);
		player.getInventory().setItem(index, item);
		player.sendMessage(getString("addOK"));
		return isBack();
	}
}
