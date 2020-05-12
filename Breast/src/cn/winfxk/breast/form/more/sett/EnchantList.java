package cn.winfxk.breast.form.more.sett;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.breast.form.FormBase;
import cn.winfxk.breast.form.more.sett.ent.ClickItem;
import cn.winfxk.breast.form.more.sett.ent.addItem;
import cn.winfxk.breast.tool.SimpleForm;

/**
 * 显示当前服务器已经支持了的附魔
 * 
 * @Createdate 2020/05/12 11:32:13
 * @author Winfxk
 */
public class EnchantList extends FormBase {
	private Map<String, Object> map;
	private List<String> more = new ArrayList<>();

	public EnchantList(Player player, FormBase upForm) {
		super(player, upForm);
		map = ac.getEnchantListConfig().getAll();
	}

	@Override
	public boolean MakeMain() {
		SimpleForm form = new SimpleForm(getID(), getTitle(), getContent());
		Map<String, Object> item;
		String[] KK = { "{Player}", "{Money}", "{EnchantName}", "{EnchantID}" };
		for (String Key : map.keySet()) {
			item = map.get(Key) != null && map.get(Key) instanceof Map ? (HashMap<String, Object>) map.get(Key)
					: new HashMap<>();
			if (item.size() <= 0)
				continue;
			form.addButton(getString("EnchantItem", KK,
					new Object[] { player.getName(), myPlayer.getMoney(), item.get("Name"), item.get("ID") }));
			listKey.add(Key);
		}
		more.add("add");
		form.addButton(getString("addEnchant"));
		more.add("bc");
		form.addButton(getBack());
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		int ID = getSimple(data).getClickedButtonId();
		if (listKey.size() < ID)
			return setForm(new ClickItem(player, this, listKey.get(ID))).make();
		switch (more.get(ID - listKey.size())) {
		case "add":
			return setForm(new addItem(player, this)).make();
		default:
			return upForm == null ? true : setForm(upForm).make();
		}
	}
}
