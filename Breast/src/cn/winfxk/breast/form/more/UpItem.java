package cn.winfxk.breast.form.more;

import java.util.HashMap;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.item.Item;
import cn.nukkit.utils.Config;
import cn.winfxk.breast.form.FormBase;
import cn.winfxk.breast.form.MainForm;
import cn.winfxk.breast.money.MyEconomy;
import cn.winfxk.breast.tool.CustomForm;
import cn.winfxk.breast.tool.Tool;

/**
 * 物品【选择完成 准备上架
 * 
 * @Createdate 2020/05/13 14:19:38
 * @author Winfxk
 */
public class UpItem extends FormBase {
	private Item item;
	private int Count = 0;
	private Config config;
	private Map<String, Object> map;
	private MyEconomy economy;
	private double UpMoney;

	public UpItem(Player player, FormBase upForm, int Index) {
		super(player, upForm);
		item = player.getInventory().getContents().get(Index);
		for (Item item : player.getInventory().getContents().values()) {
			if (item.equals(item, true, true))
				Count += item.getCount();
		}
		economy = ac.getEconomyManage().getEconomy(ac.getConfig().getString("手续费货币"));
		UpMoney = ac.getConfig().getDouble("上架手续费");
		config = ac.getShopConfig();
		map = config.getAll();
		setK("{UpMoney}", "{Player}", "{Money}", "{ItemName}", "{ItemID}", "{ItemDamage}", "{ItemPatn}", "{ItemCount}");
	}

	@Override
	public boolean MakeMain() {
		setD(UpMoney, player.getName(), myPlayer.getMoney(), itemList.getName(item), item.getId(), item.getDamage(),
				itemList.getPath(item), Count);
		if (item.getId() == 0) {
			player.sendMessage(getString("Air"));
			return isBack();
		}
		if (economy == null || !economy.isEnabled()) {
			player.sendMessage(getString("EconomyError"));
			return isBack();
		}
		CustomForm form = new CustomForm(getID(), getTitle());
		form.addLabel(getContent());
		form.addSlider(getString("SelectCount"), 1, Count, 1, item.getCount());
		form.addInput(getString("InputMoney"), "", getString("InputMoney"));
		form.addDropdown(getString("SelectEconomy"), ac.getEconomyManage().getEconomy());
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		FormResponseCustom d = getCustom(data);
		String string = d.getInputResponse(2);
		double Money = 0;
		if (string == null || string.isEmpty() || !Tool.isInteger(string) || (Money = Tool.objToDouble(string)) <= 0) {
			player.sendMessage(getString("notUInputMoney"));
			return MakeMain();
		}
		if (UpMoney > 0)
			if (economy.allowArrears() || economy.getMoney(player) >= UpMoney) {
				economy.reduceMoney(player, UpMoney);
				player.sendMessage(getString("reduceMoney"));
			} else {
				player.sendMessage(getString("NotMoney"));
				return isBack();
			}
		int Count = Tool.ObjToInt(d.getSliderResponse(1));
		string = d.getDropdownResponse(3).getElementContent();
		item.setCount(Count);
		Map<String, Object> iMap = new HashMap<>();
		iMap.put("Player", player.getName());
		iMap.put("Item", Tool.saveItem(item));
		iMap.put("Money", Money);
		iMap.put("EconomyName", string);
		iMap.put("Date", Tool.getDate() + " " + Tool.getTime());
		iMap.put("Count", Count);
		String Key = getKey(1);
		iMap.put("Key", Key);
		player.getInventory().remove(item);
		config.set(Key, iMap);
		player.sendMessage(getString("UPOK"));
		return config.save() && setForm(new MainForm(player, null)).make();
	}

	/**
	 * 获取一个不重复的Key
	 * 
	 * @param JJLength
	 * @return
	 */
	private String getKey(int JJLength) {
		String string = "";
		for (int i = 0; i < JJLength; i++)
			string += Tool.getRandString();
		if (map.containsKey(string))
			return getKey(JJLength + 1);
		return string;
	}
}
