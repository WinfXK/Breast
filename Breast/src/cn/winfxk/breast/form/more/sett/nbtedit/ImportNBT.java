package cn.winfxk.breast.form.more.sett.nbtedit;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.item.Item;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.Config;
import cn.winfxk.breast.form.FormBase;
import cn.winfxk.breast.tool.SimpleForm;

/**
 * 导入NBT
 * 
 * @Createdate 2020/05/13 11:24:38
 * @author Winfxk
 */
public class ImportNBT extends FormBase {
	private int Index;
	private Item item;
	private Config config;

	public ImportNBT(Player player, FormBase upForm, int Index) {
		super(player, upForm);
		this.Index = Index;
		setSon("NBTEditorOfEditItemOfImportNBT");
		item = player.getInventory().getContents().get(Index);
		setSon("NBTEditorOfEditItem");
		setK("{Player}", "{Money}", "{ItemName}", "{ItemID}", "{ItemDamage}", "{ItemPatn}");
		setD(player.getName(), myPlayer.getMoney(), itemList.getName(item), item.getId(), item.getDamage(),
				itemList.getPath(item));
		config = ac.getNBTConfig();
	}

	@Override
	public boolean MakeMain() {
		if (!myPlayer.isAdmin()) {
			player.sendMessage(getString("权限不足"));
			return isBack();
		}
		if (config.getAll().size() <= 0) {
			player.sendMessage(getString("notNBT"));
			return isBack();
		}
		SimpleForm form = new SimpleForm(getID(), getTitle(), getContent());
		for (String string : config.getKeys()) {
			if (config.get(string) == null)
				continue;
			listKey.add(string);
			form.addButton(string);
		}
		form.addButton(getBack());
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		int ID = getSimple(data).getClickedButtonId();
		if (listKey.size() < ID) {
			item.setCompoundTag((CompoundTag) config.get(listKey.get(ID)));
			player.getInventory().setItem(Index, item);
			player.sendMessage(getString("ImportOK"));
		}
		return isBack();
	}
}
