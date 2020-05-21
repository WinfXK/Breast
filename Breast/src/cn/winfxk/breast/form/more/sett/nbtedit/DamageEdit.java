package cn.winfxk.breast.form.more.sett.nbtedit;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.item.Item;
import cn.winfxk.breast.form.FormBase;
import cn.winfxk.breast.tool.CustomForm;
import cn.winfxk.breast.tool.Tool;

/**
 * 编辑物品的Damage
 * 
 * @Createdate 2020/05/13 11:22:49
 * @author Winfxk
 */
public class DamageEdit extends FormBase {
	private Item item;
	private int Index;

	public DamageEdit(Player player, FormBase upForm, int Index) {
		super(player, upForm);
		this.Index = Index;
		setSon("NBTEditorOfEditItemOfDamageEdit");
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
		form.addInput(getString("InputDamage"), item.getDamage(), getString("InputDamage"));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		String string = getCustom(data).getInputResponse(1);
		if (string == null || string.isEmpty() || !Tool.isInteger(string)) {
			player.sendMessage(getString("NotInputDamage"));
			return MakeMain();
		}
		item.setDamage(Tool.ObjToInt(string));
		player.getInventory().setItem(Index, item);
		setK("{Player}", "{Money}", "{ItemName}", "{ItemID}", "{ItemDamage}", "{ItemPatn}");
		setD(player.getName(), myPlayer.getMoney(), itemList.getName(item), item.getId(), item.getDamage(),
				itemList.getPath(item));
		player.sendMessage(getString("setOK"));
		return isBack();
	}
}
