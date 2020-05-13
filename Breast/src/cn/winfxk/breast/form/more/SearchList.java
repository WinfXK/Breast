package cn.winfxk.breast.form.more;

import java.util.HashMap;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.winfxk.breast.form.FormBase;
import cn.winfxk.breast.tool.EnchantList;
import cn.winfxk.breast.tool.SimpleForm;
import cn.winfxk.breast.tool.Tool;

/**
 * @Createdate 2020/05/13 13:39:33
 * @author Winfxk
 */
public class SearchList extends FormBase {
	private String Keyword;
	private boolean SearchName, SearchID, SearchDamage, SearchLore, SearchEnchant;
	private Map<String, Object> all;

	public SearchList(Player player, FormBase upForm, String Keyword, boolean SearchName, boolean SearchID,
			boolean SearchDamage, boolean SearchLore, boolean SearchEnchant) {
		super(player, upForm);
		this.Keyword = Keyword;
		this.SearchDamage = SearchDamage;
		this.SearchEnchant = SearchEnchant;
		this.SearchID = SearchID;
		this.SearchLore = SearchLore;
		this.SearchName = SearchName;
		all = ac.getShopConfig().getAll();
	}

	@Override
	public boolean MakeMain() {
		SimpleForm form = new SimpleForm(getID(), getTitle(), getContent());
		Map<String, Object> map;
		Item item;
		String string;
		boolean iscontinue = false;
		EnchantList enchantList = ac.getEnchants();
		for (Object obj : all.values()) {
			map = obj != null && obj instanceof Map ? (HashMap<String, Object>) obj : new HashMap<>();
			if (map.size() <= 0)
				continue;
			item = Tool.loadItem((Map<String, Object>) map.get("Item"));
			string = itemList.getName(item);
			if (SearchName && (string.toLowerCase().equals(Keyword.toLowerCase())

					|| string.toLowerCase().contains(Keyword.toLowerCase()))) {
				addButton(form, map, item);
				continue;
			}
			if (SearchDamage && SearchID && (itemList.objToID(Keyword, true, -1008611) == item.getId())) {
				addButton(form, map, item);
				continue;
			}
			if (SearchID && Tool.isInteger(Keyword) && Tool.ObjToInt(Keyword) == item.getId()) {
				addButton(form, map, item);
				continue;
			}
			if (SearchLore) {
				for (String s : item.getLore()) {
					if (s.toLowerCase().equals(Keyword.toLowerCase())
							|| s.toLowerCase().contains(Keyword.toLowerCase())) {
						iscontinue = true;
						addButton(form, map, item);
						break;
					}
				}
				if (iscontinue)
					continue;
			}
			if (SearchEnchant) {
				for (Enchantment enchantment : item.getEnchantments()) {
					string = enchantList.getName(enchantment);
					if ((Tool.isInteger(Keyword) && Tool.ObjToInt(Keyword) == enchantment.getId())
							|| (string.toLowerCase().equals(Keyword.toLowerCase())
									|| string.toLowerCase().contains(Keyword.toLowerCase()))) {
						iscontinue = true;
						addButton(form, map, item);
						break;
					}
				}
				if (iscontinue)
					continue;
			}
		}
		form.addButton(getBack());
		form.sendPlayer(player);
		return true;
	}

	/**
	 * 添加项目
	 * 
	 * @param form 界面对象
	 * @param map  数据对象
	 * @param item 物品对象
	 */
	private void addButton(SimpleForm form, Map<String, Object> map, Item item) {
		String[] ShopItemK = { "{Player}", "{Money}", "{ItemName}", "{ItemID}", "{ItemDamage}", "{ItemPrice}",
				"{ItemCount}", "{ByPlayer}", "{EconomyName}" };
		form.addButton(getString("ShopItem", ShopItemK,
				new Object[] { player.getName(), myPlayer.getMoney(), itemList.getName(item, true, null), item.getId(),
						item.getDamage(), map.get("Money"), item.getCount(), map.get("Player"),
						map.get("EconomyName") }));
		listKey.add(Tool.objToString(map.get("Key")));
	}

	@Override
	public boolean disMain(FormResponse data) {
		int ID = getSimple(data).getClickedButtonId();
		if (listKey.size() < ID)
			return setForm(new ClickItem(player, this, listKey.get(ID))).make();
		return isBack();
	}
}
