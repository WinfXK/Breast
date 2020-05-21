package cn.winfxk.breast.form.more;

import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.item.Item;
import cn.nukkit.utils.Config;
import cn.winfxk.breast.MyPlayer;
import cn.winfxk.breast.form.FormBase;
import cn.winfxk.breast.form.ShowItemData;
import cn.winfxk.breast.money.MyEconomy;
import cn.winfxk.breast.tool.SimpleForm;
import cn.winfxk.breast.tool.Tool;

/**
 * 玩家点击了一个商品项目 准备购买
 * 
 * @Createdate 2020/05/12 10:54:30
 * @author Winfxk
 */
public class ClickItem extends FormBase {
	private String Key, ByPlayer;
	private Item item;
	private Config config;
	private MyEconomy economy;
	private double Money;

	public ClickItem(Player player, FormBase upForm, String Key) {
		super(player, upForm);
		this.Key = Key;
		config = ac.getShopConfig();
		setSon("ShopFormClickItem");
		Map<String, Object> map2 = (Map<String, Object>) ac.getShopConfig().get(Key);
		item = Tool.loadItem((Map<String, Object>) map2.get("Item"));
		setK("{Player}", "{Money}", "{ItemName}", "{ItemID}", "{ItemDamage}", "{ItemPrice}", "{ItemCount}",
				"{ByPlayer}", "{EconomyName}");
		Money = Tool.objToDouble(map2.get("Money"));
		economy = ac.getEconomyManage().getEconomy(Tool.objToString(map2.get("EconomyName")));
		setD(player.getName(), myPlayer.getMoney(), itemList.getName(item, true, null), item.getId(), item.getDamage(),
				Money, item.getCount(), ByPlayer = Tool.objToString(map2.get("Player")), economy.getEconomyName());
	}

	@Override
	public boolean MakeMain() {
		SimpleForm form = new SimpleForm(getID(), getTitle(), getContent());
		listKey.add("b");
		form.addButton(getString("Buy"));
		if (myPlayer.isAdmin() || ByPlayer.equals(player.getName())) {
			listKey.add("d");
			form.addButton(getString("del"));
		}
		listKey.add("sd");
		form.addButton(getString("ShowData"));
		listKey.add("c");
		form.addButton(getBack());
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		switch (listKey.get(getSimple(data).getClickedButtonId())) {
		case "sd":
			return setForm(new ShowItemData(player, this, item)).make();
		case "d":
			config.remove(Key);
			player.sendMessage(getString("delOK"));
			return config.save() && isBack();
		case "b":
			if (ByPlayer == null) {
				player.sendMessage(getString("ByPlayerNull"));
				return MakeMain();
			}
			if (ByPlayer.equals(player.getName())) {
				player.sendMessage(getString("oneself"));
				return isBack();
			}
			if (economy.allowArrears() || economy.getMoney(player) >= Money) {
				config.remove(Key);
				MyPlayer.sendMessage(ByPlayer, getString("ByBuy"));
				economy.addMoney(ByPlayer, Money);
				economy.reduceMoney(player, Money);
				player.sendMessage(getString("BuyOK"));
				player.getInventory().addItem(item);
			} else
				player.sendMessage(getString("notMoney"));
		default:
			return config.save() && isBack();
		}
	}
}
