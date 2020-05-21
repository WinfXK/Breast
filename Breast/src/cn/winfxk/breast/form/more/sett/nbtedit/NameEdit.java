package cn.winfxk.breast.form.more.sett.nbtedit;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.item.Item;
import cn.winfxk.breast.form.FormBase;
import cn.winfxk.breast.tool.CustomForm;

/**
 * @Createdate 2020/05/13 11:21:03
 * @author Winfxk
 */
public class NameEdit extends FormBase {
	private int Index;
	private Item item;

	public NameEdit(Player player, FormBase upForm, int Index) {
		super(player, upForm);
		this.Index = Index;
		setSon("NBTEditorOfEditItemOfNameEdit");
		item = player.getInventory().getContents().get(Index);
		setK("{Player}", "{Money}", "{ItemName}", "{ItemID}", "{ItemDamage}", "{ItemPatn}");
		setD(player.getName(), myPlayer.getMoney(), itemList.getName(item), item.getId(), item.getDamage(),
				itemList.getPath(item));
	}

	@Override
	public boolean MakeMain() {
		if (!myPlayer.isAdmin()) {
			player.sendMessage(getString("权限不足"));
			return isBack();
		}
		CustomForm form = new CustomForm(getID(), getTitle());
		form.addLabel(getContent());
		form.addInput(getString("setName"), itemList.getName(item) == null ? "" : itemList.getName(item),
				getString("setName"));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		String Name = getCustom(data).getInputResponse(1);
		if (Name == null || Name.isEmpty()) {
			player.sendMessage(getString("notInputName"));
			return isBack();
		}
		item.setCustomName(Name);
		player.getInventory().setItem(Index, item);
		player.sendMessage(getString("setOK"));
		return isBack();
	}
}
