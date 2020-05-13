package cn.winfxk.breast.form.t;

import java.util.ArrayList;
import java.util.List;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.item.Item;
import cn.winfxk.breast.MyPlayer;
import cn.winfxk.breast.form.FormBase;
import cn.winfxk.breast.tool.SimpleForm;

/**
 * 玩家发起了实时交易，这是选择交易对象的界面
 * 
 * @Createdate 2020/05/13 15:21:19
 * @author Winfxk
 */
public class Transaction extends FormBase {
	private List<Player> players;
	private List<Item> list;

	public Transaction(Player player, FormBase upForm, List<Item> list) {
		super(player, upForm);
		this.list = list;
		setK("{Player}", "{Money}", "{ItemCount}");
	}

	@Override
	public boolean MakeMain() {
		setD(player.getName(), myPlayer.getMoney(), list.size());
		players = new ArrayList<>(Server.getInstance().getOnlinePlayers().values());
		if (players.size() <= 0) {
			player.sendMessage(getString("notPlayer"));
			return wasClosed() && isBack();
		}
		String[] KK = { "{Player}", "{Money}", "{ByPlayer}", "{ByMoney}" };
		SimpleForm form = new SimpleForm(getID(), getTitle(), getContent());
		for (Player player : players)
			form.addButton(getString("PlayerItem", KK, new Object[] { myPlayer.getName(), myPlayer.getMoney(),
					player.getName(), MyPlayer.getMoney(player.getName()) }));
		form.addButton(getBack());
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean wasClosed() {
		if (list.size() > 0)
			for (Item item : list)
				player.getInventory().addItem(item);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		int ID = getSimple(data).getClickedButtonId();
		if (players.size() < ID)
			return setForm(new ConfirmForm(player, upForm, list, players.get(ID))).make();
		return wasClosed() && isBack();
	}
}
