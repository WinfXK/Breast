package cn.winfxk.breast.form.more.sett.nbtedit;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.item.Item;
import cn.winfxk.breast.form.FormBase;
import cn.winfxk.breast.tool.CustomForm;

/**
 * 设置物品数量
 * 
 * @Createdate 2020/05/13 12:25:05
 * @author Winfxk
 */
public class setCount extends FormBase {
	private Item item;
	private int Index;

	public setCount(Player player, FormBase upForm, int Index) {
		super(player, upForm);
		this.Index = Index;
		setSon("NBTEditorOfEditItemOfsetCount");
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
		form.addLabel(getString("Content"));
		form.addSlider(getString("setCount"), 1, item.getMaxStackSize(), 1, item.getCount());
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		item.setCount(Float.valueOf(getCustom(data).getSliderResponse(1)).intValue());
		player.getInventory().setItem(Index, item);
		player.sendMessage(getString("setOK"));
		return isBack();
	}
}
