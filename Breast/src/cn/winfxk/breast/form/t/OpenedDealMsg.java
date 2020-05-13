package cn.winfxk.breast.form.t;

import java.util.List;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.item.Item;
import cn.winfxk.breast.MyPlayer;
import cn.winfxk.breast.form.FormBase;
import cn.winfxk.breast.tool.SimpleForm;

/**
 * @Createdate 2020/05/13 17:54:10
 * @author Winfxk
 */
public class OpenedDealMsg extends FormBase {
	private Player ofPlayer;
	private MyPlayer mmmmMyPlayer;
	private List<Item> list, ofItems;

	public OpenedDealMsg(Player player, List<Item> list, FormBase upForm, Player OfPlayer, List<Item> ofList) {
		super(player, upForm);
		ofPlayer = OfPlayer;
		mmmmMyPlayer = ac.getPlayers(ofPlayer);
		this.list = list;
		ofItems = ofList;
	}

	@Override
	public boolean MakeMain() {
		SimpleForm form = new SimpleForm(getID(), getTitle(), getContent());
		form.addButton(getString("Confirm"));
		form.addButton(getString("lookItem"));
		form.addButton(getBack());
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean wasClosed() {
		mmmmMyPlayer.reloadItem();
		myPlayer.reloadItem();
		mmmmMyPlayer.isTrade = false;
		myPlayer.isTrade = false;
		return super.wasClosed();
	}

	@Override
	public boolean disMain(FormResponse data) {
		int ID = getSimple(data).getClickedButtonId();
		if (ID == 1)
			return setForm(new lookItem(player, this, ofItems)).make();
		if (ID == 0) {
			for (Item item : ofItems)
				player.getInventory().addItem(item);
			for (Item item : list)
				ofPlayer.getInventory().addItem(item);
			player.sendMessage(getString("OK"));
			ofPlayer.sendMessage(getString("OK"));
			return myPlayer.clearitem() && mmmmMyPlayer.clearitem();
		}
		return wasClosed();
	}
}
