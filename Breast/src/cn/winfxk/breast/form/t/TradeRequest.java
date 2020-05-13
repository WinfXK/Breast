package cn.winfxk.breast.form.t;

import java.util.ArrayList;
import java.util.List;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.item.Item;
import cn.winfxk.breast.MyPlayer;
import cn.winfxk.breast.form.FormBase;
import cn.winfxk.breast.tool.SimpleForm;

/**
 * 发送请求的玩家已经确定交易，该界面用于显示请求
 * 
 * @Createdate 2020/05/13 16:36:26
 * @author Winfxk
 */
public class TradeRequest extends FormBase {
	private Player ofPlayer;
	private List<Item> OfList;

	public TradeRequest(Player player, FormBase upForm, Player OfPlayer, List<Item> Oflist) {
		super(player, upForm);
		ofPlayer = OfPlayer;
		OfList = Oflist;
		setK("{Player}", "{Money}", "{ByPlayer}", "{ByMoney}", "{ItemCount}");
		setD(player.getName(), myPlayer.getMoney(), OfPlayer.getName(), MyPlayer.getMoney(OfPlayer.getName()),
				Oflist.size());
		myPlayer.saveItem(new ArrayList<Item>());
		myPlayer.isTrade = true;
		MyPlayer mPlayer = ac.getPlayers(OfPlayer);
		mPlayer.isTrade = true;
		mPlayer.saveItem(Oflist);
	}

	@Override
	public boolean MakeMain() {
		SimpleForm form = new SimpleForm(getID(), getTitle(), getContent());
		form.addButton(getString("lookItem"));
		form.addButton(getString("Confirm"));
		form.addButton(getBack());
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		switch (getSimple(data).getClickedButtonId()) {
		case 0:
			setForm(new ItemList(player, this));
			break;
		case 1:
			setForm(new SelectItem(player, this, ofPlayer, OfList));
			break;
		default:
			return wasClosed() && isBack();
		}
		return true;
	}

	@Override
	public boolean wasClosed() {
		if (OfList.size() > 0)
			for (Item item : OfList)
				ofPlayer.getInventory().addItem(item);
		return true;
	}
}
