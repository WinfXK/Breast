package cn.winfxk.breast.form.t;

import java.util.List;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.item.Item;
import cn.winfxk.breast.MyPlayer;
import cn.winfxk.breast.form.FormBase;
import cn.winfxk.breast.tool.SimpleForm;

/**
 * 玩家选择了交易物品和交易对象，这个是确定是否交易的界面
 * 
 * @Createdate 2020/05/13 16:28:05
 * @author Winfxk
 */
public class ConfirmForm extends FormBase {
	private List<Item> list;
	private Player toPlayer;

	public ConfirmForm(Player player, FormBase upForm, List<Item> list, Player toPlayer) {
		super(player, upForm);
		this.toPlayer = toPlayer;
		this.list = list;
		setSon("TransactionOfMainConfirmForm");
		setK("{Player}", "{Money}", "{ByPlayer}", "{ByMoney}", "{ItemCount}");
		setD(player.getName(), myPlayer.getMoney(), toPlayer.getName(), MyPlayer.getMoney(toPlayer.getName()),
				list.size());
	}

	@Override
	public boolean MakeMain() {
		if (player.getName().equals(toPlayer.getName())) {
			player.sendMessage(getString("ToMe"));
			return wasClosed() && isBack();
		}
		MyPlayer mmMyPlayer = ac.getPlayers(toPlayer);
		if (mmMyPlayer.isSaveItem())
			mmMyPlayer.reloadItem();
		SimpleForm form = new SimpleForm(getID(), getTitle(), getContent());
		form.addButton(getString("Confirm"));
		form.addButton(getBack());
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		if (getSimple(data).getClickedButtonId() != 0)
			return wasClosed() && isBack();
		return setForm(new TradeRequest(toPlayer, upForm, player, list)).make();
	}

	@Override
	public boolean wasClosed() {
		if (list.size() > 0)
			for (Item item : list)
				player.getInventory().addItem(item);
		return true;
	}
}
